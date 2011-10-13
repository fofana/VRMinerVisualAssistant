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
    public Document document;
    public Element racine;
    private String xml;
    private List<Visualisation> listAttribut;
    private List<Visualisation> listAttributNumeric;

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

    public List<Visualisation> getListAttribut(){
            return listAttribut;
    }
    public List<Visualisation> getListAttributNumeric(){
            return listAttributNumeric;
    }
    /**
     * Reads the contents of the file and exposes them vis the ParallelSpaceModel
     * interface of the class. String values are stripped out of the model and
     * set as record labels.
     */
    public void readXMLContents(String xml) throws IOException, Exception{
        this.xml = xml;
        fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_START, 0.0f, "loading file"));

        URLConnection conn = url.openConnection();
        conn.connect();

        filesize = conn.getContentLength();
        System.out.println("filesize: " + filesize);

        InputStreamReader in = new InputStreamReader(conn.getInputStream());

        readStructure();
        setAxes();
        readData();

        fireProgressEvent(new ProgressEvent(this, ProgressEvent.PROGRESS_FINISH, 1.0f, "loading file"));

    }

    /**
     * Reads the first data line of the file and sets up the number of dimensions.
     */
//    protected void readStructure(Reader in) throws IOException{
//        String line = readLine(in);
//        bytesRead += line.length();
//
//        if (line.indexOf(' ') != -1)
//            tempNumDimensions = Integer.parseInt(line.substring(0,line.indexOf(' ')));
//        else
//            tempNumDimensions = Integer.parseInt(line);
//
//        //System.out.println("num dimensions: " + tempNumDimensions);
//
//        isStringLabel = new boolean[tempNumDimensions];
//        for (int i=0; i<tempNumDimensions; i++){
//            isStringLabel[i] = false;
//        }
//    }
public void readStructure() throws Exception{
        List<Visualisation> xmlListe = new ArrayList<Visualisation>();
        SAXBuilder sxb = new SAXBuilder();
        File xmlFile = new File(xml);
        document = sxb.build(xmlFile);
        racine = (Element) document.getRootElement();
        List listAttributes = racine.getChild("structure").getChildren("attribute");
        bytesRead = listAttributes.size();
        Iterator i = listAttributes.iterator();

       while(i.hasNext()) {
            Visualisation methode = new Visualisation();
            Element courant = (Element)i.next();
            methode.setName(courant.getChild("name").getText());
            methode.setType(courant.getChild("type").getText());
            methode.setImportance(Integer.parseInt(courant.getChild("importance").getText()));//Integer.valueOf(courant.getChild("importance").getText())
            xmlListe.add(methode);
        }
        tempNumDimensions = xmlListe.size();
        listAttribut = xmlListe;        
    }
    /**
     * Reads the header lines and sets up the variable types.
     */
    protected void setAxes() throws IOException{
        int i;
        int j=0;
        String line;
        listAttributNumeric = new ArrayList<Visualisation>();
        int numericDimensions = tempNumDimensions;
        Vector labels = new Vector();
        for (Visualisation visualisation : listAttribut) {
             String label = visualisation.getName();
             String type = visualisation.getType();
            if (type.equals("numeric")){
                listAttributNumeric.add(visualisation);
                labels.addElement(label);
                System.out.println("Axis " + j++ + " label " + label + " type " + type + ".");
            }
            else
                numericDimensions--;

        }

        this.initNumDimensions(numericDimensions);
        String tempLabels[] = (String[])labels.toArray(new String[numericDimensions]);
        this.setAxisLabels(tempLabels);
    }
public void readData() throws JDOMException, IOException{
        //int i, j, s;

        String label;

        float curVal[];
        SAXBuilder sxb = new SAXBuilder();
        try {
        //On crée un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
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
                int k = 0;
                label = null;
                curVal = new float[this.numDimensions];
                Element courant = (Element) j.next();
                for (Visualisation visualisation : listAttribut) {
                   if( visualisation.getType().equals("numeric")){
                       try {
                            float val = Float.parseFloat(courant.getChild(visualisation.getName()).getText());
                            curVal[k++] = val;
                        }
                        catch (NumberFormatException nfe){
                            System.out.println("Invalid Number Format: " + nfe.getMessage() + " -> dicarding & setting 0.0f");
                            curVal[k++] = 0.0f;
                        }
                    }
                   else{
                        label = courant.getChild(visualisation.getName()).getText();
                   }

                }
                addRecord(curVal, label);
            }
        }
        }
       catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
       }
    }
//    /**
//     * Reads the data lines.
//     */
//    protected void readData(Reader in) throws IOException{
//        String line, value;
//        int i, j, s;
//
//        String label;
//
//        float curVal[];
//
//        while ((line = readLine(in)) != null){
//            curVal = new float[numDimensions];
//
//            j=0;
//            s=0;
//            label = null;
//
//            for (i = 0; i<tempNumDimensions; i++){
//                value = str.nextToken();
////                System.out.println("value " + i + ": " + value);
//
//                if (!isStringLabel[i]) {
//                    try {
//                        float val = Float.parseFloat(value);
//                        curVal[j++] = val;
//                    }
//                    catch (NumberFormatException nfe){
//                        System.out.println("Invalid Number Format: " + nfe.getMessage() + " -> dicarding & setting 0.0f");
//                        curVal[j++] = 0.0f;
//                    }
//                }
//                else {
//                    value = value.replace('_',' ');
//                    value = value.substring(0,1).toUpperCase() + value.substring(1);
//
//                    int spcidx = 0;
//                    while ((spcidx = value.indexOf(' ', spcidx+1)) != -1){
//                        value = value.substring(0,spcidx+1) + value.substring(spcidx+1,spcidx+2).toUpperCase() + value.substring(spcidx+2);
//                    }
//
//                    if (label == null) {
//                        label = stringLabels.elementAt(s++) + ": " + value;
//                    }
//                    else {
//                        label += "\n" + stringLabels.elementAt(s++) + ": " + value;
//                    }
//                }
//
//            }
//
//            addRecord(curVal, label);
//
//        }
//    }

    
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

            //f.readXMLContents();
        }
        catch (MalformedURLException e){
            System.out.println("malformed url!");
        }
        catch (IOException ex){
            System.out.println("IOException: " + ex.getMessage());
        }

    }
}
