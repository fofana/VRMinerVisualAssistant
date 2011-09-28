/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.xml;


import VisualAssistantFDM.io.VisualAssistantXMLReader;
import VisualAssistantFDM.io.VisualAssistantXMLStructure;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.jdom.Element;
import vrminerlib.utils.LoadingBox;
import vrminerlib.utils.MessageBox;

/**
 *
 * @author Abdelheq
 */

public class VisuXMLReader extends VisualAssistantXMLReader {

    private int nbElement;
    private String fileToRead;
    private ArrayList<String> vectNumParam = new ArrayList();
    private ArrayList<String> vectTextParam = new ArrayList<String>();
    private ArrayList<String> vectStrParam = new ArrayList<String>();
    private ArrayList vectSndParam = new ArrayList();
    private ArrayList vectPictParam = new ArrayList();
    private ArrayList vectParam = new ArrayList();
    private ArrayList vectLinkParam = new ArrayList<String>();
    private ArrayList listTypeVisu = new ArrayList();
    private ArrayList shape = new ArrayList();
    private Map<String, Float> mapCoordMin;
    private Map<String, Float> mapCoordMax;
    private org.jdom.Document document;
    private Element racine;
    private Element n3dConfigElement;
    private LoadingBox loading;
    private boolean parsed = false;
    private int numberProfil;
    private String SprofilDefaut = "profil0";
    private String nameFile;

    public VisuXMLReader(String fileToRead) {
        super(fileToRead);
        this.fileToRead = fileToRead;
        //n3dConfigElement = getXmlVisualisationsElement().getChild(NUAGE3D.NUAGE3D_NAME);
        n3dConfigElement = getxmlInterGeneticAlgorithmElement().getChild(NUAGE3D.NUAGE3D_NAME);
    }

