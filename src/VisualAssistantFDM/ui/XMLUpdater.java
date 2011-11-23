/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.ui;

import VisualAssistantFDM.visualisation.ui.Appariement;
import VisualAssistantFDM.visualisation.ui.Normalisation;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import visualisation3d.xml.NUAGE3D;
import vrminerlib.io.VRMXML;
import vrminerlib.io.VRMXMLReader;
import vrminerlib.utils.MessageBox;

/**
 *
 * @author fovea
 */
public class XMLUpdater {
    Document document;
    Element racine;
   MainInterface mainInterface;
   private List<Normalisation> listNormalisation;
   public XMLUpdater( MainInterface mainInterface){
        this.mainInterface = mainInterface;
   }
   public void RafraichissementFichier() {

        try {
            //On cr?e un nouveau document JDOM avec en argument le fichier XML
            document = new VRMXMLReader(mainInterface.filePathName).getXMLDocument();
        } catch (Exception e) {
            System.err.println(e + " Erreur de lecture du xml");
            new MessageBox("Erreur de lecture du fichier xml", e.getMessage(), 1);
        }
    }
      public void AddNewUserPofilSettingsGA(int indexProfil, File xml, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph){

        SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        float[] tabCoordMinListe;
        float[] tabCoordMaxListe;
        String typeDeVisu = new Utils(mainInterface).getCurrentTypeVisu(indexProfil);
        // Pour
        // Nom dans la base diff�rent du nom dans le fichier xml
        if (typeDeVisu.equalsIgnoreCase("Nuage3D"))
            typeDeVisu = "nuage3D";
        try {
        //On cr�e un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(xml);
        //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
        racine = document.getRootElement();
        for(int l=0; l<ResultMEC.size(); l++){
            System.out.println(ResultMEC.get(l).getName_data()+" -:- "+ResultMEC.get(l).getName_v_data());
        }
        /*ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionn�e pour les axes x, y et z */
        List<Appariement> list_numerique = new ArrayList<Appariement>();
        List<Appariement> list_symbolique = new ArrayList<Appariement>();
        List<Appariement> list_texte = new ArrayList<Appariement>();
        List<Appariement> list_image = new ArrayList<Appariement>();
        List<Appariement> list_link = new ArrayList<Appariement>();
        List<Appariement> list_sound = new ArrayList<Appariement>();
        List<Appariement> list_file = new ArrayList<Appariement>();
        List<Appariement> list_temporal = new ArrayList<Appariement>();
        Iterator<Appariement> it = ResultMEC.iterator();
        while(it.hasNext()){
            Appariement currentAppariement = it.next();
            if(currentAppariement.getType_data().equals(VRMXML.NUMERIC_TYPE_NAME)){
               list_numerique.add(currentAppariement);
            }
        }
        Iterator<Appariement> it1 = ResultMEC.iterator();
        while(it1.hasNext()){
            Appariement currentAppariement = it1.next();
            if(currentAppariement.getType_data().equals(VRMXML.SYMBOLIC_TYPE_NAME)){
               list_symbolique.add(currentAppariement);
            }
        }

        Iterator<Appariement> it2 = ResultMEC.iterator();
        while(it2.hasNext()){
            Appariement currentAppariement = it2.next();
            if(currentAppariement.getType_data().equals(VRMXML.TEXT_TYPE_NAME)){
               list_texte.add(currentAppariement);
            }
        }

        Iterator<Appariement> it3 = ResultMEC.iterator();
        while(it3.hasNext()){
            Appariement currentAppariement = it3.next();
            if(currentAppariement.getType_data().equals(VRMXML.IMAGE_TYPE_NAME)){
               list_image.add(currentAppariement);
            }
        }

        Iterator<Appariement> it4 = ResultMEC.iterator();
        while(it4.hasNext()){
            Appariement currentAppariement = it4.next();
            if(currentAppariement.getType_data().equals(VRMXML.SOUND_TYPE_NAME)){
               list_sound.add(currentAppariement);
            }
        }

        Iterator<Appariement> it5 = ResultMEC.iterator();
        while(it5.hasNext()){
            Appariement currentAppariement = it5.next();
            if(currentAppariement.getType_data().equals(VRMXML.LINK_TYPE_NAME)){
               list_link.add(currentAppariement);
            }
        }

        Iterator<Appariement> it6 = ResultMEC.iterator();
        while(it6.hasNext()){
            Appariement currentAppariement = it6.next();
            if(currentAppariement.getType_data().equals(VRMXML.TEMPORAL_TYPE_NAME)){
               list_temporal.add(currentAppariement);
            }
        }

        Iterator<Appariement> it7 = ResultMEC.iterator();
        while(it7.hasNext()){
            Appariement currentAppariement = it7.next();
            if(currentAppariement.getType_data().equals(VRMXML.FILE_TYPE_NAME)){
               list_file.add(currentAppariement);
            }
        }

        listNormalisation = mainInterface.getListNormalisation();
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
        //On cr�e un Iterator sur notre liste
        Iterator n = listData.iterator();
        while (n.hasNext()) {
        //On recr�e l'Element courant � chaque tour de boucle afin de
        //pouvoir utiliser les m�thodes propres aux Element comme :
        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = (Element) n.next();
        List listDatum = datum.getChildren("datum");
        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
        Element courant = (Element) j.next();
        //On affiche le nom de l'element courant
        for (int k = 0; k < list_numerique.size(); k++) {
                //ici on recupere les valeur max et min des attributs selectionn�e pour les axes x, y et z
                tabCoord[k] = Float.parseFloat(courant.getChild(""+list_numerique.get(k).getName_data()).getValue());
                tabCoordMin[k] = (float) Math.min(tabCoord[k], tabCoordMin[k]);
                tabCoordMax[k] = (float) Math.max(tabCoord[k], tabCoordMax[k]);
        }
        /* On remplie le vecteur de poids des valuers de normalisation des axes � utiliser dans l'actulaisation des visualisations avec l'AGI */
        for (int v = 0; v < listNormalisation.size(); v++) {
                //ici on recupere les valeur max et min de tous les attributs de donn�es pour les axes x, y et z
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

        //On cr�e une Liste contenant tous les noeuds "data" de l'Element racine
        Element visu = (Element) racine.getChild("geneticalgorithm");
        Element typeVisu = (Element) visu.getChild(typeDeVisu);
        if (typeVisu == null) {
            Element VisualizationName = new Element(typeDeVisu);
            visu.addContent(VisualizationName);
            typeVisu = (Element) visu.getChild(typeDeVisu);
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");
        //On fait un test sur le profil selectionn� par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en cr�er un dans le fichier xml

        if (profilDefaut == null) {
            profilDefaut = new Element("profilDefaut");
           if (!typeDeVisu.equalsIgnoreCase("CoordonneesParalleles"))
            typeVisu.addContent(profilDefaut);
        }

        profilDefaut.setText(baliseProfil);

        Element profil = (Element) typeVisu.getChild(baliseProfil);

        if (profil == null) {
            profil = new Element(baliseProfil);
            typeVisu.addContent(profil);
        }

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();


        int j = 0;

        System.out.println("list Visual Attribute size: "+listVisualAtt.size());



        /* Fin de la mise � jour le matching entre les attributs de donn�es avec les attributs visuels de la visualisation choisie */

        //AXES
        List lll = typeVisu.getContent();
       // System.out.println(typeVisu.getChildren("Axis"+1).size());
        if (typeDeVisu.equalsIgnoreCase("CoordonneesParalleles")) {
//            int i = 0;
            for (int i = 0; i < ResultMEC.size()-1; i++) {
                Element  e = (Element) profil.getChild(ResultMEC.get(i).getName_v_data()+(i+1));
                e = new Element(ResultMEC.get(i).getName_v_data()+(i+1));
                profil.addContent(e);
                e.setText(ResultMEC.get(i).getName_data());
            }
            Element  e = (Element) profil.getChild(ResultMEC.get(ResultMEC.size()-1).getName_v_data());
            e = new Element(ResultMEC.get(ResultMEC.size()-1).getName_v_data());
            profil.addContent(e);
            e.setText(ResultMEC.get(ResultMEC.size()-1).getName_data());
//            for (Appariement appariement : ResultMEC){
//                 Element  e = (Element) profil.getChild(appariement.getName_v_data());
//                 e = new Element(appariement.getName_v_data());
//                 profil.addContent(e);
//                 e.setText(appariement.getName_data());
//                 //i++;
//            }
        }else
         {
                        /* Debut de la mise � jour du matching entre les attributs de donn�es avec les attributs visuels de la visualisation choisie */
            for(int index=0; index<listVisualAtt.size(); index++){
                Element  e = (Element) profil.getChild(listVisualAtt.get(index).getName());
                e = new Element(listVisualAtt.get(index).getName());
                profil.addContent(e);

                /* Mise � jour de la partie des attributs de donn�es numerique */
                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.NUMERIC_TYPE_NAME)){
                    if(j<list_numerique.size()){
                        e.setText(list_numerique.get(j).getName_data());
                        j++;
                    /* r�initialiser l'indice j */
                    if(j>=list_numerique.size()){
                       j=0;
                    }
                    }
                    else {
                        j=0;
                    }
                }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.TEXT_TYPE_NAME)){
                    if (j<list_texte.size()){
                        e.setText(list_texte.get(j).getName_data());
                        j++;
                    /* r�initialiser l'indice j */
                    if(j>=list_texte.size()){
                       j=0;
                    }
                    } else {
                        j=0;
                        e.setText("Pas de texte");
                     }
                }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.SYMBOLIC_TYPE_NAME)){
                    if(j<list_symbolique.size()){
                        e.setText(list_symbolique.get(j).getName_data());
                        j++;
                        if(j>=list_symbolique.size()){
                        j=0;
                        }
                     }
                    else {
                        j=0;
                    }
                }


                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.IMAGE_TYPE_NAME)) {
                    if(j<list_image.size()){
                        e.setText(list_image.get(j).getName_data());
                        j++;
                    } else {
                        j=0;
                        e.setText("Pas de texture");
                     }
                 }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.SOUND_TYPE_NAME)) {
                    if(j<list_sound.size()){
                        e.setText(list_sound.get(j).getName_data());
                        j++;
                    } else {
                        j=0;
                     }
                }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.TEMPORAL_TYPE_NAME)) {
                    if(j<list_temporal.size()){
                    e.setText(list_temporal.get(j).getName_data());
                    j++;
                    } else {
                        j=0;
                     }
                }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.LINK_TYPE_NAME)) {
                    if(j<list_link.size()){
                        e.setText(list_link.get(j).getName_data());
                    j++;
                    } else {
                        j=0;
                     }
                }

                if(listVisualAtt.get(index).getType().toString().equals(VRMXML.FILE_TYPE_NAME)) {
                    if(j<list_file.size()){
                        e.setText(list_file.get(j).getName_data());
                        j++;
                    } else {
                        j=0;
                     }
                }

            }
            Element XRatio = (Element) profil.getChild("xRatio");
            Element YRatio = (Element) profil.getChild("yRatio");
            Element ZRatio = (Element) profil.getChild("zRatio");
            XRatio = new Element("xRatio");
            YRatio = new Element("yRatio");
            ZRatio = new Element("zRatio");
            profil.addContent(XRatio);
            profil.addContent(YRatio);
            profil.addContent(ZRatio);
            XRatio.setText(mainInterface.getXRatio());
            YRatio.setText(mainInterface.getYRatio());
            ZRatio.setText(mainInterface.getZRatio());
            Element xColor = (Element) profil.getChild("xColor");
            Element yColor = (Element) profil.getChild("yColor");
            Element zColor = (Element) profil.getChild("zColor");
            xColor = new Element("xColor");
            yColor = new Element("yColor");
            zColor = new Element("zColor");
            profil.addContent(xColor);
            profil.addContent(yColor);
            profil.addContent(zColor);
            xColor.setText(mainInterface.getXColorView());
            yColor.setText(mainInterface.getYColorView());
            zColor.setText(mainInterface.getZColorView());
            //Ici on recupere la valeur min et la valeur max de xAxix, yAxix, zAxix
            Element xMinVal = (Element) profil.getChild("xMinVal");
            Element yMinVal = (Element) profil.getChild("yMinVal");
            Element zMinVal = (Element) profil.getChild("zMinVal");
            xMinVal = new Element("xMinVal");
            yMinVal = new Element("yMinVal");
            zMinVal = new Element("zMinVal");
            profil.addContent(xMinVal);
            profil.addContent(yMinVal);
            profil.addContent(zMinVal);
            xMinVal.setText(String.valueOf(tabCoordMin[0]));
            yMinVal.setText(String.valueOf(tabCoordMin[1]));
            zMinVal.setText(String.valueOf(tabCoordMin[2]));

            Element xMaxVal = (Element) profil.getChild("xMaxVal");
            Element yMaxVal = (Element) profil.getChild("yMaxVal");
            Element zMaxVal = (Element) profil.getChild("zMaxVal");
            xMaxVal = new Element("xMaxVal");
            yMaxVal = new Element("yMaxVal");
            zMaxVal = new Element("zMaxVal");
            profil.addContent(xMaxVal);
            profil.addContent(yMaxVal);
            profil.addContent(zMaxVal);
            xMaxVal.setText(String.valueOf(tabCoordMax[0]));
            yMaxVal.setText(String.valueOf(tabCoordMax[1]));
            zMaxVal.setText(String.valueOf(tabCoordMax[2]));

            // liens
            Element AfficherLiens = new Element("afficherLiens");
            profil.addContent(AfficherLiens);
            if(list_link.size()>0){
            AfficherLiens.setText("true");
            }else{
            AfficherLiens.setText("false");
            }

            //OBJET 3D
            Element Shape = (Element) profil.getChild("shape");
            Element Size = (Element) profil.getChild("size");
            Shape = new Element("shape");
            Size = new Element("size");
            profil.addContent(Shape);
            profil.addContent(Size);
            Shape.setText(ElemGraph);
            Size.setText(mainInterface.getObjectSize());
            //Element attSynth = (Element) profil.getChild("attSynth");
            Element Voix = (Element) profil.getChild("Voix");
            Element listMedia = (Element) profil.getChild("listMedia");
            //attSynth = new Element("attSynth");
            Voix = new Element("Voix");
            listMedia = new Element("listMedia");
            //profil.addContent(attSynth);
            profil.addContent(Voix);
            profil.addContent(listMedia);

            /*ChoicePanelXml choicePanel = new ChoicePanelXml();
            ArrayList list_Media = choicePanel.getListMedia();

            for (int i = 0; i < list_Media.size(); i++) {
                Element Media = new Element("Media" + i);
                listMedia.addContent(Media);
                Media.setText(list_Media.get(i).toString());
            }*/

            //TO change here
            //attSynth.setText("");
            Voix.setText("Pas de voix");

            Element COlor1 = (Element) profil.getChild("Color1");
            Element COlor2 = (Element) profil.getChild("Color2");
            Element COlor3 = (Element) profil.getChild("Color3");
            Element COlor4 = (Element) profil.getChild("Color4");
            COlor1 = new Element("Color1");
            COlor2 = new Element("Color2");
            COlor3 = new Element("Color3");
            COlor4 = new Element("Color4");
            profil.addContent(COlor1);
            profil.addContent(COlor2);
            profil.addContent(COlor3);
            profil.addContent(COlor4);
            COlor1.setText(mainInterface.getColor1());
            COlor2.setText(mainInterface.getColor2());
            COlor3.setText(mainInterface.getColor3());
            COlor4.setText(mainInterface.getColor4());

            //CAMERA
            Element camera = (Element) profil.getChild("camera");
            camera = new Element("camera");
            profil.addContent(camera);
            camera.setText(mainInterface.getCamType());

            Element XLoc = (Element) profil.getChild("xLoc");
            Element YLoc = (Element) profil.getChild("yLoc");
            Element ZLoc = (Element) profil.getChild("zLoc");
            XLoc = new Element("xLoc");
            YLoc = new Element("yLoc");
            ZLoc = new Element("zLoc");
            profil.addContent(XLoc);
            profil.addContent(YLoc);
            profil.addContent(ZLoc);
            XLoc.setText(mainInterface.getxLoc());
            YLoc.setText(mainInterface.getyLoc());
            ZLoc.setText(mainInterface.getzLoc());

            //RESEAU
            Element AdrsIpServer = (Element) profil.getChild("adrsIpServer");
            Element Port = (Element) profil.getChild("port");
            Element JCheckBoxReseau = (Element) profil.getChild("jCheckBoxReseau");
            Element CheminLecteur = (Element) profil.getChild("cheminLecteur");
            AdrsIpServer = new Element("adrsIpServer");
            Port = new Element("port");
            JCheckBoxReseau = new Element("jCheckBoxReseau");
            CheminLecteur = new Element("cheminLecteur");
            profil.addContent(AdrsIpServer);
            profil.addContent(Port);
            profil.addContent(JCheckBoxReseau);
            profil.addContent(CheminLecteur);

            AdrsIpServer.setText(mainInterface.getadrsIpServer());
            JCheckBoxReseau.setText(mainInterface.getjCheckBoxReseau());
            CheminLecteur.setText(mainInterface.getcheminLecteur());

            //ESPACE3D
            Element JCheckBoxStereo = (Element) profil.getChild("jCheckBoxStereo");
            Element JSliderYeux = (Element) profil.getChild("jSliderYeux");
            Element JTextFieldTaille = (Element) profil.getChild("jTextFieldTaille");
            Element JTextFieldDistance = (Element) profil.getChild("jTextFieldDistance");
            Element BkgdColorView = (Element) profil.getChild("bkgdColorView");
            Element GridEnabled = (Element) profil.getChild("gridEnabled");
            Element GrilleTerrain = (Element) profil.getChild("grilleTerrain");

            Element PopUpInfo = (Element) profil.getChild("popUpInfo");
            Element COlor5 = (Element) profil.getChild("Color5");
            Element ObjSize2 = (Element) profil.getChild("objSize2");

            JCheckBoxStereo = new Element("jCheckBoxStereo");
            JSliderYeux = new Element("jSliderYeux");
            JTextFieldTaille = new Element("jTextFieldTaille");
            JTextFieldDistance = new Element("jTextFieldDistance");

            BkgdColorView = new Element("bkgdColorView");
            GridEnabled = new Element("gridEnabled");
            GrilleTerrain = new Element("grilleTerrain");

            PopUpInfo = new Element("popUpInfo");
            COlor5 = new Element("Color5");
            ObjSize2 = new Element("objSize2");
            profil.addContent(JCheckBoxStereo);
            profil.addContent(JSliderYeux);
            profil.addContent(JTextFieldTaille);
            profil.addContent(JTextFieldDistance);

            profil.addContent(BkgdColorView);
            profil.addContent(GridEnabled);
            profil.addContent(GrilleTerrain);
            profil.addContent(PopUpInfo);
            profil.addContent(COlor5);
            profil.addContent(ObjSize2);
            JCheckBoxStereo.setText(mainInterface.getjCheckBoxStereo());
            JSliderYeux.setText(mainInterface.getjSliderYeux());
            JTextFieldTaille.setText(mainInterface.getjTextFieldTaille());
            JTextFieldDistance.setText(mainInterface.getjTextFieldDistance());
            BkgdColorView.setText(mainInterface.getBkgdColorView());
            GrilleTerrain.setText(mainInterface.getGrilleTerrain());

            //GridEnabled.setText(getGridEnabled());
            PopUpInfo.setText(mainInterface.getpopUpInfo());
            COlor5.setText(mainInterface.getColor5());
            ObjSize2.setText(mainInterface.getObjSize2());

            //Clusturing
            Element CLusturing = (Element) profil.getChild("Clusturing");
            Element aTtClust = (Element) profil.getChild("attClust");
            Element cLustFact = (Element) profil.getChild("clustFact");
            Element CLustVisib = (Element) profil.getChild("ClustVisib");
            Element cLustMethode = (Element) profil.getChild("clustMethode");
            Element cLustObjet = (Element) profil.getChild("clustObjet");
            Element EClairageSphere = (Element) profil.getChild("EclairageSphere");
            Element oBjSize3 = (Element) profil.getChild("objSize3");

            CLusturing = new Element("Clusturing");
            aTtClust = new Element("attClust");
            cLustFact = new Element("clustFact");
            CLustVisib = new Element("ClustVisib");
            cLustMethode = new Element("clustMethode");
            cLustObjet = new Element("clustObjet");
            EClairageSphere = new Element("EclairageSphere");
            oBjSize3 = new Element("objSize3");

            profil.addContent(CLusturing);
            profil.addContent(aTtClust);
            profil.addContent(cLustFact);
            profil.addContent(CLustVisib);
            profil.addContent(cLustMethode);
            profil.addContent(cLustObjet);
            profil.addContent(EClairageSphere);
            profil.addContent(oBjSize3);

            CLusturing.setText(mainInterface.getClusturing());
    //        if (!attClust.isEmpty()) {
    //            attClust.setSelectedItem(attClust);
    //        }
            //aTtClust.setText(attClust.getSelectedItem().toString());
            aTtClust.setText(mainInterface.getAttClust());
            cLustFact.setText(mainInterface.getclustFact());
            CLustVisib.setText(mainInterface.getClustVisib());
            cLustMethode.setText(mainInterface.getclustMethode());
            cLustObjet.setText(mainInterface.getclustObjet());
            EClairageSphere.setText(mainInterface.getEclairageSphere());
            oBjSize3.setText(mainInterface.getobjSize3());

            // Palettes de couleurs
            Element colorParam = new Element("colorParam");
            profil.addContent(colorParam);
            // On parcourt la liste des attributs
            HashMap<String, HashMap> MapAttributPalette = new HashMap<String, HashMap>();
            Iterator<String> itOverAttribut = MapAttributPalette.keySet().iterator();
            while (itOverAttribut.hasNext()) {
                // On r�cupère le nom de l'attribut
                String nomAttribut = itOverAttribut.next();
                // Ajout au sch�ma XML
                Element attribut = new Element(nomAttribut);
                colorParam.addContent(attribut);
                // On parcourt ensuite la palette
                HashMap<String, Color> mapValeurColor = MapAttributPalette.get(nomAttribut);
                Iterator<String> itOverValeur = mapValeurColor.keySet().iterator();
                while (itOverValeur.hasNext()) {
                    // On r�cupère la valeur de l'attribut
                    String valeurAttribut = itOverValeur.next();
                    // et sa couleur associ�e
                    Color couleur = mapValeurColor.get(valeurAttribut);
                    // On cr�er l'�l�ment XML correspondant
                    Element colorAttribute = new Element("color");
                    colorAttribute.setAttribute("for", valeurAttribut);
                    colorAttribute.setText(couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue());
                    // On l'ajoute au sch�ma XML
                    attribut.addContent(colorAttribute);
                }
            }
        }
      enregistreFichier(mainInterface.filePathName);
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }
    }

   public void SaveSelectedProfil(File xml, String baliseProfilSelectionne){

        String baliseProfil;
        String SprofilDefaut = "profil0";
        SAXBuilder sxb = new SAXBuilder();
        racine = document.getRootElement();
        //On cr�e un nouveau document JDOM avec en argument le fichier XML
        try{
        document = sxb.build(xml);
        //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
        racine = document.getRootElement();

        Element visualization = (Element) racine.getChild(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
        Element geneticAlgorithm = (Element) racine.getChild(VRMXML.IGA_ELEMENT_NAME);

        Element Nuage3DIGA = geneticAlgorithm.getChild(NUAGE3D.NUAGE3D_NAME);

        Element Nuage3DVisualization = visualization.getChild(NUAGE3D.NUAGE3D_NAME);
        if (Nuage3DVisualization == null) {
            Element VisualizationName = new Element(NUAGE3D.NUAGE3D_NAME);
            visualization.addContent(VisualizationName);
            Nuage3DVisualization = (Element) visualization.getChild(NUAGE3D.NUAGE3D_NAME);
        }

        int number = Nuage3DVisualization.getChildren().size();
        System.out.println("Number of all profil(s) :"+number);

        Element profilDefaut = (Element) Nuage3DVisualization.getChild("profilDefaut");
        //On fait un test sur le profil selectionn� par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en cr�er un dans le fichier xml
        if (profilDefaut == null) {
            profilDefaut = new Element("profilDefaut");
            Nuage3DVisualization.addContent(profilDefaut);
            profilDefaut.setText(SprofilDefaut);
        } else {
            //Dans le comptage du nombre de profil, on elimine le profil par defaut
            number--;
            baliseProfil = "profil"+number;
            profilDefaut.setText(baliseProfil);
        }
        baliseProfil = "profil"+number;

        Element profil = (Element) Nuage3DVisualization.getChild(baliseProfil);
        Element profil_ = (Element) Nuage3DIGA.getChild(baliseProfilSelectionne);

        if (profil == null) {
            profil = new Element(baliseProfil);
            Nuage3DVisualization.addContent(profil);
        }

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();

        Iterator it = profil_.getChildren().iterator();
        while (it.hasNext()) {
          Element courant = (Element) it.next();
          Element e = new Element(courant.getName().toString());
          profil.addContent(e);
          e.setText(courant.getText());
        }
        }
        catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }
    }
       
   public void setMainInterface(MainInterface mainInterface){
        this.mainInterface = mainInterface;
   }
   
   public void enregistreFichier(String fichier) throws Exception{
         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
         sortie.output(document, new FileOutputStream(fichier));

   }

}
