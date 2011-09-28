/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.xml;

import VisualAssistantFDM.io.VisualAssistantXMLWriterAdapter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import visualisation3d.ui.ChoicePanelXml;
import vrminerlib.io.VRMXMLReader;
import vrminerlib.io.VRMXMLWriterAdapter;

/**
 *
 * @author Vincent
 */
public class VisuXMLWriter extends VisualAssistantXMLWriterAdapter {

    public static final int REMOVE_PROFILE = 0;
    public static final int ADD_PROFILE = 1;
    private ChoicePanelXml choicePanel;
    private int indexProfil;
    private String profilChoisi;
    private int writeMode;

    /**
     *
     * @param xmlInputFileName
     * @param choicePanel
     * @param indexProfil
     * @param profilChoisi
     * @param writeMode REMOVE_PROFILE or ADD_PROFILE
     */
    public VisuXMLWriter(String xmlInputFileName, ChoicePanelXml choicePanel, int indexProfil, String profilChoisi, int writeMode) {
        super(new VRMXMLReader(xmlInputFileName).getXMLDocument(), xmlInputFileName);
        this.choicePanel = choicePanel;
        this.indexProfil = indexProfil;
        this.profilChoisi = profilChoisi;
        this.writeMode = writeMode;
    }

    @Override
    public void preFilling() {
    }

    @Override
    public void fillVisualisations(Element xmlVisualisationsElement) {
        if (writeMode == ADD_PROFILE && profilChoisi != null) {
            addProfile(xmlVisualisationsElement);
        } else if (writeMode == REMOVE_PROFILE && profilChoisi != null) {
            removeProfile(xmlVisualisationsElement);
        }
    }

