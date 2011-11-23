/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.ui;

import VisualAssistantFDM.io.LoadVisualizations;
import VisualAssistantFDM.visualisation.ui.Appariement;
import VisualAssistantFDM.visualisation.ui.Matching;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import vrminerlib.io.VRMXML;

/**
 *
 * @author fovea
 */
public  class Utils {

    Document  document;
    Element racine;
    private String filePathName;
    MainInterface mainInterface;
    public Utils(MainInterface mainInterface){
        this.mainInterface = mainInterface;
    }
    public void setMainInterface(MainInterface mainInterface){
        this.mainInterface = mainInterface;
    }
     public List<Appariement> GenerateMatchingWithProfil(int indiceVisualisation) throws Exception {

        //r�cuperer la description des attributs de donn�es à partir de la partie structure du fichier XML
        List<Visualisation> dataAttributeliste = new Matching().getListe(mainInterface.filePathName);
        //trier les attributs de donn�es selon leur type puis leur importance
        dataAttributeliste = new Matching().getListeTri(dataAttributeliste);
        //r�cuperer les attributs visuels depuis la base de donn�es pour chaque visualisation
        List<Visualisation> visualAttributeliste = new LoadVisualizations().getIdMethode(indiceVisualisation);

        List<Appariement> resultaMEC;
        if (getCurrentTypeVisu(indiceVisualisation).equalsIgnoreCase("CoordonneesParalleles"))
            resultaMEC = getMatchingParalleleCoordinate(indiceVisualisation, dataAttributeliste, visualAttributeliste);
        else
            //r�cuperer le matching attributs de donn�es / attributs visuels
            resultaMEC = getMatching(dataAttributeliste, visualAttributeliste);
        //initialiser le DefaultTableModel pour viusaliser le r�sultat du matching attributs de donn�es / attributs visuels

        return resultaMEC;

    }

    public String getCurrentTypeVisu(int profil){
        String methodeVisu = "";
        try {
            methodeVisu = new LoadVisualizations().getMethode(profil).get(0).getNom();
        } catch (Exception ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        String typeDeVisu = new StringTokenizer(methodeVisu, "_").nextToken();
        return typeDeVisu;

    }
      
    public List getProfilList(String XMLpath) throws Exception{
        List list_profil = null;
        try{
        SAXBuilder sxb = new SAXBuilder();
        //On cr�e un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(XMLpath);
        //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
        racine = document.getRootElement();
        Element visu = (Element) racine.getChild("visualizations");
        Element typeVisu = (Element) visu.getChild("nuage3D");
        List listProfil = typeVisu.getChildren("profil");
        Iterator it = listProfil.iterator();
        list_profil = new ArrayList<String>();
        while(it.hasNext()){
 	Element current = (Element) it.next();
 	list_profil.add(current);
        }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return list_profil;
    }
       public List<Appariement> getMatching(List<Visualisation> listeDataAttribute, List<Visualisation> listVisualAttribute) throws Exception{
        List<Appariement> MatchingListresult = new ArrayList<Appariement>();
        for(int i=0; i<listVisualAttribute.size(); i++){
            a : for(int j=0; j<listeDataAttribute.size(); j++){
                if(listVisualAttribute.get(i).getType().toString().equals(listeDataAttribute.get(j).getType())){
                    Appariement listAppariement = new Appariement();
                    listAppariement.setName_v_data(listVisualAttribute.get(i).getName());
                    listAppariement.setType_v_data(listVisualAttribute.get(i).getType());
                    listAppariement.setImportance_v_data(listVisualAttribute.get(i).getImportance());
                    listAppariement.setName_data(listeDataAttribute.get(j).getName());
                    listAppariement.setType_data(listeDataAttribute.get(j).getType());
                    listAppariement.setImportance_data(listeDataAttribute.get(j).getImportance());
                    MatchingListresult.add(listAppariement);
                    listeDataAttribute.remove(j);
                    break a;
                }
            }
        }

        return MatchingListresult;

    }
    public List<Appariement> getMatchingParalleleCoordinate(int profil, List<Visualisation> listeDataAttribute, List<Visualisation> listVisualAttribute) throws Exception{
        int numAxes = 0;
        for(Visualisation visu: listeDataAttribute)
            if (visu.getType().equalsIgnoreCase(VRMXML.NUMERIC_TYPE_NAME))
                numAxes++;
        List<Appariement> MatchingListresult = new ArrayList<Appariement>();
        for(int i=0; i<listVisualAttribute.size(); i++){
              a : for(int j=0; j<listeDataAttribute.size(); j++){
                if(listVisualAttribute.get(i).getType().toString().equals(listeDataAttribute.get(j).getType())){
                    Appariement listAppariement = new Appariement();
                    listAppariement.setName_v_data(listVisualAttribute.get(i).getName());
                    listAppariement.setType_v_data(listVisualAttribute.get(i).getType());
                    listAppariement.setImportance_v_data(listVisualAttribute.get(i).getImportance());
                    listAppariement.setName_data(listeDataAttribute.get(j).getName());
                    listAppariement.setType_data(listeDataAttribute.get(j).getType());
                    listAppariement.setImportance_data(listeDataAttribute.get(j).getImportance());
                    MatchingListresult.add(listAppariement);
                    if(listVisualAttribute.get(i).getType().toString().equalsIgnoreCase(VRMXML.NUMERIC_TYPE_NAME) && numAxes != 0)
                        numAxes--;
                    else{
                        break a;
                    }
                    //listeDataAttribute.remove(j);
                }
            }
        }
        return MatchingListresult;
    }
}
