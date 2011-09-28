package visualassistantfacv;




import VisualAssistantFDM.io.LoadVisualizations;
import VisualAssistantFDM.io.FiltreExtensible;
import VisualAssistantFDM.ui.VisualizationForm;
import VisualAssistantFDM.xml.UpdateXMLFile;
import VisualAssistantFDM.visualisation.ui.Matching;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.visualisation.ui.Appariement;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import visualisation3d.vrmNuage3D.Visualisation_Nuage_3D;
import visualisation3d.xml.NUAGE3D;
import visualisation3d.xml.Nuage3DVisuXMLReader;
import vrminerlib.core.VRMinerFramework;
import vrminerlib.io.VRMXML;
import vrminerlib.io.VRMXMLReader;
import vrminerlib.utils.MessageBox;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IHM_Visual_Assistant.java
 *
 * Created on 8 nov. 2010, 09:56:45
 */



/**
 *
 * @author Abdelheq
 */
public class IHM_Visual_Assistant1 extends javax.swing.JFrame {

    private MissingIcon placeholderIcon = new MissingIcon();
    private List<PreView> VisualisationsFilesNames = new ArrayList<PreView>();
    private List<String> imageCaptions = new ArrayList<String>();
    //private List<Integer> mec;
    private List<Visualisation> liste, listdtm, listAttribute, maListe, individu;
    private int idmethode, profilNum;
    public String filePathName, imagedir = "images/";
    private String colNameDataSet[] = {"Indice Visuel", "Importance"}, colNameDataSetMEC[] = {"Indice Visuel", "Attribut de donn�es"};
    private Object[][] data = null;
    private DefaultTableModel DataSetTableModel = new DefaultTableModel(data,colNameDataSet);
    private DefaultTableModel MatchingTableModel, MatchingTableModelMEC, model_list_profil;
    private JTable DataSetTable = new JTable();
    private JTable MatchingTable = new JTable();
    public Document document;
    public Element racine;
    private List<Appariement> individuMEC;
    private double similarite;
    public Visualisation_Nuage_3D visu3D;
    private Nuage3DVisuXMLReader xmlParser;
    private boolean init = false;
    private ArrayList listTypeVisu = new ArrayList();
    private JTabbedPane AdvancedSettings = new JTabbedPane();
    private Color[] axisColor;
    // Variables declaration - Clustering - 
    private javax.swing.JCheckBox ClustVisib;
    private javax.swing.JCheckBox Clusturing;
    private javax.swing.JCheckBox EclairageSphere;
    private javax.swing.JButton ToggleButton;
    private javax.swing.JComboBox attClust;
    private javax.swing.JLabel attClustLabel;
    private javax.swing.JSlider clustFact;
    private javax.swing.JLabel clustFactLabel;
    private javax.swing.JComboBox clustMethode;
    private javax.swing.JLabel clustMethodeLabel;
    private javax.swing.JComboBox clustObjet;
    private javax.swing.JLabel clustObjetLabel;
    private javax.swing.JLabel jLabelClust;
    private javax.swing.JSlider objSize3;
    private javax.swing.JLabel objSize3Label;
    private javax.swing.JLabel size3;
    private javax.swing.JLabel slideClust;
    // Variables declaration - NetWork - 
    private javax.swing.JLabel Adr_IP;
    private javax.swing.JLabel Adr_IPLabel;
    private javax.swing.JButton BkgdImgChooser2;
    private javax.swing.JButton Connexion;
    private javax.swing.JTextField adrsIpServer;
    private javax.swing.JLabel adrsIpServerLabel;
    private javax.swing.JTextField cheminLecteur;
    private javax.swing.JLabel cheminLecteurLabel;
    private javax.swing.JCheckBox jCheckBoxReseau;
    // Variables declaration - Axes -
    private javax.swing.JLabel LocLabel;
    private javax.swing.JCheckBox afficherLiensCheckBox;
    private javax.swing.JComboBox attLiens;
    private javax.swing.JLabel attLiensLabel;
    private javax.swing.JComboBox camType;
    private javax.swing.JLabel camTypeLabel;
    private javax.swing.JPanel panelAxes;
    private javax.swing.JPanel panelCamera;
    private javax.swing.JTextField xColorView;
    private javax.swing.JSpinner xLoc;
    private javax.swing.JLabel xLocLabel;
    private javax.swing.JSpinner xRatio;
    private javax.swing.JButton xRatioBtn;
    private javax.swing.JLabel xRatioLabel;
    private javax.swing.JTextField yColorView;
    // Variables declaration - Camera -
    private javax.swing.JSpinner yLoc;
    private javax.swing.JLabel yLocLabel;
    private javax.swing.JSpinner yRatio;
    private javax.swing.JButton yRatioBtn;
    private javax.swing.JLabel yRatioLabel;
    private javax.swing.JTextField zColorView;
    private javax.swing.JSpinner zLoc;
    private javax.swing.JLabel zLocLabel;
    private javax.swing.JSpinner zRatio;
    private javax.swing.JButton zRatioBtn;
    private javax.swing.JLabel zRatioLabel;
    // Variables declaration - Object3D
    private javax.swing.JButton ButtonEchelleCouleursBas;
    private javax.swing.JButton ButtonEchelleCouleursHaut;
    private javax.swing.JButton ButtonPyr1Color1;
    private javax.swing.JButton ButtonPyr1Color2;
    private javax.swing.JButton ButtonPyr2Color1;
    private javax.swing.JButton ButtonPyr2Color2;
    private javax.swing.JTextField Color1;
    private javax.swing.JTextField Color2;
    private javax.swing.JTextField Color3;
    private javax.swing.JTextField Color4;
    private javax.swing.JLabel JDrapeau;
    private javax.swing.JLabel Pyr1Color1Label;
    private javax.swing.JLabel Pyr1Color2Label;
    private javax.swing.JLabel Pyr2Color1Label;
    private javax.swing.JLabel Pyr2Color2Label;
    private javax.swing.JComboBox attSynthese;
    private javax.swing.JLabel attSyntheseLabel;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider objSize;
    private javax.swing.JLabel objSizeLabel;
    private javax.swing.JLabel size;
    private javax.swing.JComboBox voix;
    private javax.swing.JLabel voixLabel;
    // Variables declaration - Espace 3D -
    private javax.swing.JTextField Color5;
    private javax.swing.JLabel Color5Label;
    private javax.swing.JButton Color5btn;
    private javax.swing.JLabel DistanceLabel;
    private javax.swing.JLabel DistanceLabel1;
    private javax.swing.JSlider SliderYeux;
    private javax.swing.JLabel SliderYeuxLabel;
    private javax.swing.JLabel TailleLabel;
    private javax.swing.JLabel TailleLabel1;
    private javax.swing.JTextField TextFieldDistance;
    private javax.swing.JTextField TextFieldTaille;
    private javax.swing.JTextField bkgdColorView;
    private javax.swing.JLabel bkgdColorViewLabel;
    private javax.swing.JButton bkgdColorViewbtn;
    private javax.swing.JCheckBox grilleTerrain;
    private javax.swing.JCheckBox jCheckBoxStereo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JSlider objSize2;
    private javax.swing.JCheckBox popUpInfo;
    private javax.swing.JLabel size1;
    private javax.swing.JLabel size1Label;
    private javax.swing.JLabel size2;
    // End of variables declaration

    
    /** Creates new form IHM_Visual_Assistant */
    public IHM_Visual_Assistant1() throws Exception{
        VRMinerFramework.initializeFramework();
        initComponents();
        //VisualisationsFilesNames = new LoadVisualizations().LoadVisualizationsNames();
        loadimages.execute();
        
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        VisualizationPanel = new javax.swing.JPanel();
        VisualizationOverviewLabel = new javax.swing.JLabel();
        LaunchVisualizations = new javax.swing.JButton();
        AdjustIECButton = new javax.swing.JButton();
        AddNewProfil = new javax.swing.JButton();
        matching = new javax.swing.JButton();
        Load = new javax.swing.JButton();
        TauxSimilarite = new javax.swing.JProgressBar();
        OverviewPictureContainer = new javax.swing.JScrollPane();
        CloseButton = new javax.swing.JButton();
        DeleteProfilButton = new javax.swing.JButton();
        DB_Visualisations_ScrollPane = new javax.swing.JScrollPane(DB_VisualizationToolBar);
        DB_VisualizationToolBar = new javax.swing.JToolBar();
        statusBar = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INTERACTIVE USER VISUAL ASSISTANT ");
        setResizable(false);

        VisualizationPanel.setBorder(BorderFactory.createTitledBorder("Visualization"));

        VisualizationOverviewLabel.setText("Overview : ");

        LaunchVisualizations.setText("Launch");
        LaunchVisualizations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LaunchVisualizationsActionPerformed(evt);
            }
        });

        AdjustIECButton.setText("Adjust Matching Result");
        AdjustIECButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdjustIECButtonActionPerformed(evt);
            }
        });

        AddNewProfil.setText("Add New Profils");
        AddNewProfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewProfilActionPerformed(evt);
            }
        });

        matching.setText("Matching");
        matching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchingActionPerformed(evt);
            }
        });

        Load.setText("Load Data");
        Load.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadActionPerformed(evt);
            }
        });

        OverviewPictureContainer.setPreferredSize(new java.awt.Dimension(100, 120));

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        DeleteProfilButton.setText("Delete");
        DeleteProfilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteProfilButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VisualizationPanelLayout = new javax.swing.GroupLayout(VisualizationPanel);
        VisualizationPanel.setLayout(VisualizationPanelLayout);
        VisualizationPanelLayout.setHorizontalGroup(
            VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisualizationPanelLayout.createSequentialGroup()
                .addGroup(VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VisualizationPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(VisualizationOverviewLabel))
                    .addGroup(VisualizationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Load, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(matching)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddNewProfil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AdjustIECButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LaunchVisualizations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeleteProfilButton, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CloseButton))
                    .addGroup(VisualizationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(OverviewPictureContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE))
                    .addGroup(VisualizationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TauxSimilarite, javax.swing.GroupLayout.DEFAULT_SIZE, 1189, Short.MAX_VALUE)))
                .addContainerGap())
        );
        VisualizationPanelLayout.setVerticalGroup(
            VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisualizationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisualizationOverviewLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OverviewPictureContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TauxSimilarite, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Load, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteProfilButton)
                    .addComponent(matching)
                    .addComponent(AddNewProfil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdjustIECButton)
                    .addComponent(LaunchVisualizations)
                    .addComponent(CloseButton)))
        );

        LaunchVisualizations.getAccessibleContext().setAccessibleParent(VisualizationPanel);
        AdjustIECButton.getAccessibleContext().setAccessibleParent(VisualizationPanel);
        AddNewProfil.getAccessibleContext().setAccessibleParent(VisualizationPanel);
        TauxSimilarite.setValue((int)similarite);
        TauxSimilarite.setStringPainted(true);

        DB_Visualisations_ScrollPane.setPreferredSize(new Dimension(1000,3005));
        DB_Visualisations_ScrollPane.setViewportView(DB_VisualizationToolBar);
        DB_Visualisations_ScrollPane.setBorder(BorderFactory.createTitledBorder("Visualizations candidate"));

        DB_VisualizationToolBar.setRollover(true);
        DB_Visualisations_ScrollPane.setViewportView(DB_VisualizationToolBar);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1237, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisualizationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DB_Visualisations_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisualizationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DB_Visualisations_ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        statusBar.setEnabled(false);
        statusBar.setDoubleBuffered(true);
        statusBar.setText("VRMinerVisualAssistant - V1.0 (Janvier 2010)");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadActionPerformed
       
        Clusturing.setSelected(true);
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FiltreExtensible Filtre = new FiltreExtensible("Fichier xml");
        Filtre.addExtension(".xml");
        chooser.addChoosableFileFilter(Filtre);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            DataSetTableModel.setRowCount(0);
            filePathName = chooser.getSelectedFile().getAbsolutePath().toString();
            //filePath.setText(chooser.getSelectedFile().getAbsolutePath());
            try{
            liste = new Matching().getListe(filePathName);
            liste = new Matching().getListeTri(liste);
            for(int i=0; i<liste.size();i++){
                DataSetTableModel.addRow(new Object[]{liste.get(i).getName(),liste.get(i).getImportance(),liste.get(i).getType()});
            }
            DataSetTable.setModel(DataSetTableModel);
            final File file;
            file = chooser.getSelectedFile();
            Thread t = new Thread() {
                            private Nuage3DVisuXMLReader xml;

                            @Override
                            public void run() {

                                try {
                                    String f = file.getAbsolutePath();
                                    xml = new Nuage3DVisuXMLReader(f);
                                    xml.setNameFile(file.getName());
                                    xml.readXmlFile();
                                    xml.parseData();
                                    xml.setParsed(true);
                                    xmlParser = xml;
				}
                                catch (Exception ex) {
                                    xml.getLoading().dispose();
                                    ex.printStackTrace();
                                    new MessageBox("Erreur XML", "Erreur de Lecture du fichier XML", MessageBox.ERROR);

                                }
                            }
                        };
                        t.start();
            }
            catch(Exception ex){
                System.out.println("visualizations dispo ASSISTANT: ");
            }
            
            //System.out.println("Nom du fichier : "+a);
