package VisualAssistantFDM.io;



import VisualAssistantFDM.visualisation.DataBase.Connexion;
import VisualAssistantFDM.visualisation.DataBase.TypeAttribut;
import VisualAssistantFDM.userObjectives.UserPreferences;
import VisualAssistantFDM.visualisation.DataBase.ExpertObjective;
import VisualAssistantFDM.visualisation.DataBase.Methode;
import VisualAssistantFDM.visualisation.DataBase.ElementGraphique;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.visualisation.DataBase.CategorieVisualisation;
import VisualAssistantFDM.visualisation.DataBase.Categorie;
import VisualAssistantFDM.visualisation.DataBase.AttributVisuel;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdelheq
 */
public class LoadVisualizations extends JPanel{
    
    JToolBar buttonBar = new JToolBar();
    JPanel paneload = new JPanel();
    JPanel panematch = new JPanel();
    JLabel photographLabel = new JLabel(); //OverviewPictureContainer
    String sql1 = "Select nom from methode";
    String url = "jdbc:mysql://localhost:3306/assvisual";
    String userName = "root";
    String password = "admin";
    Connection connection;
    ResultSet resultat;
    Statement stmt;
    String imagedir = "images/";
    MissingIcon placeholderIcon = new MissingIcon();
    List<PreView> imageFileNames = new ArrayList<PreView>();
    /*String[] imageCaptions = {"Nuages de points", "Visages de Chernoff", "Stick icons", "Cone Trees", "Tree map",
                                "CoordonnÃ©es parallÃ¨les", "Nuages de points" };*/

    List<String> imageCaptions = new ArrayList<String>();
    int idmethode;


    public LoadVisualizations()throws Exception{
        imageFileNames = this.getImages();
        imageCaptions = this.getCaption();
        }

    public JToolBar getToolBar(JToolBar toolbar){
        toolbar = buttonBar;
        return toolbar;
    }

    public List<PreView> LoadVisualizationsNames()throws Exception{
        List<PreView> imageNames = new ArrayList<PreView>();
        for(int j=0; j<imageFileNames.size(); j++){
        PreView DB_vis = new PreView();
            DB_vis.setNom(imageFileNames.get(j).getNom());
            DB_vis.setIdmethode(imageFileNames.get(j).getIdmethode());
            DB_vis.setIdimage(imageFileNames.get(j).getIdimage());
            imageNames.add(DB_vis);
        }
       return imageNames;
    }

