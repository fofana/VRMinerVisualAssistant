/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.io;

import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FiltreExtensible extends FileFilter{

//Description et extensions acceptées par le filtre
private String description;
private List<String> extensions;


//Constructeur à partir de la description
public FiltreExtensible(String description){
if(description == null){
throw new NullPointerException("La description ne peut être null.");
}
this.description = description;
this.extensions = new ArrayList<String>();
}


//Implémentation de FileFilter
public boolean accept(File file){
if(file.isDirectory() || extensions.size()==0) {
return true;
}
String nomFichier = file.getName().toLowerCase();
for(String extension : extensions){
if(nomFichier.endsWith(extension)){
return true;
}
}
return false;
}

public String getDescription(){
StringBuffer buffer = new StringBuffer(description);
buffer.append(" (");
for(String extension : extensions){
buffer.append(extension).append(" ");
}
return buffer.append(")").toString();
}


//Quelques méthodes utilitaires
public void setDescription(String description){
if(description == null){
throw new NullPointerException("La description ne peut être null.");
}
this.description = description;
}

public void addExtension(String extension){
if(extension == null){
throw new NullPointerException("Une extension ne peut être null.");
}
extensions.add(extension);
}

public void removeExtension(String extension){
extensions.remove(extension);
}

public void clearExtensions(){
extensions.clear();
}

public List<String> getExtensions(){
return extensions;
}

/**********************************
et voici comment tu l'utilise:
/**********************************/


public static void main(String[] args){

    JFrame frame = new JFrame();
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(80, 80);
    JFileChooser ChoixFS = new JFileChooser();
    ChoixFS.setFileSelectionMode(JFileChooser.FILES_ONLY);
    ChoixFS.setAcceptAllFileFilterUsed(false);

    FiltreExtensible Filtre = new FiltreExtensible("Fichier xml");
    Filtre.addExtension(".xml");
    ChoixFS.addChoosableFileFilter(Filtre);

}

}




