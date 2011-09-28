/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.xml;

import java.io.*;
import java.util.ArrayList;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.util.List;



public class UpdateXMLFile {
   static org.jdom.Document document;
   static Element racine, visualizations, NameVisu;
   int NombreProfil;
   private ArrayList valeursProfil;
   

   public UpdateXMLFile(String filePathName) throws Exception {

       try
       {
          lireFichier(filePathName);
          NombreProfil = 0;
          NombreProfil = (visualizations.getChild("nuage3D").getContentSize()-2)/2;
          for(int i =0; i<=NombreProfil; i++){
          System.out.println("l'indice du profil est :"+i);
          supprElement("profil"+i);
          }
          enregistreFichier(filePathName);
        }
        catch(Exception ex){
        System.out.println(ex);
        }
    }
   

   //On parse le fichier et on initialise la racine de notre arborescence
   public final void lireFichier(String fichier) throws Exception {
      SAXBuilder sxb = new SAXBuilder();
      document = sxb.build(new File(fichier));
      racine = document.getRootElement();
//      visualizations = racine.getChild("visualizations");
      visualizations = racine.getChild("geneticalgorithm");
      NameVisu = visualizations.getChild("nuage3D");
      for(int j=0; j<NombreProfil; j++){
       valeursProfil.add(NameVisu.getChild("profil").getAttributeValue("valeur").toString());
       System.out.println("alllllllllllllloooooooooooooooooo : "+NameVisu.getChild("profil").getAttributeValue("valeur"));
      }

   }

   //On fait des modifications sur un Element
   static void supprElement(String element) {
      //Dans un premier temps on liste tous les étudiants
      List children = NameVisu.getChildren();
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

   static void enregistreFichier(String fichier) throws Exception
   {
         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
         sortie.output(document, new FileOutputStream(fichier));
   }

   
}