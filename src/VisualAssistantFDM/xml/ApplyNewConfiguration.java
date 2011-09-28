/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Abdelheq
 */
public class ApplyNewConfiguration {

   private static Document document;
   private static Element racine, visualizations, NameVisu;

   public ApplyNewConfiguration(String filePathName, int indiceProfilChoisie) throws Exception{

       try {
          lireFichier(filePathName);
          int x = 0;
          x = (visualizations.getChild("nuage3D").getContentSize()-2)/2;
          for(int i =0; i<=x; i++){
              if(i!=indiceProfilChoisie){
                System.out.println("Indice"+indiceProfilChoisie);
                //removeProfile(racine, "profil"+i);
                supprElement("profil"+i);
              } 
          }
          enregistreFichier(filePathName);
        }
        catch(Exception ex){
        System.out.println(ex);
        }

   }

   //On parse le fichier et on initialise la racine de
   //notre arborescence
   public void lireFichier(String fichier) throws Exception {
      SAXBuilder sxb = new SAXBuilder();
      document = sxb.build(new File(fichier));
      racine = document.getRootElement();
      visualizations = racine.getChild("visualizations");
      NameVisu = visualizations.getChild("nuage3D");
   }

   //On fait des modifications sur un Element
   static void supprElement(String element) {
      //Dans un premier temps on liste tous les noeuds profil
      List children = NameVisu.getChildren();
//      for(int i=0; i<children.size(); i++){
//
//
//
//      }
      for (Object child : children) {
                if (child instanceof Element) {
                    String valueElement = ((Element) child).getAttributeValue("valeur");
                    if (valueElement != null && valueElement.equals(element)) {
                        ((Element) child).detach();
                        break;
                    }
                }
      }

   }

   private void removeProfile(Element xmlVisualisationsElement, String profilChoisi) {
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

   static void enregistreFichier(String fichier) throws Exception
   {
         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
         sortie.output(document, new FileOutputStream(fichier));
   }

}
