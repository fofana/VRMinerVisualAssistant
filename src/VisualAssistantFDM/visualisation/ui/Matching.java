package VisualAssistantFDM.visualisation.ui;


import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.visualisation.ui.Appariement;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import vrminerlib.io.VRMXML;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdelheq
 */
public class Matching {
    
    List<Visualisation> list_numeric = new ArrayList<Visualisation>();
    List<Visualisation> list_text = new ArrayList<Visualisation>();
    List<Visualisation> list_symbolic = new ArrayList<Visualisation>();
    List<Visualisation> list_temporal = new ArrayList<Visualisation>();
    List<Visualisation> list_image = new ArrayList<Visualisation>();
    List<Visualisation> list_sound = new ArrayList<Visualisation>();
    List<Visualisation> list_edge = new ArrayList<Visualisation>();
    List<Visualisation> list_file = new ArrayList<Visualisation>();
    List<Visualisation> temp_importance = new ArrayList();
    List<Visualisation> DataAttributeList = new ArrayList<Visualisation>();
    Document document, document1;
    Element racine;
    String colNameMatching[] = {"Indice visuel", "Type", "Importance", "Attribut de données", "Type", "Importance"};
    Object[][] data = null;
    DefaultTableModel MatchingTableModel = new DefaultTableModel(data,colNameMatching);
    List<Appariement> individuMEC = new ArrayList<Appariement>();

    public Matching() throws Exception
    {


    }