    public void readXmlFile() {

        RafraichissementFichier();

        //On initialise un nouvel element racine avec l'element racine du document.
        racine = document.getRootElement();

        List listAtt = getXmlStructureElement().getChildren(VisualAssistantXMLStructure.ATTRIBUTE_ELEMENT_NAME);
        Iterator m = listAtt.iterator();
        while (m.hasNext()) {
            //Tant que attribute a des fils, on regarde ici si le type de l'attribut est numerique
            //ou chaine ,ou imageURL, ou autre...
            Element courant = (Element) m.next();
            if (VisualAssistantXMLStructure.NUMERIC_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectNumParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }
            if (VisualAssistantXMLStructure.TEXT_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectTextParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }
            if (VisualAssistantXMLStructure.SYMBOLIC_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectStrParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
                //vectPictParam.add(courant.getChild(VRMXML.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }
            if (VisualAssistantXMLStructure.IMAGE_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectPictParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }
            if (VisualAssistantXMLStructure.SOUND_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectSndParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }
            if (VisualAssistantXMLStructure.LINK_TYPE_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME).getValue()) && !VisualAssistantXMLStructure.DATUM_ID_NAME.equals(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue())) {
                vectLinkParam.add(courant.getChild(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
            }

        }

        for (int j = 0; j < vectNumParam.size(); j++) {
            vectParam.add(vectNumParam.get(j));
        }
        for (int j = 0; j < vectStrParam.size(); j++) {
            vectParam.add(vectStrParam.get(j));
        }
        for (int j = 0; j < vectPictParam.size(); j++) {
            vectParam.add(vectPictParam.get(j));
        }
        for (int j = 0; j < vectSndParam.size(); j++) {
            vectParam.add(vectSndParam.get(j));
        }
        for (int j = 0; j < vectLinkParam.size(); j++) {
            vectParam.add(vectLinkParam.get(j));
        }
        /*Ajouter pour le type texte des attributs de données*/
        for (int j = 0; j < vectTextParam.size(); j++) {
            vectParam.add(vectTextParam.get(j));
        }

        //On cree une Liste contenant tous les noeuds "visualisation" de l'Element racine
        //List listVisu = racine.getChildren(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        List listVisu = racine.getChildren(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);

        Iterator n = listVisu.iterator();
        int a = 0;
        while (n.hasNext()) {
            Element visualisation = (Element) n.next();
            visualisation = (Element) listVisu.get(a);
            a++;
            List list = visualisation.getChildren();
            Iterator o = list.iterator();

            while (o.hasNext()) {
                Element type = (Element) o.next();
                listTypeVisu.add(type.getName());

            }
            System.out.println("visualizations dispo: " + getTypeVisu());
        }

        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = getXmlDataElement();
        setNbElement(datum.getChildren().size());
        System.out.println("datum=" + getNbElement());

        /**************************************/
    }

    public void parseData() {
        System.out.println("parsedata");
        int id;
        float[] tabCoord = new float[vectNumParam.size()];
        mapCoordMax = new HashMap<String, Float>();//new float[vectNumParam.size()];
        mapCoordMin = new HashMap<String, Float>();//float[vectNumParam.size()];
        for (String currentAtt : vectNumParam) {
            mapCoordMax.put(currentAtt, -1000f);//[a] = -1000f;
            mapCoordMin.put(currentAtt, 1000f);//[a] = 1000f;
        }


        //On recree l'Element courant a chaque tour de boucle afin de
        //pouvoir utiliser les methodes propres aux Element comme :
        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = getXmlDataElement();
        List listDatum = datum.getChildren(VisualAssistantXMLStructure.DATUM_ELEMENT_NAME);
        Iterator j = listDatum.iterator();
        int u = 0;
        while (j.hasNext()) {
            Element courant = (Element) j.next();
            //On affiche le nom de l'element courant
            id = Integer.parseInt(courant.getChild(VisualAssistantXMLStructure.DATUM_ID_NAME).getValue());
            
            //Barre de progression pour la lecture:
            u++;
            getLoading().getProgressBar().setValue((int) (u / (float) (getNbElement()) * 100));
            getLoading().setAction("Lecture de l'element " + id);

            for (int k = 0; k < vectNumParam.size(); k++) {
                tabCoord[k] = Float.parseFloat(courant.getChild("" + vectNumParam.get(k)).getValue());
                mapCoordMin.put(vectNumParam.get(k), (float) Math.min(tabCoord[k], mapCoordMin.get(vectNumParam.get(k))));//[k] = (float) Math.min(tabCoord[k], mapCoordMin[k]);
                mapCoordMax.put(vectNumParam.get(k), (float) Math.max(tabCoord[k], mapCoordMax.get(vectNumParam.get(k))));//[k] = (float) Math.min(tabCoord[k], mapCoordMin[k]);
                //mapCoordMax[k] = (float) Math.max(tabCoord[k], mapCoordMax[k]);
            }
        }

    }

    public Map<String, Float> getCoordMax() {
        return mapCoordMax;
    }

    public Map<String, Float> getCoordMin() {
        return mapCoordMin;
    }

    public String getFileToRead() {
        return fileToRead;
    }

    public String getNomFile() {
        return nameFile;
    }

    public ArrayList getVectParam() {
        return vectParam;
    }

    public ArrayList getTypeVisu() {
        return listTypeVisu;
    }

    public int getNbElement() {
        return nbElement;
    }

    public void setNbElement(int nbElement) {
        this.nbElement = nbElement;
    }

    public void setFileToRead(String fileToRead) {
        this.fileToRead = fileToRead;
    }

    public void setNameFile(String nomFile) {
        this.nameFile = nomFile;
    }

    public ArrayList getVectNumParam() {
        return vectNumParam;
    }

    public ArrayList getVectStrParam() {
        return vectStrParam;
    }

    public void setVectNumParam(ArrayList vectNumParam) {
        this.vectNumParam = vectNumParam;
    }




    /*Ajouter pour le type texte des attributs de données*/
    public ArrayList<String> getVectTextParam() {
        return vectTextParam;
    }

    public void setVectTextParam(ArrayList<String> vectTextParam) {
        this.vectTextParam = vectTextParam;
    }
    /**/




    public void setTypeVisu(ArrayList typeVisu) {
        this.listTypeVisu = typeVisu;
    }

    public void setShape(ArrayList shape) {
        this.shape = shape;
    }

    public ArrayList getStringParam(String name) {
        int id;

        ArrayList listNom = new ArrayList();
        ArrayList listValeur = new ArrayList();
        ArrayList listCoordonne = new ArrayList();

        listCoordonne.add(listNom);
        listCoordonne.add(listValeur);

        //On initialise un nouvel element racine avec l'element racine du document.
        racine = document.getRootElement();

        //On cree une Liste contenant tous les noeuds "data" de l'Element racine
        Element data = getXmlDataElement();// racine.getChild("data");

        List listDatum = data.getChildren();

        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
            Element param = (Element) j.next();
            id = Integer.parseInt(param.getChild(VisualAssistantXMLStructure.DATUM_ID_NAME).getValue());

            if (id == Integer.parseInt(name)) {
                for (int k = 0; k < vectParam.size(); k++) {
                    listNom.add(param.getChild("" + vectParam.get(k)).getName().toString());
                    listValeur.add(param.getChild("" + vectParam.get(k)).getValue().toString());
                }

                break;
            }
        }

        return listCoordonne;
    }

