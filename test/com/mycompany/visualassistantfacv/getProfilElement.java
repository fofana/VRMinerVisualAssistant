/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.visualassistantfacv;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import visualisation3d.xml.NUAGE3D;
import vrminerlib.io.VRMXML;


/**
 *
 * @author Abdelheq
 */
public class getProfilElement {
    
    private org.jdom.Document document;
    private Element racine;
    private String SprofilDefaut = "profil0";
    private int numberProfil;

    public ArrayList getProfilElement(String SelectedProfil){
        
        String tag;
        ArrayList listNom = new ArrayList();
        ArrayList listValeur = new ArrayList();
        ArrayList listCoordonne = new ArrayList();

        listCoordonne.add(listNom);
        listCoordonne.add(listValeur);

        //On initialise un nouvel element racine avec l'element racine du document.
        racine = document.getRootElement();

        //On cree une Liste contenant tous les noeuds "data" de l'Element racine
        Element profil = racine.getChild("visualizations");

        List listDatum = profil.getChildren();

        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
            Element param = (Element) j.next();
            tag = param.getChild(NUAGE3D.X_AXIS_NAME).getValue();

//            if (tag.equals(SelectedProfil)) {
//                for (int k = 0; k < vectParam.size(); k++) {
//                    listNom.add(param.getChild("" + vectParam.get(k)).getName().toString());
//                    listValeur.add(param.getChild("" + vectParam.get(k)).getValue().toString());
//                }
//
//                break;
//            }
        }

        return listCoordonne;
    }

        public void SaveSelectedProfil(File xml, String baliseProfilSelectionne){

        String baliseProfil;
        SAXBuilder sxb = new SAXBuilder();
        racine = document.getRootElement();
        //On crée un nouveau document JDOM avec en argument le fichier XML
        try{
        document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
//        //On initialise la liste qui va contenir les nom de balises des profils à enregistré
//        ArrayList listNom = new ArrayList();
//        //On initialise la liste qui va contenir les valeurs de balises des profils à enregistré
//        ArrayList listValeur = new ArrayList();
//        //On initialise la liste qui va contenir les nom et les valeurs de balises des profils à enregistré
//        ArrayList listCoordonne = new ArrayList();

        Element visualizations = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        Element geneticAlgorithm = (Element) racine.getChild(VRMXML.IGA_ELEMENT_NAME);
        
        Element Nuage3DIGA = geneticAlgorithm.getChild(NUAGE3D.NUAGE3D_NAME);

        Element Nuage3DVisualization = visualizations.getChild(NUAGE3D.NUAGE3D_NAME);
        if (Nuage3DVisualization == null) {
            Element VisualizationName = new Element(NUAGE3D.NUAGE3D_NAME);
            visualizations.addContent(VisualizationName);
            Nuage3DVisualization = (Element) visualizations.getChild(NUAGE3D.NUAGE3D_NAME);
        }

        int number = Nuage3DIGA.getChildren().size();
        
        Element profilDefaut = (Element) Nuage3DVisualization.getChild("profilDefaut");
        //On fait un test sur le profil selectionné par defaut Ã  l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en créer un dans le fichier xml
        if (profilDefaut == null) {
            profilDefaut = new Element("profilDefaut");
            Nuage3DVisualization.addContent(profilDefaut);
            profilDefaut.setText(SprofilDefaut);
        } else {
            //Dans le comptage du nombre de profil, on elimine le profil par defaut
            number--;
        }
        baliseProfil = "profil"+number;
        profilDefaut.setText(baliseProfil);

        Element profil = (Element) Nuage3DVisualization.getChild(baliseProfilSelectionne);
        
        if (profil == null) {
            profil = new Element(baliseProfil);
            Nuage3DVisualization.addContent(profil);
        }

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();

        
        List selected_profil = Nuage3DIGA.getChildren(baliseProfilSelectionne);
        Iterator m = selected_profil.iterator();
        while (m.hasNext()) {
            //Tant que attribute a des fils, on regarde ici si le type de l'attribut est numerique
            //ou chaine ,ou imageURL, ou autre...
            Element courant = (Element) m.next();
            Element e = new Element(courant.getContent().toString());
            profil.addContent(e);
            profil.setText(courant.getText().toString());
//            listNom.add(courant.getChild(VRMXML.ATTRIBUTE_NAME_ELEMENT_NAME).getValue());
//            listValeur.add(courant.getChild("" + vectParam.get(k)).getValue().toString());
           
        }


        }
        catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }
    }
    
}