    public DefaultTableModel getMatchingResult(List<Visualisation> maListe, List<Visualisation> listdtm) throws Exception{
        for(int i=0; i<listdtm.size(); i++){
            a : for(int j=0; j<maListe.size(); j++){
            if(listdtm.get(i).getType().toString().equals(maListe.get(j).getType())){
            MatchingTableModel.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance(), maListe.get(j).getName(), maListe.get(j).getType(), maListe.get(j).getImportance()});
            maListe.remove(j);
            break a;
            }
            }
        }
        
        return MatchingTableModel;

    }

    public List<Appariement> getNewMatchingResult(List<Visualisation> DataAttribteList, List<Visualisation> VisualDataAttribute) throws Exception{

        List<Appariement> NewMatchingListResult = new ArrayList<Appariement>();
        for(int i=0; i<VisualDataAttribute.size(); i++){
            a : for(int j=0; j<DataAttribteList.size(); j++){
            if(VisualDataAttribute.get(i).getType().toString().equals(DataAttribteList.get(j).getType())){
            //NewMatchingListResult.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance(), maListe.get(j).getName(), maListe.get(j).getType(), maListe.get(j).getImportance()});
            Appariement indivMec = new Appariement();
            indivMec.setName_v_data(VisualDataAttribute.get(i).getName());
            indivMec.setType_v_data(VisualDataAttribute.get(i).getType());
            indivMec.setImportance_v_data(VisualDataAttribute.get(i).getImportance());
            indivMec.setName_data(DataAttribteList.get(j).getName());
            indivMec.setType_data(DataAttribteList.get(j).getName());
            indivMec.setImportance_data(DataAttribteList.get(j).getImportance());
            NewMatchingListResult.add(indivMec);
            DataAttribteList.remove(j);
            break a;
            }
            }
        }

        return NewMatchingListResult;

    }

    /*public List<Appariement> getIndividu(List<Visualisation> maListe, List<Visualisation> listdtm) throws Exception{

        for(int i=0; i<listdtm.size(); i++){
            a : for(int j=0; j<maListe.size(); j++){
            if(listdtm.get(i).getType().toString().equals(maListe.get(j).getType())){
            Appariement indivMec = new Appariement();
            indivMec.setName_v_data(listdtm.get(i).getName());
            indivMec.setType_v_data(listdtm.get(i).getType());
            indivMec.setImportance_v_data(listdtm.get(i).getImportance());
            indivMec.setName_data(maListe.get(j).getName());
            indivMec.setType_data(maListe.get(j).getType());
            indivMec.setImportance_data(maListe.get(j).getImportance());
            individuMEC.add(indivMec);
            maListe.remove(j);
            break a;
            }
            }

        }

        for(int i=0; i<individuMEC.size(); i++){
            System.out.println(individuMEC.get(i).getName_data()+" :::::::::: "+individuMEC.get(i).getName_v_data());
            }

        
    return individuMEC;
    }*/

    public List<Visualisation> getListeTri(List<Visualisation> list) throws Exception{

        for(int j=0; j<list.size();j++){
            if(list.get(j).getType().toString().equals(VRMXML.NUMERIC_TYPE_NAME)){
                Visualisation numerique = new Visualisation();
                numerique.setName(list.get(j).getName());
                numerique.setType(list.get(j).getType());
                numerique.setImportance(list.get(j).getImportance());
                list_numeric.add(numerique);
             }
            else{
              if(list.get(j).getType().toString().equals(VRMXML.SYMBOLIC_TYPE_NAME)){
                Visualisation symbolqiue = new Visualisation();
                symbolqiue.setName(list.get(j).getName());
                symbolqiue.setType(list.get(j).getType());
                symbolqiue.setImportance(list.get(j).getImportance());
                list_symbolic.add(symbolqiue);
             }
              else{
                if(list.get(j).getType().toString().equals(VRMXML.TEXT_TYPE_NAME)){
                Visualisation texte = new Visualisation();
                texte.setName(list.get(j).getName());
                texte.setType(list.get(j).getType());
                texte.setImportance(list.get(j).getImportance());
                list_text.add(texte);
             }

              else{
               if(list.get(j).getType().toString().equals(VRMXML.TEMPORAL_TYPE_NAME)){
                Visualisation num_temp = new Visualisation();
                num_temp.setName(list.get(j).getName());
                num_temp.setType(list.get(j).getType());
                num_temp.setImportance(list.get(j).getImportance());
                list_temporal.add(num_temp);
             }
              else{
               if(list.get(j).getType().toString().equals(VRMXML.IMAGE_TYPE_NAME)){
                Visualisation image = new Visualisation();
                image.setName(list.get(j).getName());
                image.setType(list.get(j).getType());
                image.setImportance(list.get(j).getImportance());
                list_image.add(image);
             }
              else{
              if(list.get(j).getType().toString().equals(VRMXML.SOUND_TYPE_NAME)){
                Visualisation sound = new Visualisation();
                sound.setName(list.get(j).getName());
                sound.setType(list.get(j).getType());
                sound.setImportance(list.get(j).getImportance());
                list_sound.add(sound);
             }
              else{
              if(list.get(j).getType().toString().equals(VRMXML.LINK_TYPE_NAME)){
                Visualisation edge = new Visualisation();
                edge.setName(list.get(j).getName());
                edge.setType(list.get(j).getType());
                edge.setImportance(list.get(j).getImportance());
                list_edge.add(edge);
             }
              else{
                Visualisation file = new Visualisation();
                file.setName(list.get(j).getName());
                file.setType(list.get(j).getType());
                file.setImportance(list.get(j).getImportance());
                list_file.add(file);
              }
            }
        }
              }
              }
            }
        }
        }
        this.TriListe(list_numeric);
        this.TriListe(list_symbolic);
        this.TriListe(list_text);
        this.TriListe(list_temporal);
        this.TriListe(list_image);
        this.TriListe(list_sound);
        this.TriListe(list_edge);
        this.TriListe(list_file);
        list.clear();
        for(int i=0; i<list_numeric.size(); i++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_numeric.get(i).getName());
        Xml.setType(list_numeric.get(i).getType());
        Xml.setImportance(list_numeric.get(i).getImportance());
        list.add(Xml);
        }
        list_numeric.clear();
        for(int j=0; j<list_symbolic.size(); j++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_symbolic.get(j).getName());
        Xml.setType(list_symbolic.get(j).getType());
        Xml.setImportance(list_symbolic.get(j).getImportance());
        list.add(Xml);
        }
        list_symbolic.clear();
        for(int v=0; v<list_text.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_text.get(v).getName());
        Xml.setType(list_text.get(v).getType());
        Xml.setImportance(list_text.get(v).getImportance());
        list.add(Xml);
        }
        list_text.clear();
        for(int v=0; v<list_temporal.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_temporal.get(v).getName());
        Xml.setType(list_temporal.get(v).getType());
        Xml.setImportance(list_temporal.get(v).getImportance());
        list.add(Xml);
        }
        list_temporal.clear();
        for(int v=0; v<list_image.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_image.get(v).getName());
        Xml.setType(list_image.get(v).getType());
        Xml.setImportance(list_image.get(v).getImportance());
        list.add(Xml);
        }
        list_image.clear();
        for(int v=0; v<list_sound.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_sound.get(v).getName());
        Xml.setType(list_sound.get(v).getType());
        Xml.setImportance(list_sound.get(v).getImportance());
        list.add(Xml);
        }
        list_sound.clear();
        for(int v=0; v<list_edge.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_edge.get(v).getName());
        Xml.setType(list_edge.get(v).getType());
        Xml.setImportance(list_edge.get(v).getImportance());
        list.add(Xml);
        }
        list_edge.clear();
        for(int v=0; v<list_file.size(); v++){
        Visualisation Xml = new Visualisation();
        Xml.setName(list_file.get(v).getName());
        Xml.setType(list_file.get(v).getType());
        Xml.setImportance(list_file.get(v).getImportance());
        list.add(Xml);
        }
        list_file.clear();

     return list;
     }

    public List<Visualisation> TriListe(List<Visualisation> list) throws Exception{

        //Trier le tableau des attributs de type Numerique
        List<Visualisation> liste_permut = new ArrayList();
        int k =0;
        while(k < list.size())
	{
        for(int x = list.size()-1; x >= k +1;x--){
            if((list.get(x).getImportance() == list.get(x-1).getImportance())||(list.get(x).getImportance() < list.get(x-1).getImportance())){
            //ne rien faire
            }
            else if (list.get(x).getImportance() > list.get(x-1).getImportance()){
            // permuter de position pour les attributs
            liste_permut.add(list.get(x-1));
            list.remove(x-1);
            list.add(liste_permut.get(0));
            liste_permut.remove(0);
            }
        }
        k++;
        }

         return list;
    }

    public List<Visualisation> getListe(String xml) throws Exception{
        List<Visualisation> xmlListe = new ArrayList<Visualisation>();
        SAXBuilder sxb = new SAXBuilder();
        document1 = sxb.build(new File(xml));
        racine = document1.getRootElement();
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

    public List<String> getProfiListe(String xml) throws Exception{
        List<String> profil = new ArrayList<String>();
        SAXBuilder sxb = new SAXBuilder();
        document1 = sxb.build(xml);
        racine = document1.getRootElement();
        List listAttributes = racine.getChild("visualizations").getChildren("nuage3D");
        Iterator i = listAttributes.iterator();

        while(i.hasNext()) {
            Element courant = (Element)i.next();
            profil.add(courant.getChild("profil").getAttributeValue("valeur"));
        }

        return profil;
    }


}