     public String getAttrValue(String attName, String idStr) {
        String value = "";
        int idIndividu = Integer.parseInt(idStr);
        //On initialise un nouvel element racine avec l'element racine du document.
        racine = document.getRootElement();

        //On cree une Liste contenant tous les noeuds "data" de l'Element racine
        Element data = getXmlDataElement();//(Element) racine.getChild("data");

        List listDatum = data.getChildren();
        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
            Element param = (Element) j.next();
            int id = Integer.parseInt(param.getChild(VisualAssistantXMLStructure.DATUM_ID_NAME).getValue());

            if (id == idIndividu) {
                value = param.getChild(attName).getValue();
                break;
            }
        }

        return value;
    }

    public ArrayList getArrayList(String profilSelected, String list_media) {
        ArrayList list = new ArrayList();

        //On initialise un nouvel element racine avec l'element racine du document.
        racine = document.getRootElement();

        //On cree une Liste contenant tous les noeuds "data" de l'Element racine
        //Element visualisation = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        Element visualisation = (Element) racine.getChild(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
        
        Element typeVisu = (Element) visualisation.getChild(NUAGE3D.NUAGE3D_NAME);

        int number = typeVisu.getChildren().size();

        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");

        if (profilDefaut != null) {
            SprofilDefaut = profilDefaut.getText().toString();
            //Dans le comptage du nombre de profil, on elimine le profil par defaut
            number--;
        } else {
            SprofilDefaut = "profil0";
        }

        setNumberProfil(number);

        //Si il n'y a pas de profil selectionne, on est au demarrage et on select le profil par defaut
        if (profilSelected.length() == 0) {
            profilSelected = SprofilDefaut;
        }

        Element profil = (Element) typeVisu.getChild(profilSelected);

        List listParam = profil.getChildren();

        if (!listParam.isEmpty()) {
            Iterator j = listParam.iterator();
            while (j.hasNext()) {
                //On affiche le nom de l'element courant
                Element param = (Element) j.next();

                if (list_media.equals(param.getName())) {
                    List list_med = param.getChildren();
                    if (!list_med.isEmpty()) {
                        Iterator k = list_med.iterator();
                        while (k.hasNext()) {
                            Element el_list = (Element) k.next();
                            String val = el_list.getValue();
                            list.add(val);
                        }
                    }
                }
            }
        }
        return list;
    }

    public String getParam(String profilSelected, String name) {
        String value = "";

        //On initialise un nouvel element racine avec l'?l?ment racine du document.
        racine = document.getRootElement();

        //On cr?e une Liste contenant tous les noeuds "data" de l'Element racine

        //Element visualisation = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        Element visualisation = (Element) racine.getChild(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
        
        Element typeVisu = (Element) visualisation.getChild(NUAGE3D.NUAGE3D_NAME);
        if (typeVisu == null) {
            return value;
        }
        int number = typeVisu.getChildren().size();

        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");

        if (profilDefaut != null) {
            SprofilDefaut = profilDefaut.getText().toString();
            //Dans le comptage du nombre de profil, on elimine le profil par defaut
            number--;
        } else {
            SprofilDefaut = "profil0";
        }

        setNumberProfil(number);

        //Si il n'y a pas de profil selectionn?, on est au d?marrage et on select le profil par defaut 
        if (profilSelected.isEmpty()) {
            profilSelected = SprofilDefaut;
        }

        Element profil = (Element) typeVisu.getChild(profilSelected);

        List listParam = profil.getChildren();

        if (!listParam.isEmpty()) {
            Iterator j = listParam.iterator();
            while (j.hasNext()) {
                //On affiche le nom de l'element courant
                Element param = (Element) j.next();

                if (name.equals(param.getName())) {
                    value = param.getValue();
                }

            }
        }
        return value;
    }

    public String getNomProfil(String profilSelected) {
        //On initialise un nouvel ?l?ment racine avec l'?l?ment racine du document.
        racine = document.getRootElement();

        //On cr?e une Liste contenant tous les noeuds "visualisation" de l'Element racine
        //Element visualisation = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        Element visualisation = (Element) racine.getChild(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
        
        Element typeVisu = (Element) visualisation.getChild(NUAGE3D.NUAGE3D_NAME);

        Element profil = (Element) typeVisu.getChild(profilSelected);

        String value = profil.getAttributeValue("valeur");

        if (value == null) {
            value = "";
        }

        return value;
    }

    public void RafraichissementFichier() {

        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML
            document = new VisualAssistantXMLReader(fileToRead).getXMLDocument();
        } catch (Exception e) {
            System.err.println(e + " Erreur de lecture du xml");
            getLoading().dispose();
            new MessageBox("Erreur de lecture du fichier xml", e.getMessage(), 1);
            //frame.dispose();
        }
    }

    public ArrayList getVectSndParam() {
        return vectSndParam;
    }

    public ArrayList getVectPictParam() {
        return vectPictParam;
    }

    public void setNumberProfil(int _number) {
        numberProfil = _number;
    }

    public int getNumberProfil() {
        return numberProfil;
    }

    public String getProfilDefaut() {
        return SprofilDefaut;
    }

    public boolean getParsed() {
        return parsed;
    }

    public void setParsed(boolean b) {
        parsed = true;
    }

    public LoadingBox getLoading() {
        if (loading == null) {
            loading = new LoadingBox("Chargement ...", 0, 100);
        }
        return loading;
    }

    public ArrayList getVectLinkParam() {
        return vectLinkParam;
    }

    public void getAllValuesForAttribute(String nomAttribut, TreeSet<String> set) {
        set.clear();
        Element eltData = getXmlDataElement();// (Element) racine.getChild("data");
        List listEltDatum = eltData.getChildren();
        Iterator it = listEltDatum.iterator();
        while (it.hasNext()) {
            Element eltDatum = (Element) it.next();
            Element eltAttribut = eltDatum.getChild(nomAttribut);
            set.add(eltAttribut.getText());
        }
    }

    public void getMapAttributePalette(String nomProfil, HashMap<String, HashMap> map) {
        map.clear();
        for (String nomAttribut : vectStrParam) {
            HashMap<String, Color> mapValuesAttributeToColor = new HashMap<String, Color>(10);
            getMapValuesAttributeToColor(nomProfil, nomAttribut, mapValuesAttributeToColor);
            map.put(nomAttribut, mapValuesAttributeToColor);
        }
    }

    public void getMapValuesAttributeToColor(String nomProfil, String nomAttribut, HashMap<String, Color> map) {
        try {
            map.clear();
            //Element eltVisualisation = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
            Element eltVisualisation = (Element) racine.getChild(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
            
            Element eltTypeVisu = (Element) eltVisualisation.getChild(NUAGE3D.NUAGE3D_NAME);
//            System.out.println("Nom profil : " + nomProfil);
            Element eltProfil = (Element) eltTypeVisu.getChild(nomProfil);
            Element eltColorParam = (Element) eltProfil.getChild("colorParam");
            if (eltColorParam == null) {
                eltColorParam = new Element("colorParam");
            }
//            System.out.println("Nom attribut : " + nomAttribut);
            Element eltAttribut = (Element) eltColorParam.getChild(nomAttribut);
            if (eltAttribut == null) {
                eltAttribut = new Element(nomAttribut);
            }

            // Si la liste de couleurs est non vide
            if (!eltAttribut.getChildren().isEmpty()) {
//                System.out.println("Palette de couleur trouv?e.");
                Iterator it = eltAttribut.getChildren().iterator();
                while (it.hasNext()) {
                    Element color = (Element) it.next();
                    String[] tabColors = color.getText().split(",");
                    int red = Integer.parseInt(tabColors[0]);
                    int green = Integer.parseInt(tabColors[1]);
                    int blue = Integer.parseInt(tabColors[2]);
                    // On ajoute la valeur de l'attribut et sa couleur correspondante ? la map fournie en entr?e
                    map.put(color.getAttribute("for").getValue(), new Color(red, green, blue));
                }
            } else {
//                System.out.println("Palette de couleur non trouv?e.");
                // Si il n'y a pas de table de couleur associ?e ? cet attribut alors on va en cr?er une
                TreeSet<String> setValeurAttribut = new TreeSet<String>();
                getAllValuesForAttribute(nomAttribut, setValeurAttribut);
                int size = setValeurAttribut.size();
                int indice = 0;
                Iterator it = setValeurAttribut.iterator();
                while (it.hasNext()) {
                    indice++;
                    Color couleur = Color.getHSBColor((float) indice / (float) size, 1.0f, 0.9f);
                    String valeurAttribut = (String) it.next();
                    map.put(valeurAttribut, couleur);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStereoEnabled(String profileName) {
        return Boolean.valueOf(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IS_STEREO_NAME));
    }

    public boolean isClusteringEnabled(String profileName) {
        return Boolean.valueOf(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IS_CLUSTERING_NAME));
    }

    public boolean isClusteringObjectVisible(String profileName) {
        return Boolean.valueOf(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IS_CLUSTERING_OBJECT_VISIBLE_NAME));
    }

    public boolean isSphereLightningEnabled(String profileName) {
        return Boolean.valueOf(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IS_SPHERE_LIGHTNING_NAME));
    }

    public boolean isGrid(String profileName) {
        return Boolean.valueOf(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IS_GRID_NAME));
    }

    public float getObjectSize2(String profileName) {
        return Float.parseFloat(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.OBJ_SIZE_2_NAME)) / 100f;
    }

    public float getObjectSize3(String profileName) {
        return Float.parseFloat(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.OBJ_SIZE_3_NAME)) / 100f;
    }

    public float getClusteringFactor(String profileName) {
        return Float.parseFloat(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLUSTERING_FACTOR_NAME)) / 100f;
    }

    public float getObjectSize(String profileName) {
        return Float.parseFloat(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.SIZE_NAME)) / 100f;
    }

    public int getClusteringAttribute(String profileName) {
        return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLUSTERING_ATTRIBUTE_NAME));
    }

    public int getClusteringMethod(String profileName) {
        return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLUSTERING_METHOD_NAME));
    }

    public int getClusteringObject(String profileName) {
        return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLUSTERING_OBJECT_NAME));
    }

    public int getVoice(String profileName) {
        return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.VOICE_NAME));
    }

    public int getServerPort(String profileName) {
        try {
            return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.SERVER_PORT_NAME));
        } catch (NumberFormatException nfe) {
            return 1000;
        }
    }

    public int getCamera(String profileName) {
        return Integer.parseInt(n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CAMERA_NAME));
    }

    public String getServerIp(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.SERVER_IP_NAME);
    }

    public String getAttribute1(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.ATTRIBUTE_1_NAME);
    }

    public String getAttribute2(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.ATTRIBUTE_2_NAME);
    }

    public String getAttribute3(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.ATTRIBUTE_3_NAME);
    }

    public String getAttribute4(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.ATTRIBUTE_4_NAME);
    }

    public String getImageAttribute(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.IMAGE_ATTRIBUTE_NAME);
    }

    public String getSynthAttribute(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.SYNTH_ATTRIBUTE_NAME);
    }

    public String getShape(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.SHAPE_NAME);
    }

    public String getClassVar(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLASS_NAME);
    }

    public String getClassVar1(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.CLASS1_NAME);
    }

    public String getXAxis(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.X_AXIS_NAME);
    }

    public String getYAxis(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.Y_AXIS_NAME);
    }

    public String getZAxis(String profileName) {
        return n3dConfigElement.getChild(profileName).getChildText(NUAGE3D.Z_AXIS_NAME);
    }
}