    public List<UserPreferences> LoadUserObjectives() throws ClassNotFoundException
    {
        List<UserPreferences> liste = new ArrayList<UserPreferences>();
        String sql = "SELECT * FROM expertobject"; // 7 puisque c'est les visualisations dÃ©jÃ  implementer.
        try{
        Class.forName("com.mysql.jdbc.Driver");
        connection = (Connection) DriverManager.getConnection(url, userName, password);
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql);
        while(resultat.next()){
            UserPreferences list_userP = new UserPreferences();
            list_userP.setIdObjectif(Integer.parseInt(resultat.getString("idobjectif")));
            list_userP.setDescription(resultat.getString("description"));
            liste.add(list_userP);
            }
        resultat.close();
        connection.close();
        }
        catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "erreur JDBC : " + ex.getMessage());
        }
        return liste;
        }

    /*Recuperer l'identifiacteur de le vecteur de poids des objectifs de la visualisation selectionnée depuis la base de données*/
    public List<UserPreferences> getUserObjectives(int idmethode) throws Exception{
        List<UserPreferences> user_Objectif_Liste = new ArrayList<UserPreferences>();
        String sql_element = "SELECT methode_expert.idobjectif, methode_expert.importance FROM methode_expert , methode , expertobject WHERE methode_expert.idmethode = methode.idmethode AND methode_expert.idobjectif= expertobject.idobjectif AND methode.idmethode ="+idmethode;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           UserPreferences objectif_graph = new UserPreferences();
           objectif_graph.setIdObjectif(Integer.parseInt(resultat.getString("idobjectif")));
           objectif_graph.setImportance(Integer.parseInt(resultat.getString("importance")));
           
           user_Objectif_Liste.add(objectif_graph);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return user_Objectif_Liste;
    }

    //Recuperer les chemins des images Ã  partir de la base de donnÃ©es pour les afficher depuis leur emplacement
     public List<PreView> getImages() throws ClassNotFoundException
    {
        List<PreView> liste = new ArrayList<PreView>();
        String sql = "SELECT * FROM IMAGE"; // 7 puisque c'est les visualisations dÃ©jÃ  implementer.
        try{
        Class.forName("com.mysql.jdbc.Driver");
        connection = (Connection) DriverManager.getConnection(url, userName, password);
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql);
        while(resultat.next()){
            PreView list_view = new PreView();
            list_view.setIdimage(resultat.getInt("idimage"));
            list_view.setIdmethode(resultat.getInt("idmethode"));
            list_view.setNom(resultat.getString("nom"));
            liste.add(list_view);
            }
        resultat.close();
        connection.close();
        }
        catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "erreur JDBC : " + ex.getMessage());
        }
        return liste;
        }

     //Recuperer la description des images Ã  partir de la base de donnÃ©es pour les afficher
     public List<String> getCaption(){
        for(int i=0; i<imageFileNames.size(); i++){
        imageCaptions.add(imageFileNames.get(i).getNom().toString());
        }
        return imageCaptions;
    }

     //Recuperer les paths des images Ã  partir de la base de donnÃ©es pour les afficher depuis leur emplacement sur le disque dur
     public ArrayList getIdMethode()
    {
        ArrayList liste = new ArrayList();
        try{
        connection = (Connection) DriverManager.getConnection(url, userName, password);
        stmt = (Statement) connection.createStatement();
        String sql_Id = "Select idmethode from image";
        resultat = stmt.executeQuery(sql_Id);
        while(resultat.next()){
            liste.add(resultat.getString("idmethode"));
            }
        resultat.close();
        connection.close();
        }
        catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "erreur JDBC : " + ex.getMessage());
        }
        return liste;
        }
    
    public List<Methode> getMethode(int id) throws Exception{
        List<Methode> methodeListe = new ArrayList<Methode>();
        String sql_methode ="Select * from methode where idmethode ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_methode);
        while (resultat.next())
        {
           Methode methode = new Methode();
           methode.setIdmethode(Integer.parseInt(resultat.getString("idmethode")));
           methode.setNom(resultat.getString("nom"));
           methode.setDescription(resultat.getString("description"));
           methode.setLien(resultat.getString("lien"));
           methodeListe.add(methode);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return methodeListe;
    }

    /*Recuperer l'identifiacteur de l'Ã©lement graphique de la mÃ©thode de visualisation selectionnÃ©e depuis la base de donnÃ©es*/
    public List<ElementGraphique> getElementGraphique(int id) throws Exception{
        List<ElementGraphique> elem_graph_Liste = new ArrayList<ElementGraphique>();
        String sql_element = "SELECT elementsgraphiques.* FROM methode_element , methode , elementsgraphiques WHERE methode_element.idmethode = methode.idmethode AND methode_element.idelement= elementsgraphiques.idelement AND methode.idmethode ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           ElementGraphique elem_graph = new ElementGraphique();
           elem_graph.setIdelement(Integer.parseInt(resultat.getString("idelement")));
           elem_graph.setNom(resultat.getString("nom"));
           elem_graph.setEvenement(resultat.getString("evenement"));
           elem_graph_Liste.add(elem_graph);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return elem_graph_Liste;
    }

    //Recuperer les renseignements contenu dans la table des attributs visuels de la mÃ©thode de visualisation selectionnÃ©e depuis la base de donnÃ©es
    public List<AttributVisuel> getAttributs(int id) throws Exception{
        List<AttributVisuel> attributeListe = new ArrayList<AttributVisuel>();
        String sql_attribute ="SELECT attributsvisuels.* FROM element_attribut , elementsgraphiques , attributsvisuels WHERE element_attribut.idelement = elementsgraphiques.idelement AND element_attribut.idattribut= attributsvisuels.idattribut AND elementsgraphiques.idelement="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_attribute);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           AttributVisuel attribute = new AttributVisuel();
           attribute.setIdattribut(Integer.parseInt(resultat.getString("idattribut")));
           attribute.setNom(resultat.getString("nom"));
           attribute.setImportance(Integer.parseInt(resultat.getString("importance")));
           attribute.setNbutilisation(Integer.parseInt(resultat.getString("nbutilisation")));
           attribute.setIlimite(Integer.parseInt(resultat.getString("ilimite")));
           attributeListe.add(attribute);
        }

        //executer la requÃªte sql
        stmt.close();
        connection.close();
        
      return attributeListe;
    }

    //Recuperer le type de donnÃ©es de chaque attributs visuels de la mÃ©thode de visualisation selectionnÃ©e depuis la base de donnÃ©es
    public List<TypeAttribut> getTypeAttribut(int id) throws Exception{
        List<TypeAttribut> typeattributListe = new ArrayList<TypeAttribut>();
        String sql_type_attribute ="SELECT typeattribut.* FROM attr_typattribut , attributsvisuels , typeattribut WHERE attr_typattribut.idattribut = attributsvisuels.idattribut AND attr_typattribut.idtypattribut= typeattribut.idtypattribut AND attributsvisuels.idattribut ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_type_attribute);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           TypeAttribut type = new TypeAttribut();
           type.setIdtypattribut(Integer.parseInt(resultat.getString("idtypattribut")));
           type.setTypattribut(resultat.getString("typattribut"));
           typeattributListe.add(type);
        }

        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return typeattributListe;
    }

    //Recuperer l'identifiacteur de la catégorie de la métthode de visualisation selectionnée depuis la base de données
    public List<ExpertObjective> getExpertObjective(int id) throws Exception{
        List<ExpertObjective> expert_objective_Liste = new ArrayList<ExpertObjective>();
        String sql_element = "SELECT expertobject.* FROM methode_expert , methode , expertobject WHERE methode_expert.idmethode = methode.idmethode AND methode_expert.idobjectif= expertobject.idobjectif AND methode.idmethode ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           ExpertObjective expert = new ExpertObjective();
           expert.setIdobjectif(Integer.parseInt(resultat.getString("idobjectif")));
           expert.setDescription(resultat.getString("description"));
           expert_objective_Liste.add(expert);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return expert_objective_Liste;
    }

    //Recuperer l'identifiacteur de la catégorie de la métthode de visualisation selectionnée depuis la base de données
    public List<ExpertObjective> getAllExpertObjective() throws Exception{
        List<ExpertObjective> expert_objective_Liste = new ArrayList<ExpertObjective>();
        String sql_element = "SELECT * FROM expertobject";
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           ExpertObjective expert = new ExpertObjective();
           expert.setIdobjectif(Integer.parseInt(resultat.getString("idobjectif")));
           expert.setDescription(resultat.getString("description"));
           expert_objective_Liste.add(expert);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return expert_objective_Liste;
    }

    //Recuperer l'identifiacteur de la catégorie de la métthode de visualisation selectionnée depuis la base de données
    public List<Categorie> getCategorie(int id) throws Exception{
        List<Categorie> categorie_Liste = new ArrayList<Categorie>();
        String sql_element = "SELECT categorie.* FROM methode_categorie , methode , categorie WHERE methode_categorie.idmethode = methode.idmethode AND methode_categorie.idcategorie= categorie.idcategorie AND methode.idmethode ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           Categorie categ = new Categorie();
           categ.setIdcategorie(Integer.parseInt(resultat.getString("idcategorie")));
           categ.setNom(resultat.getString("nom"));
           categ.setDescription(resultat.getString("description"));
           categorie_Liste.add(categ);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return categorie_Liste;
    }

    //Recuperer l'identifiacteur de la catégorie de la métthode de visualisation selectionnée depuis la base de données
    public List<Categorie> getAllCategorie() throws Exception{
        List<Categorie> categorie_Liste = new ArrayList<Categorie>();
        String sql_element = "SELECT * FROM categorie";
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           Categorie categ = new Categorie();
           categ.setIdcategorie(Integer.parseInt(resultat.getString("idcategorie")));
           categ.setNom(resultat.getString("nom"));
           categ.setDescription(resultat.getString("description"));
           categorie_Liste.add(categ);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return categorie_Liste;
    }

    //Recuperer l'identifiacteur de la catégorie visuelle de la mÃ©thode de visualisation selectionnÃ©e depuis la base de donnÃ©es
    public List<CategorieVisualisation> getCategorieVisualisation(int id) throws Exception{
        List<CategorieVisualisation> categorievisual_Liste = new ArrayList<CategorieVisualisation>();
        String sql_element = "SELECT categorievisualisation.* FROM methode_categvisual , methode , categorievisualisation WHERE methode_categvisual.idmethode = methode.idmethode AND methode_categvisual.idcategorievisual= categorievisualisation.idcategorievisual AND methode.idmethode ="+id;
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           CategorieVisualisation categvisual = new CategorieVisualisation();
           categvisual.setIdcategorievisual(Integer.parseInt(resultat.getString("idcategorievisual")));
           categvisual.setNom(resultat.getString("nom"));
           categorievisual_Liste.add(categvisual);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return categorievisual_Liste;
    }

    //Recuperer l'identifiacteur de la catégorie visuelle de la mÃ©thode de visualisation selectionnÃ©e depuis la base de donnÃ©es
    public List<CategorieVisualisation> getAllCategorieVisualisation() throws Exception{
        List<CategorieVisualisation> categorievisual_Liste = new ArrayList<CategorieVisualisation>();
        String sql_element = "SELECT * FROM categorievisualisation";
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_element);
        //recuperer l'ensemble des valeurs
        while (resultat.next())
        {
           CategorieVisualisation categvisual = new CategorieVisualisation();
           categvisual.setIdcategorievisual(Integer.parseInt(resultat.getString("idcategorievisual")));
           categvisual.setNom(resultat.getString("nom"));
           categorievisual_Liste.add(categvisual);
        }
        //executer la requéte sql
        stmt.close();
        connection.close();

      return categorievisual_Liste;
    }

    public List<Visualisation> getIdMethode(int id) throws Exception {

        List<Methode> methode_Liste = new ArrayList<Methode>();
        List<ElementGraphique> element_Liste = new ArrayList<ElementGraphique>();
        List<AttributVisuel> attribute_Liste = new ArrayList<AttributVisuel>();
        List<TypeAttribut> type_Liste = new ArrayList<TypeAttribut>();
        List<Visualisation> listAttribVisuelMEC = new ArrayList<Visualisation>();
        methode_Liste = getMethode(id);
        element_Liste = getElementGraphique(methode_Liste.get(0).getIdmethode());
        for(int j=0; j<element_Liste.size();j++){
            attribute_Liste = getAttributs(element_Liste.get(0).getIdelement());
            for(int n=0; n<attribute_Liste.size();n++){
                type_Liste = getTypeAttribut(attribute_Liste.get(n).getIdattribut());
                for(int m=0; m<type_Liste.size();m++){
                    Visualisation ListMEC = new Visualisation();
                    ListMEC.setName(attribute_Liste.get(n).getNom());
                    ListMEC.setType(type_Liste.get(m).getTypattribut());
                    ListMEC.setImportance(attribute_Liste.get(n).getImportance());
                    listAttribVisuelMEC.add(ListMEC);
                }
            }
        }

        return listAttribVisuelMEC;
    }
    
    public String getIdElement(int id) throws Exception {

        List<Methode> methode_Liste = new ArrayList<Methode>();
        List<ElementGraphique> element_Liste = new ArrayList<ElementGraphique>();
        //List<AttributVisuel> attribute_Liste = new ArrayList<AttributVisuel>();
        String elementGraphique;
        methode_Liste = getMethode(id);
        element_Liste = getElementGraphique(methode_Liste.get(0).getIdmethode());
        elementGraphique = element_Liste.get(0).getNom().toString();
        return elementGraphique;
    }

      public List<Methode> getAllMethode() throws Exception{
        List<Methode> methodeListe = new ArrayList<Methode>();
        String sql_methode ="Select * from methode";
        this.connection = (Connection) Connexion.getConnexion();
        stmt = (Statement) connection.createStatement();
        resultat = stmt.executeQuery(sql_methode);
        while (resultat.next())
        {
           Methode methode = new Methode();
           methode.setIdmethode(Integer.parseInt(resultat.getString("idmethode")));
           methode.setNom(resultat.getString("nom"));
           methode.setDescription(resultat.getString("description"));
           methode.setLien(resultat.getString("lien"));
           methodeListe.add(methode);
        }
        //executer la requÃªte sql
        stmt.close();
        connection.close();

      return methodeListe;
    }

}