//                        for(int i=0; i<listTypeVisu.size(); i++){
//                        if(listTypeVisu.get(i).equals(VRMXML.VISUALIZATIONS_ELEMENT_NAME)){
//                            System.out.println("visualizations dispo ASSISTANT: " + listTypeVisu.get(i).toString());
//                        }
//                        }

              //System.out.println("visualizations dispo ASSISTANT: " + getTypeVisu());
//            List listVisu = racine.getChildren(VRMXML.VISUALIZATIONS_ELEMENT_NAME);
//            Iterator n = listVisu.iterator();
//
//            int a = 0;
//            while (n.hasNext()) {
//            Element visualisation = (Element) n.next();
//            visualisation = (Element) listVisu.get(a);
//            a++;
//            List list = visualisation.getChildren();
//            Iterator o = list.iterator();
//
//            while (o.hasNext()) {
//                Element type = (Element) o.next();
//                listTypeVisu.add(type.getName());
//
//            }
//
//            }
//
//            //utiliser le xmlParser pour remplir les champs de la partie User Preferences
//            if (!xmlParser.getTypeVisu().isEmpty()) {
//                System.out.println("IS NOT EMPTY");
//                //fillVisuTabForm(xmlParser.getTypeVisu());
//            } else {
//                System.out.println("IS EMPTY");
//                //fillDefaultNuage3D();
//            }
        }
    }//GEN-LAST:event_LoadActionPerformed

    public ArrayList getTypeVisu() {
            return listTypeVisu;
    }

    private void matchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchingActionPerformed
        try{
        //récuperer la description des attributs de données à partir de la partie structure du fichier XML
        liste = new Matching().getListe(filePathName);
        //trier les attributs de données selon leur type puis leur importance
        liste = new Matching().getListeTri(liste);
        //récuperer le matching attributs de données / attributs visuels
        MatchingTableModel = new Matching().getMatchingResult(liste, listdtm);
        //initialiser le DefaultTableModel pour viusaliser le résultat du matching attributs de données / attributs visuels
        MatchingTableModelMEC = new DefaultTableModel(data, colNameDataSetMEC);
        //initialiser la liste du matching attributs de données / attributs visuels pour dérouler l'alogorithme génétique
        individuMEC = new ArrayList<Appariement>();
        for(int i=0; i<MatchingTableModel.getRowCount(); i++){
        MatchingTableModelMEC.addRow(new Object[]{MatchingTableModel.getValueAt(i, 0).toString(), MatchingTableModel.getValueAt(i, 3).toString()});
        Appariement indivMec = new Appariement();
        indivMec.setName_v_data(MatchingTableModel.getValueAt(i, 0).toString());
        indivMec.setType_v_data(MatchingTableModel.getValueAt(i, 1).toString());
        String w = MatchingTableModel.getValueAt(i, 3).toString();
        indivMec.setImportance_v_data(Integer.valueOf(MatchingTableModel.getValueAt(i, 2).toString()));
        indivMec.setName_data(MatchingTableModel.getValueAt(i, 3).toString());
        indivMec.setType_data(MatchingTableModel.getValueAt(i, 4).toString());

        indivMec.setImportance_data(Integer.valueOf(MatchingTableModel.getValueAt(i, 5).toString()));

        individuMEC.add(indivMec);
        }
        //afficher résultat du matching
        MatchingTable.setModel(MatchingTableModelMEC);
        int somme = 0;
        similarite = 0;
        for(int i=0; i<individuMEC.size(); i++){
            System.out.println(individuMEC.get(i).getImportance_data()+"  ::  "+individuMEC.get(i).getImportance_v_data());
            somme = (Integer.valueOf(individuMEC.get(i).getImportance_v_data()))/(Integer.valueOf(individuMEC.get(i).getImportance_data()));
            similarite = similarite+somme;
        }

        //similarite = (similarite/individuMEC.size())*100;
        similarite = (similarite/listdtm.size())*100;
        TauxSimilarite.setValue((int) similarite);
        TauxSimilarite.setStringPainted(true);
        }
        catch(Exception ex){
          JOptionPane.showMessageDialog(new JOptionPane(), "You must load data before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_matchingActionPerformed

    private void AddNewProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewProfilActionPerformed

        try{

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FiltreExtensible Filtre = new FiltreExtensible("Fichier xml");
        Filtre.addExtension(".xml");
        chooser.addChoosableFileFilter(Filtre);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        filePathName = chooser.getSelectedFile().getAbsolutePath().toString();
        System.out.println(filePathName);
        SAXBuilder sxb = new SAXBuilder();
        document = sxb.build(filePathName);
        racine = document.getRootElement();
        Element visu = (Element) racine.getChild("visualizations");
        int x = 0;
        x = (visu.getChild("nuage3D").getContentSize()-2)-1;
        System.out.println(x);
        File file = new File(filePathName);
        String shape = new LoadVisualizations().getIdElement(idmethode);
        //r�cuperer tous les attributs visuels de CUBE_CHANEL v2 pour toutes les formes de nuages 3D
        listAttribute = new LoadVisualizations().getIdMethode(1);
        listdtm = new Matching().getListe(filePathName);
        listdtm = new Matching().getListeTri(listdtm);
        for(int n=0; n<individuMEC.size(); n++){
        System.out.println(individuMEC.get(n).getName_data().toString()+" <:> "+individuMEC.get(n).getName_v_data());
        }
        Element el = xmlParser.getXmlVisualisationsElement();
        for(int i=0; i<8; i++){
        AddNewUserPofilSettings(x+i, el, listAttribute, individuMEC, shape);
        //writeNewUserPofilSettings(x+i, "profil0", file, liste, individuMEC);
        enregistreFichier(filePathName);
        }
        }
        }
          catch(Exception ex){
            JOptionPane.showMessageDialog(new JOptionPane(), "You must load data then matching them with the appropriate visualization before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_AddNewProfilActionPerformed

    private void AdjustIECButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdjustIECButtonActionPerformed
        try {
            //new VisPopulations(filePathName);
            //new IHM_MatchingAdjuset_IEC(individuMEC);
            //VRMinerFramework.initializeFramework()
//           if(visu3D!=null){
//                visu3D.destroy();
//           }
           for(int i=0; i<individuMEC.size(); i++){
               System.out.println(individuMEC.get(i).getName_data().toString()+">>>>>>"+individuMEC.get(i).getName_v_data().toString());
           }
           //new VRMinerVisualAssistant_IGA(individuMEC, filePathName).setVisible(true);
        } catch (Exception ex){
           JOptionPane.showMessageDialog(new JOptionPane(), "You must load data then matching them with the appropriate visualization, then adding result to xml file before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_AdjustIECButtonActionPerformed
    
    private void LaunchVisualizationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LaunchVisualizationsActionPerformed
        try{
            //ApplyNewConfiguration();
        new VisualizationForm(filePathName, profilNum).setVisible(true);
        /* Generer toutes les visualisations comprises dans le xml */
        /*
         *  void Generation_Nuage3D(Container frame) {

        view = new Visualisation_Nuage_3D(EcartementYeux(), Float.parseFloat(jTextFieldTaille.getText()),
                Float.parseFloat(jTextFieldDistance.getText()));

        frame.add(view.getCustomCanvas3D());

        ConfigurationNuage3D(view);

        view.getCustomCanvas3D().setSize(frame.getWidth() - 10, frame.getHeight() - 60);

        view.createScene();

        if (frame instanceof JInternalFrame) {
            ((JInternalFrame) frame).addInternalFrameListener(new InternalFrameAdapter() {

                Scene3D localView = view;

                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    localView.destroy();
                }
            });
        }
    }
         */
        } catch (Exception ex){
            //TODO
        }
        /*
        VRMinerFramework.resetFramework();
        SharedDataObjectsCollections.setAllDataObjectsCollectionManager(new CollectionManager());
        SharedDataObjectsCollections.setDisplayedDataObjectsCollectionManager(new CollectionManager());
        SharedDataObjectsCollections.setSelectedDataObjectsCollectionManager(new CollectionManager());
        int sizeList = model_list_profil_selection.getSize();
        if (sizeList > 0) {
        try {
            int positionX = 0;
            int positionY = 0;
            JInternalFrame frame_interne = null;


            for (nbFrame = 0; nbFrame < model_list_profil_selection.getSize(); nbFrame++) {
                String nom_profil = model_list_profil_selection.get(nbFrame).toString();

                SelectionProfil(nom_profil);

                frame_interne = new JInternalFrame(nom_profil, true, true, true, false);

                frame_interne.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
                frame_interne.setSize(500, 500);

                if (nbFrame == 0) {
                    positionX = 50;
                    positionY = 0;
                }
                if (nbFrame == 1) {
                    positionX = 550;
                    positionY = 0;
                }
                if (nbFrame == 2) {
                    positionX = 50;
                    positionY = 500;
                }
                if (nbFrame == 3) {
                    positionX = 550;
                    positionY = 500;
                }
                if (nbFrame == 4) {
                    positionX = 80;
                    positionY = 30;
                }
                if (nbFrame == 5) {
                    positionX = 580;
                    positionY = 30;
                }
                if (nbFrame == 6) {
                    positionX = 80;
                    positionY = 530;
                }
                if (nbFrame == 7) {
                    positionX = 580;
                    positionY = 530;
                }

                frame_interne.setLocation(positionX, positionY);
                Generation_Nuage3D(frame_interne);
                frame_interne.setVisible(true);
                tabbedDesktopPane.add(frame_interne);
            }
            if (parentContainer instanceof JInternalFrame) {
                JInternalFrame myParentInternalFrame = (JInternalFrame) parentContainer;
                myParentInternalFrame.setIcon(true);
            }



        } catch (Exception e) {

            new MessageBox("Erreur", "Une erreur est survenue lors de la génération multiframe \n " + e.getMessage(), MessageBox.ERROR);
//            frameConfiguration.setState(JFrame.NORMAL);
        }
    }*/
    }//GEN-LAST:event_LaunchVisualizationsActionPerformed

private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
    this.setVisible(false);
}//GEN-LAST:event_CloseButtonActionPerformed

private void DeleteProfilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteProfilButtonActionPerformed
        try {
            new UpdateXMLFile(filePathName);
        } catch (Exception ex) {
            Logger.getLogger(IHM_Visual_Assistant1.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_DeleteProfilButtonActionPerformed
  
    public void writeNewUserPofilSettings(int indexProfil, String ProfilChoisi, File xml, List<Visualisation> list, List<Appariement> ResultMEC){

        SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        try {
        //On crée un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        racine = document.getRootElement();
        //ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionnée pour les axes x, y et z
        float[] tabCoord = new float[ResultMEC.size()];
        tabCoordMax = new float[ResultMEC.size()];
        tabCoordMin = new float[ResultMEC.size()];
        for (int a = 0; a < ResultMEC.size(); a++) {
        tabCoordMax[a] = -1000f;
        tabCoordMin[a] = 1000f;
        }
        List listData = racine.getChildren("data");
        //On crée un Iterator sur notre liste
        Iterator n = listData.iterator();
        while (n.hasNext()) {
        //On recrée l'Element courant à chaque tour de boucle afin de
        //pouvoir utiliser les méthodes propres aux Element comme :
        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = (Element) n.next();
        List listDatum = datum.getChildren("datum");
        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
        Element courant = (Element) j.next();
        //On affiche le nom de l'element courant
        for (int k = 0; k < ResultMEC.size(); k++) {
                //ici on recupere les valeur max et min des attributs selectionnée pour les axes x, y et z
                tabCoord[k] = Float.parseFloat(courant.getChild(""+ResultMEC.get(k).getName_data()).getValue());
                tabCoordMin[k] = (float) Math.min(tabCoord[k], tabCoordMin[k]);
                tabCoordMax[k] = (float) Math.max(tabCoord[k], tabCoordMax[k]);
                }
         }
        }

        //On crée une Liste contenant tous les noeuds "data" de l'Element racine
        Element visu = (Element) racine.getChild("visualizations");
        Element typeVisu = (Element) visu.getChild("nuage3D");
        if (typeVisu == null) {
            visu.addContent(new Element("nuage3D"));
            typeVisu = (Element) visu.getChild("nuage3D");
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");
        //On fait un test sur le profil selectionné par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en créer un dans le fichier xml

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

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();
        

        //Matching result
        for(int i=0; i<individuMEC.size(); i++){
            Element  e = (Element) profil.getChild(individuMEC.get(i).getName_v_data());
            e = new Element(individuMEC.get(i).getName_v_data());
             profil.addContent(e);
             if((e.toString().equals("att3"))||(e.toString().equals("att3"))){
                 e.setText("Pas de texte");
             } else {
             if(e.toString().equals("imgAtt")){
                 e.setText("Pas de texture");
             }
             else {
             e.setText(individuMEC.get(i).getName_data());
             }
             }
        }

        //AXES
        Element xRatio = (Element) profil.getChild("xRatio");
        Element yRatio = (Element) profil.getChild("yRatio");
        Element zRatio = (Element) profil.getChild("zRatio");
        xRatio = new Element("xRatio");
        yRatio = new Element("yRatio");
        zRatio = new Element("zRatio");
        profil.addContent(xRatio);
        profil.addContent(yRatio);
        profil.addContent(zRatio);
        xRatio.setText("3");
        yRatio.setText("3");
        zRatio.setText("3");
        Element xColor = (Element) profil.getChild("xColor");
        Element yColor = (Element) profil.getChild("yColor");
        Element zColor = (Element) profil.getChild("zColor");
        xColor = new Element("xColor");
        yColor = new Element("yColor");
        zColor = new Element("zColor");
        profil.addContent(xColor);
        profil.addContent(yColor);
        profil.addContent(zColor);
        xColor.setText("16711936");
        yColor.setText("65536");
        zColor.setText("16776961");
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
        Element afficherLiens = new Element("afficherLiens");
        profil.addContent(afficherLiens);
        afficherLiens.setText("false");
        Element attributLiens = new Element("attributLiens");
        profil.addContent(attributLiens);
        attributLiens.setText("");

        //OBJET 3D
        Element shape = (Element) profil.getChild("shape");
        Element size = (Element) profil.getChild("size");
        shape = new Element("shape");
        size = new Element("size");
        profil.addContent(shape);
        profil.addContent(size);
        shape.setText("CUBE_CHANEL v2");
        size.setText("137");
        Element attSynth = (Element) profil.getChild("attSynth");
        Element Voix = (Element) profil.getChild("Voix");
        Element listMedia = (Element) profil.getChild("listMedia");
        attSynth = new Element("attSynth");
        Voix = new Element("Voix");
        listMedia = new Element("listMedia");
        profil.addContent(attSynth);
        profil.addContent(Voix);
        profil.addContent(listMedia);

        /*ChoicePanelXml choicePanel = new ChoicePanelXml();
        ArrayList list_Media = choicePanel.getListMedia();

        for (int i = 0; i < list_Media.size(); i++) {
            Element Media = new Element("Media" + i);
            listMedia.addContent(Media);
            Media.setText(list_Media.get(i).toString());
        }*/
        
        attSynth.setText("");
        Voix.setText("Pas de voix");

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
        Color1.setText("-65536");
        Color2.setText("-16711730");
        Color3.setText("-6697729");
        Color4.setText("-13434727");

        //CAMERA
        Element camera = (Element) profil.getChild("camera");
        camera = new Element("camera");
        profil.addContent(camera);
        camera.setText("Clavier et souris");

        Element xLoc = (Element) profil.getChild("xLoc");
        Element yLoc = (Element) profil.getChild("yLoc");
        Element zLoc = (Element) profil.getChild("zLoc");
        xLoc = new Element("xLoc");
        yLoc = new Element("yLoc");
        zLoc = new Element("zLoc");
        profil.addContent(xLoc);
        profil.addContent(yLoc);
        profil.addContent(zLoc);
        xLoc.setText("7.5");
        yLoc.setText("7.5");
        zLoc.setText("7.5");

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

        adrsIpServer.setText("127.0.0.1");
        jCheckBoxReseau.setText("false");
        cheminLecteur.setText("C:/Program Files/Windows Media Player/wmplayer.exe");

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
        jCheckBoxStereo.setText("true");
        jSliderYeux.setText("50");
        jTextFieldTaille.setText("1.70");
        jTextFieldDistance.setText("2");
        bkgdColorView.setText("-16777216");
        grilleTerrain.setText("false");

        //gridEnabled.setText(choicePanel.getGridEnabled());
        popUpInfo.setText("true");
        Color5.setText("-16737793");
        objSize2.setText("90");

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

        Clusturing.setText("false");
        attClust.setText("Petal_width");
        clustFact.setText("100");
        ClustVisib.setText("fals");
        clustMethode.setText("0");
        clustObjet.setText("0");
        EclairageSphere.setText("false");
        objSize3.setText("0");

        // Palettes de couleurs
        Element colorParam = new Element("colorParam");
        profil.addContent(colorParam);
        // On parcourt la liste des attributs
        HashMap<String, HashMap> mapAttributPalette = new HashMap<String, HashMap>();
        Iterator<String> itOverAttribut = mapAttributPalette.keySet().iterator();
        while (itOverAttribut.hasNext()) {
            // On récupère le nom de l'attribut
            String nomAttribut = itOverAttribut.next();
            // Ajout au schéma XML
            Element attribut = new Element(nomAttribut);
            colorParam.addContent(attribut);
            // On parcourt ensuite la palette
            HashMap<String, Color> mapValeurColor = mapAttributPalette.get(nomAttribut);
            Iterator<String> itOverValeur = mapValeurColor.keySet().iterator();
            while (itOverValeur.hasNext()) {
                // On récupère la valeur de l'attribut
                String valeurAttribut = itOverValeur.next();
                // et sa couleur associée
                Color couleur = mapValeurColor.get(valeurAttribut);
                // On créer l'élément XML correspondant
                Element colorAttribute = new Element("color");
                colorAttribute.setAttribute("for", valeurAttribut);
                colorAttribute.setText(couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue());
                // On l'ajoute au schéma XML
                attribut.addContent(colorAttribute);
            }
        }
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
    }


    }



    public void AddNewUserPofilSettings(int indexProfil,Element element, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph){

        //SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        try {
        //On crée un nouveau document JDOM avec en argument le fichier XML
        //document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        //racine = document.getRootElement();
        //ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionnée pour les axes x, y et z
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
        
        float[] tabCoord = new float[list_numerique.size()];
        tabCoordMax = new float[list_numerique.size()];
        tabCoordMin = new float[list_numerique.size()];
        for (int a = 0; a < list_numerique.size(); a++) {
        tabCoordMax[a] = -1000f;
        tabCoordMin[a] = 1000f;
        }
        List listData = racine.getChildren("data");
        //On cr�e un Iterator sur notre liste
        Iterator n = listData.iterator();
        while (n.hasNext()) {
        //On recrée l'Element courant à chaque tour de boucle afin de
        //pouvoir utiliser les méthodes propres aux Element comme :
        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = (Element) n.next();
        List listDatum = datum.getChildren("datum");
        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
        Element courant = (Element) j.next();
        //On affiche le nom de l'element courant
        for (int k = 0; k < list_numerique.size(); k++) {
                //ici on recupere les valeur max et min des attributs selectionnée pour les axes x, y et z
                tabCoord[k] = Float.parseFloat(courant.getChild(""+list_numerique.get(k).getName_data()).getValue());
                tabCoordMin[k] = (float) Math.min(tabCoord[k], tabCoordMin[k]);
                tabCoordMax[k] = (float) Math.max(tabCoord[k], tabCoordMax[k]);
        }
        }
        }
//        for (int k = 0; k < list_symbolique.size(); k++) {
//            symbolique[k] = courant.getChild(""+list_symbolique.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_texte.size(); k++) {
//            texte[k] = courant.getChild(""+list_texte.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_image.size(); k++) {
//            image[k] = courant.getChild(""+list_image.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_temporal.size(); k++) {
//            temporal[k] = courant.getChild(""+list_temporal.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_sound.size(); k++) {
//            sound[k] = courant.getChild(""+list_sound.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_link.size(); k++) {
//            link[k] = courant.getChild(""+list_link.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_file.size(); k++) {
//            file[k] = courant.getChild(""+list_file.get(k).getName_data().toString()).getValue();
//        }
        System.out.println("Size Numeric : "+list_numerique.size());
        System.out.println("Size Symbolic : "+list_symbolique.size());
        System.out.println("Size Text : "+list_texte.size());
        System.out.println("Size Image : "+list_image.size());
        System.out.println("Size Temporal : "+list_temporal.size());
        System.out.println("Size Sound : "+list_sound.size());
        System.out.println("Size Link : "+list_link.size());
        System.out.println("Size File : "+list_file.size());

        //On crée une Liste contenant tous les noeuds "data" de l'Element racine
        //Element visu = (Element) racine.getChild("visualizations");
        Element typeVisu = (Element) element.getChild("nuage3D");
        if (typeVisu == null) {
            element.addContent(new Element("nuage3D"));
            typeVisu = (Element) element.getChild("nuage3D");
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");
        //On fait un test sur le profil selectionné par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en créer un dans le fichier xml

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

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();


        //Matching result
        
        //int compteur = 0;
//        for(int i=0; i<listVisualAtt.size(); i++){
//            Element  e = (Element) profil.getChild(listVisualAtt.get(i).getName());
//            e = new Element(listVisualAtt.get(i).getName());
//            profil.addContent(e);
//            System.out.println("Element : "+listVisualAtt.get(i).getType().toString());
//            if((listVisualAtt.get(i).getName().toString().equals("att3"))||(listVisualAtt.get(i).getName().toString().equals("att4"))&&(compteur >= individuMEC.size())){
//                 e.setText("Pas de texte");
//                 j++;
//            } else if (listVisualAtt.get(i).getName().toString().equals("imgAtt")&&(compteur >= individuMEC.size())) {
//                e.setText("Pas de texture");
//                j++;
//            }
//            // A modifier : att2 dois contenir un attribut de donn�es de type num�rique
//            else if (listVisualAtt.get(i).getName().toString().equals("att2")) {
//                for(int u=0; u<individuMEC.size(); u++){
//                    if(listVisualAtt.get(i).getType().toString().equals(individuMEC.get(u).getType_data().toString())){
//                       e.setText(individuMEC.get(u).getName_data());
//                       u=individuMEC.size();
//                }
//                //e.setText("Sepal_width");
//                j++;
//            }
//            }
//             else {
//             e.setText(individuMEC.get(j).getName_data());
//             j++;
//             }
//            //e.setText(individuMEC.get(j).getName_data());
//            //j++;
//            compteur++;
//            if(j>individuMEC.size()-1){
//            j=0;
//            }
//
//        }
        int j = 0;
        for(int index=0; index<listVisualAtt.size(); index++){

            Element  e = (Element) profil.getChild(listVisualAtt.get(index).getName());
            e = new Element(listVisualAtt.get(index).getName());
            profil.addContent(e);
            //System.out.println("index = : "+index+"j = : "+j+"Type of data attribute : "+listVisualAtt.get(index).getType().toString());
            if((listVisualAtt.get(index).getType().toString().equals(VRMXML.NUMERIC_TYPE_NAME))){
                
                if(j<list_numerique.size()){
                    //System.out.println("index = : "+index+"j = : "+j+"Type of data attribute : "+listVisualAtt.get(index).getType().toString());
                    e.setText(list_numerique.get(j).getName_data());
                    j++;
                } else {
                    j=0;
                    e.setText(list_numerique.get(j).getName_data());
                }
            }

            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.SYMBOLIC_TYPE_NAME)){
                if(j<list_symbolique.size()){
                    e.setText(list_symbolique.get(j).getName_data());
                    j++;
                 } else {
                    j=0;
                    e.setText(list_symbolique.get(j).getName_data());
                    //j++;
                 }
            }
            
            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.TEXT_TYPE_NAME)){
                if (j<list_texte.size()){
                    e.setText(list_texte.get(j).getName_data());
                    j++;
                } else {
                    e.setText("Pas de texte");
                    j=0;
                 }
            }

            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.IMAGE_TYPE_NAME)) {
                if(j<list_image.size()){
                e.setText(list_image.get(j).getName_data());
                j++;
                } else {
                    e.setText("Pas de texture");
                    j=0;
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

        //AXES
        Element xRatio = (Element) profil.getChild("xRatio");
        Element yRatio = (Element) profil.getChild("yRatio");
        Element zRatio = (Element) profil.getChild("zRatio");
        xRatio = new Element("xRatio");
        yRatio = new Element("yRatio");
        zRatio = new Element("zRatio");
        profil.addContent(xRatio);
        profil.addContent(yRatio);
        profil.addContent(zRatio);
        xRatio.setText("3");
        yRatio.setText("3");
        zRatio.setText("3");
        Element xColor = (Element) profil.getChild("xColor");
        Element yColor = (Element) profil.getChild("yColor");
        Element zColor = (Element) profil.getChild("zColor");
        xColor = new Element("xColor");
        yColor = new Element("yColor");
        zColor = new Element("zColor");
        profil.addContent(xColor);
        profil.addContent(yColor);
        profil.addContent(zColor);
        xColor.setText("16711936");
        yColor.setText("65536");
        zColor.setText("16776961");
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
        Element afficherLiens = new Element("afficherLiens");
        profil.addContent(afficherLiens);
        afficherLiens.setText("false");
        Element attributLiens = new Element("attributLiens");
        profil.addContent(attributLiens);
        attributLiens.setText("");

        //OBJET 3D
        Element shape = (Element) profil.getChild("shape");
        Element size = (Element) profil.getChild("size");
        shape = new Element("shape");
        size = new Element("size");
        profil.addContent(shape);
        profil.addContent(size);
        shape.setText(ElemGraph);
        size.setText("137");
        Element attSynth = (Element) profil.getChild("attSynth");
        Element Voix = (Element) profil.getChild("Voix");
        Element listMedia = (Element) profil.getChild("listMedia");
        attSynth = new Element("attSynth");
        Voix = new Element("Voix");
        listMedia = new Element("listMedia");
        profil.addContent(attSynth);
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
        attSynth.setText("Petal_width");
        Voix.setText("Pas de voix");

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
        Color1.setText("-65536");
        Color2.setText("-16711730");
        Color3.setText("-6697729");
        Color4.setText("-13434727");

        //CAMERA
        Element camera = (Element) profil.getChild("camera");
        camera = new Element("camera");
        profil.addContent(camera);
        camera.setText("Clavier et souris");

        Element xLoc = (Element) profil.getChild("xLoc");
        Element yLoc = (Element) profil.getChild("yLoc");
        Element zLoc = (Element) profil.getChild("zLoc");
        xLoc = new Element("xLoc");
        yLoc = new Element("yLoc");
        zLoc = new Element("zLoc");
        profil.addContent(xLoc);
        profil.addContent(yLoc);
        profil.addContent(zLoc);
        xLoc.setText("7.5");
        yLoc.setText("7.5");
        zLoc.setText("7.5");

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

        adrsIpServer.setText("127.0.0.1");
        jCheckBoxReseau.setText("false");
        cheminLecteur.setText("C:/Program Files/Windows Media Player/wmplayer.exe");

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
        jCheckBoxStereo.setText("true");
        jSliderYeux.setText("50");
        jTextFieldTaille.setText("1.70");
        jTextFieldDistance.setText("2");
        bkgdColorView.setText("-16777216");
        grilleTerrain.setText("false");

        //gridEnabled.setText(choicePanel.getGridEnabled());
        popUpInfo.setText("true");
        Color5.setText("-16737793");
        objSize2.setText("90");

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

        Clusturing.setText("false");
        //TO change here
        attClust.setText("Sepal_width");
        clustFact.setText("100");
        ClustVisib.setText("false");
        clustMethode.setText("0");
        clustObjet.setText("0");
        EclairageSphere.setText("false");
        objSize3.setText("0");

        // Palettes de couleurs
        Element colorParam = new Element("colorParam");
        profil.addContent(colorParam);
        // On parcourt la liste des attributs
        HashMap<String, HashMap> mapAttributPalette = new HashMap<String, HashMap>();
        Iterator<String> itOverAttribut = mapAttributPalette.keySet().iterator();
        while (itOverAttribut.hasNext()) {
            // On récupère le nom de l'attribut
            String nomAttribut = itOverAttribut.next();
            // Ajout au schéma XML
            Element attribut = new Element(nomAttribut);
            colorParam.addContent(attribut);
            // On parcourt ensuite la palette
            HashMap<String, Color> mapValeurColor = mapAttributPalette.get(nomAttribut);
            Iterator<String> itOverValeur = mapValeurColor.keySet().iterator();
            while (itOverValeur.hasNext()) {
                // On récupère la valeur de l'attribut
                String valeurAttribut = itOverValeur.next();
                // et sa couleur associée
                Color couleur = mapValeurColor.get(valeurAttribut);
                // On créer l'élément XML correspondant
                Element colorAttribute = new Element("color");
                colorAttribute.setAttribute("for", valeurAttribut);
                colorAttribute.setText(couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue());
                // On l'ajoute au schéma XML
                attribut.addContent(colorAttribute);
            }
        }
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }


    }

    /*public void AddNewUserPofilSettings(int indexProfil, File xml, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph){

        //SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        try {
        //On crée un nouveau document JDOM avec en argument le fichier XML
        //document = sxb.build(xml);
        //On initialise un nouvel élément racine avec l'élément racine du document.
        //racine = document.getRootElement();
        //ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionnée pour les axes x, y et z
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

        float[] tabCoord = new float[list_numerique.size()];
        tabCoordMax = new float[list_numerique.size()];
        tabCoordMin = new float[list_numerique.size()];
        for (int a = 0; a < list_numerique.size(); a++) {
        tabCoordMax[a] = -1000f;
        tabCoordMin[a] = 1000f;
        }
        List listData = racine.getChildren("data");
        //On cr�e un Iterator sur notre liste
        Iterator n = listData.iterator();
        while (n.hasNext()) {
        //On recrée l'Element courant à chaque tour de boucle afin de
        //pouvoir utiliser les méthodes propres aux Element comme :
        //selectionner un noeud fils, modifier du texte, etc...
        Element datum = (Element) n.next();
        List listDatum = datum.getChildren("datum");
        Iterator j = listDatum.iterator();
        while (j.hasNext()) {
        Element courant = (Element) j.next();
        //On affiche le nom de l'element courant
        for (int k = 0; k < list_numerique.size(); k++) {
                //ici on recupere les valeur max et min des attributs selectionnée pour les axes x, y et z
                tabCoord[k] = Float.parseFloat(courant.getChild(""+list_numerique.get(k).getName_data()).getValue());
                tabCoordMin[k] = (float) Math.min(tabCoord[k], tabCoordMin[k]);
                tabCoordMax[k] = (float) Math.max(tabCoord[k], tabCoordMax[k]);
        }
        }
        }
//        for (int k = 0; k < list_symbolique.size(); k++) {
//            symbolique[k] = courant.getChild(""+list_symbolique.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_texte.size(); k++) {
//            texte[k] = courant.getChild(""+list_texte.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_image.size(); k++) {
//            image[k] = courant.getChild(""+list_image.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_temporal.size(); k++) {
//            temporal[k] = courant.getChild(""+list_temporal.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_sound.size(); k++) {
//            sound[k] = courant.getChild(""+list_sound.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_link.size(); k++) {
//            link[k] = courant.getChild(""+list_link.get(k).getName_data().toString()).getValue();
//        }
//
//        for (int k = 0; k < list_file.size(); k++) {
//            file[k] = courant.getChild(""+list_file.get(k).getName_data().toString()).getValue();
//        }
        System.out.println("Size Numeric : "+list_numerique.size());
        System.out.println("Size Symbolic : "+list_symbolique.size());
        System.out.println("Size Text : "+list_texte.size());
        System.out.println("Size Image : "+list_image.size());
        System.out.println("Size Temporal : "+list_temporal.size());
        System.out.println("Size Sound : "+list_sound.size());
        System.out.println("Size Link : "+list_link.size());
        System.out.println("Size File : "+list_file.size());

        //On crée une Liste contenant tous les noeuds "data" de l'Element racine
        //Element visu = (Element) racine.getChild("visualizations");
        Element typeVisu = (Element) visu.getChild("nuage3D");
        if (typeVisu == null) {
            visu.addContent(new Element("nuage3D"));
            typeVisu = (Element) visu.getChild("nuage3D");
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");
        //On fait un test sur le profil selectionné par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en créer un dans le fichier xml

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

        profil.setAttribute("valeur", baliseProfil);
        profil.removeContent();


        //Matching result

        //int compteur = 0;
//        for(int i=0; i<listVisualAtt.size(); i++){
//            Element  e = (Element) profil.getChild(listVisualAtt.get(i).getName());
//            e = new Element(listVisualAtt.get(i).getName());
//            profil.addContent(e);
//            System.out.println("Element : "+listVisualAtt.get(i).getType().toString());
//            if((listVisualAtt.get(i).getName().toString().equals("att3"))||(listVisualAtt.get(i).getName().toString().equals("att4"))&&(compteur >= individuMEC.size())){
//                 e.setText("Pas de texte");
//                 j++;
//            } else if (listVisualAtt.get(i).getName().toString().equals("imgAtt")&&(compteur >= individuMEC.size())) {
//                e.setText("Pas de texture");
//                j++;
//            }
//            // A modifier : att2 dois contenir un attribut de donn�es de type num�rique
//            else if (listVisualAtt.get(i).getName().toString().equals("att2")) {
//                for(int u=0; u<individuMEC.size(); u++){
//                    if(listVisualAtt.get(i).getType().toString().equals(individuMEC.get(u).getType_data().toString())){
//                       e.setText(individuMEC.get(u).getName_data());
//                       u=individuMEC.size();
//                }
//                //e.setText("Sepal_width");
//                j++;
//            }
//            }
//             else {
//             e.setText(individuMEC.get(j).getName_data());
//             j++;
//             }
//            //e.setText(individuMEC.get(j).getName_data());
//            //j++;
//            compteur++;
//            if(j>individuMEC.size()-1){
//            j=0;
//            }
//
//        }
        int j = 0;
        for(int index=0; index<listVisualAtt.size(); index++){

            Element  e = (Element) profil.getChild(listVisualAtt.get(index).getName());
            e = new Element(listVisualAtt.get(index).getName());
            profil.addContent(e);
            //System.out.println("index = : "+index+"j = : "+j+"Type of data attribute : "+listVisualAtt.get(index).getType().toString());
            if((listVisualAtt.get(index).getType().toString().equals(VRMXML.NUMERIC_TYPE_NAME))){

                if(j<list_numerique.size()){
                    //System.out.println("index = : "+index+"j = : "+j+"Type of data attribute : "+listVisualAtt.get(index).getType().toString());
                    e.setText(list_numerique.get(j).getName_data());
                    j++;
                } else {
                    j=0;
                    e.setText(list_numerique.get(j).getName_data());
                }
            }

            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.SYMBOLIC_TYPE_NAME)){
                if(j<list_symbolique.size()){
                    e.setText(list_symbolique.get(j).getName_data());
                    j++;
                 } else {
                    j=0;
                    e.setText(list_symbolique.get(j).getName_data());
                    //j++;
                 }
            }

            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.TEXT_TYPE_NAME)){
                if (j<list_texte.size()){
                    e.setText(list_texte.get(j).getName_data());
                    j++;
                } else {
                    e.setText("Pas de texte");
                    j=0;
                 }
            }

            if(listVisualAtt.get(index).getType().toString().equals(VRMXML.IMAGE_TYPE_NAME)) {
                if(j<list_image.size()){
                e.setText(list_image.get(j).getName_data());
                j++;
                } else {
                    e.setText("Pas de texture");
                    j=0;
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

        //AXES
        Element xRatio = (Element) profil.getChild("xRatio");
        Element yRatio = (Element) profil.getChild("yRatio");
        Element zRatio = (Element) profil.getChild("zRatio");
        xRatio = new Element("xRatio");
        yRatio = new Element("yRatio");
        zRatio = new Element("zRatio");
        profil.addContent(xRatio);
        profil.addContent(yRatio);
        profil.addContent(zRatio);
        xRatio.setText("3");
        yRatio.setText("3");
        zRatio.setText("3");
        Element xColor = (Element) profil.getChild("xColor");
        Element yColor = (Element) profil.getChild("yColor");
        Element zColor = (Element) profil.getChild("zColor");
        xColor = new Element("xColor");
        yColor = new Element("yColor");
        zColor = new Element("zColor");
        profil.addContent(xColor);
        profil.addContent(yColor);
        profil.addContent(zColor);
        xColor.setText("16711936");
        yColor.setText("65536");
        zColor.setText("16776961");
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
        Element afficherLiens = new Element("afficherLiens");
        profil.addContent(afficherLiens);
        afficherLiens.setText("false");
        Element attributLiens = new Element("attributLiens");
        profil.addContent(attributLiens);
        attributLiens.setText("");

        //OBJET 3D
        Element shape = (Element) profil.getChild("shape");
        Element size = (Element) profil.getChild("size");
        shape = new Element("shape");
        size = new Element("size");
        profil.addContent(shape);
        profil.addContent(size);
        shape.setText(ElemGraph);
        size.setText("137");
        Element attSynth = (Element) profil.getChild("attSynth");
        Element Voix = (Element) profil.getChild("Voix");
        Element listMedia = (Element) profil.getChild("listMedia");
        attSynth = new Element("attSynth");
        Voix = new Element("Voix");
        listMedia = new Element("listMedia");
        profil.addContent(attSynth);
        profil.addContent(Voix);
        profil.addContent(listMedia);

//        ChoicePanelXml choicePanel = new ChoicePanelXml();
//        ArrayList list_Media = choicePanel.getListMedia();
//
//        for (int i = 0; i < list_Media.size(); i++) {
//            Element Media = new Element("Media" + i);
//            listMedia.addContent(Media);
//            Media.setText(list_Media.get(i).toString());
//        }

        //TO change here
        attSynth.setText("Petal_width");
        Voix.setText("Pas de voix");

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
        Color1.setText("-65536");
        Color2.setText("-16711730");
        Color3.setText("-6697729");
        Color4.setText("-13434727");

        //CAMERA
        Element camera = (Element) profil.getChild("camera");
        camera = new Element("camera");
        profil.addContent(camera);
        camera.setText("Clavier et souris");

        Element xLoc = (Element) profil.getChild("xLoc");
        Element yLoc = (Element) profil.getChild("yLoc");
        Element zLoc = (Element) profil.getChild("zLoc");
        xLoc = new Element("xLoc");
        yLoc = new Element("yLoc");
        zLoc = new Element("zLoc");
        profil.addContent(xLoc);
        profil.addContent(yLoc);
        profil.addContent(zLoc);
        xLoc.setText("7.5");
        yLoc.setText("7.5");
        zLoc.setText("7.5");

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

        adrsIpServer.setText("127.0.0.1");
        jCheckBoxReseau.setText("false");
        cheminLecteur.setText("C:/Program Files/Windows Media Player/wmplayer.exe");

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
        jCheckBoxStereo.setText("true");
        jSliderYeux.setText("50");
        jTextFieldTaille.setText("1.70");
        jTextFieldDistance.setText("2");
        bkgdColorView.setText("-16777216");
        grilleTerrain.setText("false");

        //gridEnabled.setText(choicePanel.getGridEnabled());
        popUpInfo.setText("true");
        Color5.setText("-16737793");
        objSize2.setText("90");

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

        Clusturing.setText("false");
        //TO change here
        attClust.setText("Sepal_width");
        clustFact.setText("100");
        ClustVisib.setText("false");
        clustMethode.setText("0");
        clustObjet.setText("0");
        EclairageSphere.setText("false");
        objSize3.setText("0");

        // Palettes de couleurs
        Element colorParam = new Element("colorParam");
        profil.addContent(colorParam);
        // On parcourt la liste des attributs
        HashMap<String, HashMap> mapAttributPalette = new HashMap<String, HashMap>();
        Iterator<String> itOverAttribut = mapAttributPalette.keySet().iterator();
        while (itOverAttribut.hasNext()) {
            // On récupère le nom de l'attribut
            String nomAttribut = itOverAttribut.next();
            // Ajout au schéma XML
            Element attribut = new Element(nomAttribut);
            colorParam.addContent(attribut);
            // On parcourt ensuite la palette
            HashMap<String, Color> mapValeurColor = mapAttributPalette.get(nomAttribut);
            Iterator<String> itOverValeur = mapValeurColor.keySet().iterator();
            while (itOverValeur.hasNext()) {
                // On récupère la valeur de l'attribut
                String valeurAttribut = itOverValeur.next();
                // et sa couleur associée
                Color couleur = mapValeurColor.get(valeurAttribut);
                // On créer l'élément XML correspondant
                Element colorAttribute = new Element("color");
                colorAttribute.setAttribute("for", valeurAttribut);
                colorAttribute.setText(couleur.getRed() + "," + couleur.getGreen() + "," + couleur.getBlue());
                // On l'ajoute au schéma XML
                attribut.addContent(colorAttribute);
            }
        }
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }


    }*/

   //On enregsitre notre nouvelle arborescence dans le fichier d'origine dans un format classique.
   public void enregistreFichier(String fichier) throws Exception
   {
         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
         sortie.output(document, new FileOutputStream(fichier));

   }

    public List<String> getCaption(){
        for(int i=0; i<VisualisationsFilesNames.size(); i++){
        imageCaptions.add(VisualisationsFilesNames.get(i).getNom().toString());
        }
        return imageCaptions;
    }

    private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>() {

        /**
         * Creates full size and thumbnail versions of the target image files.
         */

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < VisualisationsFilesNames.size(); i++) {
                ImageIcon icon;
                int id = VisualisationsFilesNames.get(i).getIdimage();
                String shape = VisualisationsFilesNames.get(i).getNom();
                System.out.println("Id :"+ id+"  : Shape  "+shape);
                String identif = String.valueOf(id);
                icon = createImageIcon(imagedir+VisualisationsFilesNames.get(i).getNom()+".jpg", identif);
                //Action executer lors du clique sur l'icone
                ThumbnailAction thumbAction;
                if(icon != null){
                    //Dimension de l'icone (image) placer en bas de la fenetres (visualisations proposées par le système contenus dans la base de données
                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 170, 170));
                    ImageIcon OverviewIcon = new ImageIcon(getScaledImage(icon.getImage(), 700, 395));
                    //paramétre affecté à thumbAction
                    thumbAction = new ThumbnailAction(OverviewIcon, thumbnailIcon, identif);

                }else{
                    // the image failed to load for some reason so load a placeholder instead
                    thumbAction = new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions.get(i));
                }
                publish(thumbAction);
               
            }
            // unfortunately we must return something, and only null is valid to
            // return when the return type is void.

            return null;
        }

        /**
         * Process all loaded images.
         */
        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction thumbAction : chunks) {
                JButton thumbButton = new JButton(thumbAction);
                // add the new button BEFORE the last glue
                // this centers the buttons in the toolbar
//                JProgressBar progressBar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
//                //progressBar.set
//                progressBar.setValue(53);
//                progressBar.setStringPainted(true);
//                //DB_VisualizationToolBar.add(progressBar);
                DB_VisualizationToolBar.add(thumbButton, DB_VisualizationToolBar.getComponentCount());
            }
        }


        };



    /**
     * Creates an ImageIcon if the path is valid.
     * @param String - resource path
     * @param String - description of the file
     */
    protected ImageIcon createImageIcon(String path,
            String description) throws IOException {
        
        //java.net.URL imgURL = getClass().getResource(path);
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        java.net.URL imgURL = ClassLoader.getSystemResource(path);
        
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    private class ThumbnailAction extends AbstractAction{

        /**
         *The icon if the full image we want to display.
         */
        private Icon displayPhoto;
        private int idtable;


        /**
         * @param Icon - The full size photo to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param String - The descriptioon of the icon.
         */
        public ThumbnailAction(Icon photo, Icon thumb, String desc){
            
            //Dimension de l'icone (image) placer en bas de la fenetres (visualisations proposées par le système contenus dans la base de données
            displayPhoto = photo;
            //displayPhoto = new ImageIcon(getScaledImage(photo.getImage(), 200, 200));


            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);
            idtable = Integer.valueOf(desc);
            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }

        /**
         * Shows the full image in the main area and sets the application title.
         */
        @Override
    public void actionPerformed(ActionEvent e) {
        //
        //"C:\\Users\\Abdelheq\\Desktop\\VRMiner\\ceries exemples.xml"
        //OverviewPictureContainer.setIcon(displayPhoto);
        String path = "C:\\Users\\Abdelheq\\Desktop\\VRMiner\\Presentation visualisations exemples\\Photos\\ceries exemples.xml";
            try {
                System.out.println("Id Visualization : "+idtable);
                idmethode = idtable;
                //creer la scene 3D
                if(visu3D!=null){
                visu3D = null;
                visu3D.destroy();
                }
                updatePreview3D(path, idmethode-1);
                String shape = new LoadVisualizations().getIdElement(idmethode);
                System.out.println("Shape : "+shape);
                listdtm = new LoadVisualizations().getIdMethode(idmethode);
                 for(int i =0; i<listdtm.size(); i++){
                     System.out.println("Attribute : "+listdtm.get(i).getName().toString());
                }

                } catch (Exception ex) {
                //TODO
                }
        }
    }

    private void updatePreview3D(String path, int profil){
//      if(visu3D!=null){
//                visu3D.destroy();
//      }
        visu3D = new Visualisation_Nuage_3D(0, 0, 0);
        //String path = "C:\\Users\\Abdelheq\\Desktop\\IRIS.xml";
        visu3D.ConfigurationNuage3D(path, "profil"+profil);
        visu3D.createScene();
        OverviewPictureContainer.setViewportView(visu3D.getCustomCanvas3D());
    }

    private void fillVisuTabForm(ArrayList visus) {
        for (int i = 0; i < visus.size(); i++) {
            if (((String) visus.get(i)).equals(NUAGE3D.NUAGE3D_NAME)) {
                String profilDefaut = xmlParser.getProfilDefaut();
                fillNuage3DTab(profilDefaut);
            }
//            else if (((String) visus.get(i)).equals(TUBE)) {
//                //tabPanel.getTabComponentAt(1).setEnabled(true);
//                //fillTubeTab();
//            } else if (((String) visus.get(i)).equals(POI)) {
//                //tabPanel.getTabComponentAt(2).setEnabled(true);
//                fillPoiTab();
//            } else if (((String) visus.get(i)).equals(ANTBUILDING)) {
//                //tabPanel.getTabComponentAt(2).setEnabled(true);
//                fillAntBuildingTab();
//            } 
            else {
                System.out.println("Type de visualisation non reconnue!");
            }
        }

    }

    private void fillDefaultNuage3D() {

        /*
         * Initialisation des objets swings dans la partie configuration de la visualisation
         * D�claration des swings pour le Panel User Preferences
         */

//        size.setText("" + objSize.getValue());
//        axisColor = new Color[3];
//        axisColor[0] = new Color(0, 1f, 0);
//        axisColor[1] = new Color(1f, 0, 0);
//        axisColor[2] = new Color(0, 0, 1f);
//        xColorView.setBackground(axisColor[0]);
//        yColorView.setBackground(axisColor[1]);
//        zColorView.setBackground(axisColor[2]);
//
//        xRatio.setValue(new Integer(1));
//        yRatio.setValue(new Integer(1));
//        zRatio.setValue(new Integer(1));
//
//        /*********** AXES ********************************/
//        //xmlFile.parseData();
//        //remplissage des param?tres possibles utilisables
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            xVar.addItem(xmlParser.getVectNumParam().get(i));
//            yVar.addItem(xmlParser.getVectNumParam().get(i));
//            zVar.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        /********* OBJECT 3D ************************/
//        //remplissage des formes
//        typeObj.addItem("CUBE_CHANEL v2");
//        typeObj.addItem("CUBE_CHANEL v1");
//        typeObj.addItem("CUBE");
//        typeObj.addItem("FACE");
//        typeObj.addItem("SPHERE");
//
//        float[] valCouleur = new float[xmlParser.getVectNumParam().size()];
//
//        //remplissage des param?tres possibles pour la classe
//        //for(int i=0;i<xmlFile.getVectNumParam().size();i++)
//        // classVar.addItem(xmlFile.getVectNumParam().get(i));
//
//        //remplissage des param?tres possibles pour l'attribut 1
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            attPyrHaut.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        //remplissage des param?tres possibles pour l'attribut 2
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            attPyrBas.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        //remplissage des param?tres possibles pour l'image
//        imgAtt.addItem("Pas de texture");
//        for (int i = 0; i < xmlParser.getVectPictParam().size(); i++) {
//            imgAtt.addItem(xmlParser.getVectPictParam().get(i));
//        }
//
//        //remplissage des param?tres possibles pour l'image
//        for (int i = 0; i < xmlParser.getVectStrParam().size(); i++) {
//            attMedia.addItem(xmlParser.getVectStrParam().get(i));
//        }
//
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            attSynthese.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        for (int i = xmlParser.getVectNumParam().size(); i < xmlParser.getVectStrParam().size(); i++) {
//            attSynthese.addItem(xmlParser.getVectStrParam().get(i));
//        }
//
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            attClust.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        attTextHaut.addItem("Pas de texte");
//        //remplissage des param?tres possibles pour l'attribut 3
//        for (int i = 0; i < xmlParser.getVectStrParam().size(); i++) {
//            attTextHaut.addItem(xmlParser.getVectStrParam().get(i));
//        }
//        attTextBas.addItem("Pas de texte");
//        //remplissage des param?tres possibles pour l'attribut 4
//        for (int i = 0; i < xmlParser.getVectStrParam().size(); i++) {
//            attTextBas.addItem(xmlParser.getVectStrParam().get(i));
//        }
//
//        // Couleurs Num
//        for (int i = 0; i < xmlParser.getVectNumParam().size(); i++) {
//            attCouleurHaut.addItem(xmlParser.getVectNumParam().get(i));
//            attCouleurBas.addItem(xmlParser.getVectNumParam().get(i));
//        }
//
//        // Couleurs Str
//        for (int i = 0; i < xmlParser.getVectStrParam().size(); i++) {
//            attCouleurHaut.addItem(xmlParser.getVectStrParam().get(i));
//            attCouleurBas.addItem(xmlParser.getVectStrParam().get(i));
//        }
//        //d?finition de la couleur des objets
//
//        //d?finition de l'?chelle
//        objSize.setValue(100);
//        //imgAtt.setSelectedItem("images");
//
//        /********* CAMERA ***************************/
//        //type de la cam?ra
//        camType.addItem("Clavier et souris");
//        camType.addItem("Marche");
//        camType.addItem("SpacePilot mode Orbit");
//        camType.addItem("SpacePilot mode Vol");
//        camType.addItem("SpacePilot mode Examine");
//        camType.addItem("Examine");
//        camType.addItem("Wiimote Souris");
//        //camType.addItem("Wiimote Reverse");
//
//        //position initiale de la cam?ra
//        float xLoctemp = (Float.parseFloat(xRatio.getValue().toString())) * 2.5f;
//        xLoc.setValue(xLoctemp);
//        float yLoctemp = (Float.parseFloat(xRatio.getValue().toString())) * 2.5f;
//        yLoc.setValue(yLoctemp);
//        float zLoctemp = (Float.parseFloat(xRatio.getValue().toString())) * 2.5f;
//        zLoc.setValue(zLoctemp);
//
//        imgAtt.setSelectedItem("fichier_photo");
//        attClust.setSelectedItem("groupe");
//
//        jListMedia.setModel(model_list);
//
//
//        /*************************************/
//        //IMAGES
//        BufferedImage imfond = null;
//        imageIconFond = new ImageIcon();
//        try {
//            imfond = ImageIO.read(getClass().getResourceAsStream("/images/fond.jpg"));
//            imfond = (BufferedImage) scale(imfond, 1500, 1300);
//        } catch (Exception e) {
//        }
//        imageIconFond.setImage(imfond);
//        jLabelBackground.setIcon(imageIconFond);
//
//        BufferedImage imPolytech = null;
//        imageIconLogo = new ImageIcon();
//        try {
//            imPolytech = ImageIO.read(getClass().getResourceAsStream("/images/Logo.jpg"));
//            imPolytech = (BufferedImage) scale(imPolytech, 148, 58);
//        } catch (Exception e) {
//        }
//        imageIconLogo.setImage(imPolytech);
//        jLabelPolytech.setIcon(imageIconLogo);
//
//
//        BufferedImage imCeries = null;
//        imageIconLogo1 = new ImageIcon();
//        try {
//            imCeries = ImageIO.read(getClass().getResourceAsStream("/images/ceries.PNG"));
//            imCeries = (BufferedImage) scale(imCeries, 148, 58);
//        } catch (Exception e) {
//        }
//        imageIconLogo1.setImage(imCeries);
//        //jLabelCeries.setIcon(imageIconLogo1);
//
//        /************** Liens ************************/
//        attLiens.addItem("");
//        for (int i = 0; i < xmlParser.getVectLinkParam().size(); i++) {
//            attLiens.addItem(xmlParser.getVectLinkParam().get(i));
//        }


        //Remplissage de listBox avec le nom de profils
        initProfil();

    }

    protected void fillNuage3DTab(String choixProfil) {
        xmlParser.RafraichissementFichier();
    }

    public void initProfil() {

//        //Je renitialise pas ListBox
//        model_list_profil.clear();
//        String valIndex = xmlParser.getProfilDefaut().replace("profil", "");
//        //Je recup?re le nombre de profil
//        int nbrProfil = xmlParser.getNumberProfil();
//
//        for (int i = 0; i < nbrProfil; i++) {
//            String valeur = xmlParser.getNomProfil("profil" + i);
//            if (valeur.isEmpty() || xmlParser.getNumberProfil() < 1) {
//                valeur = "profil par defaut";
//            }
//            if (i == Integer.parseInt(valIndex)) {
//                jLabel_Profil.setText(valeur);
//            }
//            model_list_profil.add(i, valeur);
//        }
//
//        jListProfil.setSelectedIndex(Integer.parseInt(valIndex));
    }

    public void RafraichissementFichier() {

        try {
            //On cr?e un nouveau document JDOM avec en argument le fichier XML
            document = new VRMXMLReader(filePathName).getXMLDocument();
        } catch (Exception e) {
            System.err.println(e + " Erreur de lecture du xml");
            new MessageBox("Erreur de lecture du fichier xml", e.getMessage(), 1);
        }
    }

    //Creer le panel pour configurer les param�tres des axes z, y et z et les param�tres de la cam�ra
    public class Axes extends javax.swing.JPanel {

    private Color[] axisColor;


    /** Creates new form AxesPanel */
    public Axes() {

        panelAxes = new javax.swing.JPanel();
        yColorView = new javax.swing.JTextField();
        yRatioBtn = new javax.swing.JButton();
        zColorView = new javax.swing.JTextField();
        zRatio = new javax.swing.JSpinner();
        yRatioLabel = new javax.swing.JLabel();
        zRatioLabel = new javax.swing.JLabel();
        xRatioBtn = new javax.swing.JButton();
        yRatio = new javax.swing.JSpinner();
        zRatioBtn = new javax.swing.JButton();
        xRatioLabel = new javax.swing.JLabel();
        xRatio = new javax.swing.JSpinner();
        afficherLiensCheckBox = new javax.swing.JCheckBox();
        xColorView = new javax.swing.JTextField();
        attLiensLabel = new javax.swing.JLabel();
        attLiens = new javax.swing.JComboBox();
        panelCamera = new javax.swing.JPanel();
        LocLabel = new javax.swing.JLabel();
        xLocLabel = new javax.swing.JLabel();
        xLoc = new javax.swing.JSpinner();
        yLocLabel = new javax.swing.JLabel();
        yLoc = new javax.swing.JSpinner();
        zLocLabel = new javax.swing.JLabel();
        zLoc = new javax.swing.JSpinner();
        camTypeLabel = new javax.swing.JLabel();
        camType = new javax.swing.JComboBox();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yColorView.setBackground(new java.awt.Color(255, 255, 102));
        yColorView.setEditable(false);

        yRatioBtn.setText("...");
        yRatioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yRatioBtnActionPerformed(evt);
            }
        });

        zColorView.setBackground(new java.awt.Color(51, 51, 255));
        zColorView.setEditable(false);

        zRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zRatioStateChanged(evt);
            }
        });

        yRatioLabel.setText("Ratio Y :");

        zRatioLabel.setText("Ratio Z :");

        xRatioBtn.setText("...");
        xRatioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xRatioBtnActionPerformed(evt);
            }
        });

        yRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yRatioStateChanged(evt);
            }
        });

        zRatioBtn.setText("...");
        zRatioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zRatioBtnActionPerformed(evt);
            }
        });

        xRatioLabel.setText("Ratio X :");

        xRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xRatioStateChanged(evt);
            }
        });

        afficherLiensCheckBox.setText("Dispaly links");

        xColorView.setBackground(new java.awt.Color(51, 255, 255));
        xColorView.setEditable(false);

        attLiensLabel.setText("Attribut liens");

        attLiens.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelAxesLayout = new javax.swing.GroupLayout(panelAxes);
        panelAxes.setLayout(panelAxesLayout);
        panelAxesLayout.setHorizontalGroup(
            panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAxesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAxesLayout.createSequentialGroup()
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(xRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(zRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(yRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(zRatio, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(yRatio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(xRatio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(xRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(zColorView)
                            .addComponent(yColorView)
                            .addComponent(xColorView, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(attLiensLabel))
                    .addGroup(panelAxesLayout.createSequentialGroup()
                        .addComponent(afficherLiensCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(attLiens, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelAxesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {xRatioBtn, yRatioBtn, zRatioBtn});

        panelAxesLayout.setVerticalGroup(
            panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAxesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAxesLayout.createSequentialGroup()
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(xRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xRatioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(xColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(yRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(yColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                            .addComponent(zRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(attLiensLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAxesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afficherLiensCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(attLiens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(panelAxes, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 430, 175));
        panelAxes.setBorder(javax.swing.BorderFactory.createTitledBorder("Axes"));

        LocLabel.setText("Position initiale :");

        xLocLabel.setText("X :");

        yLocLabel.setText("Y :");

        zLocLabel.setText("Z :");

        camTypeLabel.setText("Type de la camera par defaut");

        camType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Clavier et souris" }));
        camType.setPreferredSize(new java.awt.Dimension(200, 22));

        javax.swing.GroupLayout panelCameraLayout = new javax.swing.GroupLayout(panelCamera);
        panelCamera.setLayout(panelCameraLayout);
        panelCameraLayout.setHorizontalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCameraLayout.createSequentialGroup()
                        .addComponent(xLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(yLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(zLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LocLabel))
                .addGap(153, 153, 153))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCameraLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(camTypeLabel)
                    .addComponent(camType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );

        panelCameraLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {xLoc, yLoc, zLoc});

        panelCameraLayout.setVerticalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(camTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(camType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LocLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(zLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLocLabel))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        panelCameraLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {xLoc, yLoc, zLoc});

        add(panelCamera, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 430, 150));
        panelCamera.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera"));
    }// </editor-fold>

    private void xRatioStateChanged(javax.swing.event.ChangeEvent evt) {
        if (Integer.parseInt(xRatio.getValue().toString()) > 15 || (Integer.parseInt(xRatio.getValue().toString()) < 1)) {


        if (Integer.parseInt(xRatio.getValue().toString()) > 15) {
            xRatio.setValue(xRatio.getPreviousValue());
        }
        if ((Integer.parseInt(xRatio.getValue().toString()) < 1)) {
            xRatio.setValue(xRatio.getNextValue());
        }
    }
    float xLoctemp = (Float.parseFloat(xRatio.getValue().toString())) * 2.5f;
    xLoc.setValue(xLoctemp);
    }

    private void yRatioStateChanged(javax.swing.event.ChangeEvent evt) {
        if (Integer.parseInt(xRatio.getValue().toString()) > 15 || (Integer.parseInt(yRatio.getValue().toString()) < 1)) {


        if (Integer.parseInt(yRatio.getValue().toString()) > 15) {
            yRatio.setValue(yRatio.getPreviousValue());
        }
        if ((Integer.parseInt(yRatio.getValue().toString()) < 1)) {
            yRatio.setValue(yRatio.getNextValue());
        }
    }
    float xLoctemp = (Float.parseFloat(yRatio.getValue().toString())) * 2.5f;
    yLoc.setValue(xLoctemp);
    }

    private void zRatioStateChanged(javax.swing.event.ChangeEvent evt) {
        if (Integer.parseInt(xRatio.getValue().toString()) > 15 || (Integer.parseInt(zRatio.getValue().toString()) < 1)) {


        if (Integer.parseInt(zRatio.getValue().toString()) > 15) {
            zRatio.setValue(zRatio.getPreviousValue());
        }
        if ((Integer.parseInt(zRatio.getValue().toString()) < 1)) {
            zRatio.setValue(zRatio.getNextValue());
        }
    }
    float xLoctemp = (Float.parseFloat(zRatio.getValue().toString())) * 2.5f;
    zLoc.setValue(xLoctemp);
    }

    private void xRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {
        axisColor[0] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.GREEN);
        xColorView.setBackground(axisColor[0]);
    }

    private void yRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {
        axisColor[1] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.RED);
        yColorView.setBackground(axisColor[1]);
    }

    private void zRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {
        axisColor[2] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.BLUE);
        zColorView.setBackground(axisColor[2]);
    }

    public String getAfficherLiens() {
        if (afficherLiensCheckBox.isSelected()) {
            return "true";
        }
        return "false";
    }

    public String getAttributLiens() {
        return attLiens.getSelectedItem().toString();
    }
    }
    //Fin de la classe qui cr�e le panel pour configurer les param�tres des axes z, y et z et les param�tres de la cam�ra

    //Creer le panel pour configurer les param�tres d'un objet3d (l'�l�ment graphique)
    public class Object3D extends javax.swing.JPanel {

    private ImageIcon imageIcon;
    private Color ChoixCouleur3 = null;
    private Color ChoixCouleur2 = null;
    private Color ChoixCouleur1 = null;
    private Color ChoixCouleur4 = null;

    /** Creates new form Object3DPanel */
    public Object3D() {

        jPanel4 = new javax.swing.JPanel();
        ButtonEchelleCouleursBas = new javax.swing.JButton();
        Pyr2Color2Label = new javax.swing.JLabel();
        Pyr2Color1Label = new javax.swing.JLabel();
        ButtonPyr2Color2 = new javax.swing.JButton();
        Color4 = new javax.swing.JTextField();
        Color3 = new javax.swing.JTextField();
        ButtonPyr2Color1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        ButtonEchelleCouleursHaut = new javax.swing.JButton();
        Pyr1Color2Label = new javax.swing.JLabel();
        Pyr1Color1Label = new javax.swing.JLabel();
        ButtonPyr1Color2 = new javax.swing.JButton();
        Color2 = new javax.swing.JTextField();
        Color1 = new javax.swing.JTextField();
        ButtonPyr1Color1 = new javax.swing.JButton();
        voix = new javax.swing.JComboBox();
        voixLabel = new javax.swing.JLabel();
        attSyntheseLabel = new javax.swing.JLabel();
        attSynthese = new javax.swing.JComboBox();
        objSize = new javax.swing.JSlider();
        objSizeLabel = new javax.swing.JLabel();
        size = new javax.swing.JLabel();
        JDrapeau = new javax.swing.JLabel();

        jPanel4.setPreferredSize(new java.awt.Dimension(195, 112));

        ButtonEchelleCouleursBas.setText("Echelle de couleurs...");

        Pyr2Color2Label.setText("Couleur Min");

        Pyr2Color1Label.setText("Couleur Max");

        ButtonPyr2Color2.setText("...");
        ButtonPyr2Color2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPyr2Color2ActionPerformed(evt);
            }
        });

        Color4.setBackground(new java.awt.Color(204, 204, 255));

        Color3.setBackground(new java.awt.Color(0, 255, 255));
        Color3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color3ActionPerformed(evt);
            }
        });

        ButtonPyr2Color1.setText("...");
        ButtonPyr2Color1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPyr2Color1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonEchelleCouleursBas, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ButtonPyr2Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(Color4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Pyr2Color2Label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ButtonPyr2Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Color3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Pyr2Color1Label))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonEchelleCouleursBas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Pyr2Color1Label)
                    .addComponent(Pyr2Color2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Color4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Color3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonPyr2Color1)
                    .addComponent(ButtonPyr2Color2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ButtonEchelleCouleursHaut.setText("Echelle de couleurs...");
        ButtonEchelleCouleursHaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEchelleCouleursHautActionPerformed(evt);
            }
        });

        Pyr1Color2Label.setText("Couleur Min");

        Pyr1Color1Label.setText("Couleur Max");

        ButtonPyr1Color2.setText("...");
        ButtonPyr1Color2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPyr1Color2ActionPerformed(evt);
            }
        });

        Color2.setBackground(new java.awt.Color(204, 204, 255));

        Color1.setBackground(new java.awt.Color(0, 255, 255));
        Color1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color1ActionPerformed(evt);
            }
        });

        ButtonPyr1Color1.setText("...");
        ButtonPyr1Color1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPyr1Color1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCameraLayout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(panelCameraLayout);
        panelCameraLayout.setHorizontalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonEchelleCouleursHaut, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addGroup(panelCameraLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCameraLayout.createSequentialGroup()
                                .addComponent(ButtonPyr1Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Pyr1Color2Label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCameraLayout.createSequentialGroup()
                                .addComponent(ButtonPyr1Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Pyr1Color1Label))))
                .addContainerGap())
        );

        panelCameraLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ButtonPyr1Color1, ButtonPyr1Color2, Color1, Color2});

        panelCameraLayout.setVerticalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonEchelleCouleursHaut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Pyr1Color1Label)
                    .addComponent(Pyr1Color2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Color1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonPyr1Color1)
                    .addComponent(ButtonPyr1Color2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCameraLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ButtonPyr1Color1, ButtonPyr1Color2, Color1, Color2});

        voix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pas de voix" }));
        voix.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                voixItemStateChanged(evt);
            }
        });

        voixLabel.setText("Voix :");

        attSyntheseLabel.setText("Attribut synthese vocale :");

        attSynthese.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ItemXXXXXXXX" }));

        objSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSizeStateChanged(evt);
            }
        });

        objSizeLabel.setText("Taille de l'objet 3D :");

        size.setText("137");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(objSizeLabel)
                        .addGap(4, 4, 4)
                        .addComponent(size)
                        .addGap(19, 19, 19)
                        .addComponent(attSyntheseLabel)
                        .addGap(15, 15, 15)
                        .addComponent(voixLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JDrapeau, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(objSize, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(attSynthese, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(voix, 0, 81, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel3, jPanel4});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(objSizeLabel)
                    .addComponent(size)
                    .addComponent(attSyntheseLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(voixLabel)
                        .addComponent(JDrapeau, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(objSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attSynthese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(voix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel3, jPanel4});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pyramidion Haut ou Sphere"));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pyramidion Haut ou Sphere"));
    }// </editor-fold>

    private void Color1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void Color3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
}

    private void objSizeStateChanged(javax.swing.event.ChangeEvent evt) {
         size.setText("" + objSize.getValue());
    }

    private void voixItemStateChanged(java.awt.event.ItemEvent evt) {
        BufferedImage im = null;
        imageIcon = new ImageIcon();
        JDrapeau.setDisabledIcon(imageIcon);

    if (voix.getSelectedItem() == "Brian") {

        try {
            im = ImageIO.read(getClass().getResourceAsStream("/images/Drapeau1.png"));

        } catch (IOException e) {
        }
        imageIcon.setImage(im);

        JDrapeau.setIcon(imageIcon);

    } else {
        try {
            im = ImageIO.read(getClass().getResourceAsStream("/images/Drapeau.png"));

        } catch (IOException e) {
        }
        imageIcon.setImage(im);
        JDrapeau.setIcon(imageIcon);

    }
    }

    private void ButtonEchelleCouleursHautActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void ButtonPyr1Color2ActionPerformed(java.awt.event.ActionEvent evt) {
        Color col = ButtonPyr1Color2.getBackground();

        Color color = JColorChooser.showDialog(
                this,
                "Couleur du pyramdian du haut",
                col);
        Color2.setBackground(color);

        ChoixCouleur2 = color;
    }

    private void ButtonPyr1Color1ActionPerformed(java.awt.event.ActionEvent evt) {
        Color col = ButtonPyr1Color2.getBackground();

        Color color = JColorChooser.showDialog(
                this,
                "Couleur du pyramdian du haut",
                col);
        Color2.setBackground(color);

        ChoixCouleur2 = color;
    }

    private void ButtonPyr2Color2ActionPerformed(java.awt.event.ActionEvent evt) {
        Color col = Color4.getBackground();

        Color color = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
        Color4.setBackground(color);

        ChoixCouleur4 = color;
    }

    private void ButtonPyr2Color1ActionPerformed(java.awt.event.ActionEvent evt) {
        Color col = Color3.getBackground();

        Color color = JColorChooser.showDialog(
                this,
                "Couleur du pyramdian du haut",
                col);
        Color3.setBackground(color);

        ChoixCouleur3 = color;
    }
    
    }
    //Fin de la classe qui cr�e le panel pour configurer les param�tres d'un objet3d (l'�l�ment graphique)

    //Creer le panel pour configurer les param�tres de l'espace 3d de la sc�ne
    public class Espace3D extends javax.swing.JPanel {

    private float ValEcartYeux = 0.0035f;
    private Color backGroundColor;
    private Color ChoixCouleur5 = null;
    private boolean terrain = false;
    private boolean bgrid;

    /** Creates new form Espace3DPanel */
    public Espace3D() {

        jCheckBoxStereo = new javax.swing.JCheckBox();
        SliderYeuxLabel = new javax.swing.JLabel();
        size1 = new javax.swing.JLabel();
        size1Label = new javax.swing.JLabel();
        TailleLabel = new javax.swing.JLabel();
        TextFieldTaille = new javax.swing.JTextField();
        SliderYeux = new javax.swing.JSlider();
        TailleLabel1 = new javax.swing.JLabel();
        DistanceLabel = new javax.swing.JLabel();
        TextFieldDistance = new javax.swing.JTextField();
        DistanceLabel1 = new javax.swing.JLabel();
        grilleTerrain = new javax.swing.JCheckBox();
        popUpInfo = new javax.swing.JCheckBox();
        bkgdColorViewLabel = new javax.swing.JLabel();
        bkgdColorView = new javax.swing.JTextField();
        bkgdColorViewbtn = new javax.swing.JButton();
        Color5 = new javax.swing.JTextField();
        Color5btn = new javax.swing.JButton();
        Color5Label = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        objSize2 = new javax.swing.JSlider();
        size2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jCheckBoxStereo.setText("Activer Stereoscopie");
        jCheckBoxStereo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxStereoActionPerformed(evt);
            }
        });
        add(jCheckBoxStereo, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 9, -1, -1));

        SliderYeuxLabel.setText("Ecartement des yeux");
        add(SliderYeuxLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 13, -1, -1));

        size1.setText("0.0000X");
        add(size1, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 13, -1, -1));

        size1Label.setText("m");
        add(size1Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(369, 13, -1, -1));

        TailleLabel.setText("Votre taille :");
        add(TailleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 58, -1, -1));

        TextFieldTaille.setText("1.70");
        add(TextFieldTaille, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 52, -1, -1));

        SliderYeux.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SliderYeuxStateChanged(evt);
            }
        });
        add(SliderYeux, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 36, 181, -1));

        TailleLabel1.setText("m");
        add(TailleLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 58, -1, -1));

        DistanceLabel.setText("Distance Projecteur - Ecran :");
        add(DistanceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 98, -1, -1));

        TextFieldDistance.setText(" 2");
        TextFieldDistance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldDistanceActionPerformed(evt);
            }
        });
        add(TextFieldDistance, new org.netbeans.lib.awtextra.AbsoluteConstraints(184, 92, 24, -1));

        DistanceLabel1.setText("m");
        add(DistanceLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 98, -1, -1));

        grilleTerrain.setText("Afficher grille terrain");
        add(grilleTerrain, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 123, -1, -1));

        popUpInfo.setText("Pop up d'Informations");
        add(popUpInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 153, -1, -1));

        bkgdColorViewLabel.setText("Couleur de fond");
        add(bkgdColorViewLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 162, -1, -1));

        bkgdColorView.setBackground(new java.awt.Color(153, 153, 153));
        add(bkgdColorView, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 185, 52, 25));

        bkgdColorViewbtn.setText("...");
        bkgdColorViewbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkgdColorViewbtnActionPerformed(evt);
            }
        });
        add(bkgdColorViewbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 185, 52, -1));

        Color5.setBackground(new java.awt.Color(204, 0, 0));
        add(Color5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 245, 52, 25));

        Color5btn.setText("...");
        Color5btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Color5btnActionPerformed(evt);
            }
        });
        add(Color5btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 245, 52, -1));

        Color5Label.setText("Couleur du cube englobant");
        add(Color5Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 222, -1, -1));

        jLabel10.setText("Pourcentage de la transparence :");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 222, -1, -1));

        objSize2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSize2StateChanged(evt);
            }
        });
        add(objSize2, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 245, 142, -1));

        size2.setText("90");
        add(size2, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 245, -1, -1));

        jLabel12.setText("%");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 245, -1, -1));
    }// </editor-fold>

    private void TextFieldDistanceActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jCheckBoxStereoActionPerformed(java.awt.event.ActionEvent evt) {
        if (jCheckBoxStereo.isSelected()) {
        SliderYeux.setValue(50);
        } else {
            SliderYeux.setValue(0);
        }
    }

    private void SliderYeuxStateChanged(javax.swing.event.ChangeEvent evt) {
        float ValTrans = (float) 0.00007;
        float valTemp = SliderYeux.getValue() * ValTrans;
        setValTemp(valTemp);

        DecimalFormat df = new DecimalFormat("########.00000");
        size1.setText("" + (df.format(valTemp)));

        setValTemp(valTemp);
    }

    private void bkgdColorViewbtnActionPerformed(java.awt.event.ActionEvent evt) {
        backGroundColor = JColorChooser.showDialog(
            this,
            "Couleur du fond",
            Color.GRAY);
        bkgdColorView.setBackground(backGroundColor);
    }

    private void Color5btnActionPerformed(java.awt.event.ActionEvent evt) {
        Color col = Color5.getBackground();

        Color color1 = JColorChooser.showDialog(
                this,
                "Couleur du pyramdian du haut",
                col);
        Color5.setBackground(color1);

        ChoixCouleur5 = color1;
    }

    private void objSize2StateChanged(javax.swing.event.ChangeEvent evt) {
        size2.setText("" + objSize2.getValue() + "%");
    }

    public void setValTemp(float valTemp) {
        ValEcartYeux = valTemp;
    }

    public String getColor5() {
        return String.valueOf(Color5.getBackground().getRGB());
    }

    public String getObjSize2() {
        return String.valueOf(objSize2.getValue());
    }

    public String getGrilleTerrain() {
        if (grilleTerrain.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getBkgdColorView() {
        return "" + bkgdColorView.getBackground().getRGB();
    }

    public String getjCheckBoxStereo() {
        if (jCheckBoxStereo.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getjSliderYeux() {
        return String.valueOf(SliderYeux.getValue());
    }

    public String getjTextFieldTaille() {
        return TextFieldTaille.getText();
    }

    public String getjTextFieldDistance() {
        return TextFieldDistance.getText();
    }

    public String getpopUpInfo() {
        if (popUpInfo.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public boolean getTerrain() {
        return terrain;
    }

    public void setTerrain(boolean _terrain) {
        terrain = _terrain;
    }

    public float EcartementYeux() {
        return ValEcartYeux;
    }

    public void setGrid(boolean _bgrid) {
        bgrid = _bgrid;
    }

    public boolean getGrid() {
        return bgrid;
    }

    }
    //Fin de la classe qui cr�e le panel pour configurer les param�tres de l'espace 3d de la sc�ne

    //Creer le panel pour configurer les param�tres du clusturing
    public class Clustering extends javax.swing.JPanel{

    public Clustering(){
            
        Clusturing = new javax.swing.JCheckBox();
        ClustVisib = new javax.swing.JCheckBox();
        EclairageSphere = new javax.swing.JCheckBox();
        jLabelClust = new javax.swing.JLabel();
        attClustLabel = new javax.swing.JLabel();
        attClust = new javax.swing.JComboBox();
        clustMethodeLabel = new javax.swing.JLabel();
        clustMethode = new javax.swing.JComboBox();
        clustObjetLabel = new javax.swing.JLabel();
        clustObjet = new javax.swing.JComboBox();
        objSize3 = new javax.swing.JSlider();
        clustFact = new javax.swing.JSlider();
        objSize3Label = new javax.swing.JLabel();
        clustFactLabel = new javax.swing.JLabel();
        size3 = new javax.swing.JLabel();
        ToggleButton = new javax.swing.JButton();
        slideClust = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Clusturing.setText("Activer Clustering");
        Clusturing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClusturingActionPerformed(evt);
            }
        });
        add(Clusturing, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 183, -1));

        ClustVisib.setText("Visibilite Objets");
        add(ClustVisib, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 43, -1, -1));

        EclairageSphere.setText("Spheres eclairees");
        EclairageSphere.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EclairageSphereItemStateChanged(evt);
            }
        });
        add(EclairageSphere, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 68, -1, -1));

        jLabelClust.setIcon(new javax.swing.ImageIcon("C:\\Users\\Abdelheq\\Desktop\\VRMiner\\trunk\\Nuage3d\\src\\images\\Clusturing.PNG")); // NOI18N
        jLabelClust.setText("Placer l'icone ici");
        add(jLabelClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 13, 170, 134));

        attClustLabel.setText("Attribut classe :");
        add(attClustLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 102, 198, -1));

        attClust.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(attClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 125, 164, -1));

        clustMethodeLabel.setText("Methodes :");
        add(clustMethodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 165, 143, -1));

        clustMethode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clustMethode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clustMethodeItemStateChanged(evt);
            }
        });
        add(clustMethode, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 188, 163, -1));

        clustObjetLabel.setText("Objets Liaisons");
        add(clustObjetLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 223, 143, -1));

        clustObjet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        clustObjet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clustObjetItemStateChanged(evt);
            }
        });
        add(clustObjet, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 246, 160, -1));

        objSize3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSize3StateChanged(evt);
            }
        });
        add(objSize3, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 188, 135, -1));

        clustFact.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clustFactStateChanged(evt);
            }
        });
        add(clustFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 245, 135, -1));

        objSize3Label.setText("Pourcentage Transparence :");
        add(objSize3Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 165, -1, -1));

        clustFactLabel.setText("Echelle Rayon Sph�res : ");
        add(clustFactLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 222, -1, -1));

        size3.setText("0%");
        add(size3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 156, 30, 30));

        ToggleButton.setText("Reset");
        ToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToggleButtonActionPerformed(evt);
            }
        });
        add(ToggleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(323, 275, -1, -1));

        slideClust.setText("1");
        add(slideClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));
        }

    private void ClusturingActionPerformed(java.awt.event.ActionEvent evt) {

    if (!Clusturing.isSelected()) {
        ClustVisib.setEnabled(false);
        attClust.setEnabled(false);
        clustMethode.setEnabled(false);
        clustFact.setEnabled(false);
        objSize3.setEnabled(false);
        size3.setEnabled(false);
        EclairageSphere.setEnabled(false);
        ToggleButton.setEnabled(false);


    } else {
        ClustVisib.setEnabled(true);
        attClust.setEnabled(true);
        clustMethode.setEnabled(true);
        clustFact.setEnabled(true);
        objSize3.setEnabled(true);
        size3.setEnabled(true);
        EclairageSphere.setEnabled(true);
        ToggleButton.setEnabled(true);
        if (EclairageSphere.isSelected()) {
            objSize3.setEnabled(false);
            ClustVisib.setEnabled(false);
        } else {
            objSize3.setEnabled(true);
            ClustVisib.setEnabled(true);
        }

    }

    }

    private void EclairageSphereItemStateChanged(java.awt.event.ItemEvent evt) {
        if (EclairageSphere.isSelected()) {
        objSize3.setEnabled(false);
        ClustVisib.setEnabled(false);
    } else {
        objSize3.setEnabled(true);
        ClustVisib.setEnabled(true);
    }
    }

    private void clustMethodeItemStateChanged(java.awt.event.ItemEvent evt) {
        if (clustMethode.getSelectedIndex() == 1) {
        clustObjet.setEnabled(true);
    } else {
        clustObjet.setEnabled(false);
    }

    //testImagesClust();

    }

    private void clustObjetItemStateChanged(java.awt.event.ItemEvent evt) {
    ClassLoader cldr = this.getClass().getClassLoader();
    if (clustMethode.getSelectedIndex() == 1 && clustObjet.getSelectedIndex() == 0) {

        BufferedImage im = null;
        ImageIcon imageIconClust = new ImageIcon();
        try {
            im = ImageIO.read(getClass().getResourceAsStream("/images/PPV_base_chanel_sphere.PNG"));
            im = (BufferedImage) scale(im, 138, 98);
        } catch (Exception e) {
        }
        imageIconClust.setImage(im);
        jLabelClust.setIcon(imageIconClust);
    } else if (clustMethode.getSelectedIndex() == 1 && clustObjet.getSelectedIndex() == 1) {

        BufferedImage im = null;
        ImageIcon imageIconClust = new ImageIcon();
        try {
            im = ImageIO.read(getClass().getResourceAsStream("/images/PPV_base_chanel_tube.PNG"));
            im = (BufferedImage) scale(im, 138, 98);
        } catch (Exception e) {
        }
        imageIconClust.setImage(im);
        jLabelClust.setIcon(imageIconClust);
    }
    }

    private void objSize3StateChanged(javax.swing.event.ChangeEvent evt) {
        size3.setText("" + objSize3.getValue() + "%");
    }

    private void ToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (clustMethode.getSelectedIndex() == 1) {
        clustObjet.setEnabled(true);
    } else {
        clustObjet.setEnabled(false);
    }

    //testImagesClust();
    }

    private void clustFactStateChanged(javax.swing.event.ChangeEvent evt) {
        float val = clustFact.getValue();
        if (val != 0.0f) {
            val = val / 100f;
        }

        slideClust.setText(String.valueOf(val));
    }

    public String getAttClust() {
        return (String) attClust.getSelectedItem();
    }

    public String getClusturing() {
        if (Clusturing.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getClustVisib() {
        if (ClustVisib.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getEclairageSphere() {
        if (EclairageSphere.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getclustMethode() {
        return String.valueOf(clustMethode.getSelectedIndex());
    }

    public String getclustObjet() {
        return String.valueOf(clustObjet.getSelectedIndex());
    }

    public String getclustFact() {
        return String.valueOf(clustFact.getValue());
    }

    public String getobjSize3() {
        return String.valueOf(objSize3.getValue());
    }

    private Image scale(Image source, int width, int height) {
        BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buf.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return buf;
    }

    }
    //fin de la classe qui cree le panel pour configurer les param�tres du clusturing

    //Creer le panel pour configurer les param�tres r�seau
    public class Network extends javax.swing.JPanel{

    public Network(){

        adrsIpServerLabel = new javax.swing.JLabel();
        adrsIpServer = new javax.swing.JTextField();
        Connexion = new javax.swing.JButton();
        jCheckBoxReseau = new javax.swing.JCheckBox();
        Adr_IPLabel = new javax.swing.JLabel();
        Adr_IP = new javax.swing.JLabel();
        cheminLecteurLabel = new javax.swing.JLabel();
        cheminLecteur = new javax.swing.JTextField();
        BkgdImgChooser2 = new javax.swing.JButton();
        adrsIpServerLabel.setText("Adresse IP serveur :");
        adrsIpServer.setText("127.0.0.1  ");
        Connexion.setText("Connexion");
        jCheckBoxReseau.setText("Connexion reseau");
        jCheckBoxReseau.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBoxReseauMouseClicked(evt);
            }
        });
        Adr_IPLabel.setText("Adresse IP Macine Courante :");
        Adr_IP.setBackground(new java.awt.Color(255, 0, 51));
        Adr_IP.setForeground(new java.awt.Color(204, 0, 51));
        cheminLecteurLabel.setText("Chemin du Lecteur de Medias :");
        cheminLecteur.setText("C:/Program Files/Windows Media Player/wmplayer.exe");
        BkgdImgChooser2.setText("...");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(adrsIpServerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(adrsIpServer, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(Connexion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jCheckBoxReseau))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(Adr_IPLabel)
                        .addGap(12, 12, 12)
                        .addComponent(Adr_IP, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cheminLecteurLabel)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cheminLecteur, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BkgdImgChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(adrsIpServerLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(adrsIpServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Connexion))
                .addGap(9, 9, 9)
                .addComponent(jCheckBoxReseau)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Adr_IPLabel)
                    .addComponent(Adr_IP))
                .addGap(18, 18, 18)
                .addComponent(cheminLecteurLabel)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cheminLecteur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BkgdImgChooser2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

    }

    private void jCheckBoxReseauMouseClicked(java.awt.event.MouseEvent evt) {
        if (jCheckBoxReseau.isSelected()) {
        if (!Connexion.isEnabled()) {
            jCheckBoxReseau.setSelected(true);
        } else {
            new MessageBox("Warning", "Vous devez verifier votre connexion au reseau, cliquez sur connexion", MessageBox.WARNING);
            jCheckBoxReseau.setSelected(false);
        }
    } else {
        jCheckBoxReseau.setSelected(false);
    }
    }

    public String getadrsIpServer() {
        return adrsIpServer.getText();
    }

    public String getjCheckBoxReseau() {
        if (jCheckBoxReseau.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getcheminLecteur() {
        return cheminLecteur.getText();
    }

    }
    //Fin de la classe qui cree le panel pour configurer les param�tres r�seau



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IHM_Visual_Assistant1().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(IHM_Visual_Assistant1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNewProfil;
    private javax.swing.JButton AdjustIECButton;
    private javax.swing.JButton CloseButton;
    private javax.swing.JScrollPane DB_Visualisations_ScrollPane;
    private javax.swing.JToolBar DB_VisualizationToolBar;
    private javax.swing.JButton DeleteProfilButton;
    private javax.swing.JButton LaunchVisualizations;
    private javax.swing.JButton Load;
    private javax.swing.JScrollPane OverviewPictureContainer;
    private javax.swing.JProgressBar TauxSimilarite;
    private javax.swing.JLabel VisualizationOverviewLabel;
    private javax.swing.JPanel VisualizationPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JButton matching;
    private javax.swing.JTextField statusBar;
    // End of variables declaration//GEN-END:variables

}