    private void addProfile(Element xmlVisualisationsElement) {
        String baliseProfil = "profil" + indexProfil;

        Element typeVisu = (Element) xmlVisualisationsElement.getChild("nuage3D");
        if (typeVisu == null) {
            xmlVisualisationsElement.addContent(new Element("nuage3D"));
            typeVisu = (Element) xmlVisualisationsElement.getChild("nuage3D");
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");

        if (profilDefaut == null) {
            profilDefaut = new Element("profilDefaut");
            typeVisu.addContent(profilDefaut);
        }

        profilDefaut.setText(baliseProfil);

        Element profil = (Element) typeVisu.getChild(baliseProfil);

        if (profil == null) {
            profil = new Element(baliseProfil);
            typeVisu.addContent(profil);
        }

        profil.setAttribute("valeur", profilChoisi);

        profil.removeContent();


        //AXES
        Element xAxis = (Element) profil.getChild("xAxis");
        Element yAxis = (Element) profil.getChild("yAxis");
        Element zAxis = (Element) profil.getChild("zAxis");
        xAxis = new Element("xAxis");
        yAxis = new Element("yAxis");
        zAxis = new Element("zAxis");
        profil.addContent(xAxis);
        profil.addContent(yAxis);
        profil.addContent(zAxis);
        xAxis.setText(choicePanel.getXVar());
        yAxis.setText(choicePanel.getYVar());
        zAxis.setText(choicePanel.getZVar());

        Element xRatio = (Element) profil.getChild("xRatio");
        Element yRatio = (Element) profil.getChild("yRatio");
        Element zRatio = (Element) profil.getChild("zRatio");
        xRatio = new Element("xRatio");
        yRatio = new Element("yRatio");
        zRatio = new Element("zRatio");
        profil.addContent(xRatio);
        profil.addContent(yRatio);
        profil.addContent(zRatio);
        xRatio.setText(choicePanel.getXRatio());
        yRatio.setText(choicePanel.getYRatio());
        zRatio.setText(choicePanel.getZRatio());

        Element xColor = (Element) profil.getChild("xColor");
        Element yColor = (Element) profil.getChild("yColor");
        Element zColor = (Element) profil.getChild("zColor");
        xColor = new Element("xColor");
        yColor = new Element("yColor");
        zColor = new Element("zColor");
        profil.addContent(xColor);
        profil.addContent(yColor);
        profil.addContent(zColor);
        xColor.setText(choicePanel.getXColorView());
        yColor.setText(choicePanel.getYColorView());
        zColor.setText(choicePanel.getZColorView());

        Element xMinVal = (Element) profil.getChild("xMinVal");
        Element yMinVal = (Element) profil.getChild("yMinVal");
        Element zMinVal = (Element) profil.getChild("zMinVal");
        xMinVal = new Element("xMinVal");
        yMinVal = new Element("yMinVal");
        zMinVal = new Element("zMinVal");
        profil.addContent(xMinVal);
        profil.addContent(yMinVal);
        profil.addContent(zMinVal);
        xMinVal.setText(choicePanel.getxMinVal());
        yMinVal.setText(choicePanel.getyMinVal());
        zMinVal.setText(choicePanel.getzMinVal());

        Element xMaxVal = (Element) profil.getChild("xMaxVal");
        Element yMaxVal = (Element) profil.getChild("yMaxVal");
        Element zMaxVal = (Element) profil.getChild("zMaxVal");
        xMaxVal = new Element("xMaxVal");
        yMaxVal = new Element("yMaxVal");
        zMaxVal = new Element("zMaxVal");
        profil.addContent(xMaxVal);
        profil.addContent(yMaxVal);
        profil.addContent(zMaxVal);
        xMaxVal.setText(choicePanel.getxMaxVal());
        yMaxVal.setText(choicePanel.getyMaxVal());
        zMaxVal.setText(choicePanel.getzMaxVal());

        // liens
        Element afficherLiens = new Element("afficherLiens");
        profil.addContent(afficherLiens);
        afficherLiens.setText(choicePanel.getAfficherLiens());
        Element attributLiens = new Element("attributLiens");
        profil.addContent(attributLiens);
        attributLiens.setText(choicePanel.getAttributLiens());

        //OBJET 3D
        Element shape = (Element) profil.getChild("shape");
        Element size = (Element) profil.getChild("size");
        shape = new Element("shape");
        size = new Element("size");
        profil.addContent(shape);
        profil.addContent(size);
        shape.setText(choicePanel.getShape());
        size.setText(choicePanel.getObjSize());

        Element att1 = (Element) profil.getChild("att1");
        Element att2 = (Element) profil.getChild("att2");
        Element att3 = (Element) profil.getChild("att3");
        Element att4 = (Element) profil.getChild("att4");

        att1 = new Element("att1");
        att2 = new Element("att2");
        att3 = new Element("att3");
        att4 = new Element("att4");

        profil.addContent(att1);
        profil.addContent(att2);
        profil.addContent(att3);
        profil.addContent(att4);

        att1.setText(choicePanel.getAtt1());
        att2.setText(choicePanel.getAtt2());
        att3.setText(choicePanel.getAtt3());
        att4.setText(choicePanel.getAtt4());


        Element attSynth = (Element) profil.getChild("attSynth");
        Element Voix = (Element) profil.getChild("Voix");
        Element classe = (Element) profil.getChild("classe");
        Element classe1 = (Element) profil.getChild("classe1");
        Element imgAtt = (Element) profil.getChild("imgAtt");
        Element listMedia = (Element) profil.getChild("listMedia");

        classe = new Element("classe");
        classe1 = new Element("classe1");
        imgAtt = new Element("imgAtt");
        attSynth = new Element("attSynth");
        Voix = new Element("Voix");
        listMedia = new Element("listMedia");

        profil.addContent(classe);
        profil.addContent(classe1);
        profil.addContent(imgAtt);
        profil.addContent(attSynth);
        profil.addContent(Voix);
        profil.addContent(listMedia);

        ArrayList list_Media = choicePanel.getListMedia();

        for (int i = 0; i < list_Media.size(); i++) {
            Element Media = new Element("Media" + i);
            listMedia.addContent(Media);
            Media.setText(list_Media.get(i).toString());
        }
        classe.setText(choicePanel.getClassVar());
        classe1.setText(choicePanel.getClassVar1());
        imgAtt.setText(choicePanel.getImg());
        attSynth.setText(choicePanel.getSynthAtt1());
        Voix.setText(choicePanel.getVoix());

        Element Color1 = (Element) profil.getChild("Color1");
        Element Color2 = (Element) profil.getChild("Color2");
        Element Color3 = (Element) profil.getChild("Color3");
        Element Color4 = (Element) profil.getChild("Color4");
        Color1 = new Element("Color1");
        Color2 = new Element("Color2");
        Color3 = new Element("Color3");
        Color4 = new Element("Color4");
        profil.addContent(Color1);
        profil.addContent(Color2);
        profil.addContent(Color3);
        profil.addContent(Color4);
        Color1.setText(choicePanel.getColor1());
        Color2.setText(choicePanel.getColor2());
        Color3.setText(choicePanel.getColor3());
        Color4.setText(choicePanel.getColor4());

        //CAMERA
        Element camera = (Element) profil.getChild("camera");
        camera = new Element("camera");
        profil.addContent(camera);
        camera.setText(choicePanel.getCamType());

        Element xLoc = (Element) profil.getChild("xLoc");
        Element yLoc = (Element) profil.getChild("yLoc");
        Element zLoc = (Element) profil.getChild("zLoc");
        xLoc = new Element("xLoc");
        yLoc = new Element("yLoc");
        zLoc = new Element("zLoc");
        profil.addContent(xLoc);
        profil.addContent(yLoc);
        profil.addContent(zLoc);
        xLoc.setText(choicePanel.getxLoc());
        yLoc.setText(choicePanel.getyLoc());
        zLoc.setText(choicePanel.getzLoc());

        //RESEAU
        Element adrsIpServer = (Element) profil.getChild("adrsIpServer");
        Element port = (Element) profil.getChild("port");
        Element jCheckBoxReseau = (Element) profil.getChild("jCheckBoxReseau");
        Element cheminLecteur = (Element) profil.getChild("cheminLecteur");
        adrsIpServer = new Element("adrsIpServer");
        port = new Element("port");
        jCheckBoxReseau = new Element("jCheckBoxReseau");
        cheminLecteur = new Element("cheminLecteur");
        profil.addContent(adrsIpServer);
        profil.addContent(port);
        profil.addContent(jCheckBoxReseau);
        profil.addContent(cheminLecteur);

        adrsIpServer.setText(choicePanel.getadrsIpServer());
        jCheckBoxReseau.setText(choicePanel.getjCheckBoxReseau());
        cheminLecteur.setText(choicePanel.getcheminLecteur());


        //ESPACE3D
        Element jCheckBoxStereo = (Element) profil.getChild("jCheckBoxStereo");
        Element jSliderYeux = (Element) profil.getChild("jSliderYeux");
        Element jTextFieldTaille = (Element) profil.getChild("jTextFieldTaille");
        Element jTextFieldDistance = (Element) profil.getChild("jTextFieldDistance");

        Element bkgdColorView = (Element) profil.getChild("bkgdColorView");
        Element gridEnabled = (Element) profil.getChild("gridEnabled");
        Element grilleTerrain = (Element) profil.getChild("grilleTerrain");

        Element popUpInfo = (Element) profil.getChild("popUpInfo");
        Element Color5 = (Element) profil.getChild("Color5");
        Element objSize2 = (Element) profil.getChild("objSize2");

        jCheckBoxStereo = new Element("jCheckBoxStereo");
        jSliderYeux = new Element("jSliderYeux");
        jTextFieldTaille = new Element("jTextFieldTaille");
        jTextFieldDistance = new Element("jTextFieldDistance");

        bkgdColorView = new Element("bkgdColorView");
        //gridEnabled = new Element("gridEnabled");
        grilleTerrain = new Element("grilleTerrain");

        popUpInfo = new Element("popUpInfo");
        Color5 = new Element("Color5");
        objSize2 = new Element("objSize2");
        profil.addContent(jCheckBoxStereo);
        profil.addContent(jSliderYeux);
        profil.addContent(jTextFieldTaille);
        profil.addContent(jTextFieldDistance);

        profil.addContent(bkgdColorView);
        //profil.addContent(gridEnabled);
        profil.addContent(grilleTerrain);
        profil.addContent(popUpInfo);
        profil.addContent(Color5);
        profil.addContent(objSize2);
        jCheckBoxStereo.setText(choicePanel.getjCheckBoxStereo());
        jSliderYeux.setText(choicePanel.getjSliderYeux());
        jTextFieldTaille.setText(choicePanel.getjTextFieldTaille());
        jTextFieldDistance.setText(choicePanel.getjTextFieldDistance());

        grilleTerrain.setText(choicePanel.getGrilleTerrain());
        bkgdColorView.setText(choicePanel.getBkgdColorView());
        //gridEnabled.setText(choicePanel.getGridEnabled());
        popUpInfo.setText(choicePanel.getpopUpInfo());
        Color5.setText(choicePanel.getColor5());
        objSize2.setText(choicePanel.getObjSize2());


        //Clusturing
        Element Clusturing = (Element) profil.getChild("Clusturing");
        Element attClust = (Element) profil.getChild("attClust");
        Element clustFact = (Element) profil.getChild("clustFact");
        Element ClustVisib = (Element) profil.getChild("ClustVisib");
        Element clustMethode = (Element) profil.getChild("clustMethode");
        Element clustObjet = (Element) profil.getChild("clustObjet");
        Element EclairageSphere = (Element) profil.getChild("EclairageSphere");
        Element objSize3 = (Element) profil.getChild("objSize3");

        Clusturing = new Element("Clusturing");
        attClust = new Element("attClust");
        clustFact = new Element("clustFact");
        ClustVisib = new Element("ClustVisib");
        clustMethode = new Element("clustMethode");
        clustObjet = new Element("clustObjet");
        EclairageSphere = new Element("EclairageSphere");
        objSize3 = new Element("objSize3");

        profil.addContent(Clusturing);
        profil.addContent(attClust);
        profil.addContent(clustFact);
        profil.addContent(ClustVisib);
        profil.addContent(clustMethode);
        profil.addContent(clustObjet);
        profil.addContent(EclairageSphere);
        profil.addContent(objSize3);

        Clusturing.setText(choicePanel.getClusturing());
        attClust.setText(choicePanel.getAttClust());
        clustFact.setText(choicePanel.getclustFact());
        ClustVisib.setText(choicePanel.getClustVisib());
        clustMethode.setText(choicePanel.getclustMethode());
        clustObjet.setText(choicePanel.getclustObjet());
        EclairageSphere.setText(choicePanel.getEclairageSphere());
        objSize3.setText(choicePanel.getobjSize3());

        // Palettes de couleurs
        Element colorParam = new Element("colorParam");
        profil.addContent(colorParam);
        // On parcourt la liste des attributs
        HashMap<String, HashMap> mapAttributPalette = choicePanel.getAttributPalette();
        Iterator<String> itOverAttribut = mapAttributPalette.keySet().iterator();
        while (itOverAttribut.hasNext()) {
            // On recupere le nom de l'attribut
            String nomAttribut = itOverAttribut.next();
            // Ajout au schema XML
            Element attribut = new Element(nomAttribut);
            colorParam.addContent(attribut);
            // On parcourt ensuite la palette
            HashMap<String, Color> mapValeurColor = mapAttributPalette.get(nomAttribut);
            Iterator<String> itOverValeur = mapValeurColor.keySet().iterator();
            while (itOverValeur.hasNext()) {
                // On recupere la valeur de l'attribut
                String valeurAttribut = itOverValeur.next();
                // et sa couleur associee
                Color couleur = mapValeurColor.get(valeurAttribut);
                // On creer l'element XML correspondant
                Element colorAttribute = new Element("color");
                colorAttribute.setAttribute("for", valeurAttribut);
                colorAttribute.setText(couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue());
                // On l'ajoute au schema XML
                attribut.addContent(colorAttribute);
            }
        }
    }

    @Override
    public void postFilling() {
    }

    private void removeProfile(Element xmlVisualisationsElement) {
        Element typeVisu = (Element) xmlVisualisationsElement.getChild("nuage3D");
        if (typeVisu != null) {
            List children = typeVisu.getChildren();

            for (Object child : children) {
                if (child instanceof Element) {
                    String valueElement = ((Element) child).getAttributeValue("valeur");
                    if (valueElement != null && valueElement.equals(profilChoisi)) {
                        ((Element) child).detach();
                        break;
                    }
                }
            }
        }
    }
}
