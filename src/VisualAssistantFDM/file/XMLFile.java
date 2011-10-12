/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.file;

import VisualAssistantFDM.visualisation.ui.Appariement;
import VisualAssistantFDM.visualisation.ui.Normalisation;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.mediavirus.parvis.gui.ProgressEvent;
import org.mediavirus.parvis.gui.ProgressListener;
import org.mediavirus.parvis.model.SimpleParallelSpaceModel;
import vrminerlib.io.VRMXML;

/**
 *
 * @author fovea
 */
public class XMLFile extends SimpleParallelSpaceModel  {
 /** The url of the file. */
    URL url;

    private int tempNumDimensions;

    private int bytesRead = 0;
    private int filesize = 0;

    private Vector stringLabels = new Vector();
    private boolean isStringLabel[];

    private String name = "";
    private Document document;
    private Element racine;
    private List<Normalisation> listNormalisation;

    /**
     * Creates a new STFFile with the given url. The content is not read until
     * readContents() is called.
     *
     * @param url The url of the file to read.
     */
    public XMLFile(URL url) {
        this.url = url;
        name = url.getFile();
        name = name.substring(name.lastIndexOf('/') + 1);
    }

    /**
     * Returns the filename (without path).
     *
     * @return The filename.
     */
    public String getName(){
        return name;
    }
public List<Visualisation> getStructure(String xml) throws Exception{
        List<Visualisation> xmlListe = new ArrayList<Visualisation>();
        SAXBuilder sxb = new SAXBuilder();
        document = sxb.build(new File(xml));
        racine = (Element) document.getRootElement();
        List listAttributes = racine.getChild("structure").getChildren("attribute");
        Iterator i = listAttributes.iterator();

        while(i.hasNext()) {
            Visualisation methode = new Visualisation();
            Element courant = (Element)i.next();
            methode.setName(courant.getChild("name").getText());
            methode.setType(courant.getChild("type").getText());
            methode.setImportance(Integer.parseInt(courant.getChild("importance").getText()));//Integer.valueOf(courant.getChild("importance").getText())
            xmlListe.add(methode);
        }

        return xmlListe;
    }
public void getData(int indexProfil, File xml, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph) throws JDOMException, IOException{

        SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        float[] tabCoordMinListe;
        float[] tabCoordMaxListe;
        try {
        //On crée un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
        for(int l=0; l<ResultMEC.size(); l++){
            System.out.println(ResultMEC.get(l).getName_data()+" -:- "+ResultMEC.get(l).getName_v_data());
        }
        /*ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionnée pour les axes x, y et z */
        List<Appariement> list_numerique = new ArrayList<Appariement>();
        List<Appariement> list_symbolique = new ArrayList<Appariement>();
        List<Appariement> list_texte = new ArrayList<Appariement>();
        List<Appariement> list_image = new ArrayList<Appariement>();
        List<Appariement> list_link = new ArrayList<Appariement>();
        List<Appariement> list_sound = new ArrayList<Appariement>();
        List<Appariement> list_file = new ArrayList<Appariement>();
        List<Appariement> list_temporal = new ArrayList<Appariement>();
        float[] tabCoord = new float[list_numerique.size()];
        float[] tabCoordListe = new float[listNormalisation.size()];
        tabCoordMax = new float[list_numerique.size()];
        tabCoordMin = new float[list_numerique.size()];
        tabCoordMaxListe = new float[listNormalisation.size()];
        tabCoordMinListe = new float[listNormalisation.size()];
        for (int a = 0; a < list_numerique.size(); a++) {
        tabCoordMax[a] = -1000f;
        tabCoordMin[a] = 1000f;
        }
        for (int a = 0; a < listNormalisation.size(); a++) {
        tabCoordMaxListe[a] = -1000f;
        tabCoordMinListe[a] = 1000f;
        }
        List listData = racine.getChildren("data");
        //On crée un Iterator sur notre liste
        Iterator n = listData.iterator();
        while (n.hasNext()) {
            //On recrée l'Element courant à  chaque tour de boucle afin de
            //pouvoir utiliser les méthodes propres aux Element comme :
            //selectionner un noeud fils, modifier du texte, etc...
            Element datum = (Element) n.next();
            List listDatum = datum.getChildren("datum");
            Iterator j = listDatum.iterator();
            while (j.hasNext()) {
                Element courant = (Element) j.next();
                //On affiche le nom de l'element courant
                for (int k = 0; k < list_numerique.size(); k++) {
                        //ici on recupere les valeur max et min des attributs selectionnée pour les axes x, y et z
                        tabCoord[k] = Float.parseFloat(courant.getChild(""+list_numerique.get(k).getName_data()).getValue());
                        tabCoordMin[k] = (float) Math.min(tabCoord[k], tabCoordMin[k]);
                        tabCoordMax[k] = (float) Math.max(tabCoord[k], tabCoordMax[k]);
                }
                /* On remplie le vecteur de poids des valuers de normalisation des axes à utiliser dans l'actulaisation des visualisations avec l'AGI */
                for (int v = 0; v < listNormalisation.size(); v++) {
                        //ici on recupere les valeur max et min de tous les attributs de données pour les axes x, y et z
                        tabCoordListe[v] = Float.parseFloat(courant.getChild(""+listNormalisation.get(v).getNom()).getValue());
                        tabCoordMinListe[v] = (float) Math.min(tabCoordListe[v], tabCoordMinListe[v]);
                        tabCoordMaxListe[v] = (float) Math.max(tabCoordListe[v], tabCoordMaxListe[v]);
                }
            }
        }

        for(int i=0; i<listNormalisation.size(); i++){
                Normalisation result = new Normalisation();
                result.setNom(listNormalisation.get(i).getNom());
                result.setMin(tabCoordMinListe[i]);
                result.setMax(tabCoordMaxListe[i]);
                listNormalisation.add(i, result);
                listNormalisation.remove(i+1);
         }

    }
       catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }
    }
    /**
     * Reads the contents of the file and exposes them vis the ParallelSpaceModel
     * interface of the class. String values are stripped out of the model and
     * set as record labels.
     */
    public void readContents() throws IOException{

        fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_START, 0.0f, "loading file"));

        URLConnection conn = url.openConnection();
        conn.connect();

        filesize = conn.getContentLength();
        System.out.println("filesize: " + filesize);

        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        readFirstLine(in);
        readHeaderLines(in);
        readData(in);

        fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_FINISH, 1.0f, "loading file"));

    }

    /**
     * Reads the first data line of the file and sets up the number of dimensions.
     */
    protected void readFirstLine(Reader in) throws IOException{
        String line = readLine(in);
        bytesRead += line.length();

        if (line.indexOf(' ') != -1)
            tempNumDimensions = Integer.parseInt(line.substring(0,line.indexOf(' ')));
        else
            tempNumDimensions = Integer.parseInt(line);

        //System.out.println("num dimensions: " + tempNumDimensions);

        isStringLabel = new boolean[tempNumDimensions];
        for (int i=0; i<tempNumDimensions; i++){
            isStringLabel[i] = false;
        }
    }

    /**
     * Reads the header lines and sets up the variable types.
     */
    protected void readHeaderLines(Reader in) throws IOException{
        int i;
        int j=0;
        String line;

        int numDimensions = tempNumDimensions;
        Vector labels = new Vector();

        for (i=0; i<tempNumDimensions; i++){
            line = readLine(in);
            bytesRead += line.length();

            StringTokenizer str = new StringTokenizer(line);
            String label = str.nextToken();
            String type = str.nextToken();

            if (type.equalsIgnoreCase("string")){
                isStringLabel[i] = true;
                stringLabels.addElement(label);

                numDimensions--;
                //System.out.println("Recordlabel " + label);
            }
            else {
                labels.addElement(label);
                System.out.println("Axis " + j++ + " label " + label + " type " + type + ".");
            }

        }

        this.initNumDimensions(numDimensions);
        String tempLabels[] = (String[])labels.toArray(new String[numDimensions]);
        this.setAxisLabels(tempLabels);
    }

    /**
     * Reads the data lines.
     */
    protected void readData(Reader in) throws IOException{
        String line, value;
        int i, j, s;

        String label;

        float curVal[];

        while ((line = readLine(in)) != null){
            bytesRead += line.length();

            float progress = (float)bytesRead / filesize;
            fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_UPDATE, progress, "loading file"));

            StringTokenizer str = new StringTokenizer(line);
            curVal = new float[numDimensions];

            j=0;
            s=0;
            label = null;

            for (i = 0; i<tempNumDimensions; i++){
                value = str.nextToken();
//                System.out.println("value " + i + ": " + value);

                if (!isStringLabel[i]) {
                    try {
                        float val = Float.parseFloat(value);
                        curVal[j++] = val;
                    }
                    catch (NumberFormatException nfe){
                        System.out.println("Invalid Number Format: " + nfe.getMessage() + " -> dicarding & setting 0.0f");
                        curVal[j++] = 0.0f;
                    }
                }
                else {
                    value = value.replace('_',' ');
                    value = value.substring(0,1).toUpperCase() + value.substring(1);

                    int spcidx = 0;
                    while ((spcidx = value.indexOf(' ', spcidx+1)) != -1){
                        value = value.substring(0,spcidx+1) + value.substring(spcidx+1,spcidx+2).toUpperCase() + value.substring(spcidx+2);
                    }

                    if (label == null) {
                        label = stringLabels.elementAt(s++) + ": " + value;
                    }
                    else {
                        label += "\n" + stringLabels.elementAt(s++) + ": " + value;
                    }
                }

            }

            addRecord(curVal, label);

        }
    }

    /**
     * Reads on line, skipping comments and empty lines.
     */
    protected String readLine(Reader in) throws IOException{
        char buf[] = new char[128];
        int offset = 0;
        int ch;

        boolean skip = false;

        for (;;) {
            ch = in.read();

            if (ch == -1){
                break;
            }

            if (ch == '\n' || ch == '\r') {
                if (offset == 0 && !skip){
                    //skip empty line -> do nothing
                    skip = true;
//                    System.out.println("skipping line: empty");
                }

                if (skip){
                    // next line reached -> stop skipping
                    skip = false;
                }
                else {
                    // line finished -> break and return
                    break;
                }
            }
            else if(ch == '#' && offset == 0){
                // skip this line
                skip = true;
//                System.out.println("skipping line: comment");
            }
            else if (!skip){
                if (offset == buf.length) {
                    char tmpbuf[] = buf;
                    buf = new char[tmpbuf.length * 2];
                    System.arraycopy(tmpbuf, 0, buf, 0, offset);
                }
                buf[offset++] = (char) ch;
            }

        }

        if ((offset == 0) || skip){ //eof
            return null;
        }
        return String.copyValueOf(buf, 0, offset);
    }

    private Vector progressListeners = new Vector();

    /**
     * Method to add a ProgressListener to get notified of the loading progress.
     */
    public void addProgressListener(ProgressListener l){
        progressListeners.add(l);
    }

    /**
     * Remove a ProgressListener.
     */
    public void removeProgressListener(ProgressListener l){
        progressListeners.remove(l);
    }

    /**
     * Dispatches a ProgressEvent to all listeners.
     *
     * @param e The ProgressEvent to send.
     */
    protected void fireProgressEvent(ProgressEvent e){
        Vector list = (Vector)progressListeners.clone();
        for (int i=0; i<list.size(); i++){
            ProgressListener l = (ProgressListener)list.elementAt(i);
            l.processProgressEvent(e);
        }
    }

    /**
     * Main method for testing purposes.
     */
    public static void main(String args[]){
        try {
            XMLFile f = new XMLFile(new URL("file:///d:/uni/visualisierung/datasets/table1.stf"));

            f.readContents();
        }
        catch (MalformedURLException e){
            System.out.println("malformed url!");
        }
        catch (IOException ex){
            System.out.println("IOException: " + ex.getMessage());
        }

    }
}
