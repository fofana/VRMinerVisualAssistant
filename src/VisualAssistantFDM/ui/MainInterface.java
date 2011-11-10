package VisualAssistantFDM.ui;





import ParallelCoordinate.file.XMLFile;
import ParallelCoordinate.ui.ParallelDisplay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import visualisation3d.ui.Filtre;
import visualisation3d.vrmNuage3D.Visualisation_Nuage_3D;
import visualisation3d.xml.NUAGE3D;
import visualisation3d.xml.Nuage3DVisuXMLReader;
import vrminerlib.core.VRMinerFramework;
import vrminerlib.io.ExcelToXMLWriter;
import vrminerlib.io.VRMXML;
import vrminerlib.io.VRMXMLReader;
import vrminerlib.utils.MessageBox;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import VisualAssistantFDM.visualisation.ui.Appariement;
import VisualAssistantFDM.visualisation.DataBase.Categorie;
import VisualAssistantFDM.visualisation.DataBase.CategorieVisualisation;
import VisualAssistantFDM.visualisation.DataBase.ExpertObjective;
import VisualAssistantFDM.io.FiltreExtensible;
import VisualAssistantFDM.io.LoadVisualizations;
import VisualAssistantFDM.userObjectives.UserPreferences;
import VisualAssistantFDM.visualisation.ui.Matching;
import VisualAssistantFDM.io.MissingIcon;
import VisualAssistantFDM.visualisation.ui.Normalisation;
import VisualAssistantFDM.io.PreView;
import VisualAssistantFDM.xml.UpdateXMLFile;
import visualassistantfacv.VRMinerVisualAssistant;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.xml.VisuXMLReader;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import vrminerlib.control.meta.NoneVRMMetaControlInfo;
import vrminerlib.object3d.Object3D;
import vrminerlib.scene.PointOfView;
import vrminerlib.scene.PointOfViewMouseAdapter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VRMinerVisualAssistant.java
 *
 * Created on 8 nov. 2010, 09:56:45
 */



/**
 *
 * @author Abdelheq
 */
public class MainInterface extends javax.swing.JFrame {

    private MissingIcon placeholderIcon;
    private List<String> imageCaptions;
    private List<PreView> VisualisationsFilesNames;
    private List<Normalisation> listNormalisation;
    private List<Visualisation> liste, listdtm, listAttribute, DataAttribute_liste;
    private int idMethode, thumbnailIconDimension, profilNum;
    public String filePathName, imageDir = "images/", shape, afficherLiensS, jCheckBoxReseauS, colNameDataSet[] = {"Data Attribute", "Importance"}, colNameVisualization[] = {"Visual Attribute", "Type", "Importance"}, colNameDataSetMEC[] = {"Indice Visuel", "Attribut de donn�es"};
    private Object[][] data = null, visualAttribute = null;;
    private DefaultTableModel DataSetTableModel, VisualAttributeTableModel, MatchingTableModel, MatchingTableModelMEC;
    private DefaultListModel model_list_profil, model_list_profil_selection, AllExpertObjectiveListModel, ExpertObjectiveListModel, CatgoryListModel, AllVisualizationCatgoryListModel, VisualizationCatgoryListModel, ProfilListModel;
    public Document document;
    public Element racine;
    private List<Appariement> individuMEC;
    private double similarite;
    public Visualisation_Nuage_3D visu3D, visu3D1, visu3D2, visu3D3, visu3D4, visu3D5, visu3D6, visu3D7, visu3D8, visu3D9;
    public List<Visualisation_Nuage_3D> listVisu3D;
    private VisuXMLReader xmlParser;
    private ArrayList listTypeVisu = new ArrayList();
    private JTabbedPane AdvancedSettings = new JTabbedPane();
    private JScrollPane scrollpane1, scrollpane2, scrollpane3, scrollpane4, scrollpane5 ;
    private JSeparator jSeparator10, jSeparator20, jSeparator30, jSeparator40;
    private Color[] axisColor;
    private Color backGroundColor, ChoixCouleur3 = null, ChoixCouleur2 = null, ChoixCouleur1 = null, ChoixCouleur4 = null, ChoixCouleur5 = null;
    private boolean modePaletteHaut, modePaletteBas, terrain = false, bgrid, init = false;
    private float ValEcartYeux = 0.0035f;
    private HashMap<String, HashMap> mapAttributPalette = new HashMap<String, HashMap>(50);
    private ImageIcon imageIcon;
    private File file;
    private List<Double> objectivesWeight;
    private UserObjectivesDialog dialog;
    private LoadingBox loading;
    private int numMethode;
    private List<JScrollPane> listJScrollPaneVisu3D;
    private ParallelDisplay parallelDisplay;
    /**
     * Constructeur creates new form VRMinerVisualAssistant
     */

    public MainInterface() throws Exception{
        VRMinerFramework.initializeFramework();
        ExpertObjectiveListModel = new DefaultListModel();
        AllExpertObjectiveListModel = new DefaultListModel();
        model_list_profil = new DefaultListModel();
        model_list_profil_selection = new DefaultListModel();
        VisualizationCatgoryListModel = new DefaultListModel();
        AllVisualizationCatgoryListModel = new DefaultListModel();
        DataSetTableModel = new DefaultTableModel(data,colNameDataSet);
        VisualAttributeTableModel = new DefaultTableModel(visualAttribute , colNameVisualization);
        CatgoryListModel = new DefaultListModel();
        ProfilListModel = new DefaultListModel();
        placeholderIcon = new MissingIcon();
        VisualisationsFilesNames = new ArrayList<PreView>();
        imageCaptions = new ArrayList<String>();
        initComponents();
        UserPreferencePanel.setVisible(false);
        DataSetPanel.setVisible(false);
        //DB_Visualisations_ScrollPane.setPreferredSize(new Dimension(500, 500));
        Step1.setOpaque(true);
        //Step1.setBackground(Color.GRAY);
        Step2.setEnabled(false);
        Step3.setEnabled(false);
        //Step4.setEnabled(false);
        //Step5.setEnabled(false);
        Step6.setEnabled(false);
        VisualisationsFilesNames = new LoadVisualizations().LoadVisualizationsNames();
        imageCaptions = new LoadVisualizations().getCaption();
        //loadimages.execute();
        dialog = new UserObjectivesDialog(this, true);
        numMethode = new LoadVisualizations().getAllMethode().size();       
        listVisu3D = new ArrayList<Visualisation_Nuage_3D>();        
        listJScrollPaneVisu3D = new ArrayList<JScrollPane>();
        for (int i = 0; i<numMethode; i++){
            Visualisation_Nuage_3D Visu = new Visualisation_Nuage_3D(0, 0, 0);
            listVisu3D.add(Visu);
        }
        parallelDisplay = new ParallelDisplay();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane8 = new javax.swing.JScrollPane();
        DataSetPanel = new javax.swing.JPanel();
        DataSetDescriptionLabel = new javax.swing.JLabel();
        spDataSet = new javax.swing.JScrollPane();
        DataSetTable = new javax.swing.JTable();
        MatchingScrollPane = new javax.swing.JScrollPane();
        MatchingTable = new javax.swing.JTable();
        DataSetDescriptionLabel1 = new javax.swing.JLabel();
        loadXLSfile = new javax.swing.JButton();
        VisualizationPanel = new javax.swing.JPanel();
        LaunchVisualizations = new javax.swing.JButton();
        AdjustIECButton = new javax.swing.JButton();
        LoadXMLData = new javax.swing.JButton();
        TauxSimilarite = new javax.swing.JProgressBar();
        OverviewPictureContainer = new javax.swing.JScrollPane();
        CloseButton = new javax.swing.JButton();
        UserPreferencesButton = new javax.swing.JButton();
        UserPreferencePanel = new javax.swing.JPanel();
        AdjustPrefencesButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        VisualizationDescriptionPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Visualization_Name = new javax.swing.JLabel();
        GraphicElement_Name = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        OverviewVisualization = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        SP_VisualAttributeDescription = new javax.swing.JScrollPane();
        VisualAttributeTable = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        AxesPanel = new javax.swing.JPanel();
        xRatioLabel = new javax.swing.JLabel();
        yRatioLabel = new javax.swing.JLabel();
        zRatioLabel = new javax.swing.JLabel();
        xColorView = new javax.swing.JTextField();
        yColorView = new javax.swing.JTextField();
        zColorView = new javax.swing.JTextField();
        xRatio = new javax.swing.JSpinner();
        yRatio = new javax.swing.JSpinner();
        zRatio = new javax.swing.JSpinner();
        xAxisColor = new javax.swing.JButton();
        yAxisColor = new javax.swing.JButton();
        zAxisColor = new javax.swing.JButton();
        afficherLiens = new javax.swing.JCheckBox();
        attLiensLabel = new javax.swing.JLabel();
        attLiens = new javax.swing.JComboBox();
        CameraPanel = new javax.swing.JPanel();
        camTypeLabel = new javax.swing.JLabel();
        camType = new javax.swing.JComboBox();
        LocsLabel = new javax.swing.JLabel();
        xLocLabel = new javax.swing.JLabel();
        xLoc = new javax.swing.JSpinner();
        yLocLabel = new javax.swing.JLabel();
        yLoc = new javax.swing.JSpinner();
        zLoc = new javax.swing.JSpinner();
        zLocLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        Object3DPanel = new javax.swing.JPanel();
        sizeLabel = new javax.swing.JLabel();
        size = new javax.swing.JLabel();
        attSyntheseLabel = new javax.swing.JLabel();
        voixLabel = new javax.swing.JLabel();
        objSize = new javax.swing.JSlider();
        attSynthese = new javax.swing.JComboBox();
        voix = new javax.swing.JComboBox();
        jPanel13 = new javax.swing.JPanel();
        attCouleurHautLabel = new javax.swing.JLabel();
        attCouleurHaut = new javax.swing.JComboBox();
        Color2Label = new javax.swing.JLabel();
        Color2 = new javax.swing.JTextField();
        jButtonPyr1Color2 = new javax.swing.JButton();
        attPyrHautLabel = new javax.swing.JLabel();
        attPyrHaut = new javax.swing.JComboBox();
        attTextHautLabel = new javax.swing.JLabel();
        attTextHaut = new javax.swing.JComboBox();
        Color1Label = new javax.swing.JLabel();
        Color1 = new javax.swing.JTextField();
        jButtonPyr1Color1 = new javax.swing.JButton();
        jButtonEchelleCouleursHaut = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        attCouleurBasLabel = new javax.swing.JLabel();
        attCouleurBas = new javax.swing.JComboBox();
        attPyrBasLabel = new javax.swing.JLabel();
        attPyrBas = new javax.swing.JComboBox();
        attTextBasLabel = new javax.swing.JLabel();
        attTextBas = new javax.swing.JComboBox();
        jButtonEchelleCouleursBas = new javax.swing.JButton();
        Color3Label = new javax.swing.JLabel();
        Color4Label = new javax.swing.JLabel();
        jButtonPyr2Color2 = new javax.swing.JButton();
        Color4 = new javax.swing.JTextField();
        Color3 = new javax.swing.JTextField();
        jButtonPyr2Color1 = new javax.swing.JButton();
        JDrapeau = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        Space3DPanel = new javax.swing.JPanel();
        jCheckBoxStereo = new javax.swing.JCheckBox();
        size1Label = new javax.swing.JLabel();
        size1 = new javax.swing.JLabel();
        size1LabelMesure = new javax.swing.JLabel();
        jTextFieldTailleLabel = new javax.swing.JLabel();
        jTextFieldTaille = new javax.swing.JTextField();
        jTextFieldTailleLabelMesure = new javax.swing.JLabel();
        jSliderYeux = new javax.swing.JSlider();
        jTextFieldDistanceLabel = new javax.swing.JLabel();
        jTextFieldDistance = new javax.swing.JTextField();
        jTextFieldDistanceLabelMesure = new javax.swing.JLabel();
        grilleTerrain = new javax.swing.JCheckBox();
        popUpInfo = new javax.swing.JCheckBox();
        bkgdColorViewLabel = new javax.swing.JLabel();
        bkgdColorView = new javax.swing.JTextField();
        bkgdColorColorChooser = new javax.swing.JButton();
        Color5Label = new javax.swing.JLabel();
        jButtonColCube = new javax.swing.JButton();
        Color5 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        objSize2 = new javax.swing.JSlider();
        size2 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        NetworkPanel = new javax.swing.JPanel();
        adrsIpServerLabel = new javax.swing.JLabel();
        adrsIpServer = new javax.swing.JTextField();
        Connexion = new javax.swing.JButton();
        jCheckBoxReseau = new javax.swing.JCheckBox();
        Adr_IPLabel = new javax.swing.JLabel();
        Adr_IP = new javax.swing.JLabel();
        cheminLecteurLabel = new javax.swing.JLabel();
        cheminLecteur = new javax.swing.JTextField();
        BkgdImgChooser2 = new javax.swing.JButton();
        ClusturingPanel = new javax.swing.JPanel();
        Clusturing = new javax.swing.JCheckBox();
        EclairageSphere = new javax.swing.JCheckBox();
        ClustVisib = new javax.swing.JCheckBox();
        jLabelClust = new javax.swing.JLabel();
        attClustLabel = new javax.swing.JLabel();
        attClust = new javax.swing.JComboBox();
        clustMethode = new javax.swing.JComboBox();
        clustMethodeLabel = new javax.swing.JLabel();
        clustObjet = new javax.swing.JComboBox();
        clustObjetLabel = new javax.swing.JLabel();
        objSize3Label = new javax.swing.JLabel();
        clustFactLabel = new javax.swing.JLabel();
        objSize3 = new javax.swing.JSlider();
        clustFact = new javax.swing.JSlider();
        size3 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JButton();
        slideClust = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        UserObjectivePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        All_UserObjective = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        Selected_UserObjective = new javax.swing.JList();
        All_UserObjectiveLabel = new javax.swing.JLabel();
        Selected_UserObjectiveLabel = new javax.swing.JLabel();
        SelectObjective = new javax.swing.JButton();
        RemoveSelectObjective = new javax.swing.JButton();
        OthersPanel = new javax.swing.JPanel();
        VisualCategoryListLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        VisualCategoryList = new javax.swing.JList();
        VisualDimensionListLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        VisualDimensionList = new javax.swing.JList();
        jPanel6 = new javax.swing.JPanel();
        ProfilsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListProfil = new javax.swing.JList();
        Visualize_Profil = new javax.swing.JButton();
        DeleteProfil = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        UpdateProfil = new javax.swing.JButton();
        GenerateNewProfil = new javax.swing.JButton();
        DB_Visualisations_ScrollPane = new javax.swing.JScrollPane(DB_VisualizationToolBar);
        DB_VisualizationToolBar = new javax.swing.JToolBar();
        jPanelVisu = new javax.swing.JPanel();
        statusBar = new javax.swing.JTextField();
        Step1 = new javax.swing.JLabel();
        Step2 = new javax.swing.JLabel();
        Step3 = new javax.swing.JLabel();
        Step6 = new javax.swing.JLabel();
        filePath = new javax.swing.JLabel();
        filePathValue = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        XLSLoadItem = new javax.swing.JMenuItem();
        XMLLoadItem = new javax.swing.JMenuItem();
        LaunchVRMinerItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        DataSetDescriptionItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        AboutItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INTERACTIVE USER VISUAL ASSISTANT ");
        setBackground(new java.awt.Color(102, 153, 255));

        DataSetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Data Attribute Description "));

        DataSetDescriptionLabel.setBackground(new java.awt.Color(102, 102, 102));
        DataSetDescriptionLabel.setText("Data attribute descriprion :");

        //spDataSet.setVisible(false);
        spDataSet.setViewportView(DataSetTable);

        MatchingScrollPane.setViewportView(MatchingTable);

        DataSetDescriptionLabel1.setBackground(new java.awt.Color(102, 102, 102));
        DataSetDescriptionLabel1.setText("Matching Result : ");

        loadXLSfile.setText("load XLS File");
        loadXLSfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadXLSfileActionPerformed(evt);
            }
        });

        //DataSetPanel.setVisible(false);

        javax.swing.GroupLayout DataSetPanelLayout = new javax.swing.GroupLayout(DataSetPanel);
        DataSetPanel.setLayout(DataSetPanelLayout);
        DataSetPanelLayout.setHorizontalGroup(
            DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataSetPanelLayout.createSequentialGroup()
                .addGroup(DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataSetPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(DataSetDescriptionLabel))
                    .addGroup(DataSetPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(spDataSet, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DataSetPanelLayout.createSequentialGroup()
                                .addComponent(DataSetDescriptionLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE))
                            .addComponent(MatchingScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                    .addGroup(DataSetPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(loadXLSfile, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        DataSetPanelLayout.setVerticalGroup(
            DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataSetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataSetDescriptionLabel)
                    .addComponent(DataSetDescriptionLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DataSetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MatchingScrollPane, 0, 0, Short.MAX_VALUE)
                    .addComponent(spDataSet, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadXLSfile)
                .addGap(13, 13, 13))
        );

        VisualizationPanel.setPreferredSize(new Dimension(1310, 530));
        VisualizationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(" Visualization Overview"));
        VisualizationPanel.setPreferredSize(new java.awt.Dimension(830, 530));

        LaunchVisualizations.setText("Save this Matching");
        LaunchVisualizations.setEnabled(false);
        LaunchVisualizations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveVisualizationsActionPerformed(evt);
            }
        });

        AdjustIECButton.setText("Adjust this Matching");
        AdjustIECButton.setEnabled(false);
        AdjustIECButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdjustIECButtonActionPerformed(evt);
            }
        });

        LoadXMLData.setText("Load Data");
        LoadXMLData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadXMLDataActionPerformed(evt);
            }
        });

        OverviewPictureContainer.setPreferredSize(new java.awt.Dimension(100, 120));

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        UserPreferencesButton.setText("User Objectives");
        UserPreferencesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserPreferencesButtonActionPerformed(evt);
            }
        });

        //Dimension dimension = new Dimension(1000, 800);
        //VisualizationPanel.setPreferredSize(dimension);

        javax.swing.GroupLayout VisualizationPanelLayout = new javax.swing.GroupLayout(VisualizationPanel);
        VisualizationPanel.setLayout(VisualizationPanelLayout);
        VisualizationPanelLayout.setHorizontalGroup(
            VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VisualizationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VisualizationPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(UserPreferencesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LoadXMLData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AdjustIECButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LaunchVisualizations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(CloseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VisualizationPanelLayout.createSequentialGroup()
                        .addGroup(VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(OverviewPictureContainer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                            .addComponent(TauxSimilarite, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        VisualizationPanelLayout.setVerticalGroup(
            VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VisualizationPanelLayout.createSequentialGroup()
                .addComponent(OverviewPictureContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TauxSimilarite, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(VisualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(LaunchVisualizations)
                    .addComponent(CloseButton)
                    .addComponent(AdjustIECButton)
                    .addComponent(LoadXMLData)
                    .addComponent(UserPreferencesButton)))
        );

        LaunchVisualizations.getAccessibleContext().setAccessibleParent(VisualizationPanel);
        AdjustIECButton.getAccessibleContext().setAccessibleParent(VisualizationPanel);
        TauxSimilarite.setValue((int)similarite);
        TauxSimilarite.setStringPainted(true);

        UserPreferencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Preferences"));
        UserPreferencePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AdjustPrefencesButton.setText("Update Visualization Preferences");
        UserPreferencePanel.add(AdjustPrefencesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 220, -1));

        VisualizationDescriptionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Visualization Description"));
        VisualizationDescriptionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Visual Attributes Table of visualization : ");
        VisualizationDescriptionPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 125, -1, -1));
        VisualizationDescriptionPanel.add(Visualization_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 35, 140, 20));
        VisualizationDescriptionPanel.add(GraphicElement_Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 80, 120, 20));

        jLabel2.setText("Visualization Name :");
        VisualizationDescriptionPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 35, 120, -1));
        VisualizationDescriptionPanel.add(OverviewVisualization, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 130, 125));

        jLabel1.setText("Graphic Element Name :");
        VisualizationDescriptionPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 80, 144, -1));

        jButton4.setText("Delete");
        VisualizationDescriptionPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 225, 78, -1));

        VisualAttributeTable.setModel(VisualAttributeTableModel);
        SP_VisualAttributeDescription.setViewportView(VisualAttributeTable);

        VisualizationDescriptionPanel.add(SP_VisualAttributeDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 154, 315, 190));

        jButton5.setText("Save");
        VisualizationDescriptionPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 257, 78, -1));

        jButton3.setText("Add");
        VisualizationDescriptionPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 193, 78, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisualizationDescriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VisualizationDescriptionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Visualization Description", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        AxesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Axis Settings"));
        AxesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        xRatioLabel.setText("Ratio X: ");
        AxesPanel.add(xRatioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 39, -1, -1));

        yRatioLabel.setText("Ratio Y :");
        AxesPanel.add(yRatioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 76, -1, -1));

        zRatioLabel.setText("Ratio Z :");
        AxesPanel.add(zRatioLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 114, -1, -1));

        xColorView.setBackground(new java.awt.Color(255, 0, 0));
        xColorView.setEditable(false);
        AxesPanel.add(xColorView, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 36, 49, -1));

        yColorView.setBackground(new java.awt.Color(0, 153, 255));
        yColorView.setEditable(false);
        AxesPanel.add(yColorView, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 73, 49, -1));

        zColorView.setBackground(new java.awt.Color(51, 255, 51));
        zColorView.setEditable(false);
        AxesPanel.add(zColorView, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 111, 49, -1));

        xRatio.setValue(2);
        xRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xRatioStateChanged(evt);
            }
        });
        AxesPanel.add(xRatio, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 36, 43, 20));

        yRatio.setValue(2);
        yRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yRatioStateChanged(evt);
            }
        });
        AxesPanel.add(yRatio, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 73, 43, -1));

        zRatio.setValue(2);
        zRatio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zRatioStateChanged(evt);
            }
        });
        AxesPanel.add(zRatio, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 111, 43, -1));

        xAxisColor.setText("...");
        AxesPanel.add(xAxisColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 35, 55, -1));

        yAxisColor.setText("...");
        AxesPanel.add(yAxisColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 73, 55, -1));

        zAxisColor.setText("...");
        AxesPanel.add(zAxisColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 111, 55, -1));

        afficherLiens.setText("Dispaly links");
        afficherLiens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afficherLiensActionPerformed(evt);
            }
        });
        AxesPanel.add(afficherLiens, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 35, -1, -1));

        attLiensLabel.setText("Links attribute :");
        AxesPanel.add(attLiensLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 74, 135, -1));

        attLiens.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        AxesPanel.add(attLiens, new org.netbeans.lib.awtextra.AbsoluteConstraints(265, 103, 130, -1));

        CameraPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera Settings"));
        CameraPanel.setPreferredSize(new java.awt.Dimension(425, 157));
        CameraPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        camTypeLabel.setText("The default type of camera :");
        CameraPanel.add(camTypeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 22, 169, -1));

        camType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        CameraPanel.add(camType, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 45, 182, -1));

        LocsLabel.setText("Initial coordinate position :");
        CameraPanel.add(LocsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 85, -1, -1));

        xLocLabel.setText("X :");
        CameraPanel.add(xLocLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 117, -1, -1));

        xLoc.setValue(5);
        CameraPanel.add(xLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 114, 42, -1));

        yLocLabel.setText("Y :");
        CameraPanel.add(yLocLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 117, -1, -1));

        yLoc.setValue(5);
        CameraPanel.add(yLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 114, 42, -1));

        zLoc.setValue(5);
        CameraPanel.add(zLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(281, 114, 42, -1));

        zLocLabel.setText("Z :");
        CameraPanel.add(zLocLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 117, -1, -1));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CameraPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(AxesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(176, Short.MAX_VALUE)
                .addComponent(CameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(AxesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(194, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Axis & Camera", jPanel7);

        Object3DPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("3D Object Settings "));
        Object3DPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sizeLabel.setText("Size Of the 3D Object :");
        Object3DPanel.add(sizeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 22, -1, -1));

        size.setText("137");
        Object3DPanel.add(size, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 22, 30, -1));

        attSyntheseLabel.setText("Voice synthesis attribute :");
        Object3DPanel.add(attSyntheseLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 74, -1, -1));

        voixLabel.setText("Voice :");
        Object3DPanel.add(voixLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 74, 78, -1));

        objSize.setMaximum(200);
        objSize.setValue(137);
        objSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSizeStateChanged(evt);
            }
        });
        Object3DPanel.add(objSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 44, -1, -1));

        Object3DPanel.add(attSynthese, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 97, 146, -1));

        voix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pas de voix", "Thierry", "Celine", "Vincent", "AnneCarole", "Fabrice", "Xavier", "Soldat Inconnu", "Brian" }));
        voix.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                voixItemStateChanged(evt);
            }
        });
        Object3DPanel.add(voix, new org.netbeans.lib.awtextra.AbsoluteConstraints(222, 97, 143, -1));

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Pyramidion up or sphere"));

        attCouleurHautLabel.setText("Color attribute");

        attCouleurHaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attCouleurHautActionPerformed(evt);
            }
        });

        Color2Label.setText("Min Color");

        Color2.setBackground(new java.awt.Color(0, 255, 206));
        Color2.setEditable(false);

        jButtonPyr1Color2.setText("...");
        jButtonPyr1Color2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPyr1Color2ActionPerformed(evt);
            }
        });

        attPyrHautLabel.setText("Up size :");

        attTextHautLabel.setText("Up text :");

        Color1Label.setText("Max Color");

        Color1.setBackground(new java.awt.Color(255, 0, 0));
        Color1.setEditable(false);

        jButtonPyr1Color1.setText("...");
        jButtonPyr1Color1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPyr1Color1ActionPerformed(evt);
            }
        });

        jButtonEchelleCouleursHaut.setText("Echelle de couleurs...");
        jButtonEchelleCouleursHaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEchelleCouleursHautActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(attCouleurHautLabel)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(Color2Label)
                            .addGap(22, 22, 22)
                            .addComponent(Color1Label))
                        .addComponent(attCouleurHaut, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(attPyrHaut, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(attTextHaut, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(attPyrHautLabel)
                .addGap(44, 44, 44)
                .addComponent(attTextHautLabel))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPyr1Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPyr1Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButtonEchelleCouleursHaut, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {attPyrHaut, attTextHaut});

        jPanel13Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Color1, Color2, jButtonPyr1Color1, jButtonPyr1Color2});

        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(attCouleurHautLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attCouleurHaut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Color2Label)
                    .addComponent(Color1Label, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Color2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPyr1Color2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPyr1Color1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(attPyrHautLabel)
                    .addComponent(attTextHautLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(attTextHaut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attPyrHaut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(48, 48, 48)
                    .addComponent(jButtonEchelleCouleursHaut)
                    .addContainerGap(103, Short.MAX_VALUE)))
        );

        Object3DPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 126, -1, -1));

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Pyramidion down"));

        attCouleurBasLabel.setText("Color attribute");

        attCouleurBas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attCouleurBasActionPerformed(evt);
            }
        });

        attPyrBasLabel.setText("Down size :");

        attTextBasLabel.setText("Down text :");

        jButtonEchelleCouleursBas.setText("Echelle de couleurs...");
        jButtonEchelleCouleursBas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEchelleCouleursBasActionPerformed(evt);
            }
        });

        Color3Label.setText("Max Color");

        Color4Label.setText("Min Color");

        jButtonPyr2Color2.setText("...");
        jButtonPyr2Color2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPyr2Color2ActionPerformed(evt);
            }
        });

        Color4.setBackground(new java.awt.Color(0, 255, 206));
        Color4.setEditable(false);

        Color3.setBackground(new java.awt.Color(255, 0, 0));
        Color3.setEditable(false);

        jButtonPyr2Color1.setText("...");
        jButtonPyr2Color1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPyr2Color1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(attCouleurBasLabel)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(Color4Label)
                                        .addGap(22, 22, 22)
                                        .addComponent(Color3Label))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(Color4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonPyr2Color2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Color3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonPyr2Color1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(attCouleurBas, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(attPyrBas, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(attPyrBasLabel)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(attTextBas, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(attTextBasLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jButtonEchelleCouleursBas, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {attCouleurBas, jButtonEchelleCouleursBas});

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Color3, Color4});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(attCouleurBasLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attCouleurBas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Color4Label)
                    .addComponent(Color3Label, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Color4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPyr2Color2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(Color3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPyr2Color1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(attPyrBasLabel)
                            .addComponent(attTextBasLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attPyrBas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(attTextBas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jButtonEchelleCouleursBas)
                    .addContainerGap(102, Short.MAX_VALUE)))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Color3, Color4});

        Object3DPanel.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 126, 189, -1));
        Object3DPanel.add(JDrapeau, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 93, 67, 16));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Object3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(Object3DPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("3D Object", jPanel8);

        Space3DPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("3D Space Settings"));

        jCheckBoxStereo.setText("Enable Stereoscopy");
        jCheckBoxStereo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxStereoActionPerformed(evt);
            }
        });

        size1Label.setText("Spacing of the eyes :");

        size1.setText("0.0035");

        size1LabelMesure.setText("m");

        jTextFieldTailleLabel.setText("Your size :");

        jTextFieldTaille.setText("1.70");

        jTextFieldTailleLabelMesure.setText("m");

        jSliderYeux.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderYeuxStateChanged(evt);
            }
        });

        jTextFieldDistanceLabel.setText("Distance Projector - Screen :");

        jTextFieldDistance.setText(" 2 ");

        jTextFieldDistanceLabelMesure.setText("m");

        grilleTerrain.setSelected(true);
        grilleTerrain.setText("Display ground grid");

        popUpInfo.setSelected(true);
        popUpInfo.setText("Pop up of Infomations");

        bkgdColorViewLabel.setText("Background Color :");

        bkgdColorView.setBackground(new java.awt.Color(102, 102, 102));
        bkgdColorView.setEditable(false);

        bkgdColorColorChooser.setText("...");
        bkgdColorColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkgdColorColorChooserActionPerformed(evt);
            }
        });

        Color5Label.setText("Cube covering color : ");

        jButtonColCube.setText("...");
        jButtonColCube.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonColCubeActionPerformed(evt);
            }
        });

        Color5.setBackground(new java.awt.Color(0, 153, 255));
        Color5.setEditable(false);

        jLabel13.setText("Transparency Rate :");

        objSize2.setValue(90);
        objSize2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSize2StateChanged(evt);
            }
        });

        size2.setText("90%");

        javax.swing.GroupLayout Space3DPanelLayout = new javax.swing.GroupLayout(Space3DPanel);
        Space3DPanel.setLayout(Space3DPanelLayout);
        Space3DPanelLayout.setHorizontalGroup(
            Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTextFieldDistanceLabel)
                .addGap(59, 59, 59)
                .addComponent(jTextFieldDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jTextFieldDistanceLabelMesure))
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(bkgdColorViewLabel)
                .addGap(79, 79, 79)
                .addComponent(popUpInfo))
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(bkgdColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(bkgdColorColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(grilleTerrain))
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(Color5Label)
                .addGap(62, 62, 62)
                .addComponent(jLabel13))
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(Color5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButtonColCube, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(objSize2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(size2))
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addComponent(jTextFieldTailleLabel)
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldTaille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldTailleLabelMesure))
                    .addComponent(jCheckBoxStereo))
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(size1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(size1)
                        .addGap(7, 7, 7)
                        .addComponent(size1LabelMesure))
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jSliderYeux, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        Space3DPanelLayout.setVerticalGroup(
            Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Space3DPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(size1Label)
                    .addComponent(jCheckBoxStereo)
                    .addComponent(size1)
                    .addComponent(size1LabelMesure))
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSliderYeux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Space3DPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jTextFieldTailleLabel))
                            .addComponent(jTextFieldTaille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Space3DPanelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jTextFieldTailleLabelMesure)))))
                .addGap(18, 18, 18)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextFieldDistanceLabel))
                    .addComponent(jTextFieldDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jTextFieldDistanceLabelMesure)))
                .addGap(18, 18, 18)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bkgdColorViewLabel)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(popUpInfo)))
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bkgdColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bkgdColorColorChooser)
                    .addComponent(grilleTerrain))
                .addGap(18, 18, 18)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Color5Label)
                    .addComponent(jLabel13))
                .addGap(7, 7, 7)
                .addGroup(Space3DPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(Color5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Space3DPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButtonColCube))
                    .addComponent(objSize2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(size2)))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Space3DPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Space3DPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("3D Space", jPanel9);

        NetworkPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Network Settings"));
        NetworkPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adrsIpServerLabel.setText("IP server adress :");
        NetworkPanel.add(adrsIpServerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 32, -1, -1));

        adrsIpServer.setText("127.0.0.1");
        adrsIpServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrsIpServerActionPerformed(evt);
            }
        });
        NetworkPanel.add(adrsIpServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 29, 101, -1));

        Connexion.setText("Connexion");
        Connexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnexionActionPerformed(evt);
            }
        });
        NetworkPanel.add(Connexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 58, -1, -1));

        jCheckBoxReseau.setText("Network connexion");
        jCheckBoxReseau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxReseauItemStateChanged(evt);
            }
        });
        NetworkPanel.add(jCheckBoxReseau, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 28, -1, -1));

        Adr_IPLabel.setText("Current Hote IP Adresse : ");
        NetworkPanel.add(Adr_IPLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 58, 157, -1));

        Adr_IP.setForeground(new java.awt.Color(204, 0, 51));
        NetworkPanel.add(Adr_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 58, 102, 16));

        cheminLecteurLabel.setText("Windows Media Player Path :");
        NetworkPanel.add(cheminLecteurLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 81, -1, -1));

        cheminLecteur.setText("C:/Program Files/Windows Media Player/wmplayer.exe");
        NetworkPanel.add(cheminLecteur, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 105, -1, -1));

        BkgdImgChooser2.setText("...");
        BkgdImgChooser2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BkgdImgChooser2ActionPerformed(evt);
            }
        });
        NetworkPanel.add(BkgdImgChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 104, 44, -1));

        ClusturingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Clustering Settings"));
        ClusturingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Clusturing.setText("Enable Clusturing");
        Clusturing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClusturingActionPerformed(evt);
            }
        });
        ClusturingPanel.add(Clusturing, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 22, -1, -1));

        EclairageSphere.setText("Lighted spheres");
        EclairageSphere.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EclairageSphereItemStateChanged(evt);
            }
        });
        ClusturingPanel.add(EclairageSphere, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 22, -1, -1));

        ClustVisib.setText("Objects Visibility");
        ClusturingPanel.add(ClustVisib, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 22, -1, -1));

        jLabelClust.setBackground(new java.awt.Color(255, 255, 255));
        ClusturingPanel.add(jLabelClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 50, 132, 80));

        attClustLabel.setText("Class Attribute :");
        ClusturingPanel.add(attClustLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 53, -1, -1));

        ClusturingPanel.add(attClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 50, 132, -1));

        clustMethode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sans methode", "Plus Proche Voisin", "Centre Fixe", "Rayon Maximum", "Centre Fixe", "Rayon Minimum", "Centre Mobile", "Rayon Maximum", "Centre Mobile", "Rayon Minimum" }));
        clustMethode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clustMethodeItemStateChanged(evt);
            }
        });
        ClusturingPanel.add(clustMethode, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 79, -1, -1));

        clustMethodeLabel.setText("Methods :");
        ClusturingPanel.add(clustMethodeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 82, -1, -1));

        clustObjet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Spheres", "Lignes" }));
        clustObjet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clustObjetItemStateChanged(evt);
            }
        });
        ClusturingPanel.add(clustObjet, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 108, 131, -1));

        clustObjetLabel.setText("Link objects :");
        ClusturingPanel.add(clustObjetLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 111, -1, -1));

        objSize3Label.setText("Transparency Rate :");
        ClusturingPanel.add(objSize3Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 134, -1, -1));

        clustFactLabel.setText("Scale of sphere radius : ");
        ClusturingPanel.add(clustFactLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 165, -1, -1));

        objSize3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                objSize3StateChanged(evt);
            }
        });
        ClusturingPanel.add(objSize3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 140, 20));

        clustFact.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                clustFactStateChanged(evt);
            }
        });
        ClusturingPanel.add(clustFact, new org.netbeans.lib.awtextra.AbsoluteConstraints(164, 162, 130, -1));

        size3.setText("50%");
        ClusturingPanel.add(size3, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 134, -1, -1));

        jToggleButton1.setText("Reset");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        ClusturingPanel.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        slideClust.setText("  1");
        ClusturingPanel.add(slideClust, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 165, 30, -1));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ClusturingPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NetworkPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(NetworkPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(ClusturingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Network & Clusturing", jPanel10);

        jPanel2.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Advanced Settings", jPanel2);

        jPanel5.setEnabled(false);

        UserObjectivePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Objectives"));
        UserObjectivePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        All_UserObjective.setModel(ExpertObjectiveListModel);
        jScrollPane3.setViewportView(All_UserObjective);

        UserObjectivePanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 58, 169, 130));

        jScrollPane4.setViewportView(Selected_UserObjective);

        UserObjectivePanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 58, 169, -1));

        All_UserObjectiveLabel.setText("All objectives :");
        UserObjectivePanel.add(All_UserObjectiveLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 35, -1, -1));

        Selected_UserObjectiveLabel.setText("Selected objectives :");
        UserObjectivePanel.add(Selected_UserObjectiveLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(242, 35, -1, -1));

        SelectObjective.setText(">");
        UserObjectivePanel.add(SelectObjective, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 83, -1, -1));

        RemoveSelectObjective.setText("<");
        UserObjectivePanel.add(RemoveSelectObjective, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 138, -1, -1));

        OthersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Others"));
        OthersPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        VisualCategoryListLabel.setText("Visual Category :");
        OthersPanel.add(VisualCategoryListLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 22, -1, -1));

        VisualCategoryList.setModel(VisualizationCatgoryListModel);
        jScrollPane5.setViewportView(VisualCategoryList);

        OthersPanel.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 45, 163, 112));

        VisualDimensionListLabel.setText("Visual Dimension :");
        OthersPanel.add(VisualDimensionListLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 22, -1, -1));

        VisualDimensionList.setModel(CatgoryListModel);
        jScrollPane6.setViewportView(VisualDimensionList);

        OthersPanel.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 45, 169, 112));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserObjectivePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addComponent(OthersPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UserObjectivePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(OthersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("User Objectives", jPanel5);

        jPanel6.setEnabled(false);

        ProfilsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Profils"));
        ProfilsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jListProfil.setModel(ProfilListModel);
        jScrollPane1.setViewportView(jListProfil);

        ProfilsPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 76, 155, 151));

        Visualize_Profil.setText("Visualize Profil");
        Visualize_Profil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Visualize_ProfilActionPerformed(evt);
            }
        });
        ProfilsPanel.add(Visualize_Profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 76, 123, -1));

        DeleteProfil.setText("Delete ");
        DeleteProfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteProfilActionPerformed(evt);
            }
        });
        ProfilsPanel.add(DeleteProfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 146, 123, -1));

        jLabel5.setText("Existing profils");
        ProfilsPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 50, -1, -1));

        UpdateProfil.setText("Update");
        ProfilsPanel.add(UpdateProfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 181, 123, -1));

        GenerateNewProfil.setText("Add New Profil");
        ProfilsPanel.add(GenerateNewProfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 111, 123, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProfilsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(ProfilsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profils", jPanel6);

        UserPreferencePanel.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 470, 410));

        //UserPreferencePanel.setVisible(false);

        thumbnailIconDimension = 240;
        DB_Visualisations_ScrollPane.setPreferredSize(new Dimension(1340, 310));

        DB_Visualisations_ScrollPane.setViewportView(DB_VisualizationToolBar);
        DB_Visualisations_ScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(" Suggested visualizations "));
        DB_Visualisations_ScrollPane.setAutoscrolls(true);
        DB_Visualisations_ScrollPane.setPreferredSize(new java.awt.Dimension(1300, 310));

        DB_VisualizationToolBar.setBorder(null);
        DB_VisualizationToolBar.setRollover(true);

        javax.swing.GroupLayout jPanelVisuLayout = new javax.swing.GroupLayout(jPanelVisu);
        jPanelVisu.setLayout(jPanelVisuLayout);
        jPanelVisuLayout.setHorizontalGroup(
            jPanelVisuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 585, Short.MAX_VALUE)
        );
        jPanelVisuLayout.setVerticalGroup(
            jPanelVisuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
        );

        DB_VisualizationToolBar.add(jPanelVisu);

        DB_Visualisations_ScrollPane.setViewportView(DB_VisualizationToolBar);

        statusBar.setEditable(false);
        statusBar.setPreferredSize(new java.awt.Dimension(6, 23));

        Step1.setText("                  1. Load Data");

        Step2.setText("                      2. Choose Visualization");
        Step2.setPreferredSize(new java.awt.Dimension(220, 8));

        Step3.setText("            3. Launch IGA to Adjust Matching");

        Step6.setText("                                4. Close");

        filePath.setText("Path of Data Base : ");

        filePathValue.setForeground(new java.awt.Color(0, 51, 255));

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        XLSLoadItem.setText("Load XLS file");
        XLSLoadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XLSLoadItemActionPerformed(evt);
            }
        });
        jMenu1.add(XLSLoadItem);

        XMLLoadItem.setText("Load XML file");
        XMLLoadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XMLLoadItemActionPerformed(evt);
            }
        });
        jMenu1.add(XMLLoadItem);

        LaunchVRMinerItem.setText("Launch manual Configuration Process (VRMiner) ");
        jMenu1.add(LaunchVRMinerItem);
        jMenu1.add(jSeparator1);

        jMenuItem1.setText("Exit");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        DataSetDescriptionItem.setText("Data Attribute Description");
        DataSetDescriptionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataSetDescriptionItemActionPerformed(evt);
            }
        });
        jMenu2.add(DataSetDescriptionItem);

        jMenu4.setText("User Preferences");

        jMenuItem2.setText("Adjust Visual Attribute Weight ");
        jMenu4.add(jMenuItem2);

        jMenuItem3.setText("3D Space Configuration");
        jMenu4.add(jMenuItem3);

        jMenuItem4.setText("jMenuItem4");
        jMenu4.add(jMenuItem4);

        jMenu2.add(jMenu4);

        jMenuItem5.setText("Hide Configuration Panels");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        AboutItem.setText("About Visual Assistant");
        jMenu3.add(AboutItem);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DataSetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(UserPreferencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DB_Visualisations_ScrollPane, 0, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(VisualizationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(Step1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Step2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(Step3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Step6)))
                .addContainerGap())
            .addComponent(statusBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filePathValue, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filePathValue, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VisualizationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DB_Visualisations_ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DataSetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UserPreferencePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(Step6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Step3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Step2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Step1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        statusBar.setEnabled(false);
        statusBar.setDoubleBuffered(true);
        statusBar.setText("VRMinerVisualAssistant - V1.1 (F�vrier 2011)");

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1164)/2, (screenSize.height-886)/2, 1164, 886);
    }// </editor-fold>//GEN-END:initComponents

    public ArrayList getTypeVisu() {
            return listTypeVisu;
    }
    /**
     * Methode pour le chargement des donn�es � partir du fichier xml
     * @param evt
     */
    private void LoadXMLDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadXMLDataActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FiltreExtensible Filtre = new FiltreExtensible("Fichier xml");
        Filtre.addExtension(".xml");
        chooser.addChoosableFileFilter(Filtre);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            DataSetTableModel.setRowCount(0);
            filePathName = chooser.getSelectedFile().getAbsolutePath().toString();
            filePathValue.setText(chooser.getSelectedFile().getAbsolutePath());
            System.out.println("List path : "+filePathName);
            try{
            liste = new Matching().getListe(filePathName);
            System.out.println("List size : "+liste.size());
            // get DataList
            liste = new Matching().getListeTri(liste);
            listNormalisation = new ArrayList<Normalisation>();
            for(int i=0; i<liste.size(); i++){
                Normalisation result = new Normalisation();
                if(liste.get(i).getType().equals("numeric")){
                result.setNom(liste.get(i).getName());
                result.setMin(0);
                result.setMax(0);
                listNormalisation.add(result);
                }
            }

            for(int i=0; i<liste.size();i++){
                DataSetTableModel.addRow(new Object[]{liste.get(i).getName(),liste.get(i).getImportance(),liste.get(i).getType()});
            }

            DataSetTable.setModel(DataSetTableModel);

            final File Fichier;
            Fichier = chooser.getSelectedFile();
            Step3.setEnabled(false);
            Step6.setEnabled(false);
            File fichier = new File(filePathName);
            // get VisualAtributes
            List<Visualisation> listViusaAttribute = new LoadVisualizations().getIdMethode(1);
            try {
                new UpdateXMLFile(filePathName);
            } catch (Exception ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            //loading = new LoadingBox("Load & Display Data on real-time");
            //loading = new JProgressBar();
            for(int j=1; j<=numMethode; j++){
            //Barre de progression pour la lecture:
            //getLoading();
            //getLoading().getProgressBar().setValue((int) (j / (float) (9) * 100));
            //getLoading().setAction("Lecture des elements du profil" + j);
                // get Appariement
                List<Appariement> individuResultatMEC = GenerateMatchingWithProfil(j);
                shape = new LoadVisualizations().getIdElement(j);
                AddNewUserPofilSettingsGA(j, fichier, listViusaAttribute, individuResultatMEC, shape);
                enregistreFichier(filePathName);
            }
            AfficherIndividus(filePathName);
            Step2.setEnabled(true);
            //loadimages.execute();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "erreur Loading XML File : " + ex.getMessage());
            }

            
        }
    }//GEN-LAST:event_LoadXMLDataActionPerformed
    
    public LoadingBox getLoading() {
        if (loading == null) {
            loading = new LoadingBox("Chargement ...", 0, 100);
        }
        return loading;
    }
    /**
     * Methode pour lancer l'interface du param�trage semi-automatique avec l'AGI
     * @param evt
     */
    private void AdjustIECButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdjustIECButtonActionPerformed
        try {
           addProfils();
           liste = new Matching().getListe(filePathName);
           liste = new Matching().getListeTri(liste);
           listAttribute = new LoadVisualizations().getIdMethode(1);
           initialisationNuage3DEmpty();
           new IGAInterface(individuMEC, filePathName, liste, listAttribute, shape, listNormalisation).setVisible(true);
           this.setVisible(false);
           }
         catch (Exception ex){
           JOptionPane.showMessageDialog(new JOptionPane(), "You must load data then matching them with the appropriate visualization, then adding result to xml file before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_AdjustIECButtonActionPerformed

    /**
     * Methode pour lancer enregistrer le param�trage de la visualisation s�lectionn�e
     * @param evt
     */
    private void SaveVisualizationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveVisualizationsActionPerformed
    try {
            /**
             * pour chaque individu s�l�ctionner cr�er un nouveau noeud profil
             * puis, l'associ� au noeud nuage3D du noeud visualization,
             * le num�ro du profil sert juste � r�cuperer le profil
             */

            File f = new File(filePathName);
            SaveSelectedProfil(f, "profil"+idMethode);
            enregistreFichier(filePathName);


            //new ApplyNewConfiguration(XMLfilepathName, indiceSelectedProfil);
        } catch (Exception ex) {
            Logger.getLogger(VRMinerVisualAssistant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SaveVisualizationsActionPerformed

    /**
     * Methode pour fermer l'interface
     * @param evt
     */
private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
    System.exit(0);
}//GEN-LAST:event_CloseButtonActionPerformed

private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
    this.setVisible(false);
}//GEN-LAST:event_jMenu1ActionPerformed

    /**
     * Methode pour supprimer les profils du fichiers xml
     * @param evt
     */
private void DeleteProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteProfilActionPerformed
    try {
        new UpdateXMLFile(filePathName);
    } catch (Exception ex) {
        Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_DeleteProfilActionPerformed

private void Visualize_ProfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Visualize_ProfilActionPerformed
    try{
        new VisualizationForm(filePathName, profilNum).setVisible(true);
    } catch (Exception ex){
        JOptionPane.showMessageDialog(null, "erreur reading XML Profil : " + ex.getMessage());
    }
}//GEN-LAST:event_Visualize_ProfilActionPerformed

private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
    clustFact.setValue(100);
}//GEN-LAST:event_jToggleButton1ActionPerformed

private void clustFactStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_clustFactStateChanged
    float val = clustFact.getValue();
    if (val != 0.0f) {
        val = val / 100f;
    }

    slideClust.setText(String.valueOf(val));
}//GEN-LAST:event_clustFactStateChanged

private void objSize3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_objSize3StateChanged
    size3.setText("" + objSize3.getValue() + "%");
}//GEN-LAST:event_objSize3StateChanged

private void clustObjetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clustObjetItemStateChanged
    ClassLoader cldr = this.getClass().getClassLoader();
    if (clustMethode.getSelectedIndex() == 1 && clustObjet.getSelectedIndex() == 0) {

        BufferedImage im = null;
        ImageIcon imageIconClust = new ImageIcon();
        try {
            im = ImageIO.read(getClass().getResourceAsStream("/pictures/PPV_base_chanel_sphere.PNG"));
            im = (BufferedImage) scale(im, 138, 98);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur clustObjetItemStateChanged : " + e.getMessage());
        }
        imageIconClust.setImage(im);
        jLabelClust.setIcon(imageIconClust);
    } else if (clustMethode.getSelectedIndex() == 1 && clustObjet.getSelectedIndex() == 1) {

        BufferedImage im = null;
        ImageIcon imageIconClust = new ImageIcon();
        try {
            im = ImageIO.read(getClass().getResourceAsStream("/pictures/PPV_base_chanel_tube.PNG"));
            im = (BufferedImage) scale(im, 138, 98);
        } catch (Exception e) {
        }
        imageIconClust.setImage(im);
        jLabelClust.setIcon(imageIconClust);
    }
}//GEN-LAST:event_clustObjetItemStateChanged

private void clustMethodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clustMethodeItemStateChanged
    if (clustMethode.getSelectedIndex() == 1) {
        clustObjet.setEnabled(true);
    } else {
        clustObjet.setEnabled(false);
    }

    //testImagesClust();
}//GEN-LAST:event_clustMethodeItemStateChanged

private void EclairageSphereItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EclairageSphereItemStateChanged
    if (EclairageSphere.isSelected()) {
        objSize3.setEnabled(false);
        ClustVisib.setEnabled(false);
    } else {
        objSize3.setEnabled(true);
        ClustVisib.setEnabled(true);
    }
}//GEN-LAST:event_EclairageSphereItemStateChanged

private void ClusturingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClusturingActionPerformed

    if (!Clusturing.isSelected()) {
        ClustVisib.setEnabled(false);
        attClust.setEnabled(false);
        clustMethode.setEnabled(false);
        clustFact.setEnabled(false);
        objSize3.setEnabled(false);
        size3.setEnabled(false);
        EclairageSphere.setEnabled(false);
        jToggleButton1.setEnabled(false);

    } else {
        ClustVisib.setEnabled(true);
        attClust.setEnabled(true);
        clustMethode.setEnabled(true);
        clustFact.setEnabled(true);
        objSize3.setEnabled(true);
        size3.setEnabled(true);
        EclairageSphere.setEnabled(true);
        jToggleButton1.setEnabled(true);
        if (EclairageSphere.isSelected()) {
            objSize3.setEnabled(false);
            ClustVisib.setEnabled(false);
        } else {
            objSize3.setEnabled(true);
            ClustVisib.setEnabled(true);
        }

    }
}//GEN-LAST:event_ClusturingActionPerformed

private void BkgdImgChooser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BkgdImgChooser2ActionPerformed
    int choix;
    int mode = JFileChooser.FILES_ONLY;

    Filtre fi = new Filtre("Fichiers Executable", ".exe");
    JFileChooser chooser = new JFileChooser(".");
    chooser.setFileSelectionMode(mode);
    chooser.addChoosableFileFilter(fi);
    choix = chooser.showOpenDialog(null);

    if (choix == JFileChooser.APPROVE_OPTION) {
        file = chooser.getSelectedFile();
        System.out.println("chemin du fichier: " + file.getAbsolutePath());

        //tf.setText(file.getAbsolutePath());
        /// LoadingBox loading = new LoadingBox();

        String f = file.getAbsolutePath();

        if (f.contains("wmplayer.exe")) {
            cheminLecteur.setText(f);
        } else {
            new MessageBox("Mauvais Chemin", "Vous devez selectionner un lecteur Windows Media Player", MessageBox.ERROR);
        }

    }
}//GEN-LAST:event_BkgdImgChooser2ActionPerformed

private void jCheckBoxReseauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxReseauItemStateChanged
    if (jCheckBoxReseau.isSelected()) {
        if (!Connexion.isEnabled()) {
            jCheckBoxReseau.setSelected(true);
            jCheckBoxReseauS = "true";
        } else {
            new MessageBox("Warning", "Vous devez verifier votre connexion au reseau, cliquez sur connexion", MessageBox.WARNING);
            jCheckBoxReseau.setSelected(false);
            jCheckBoxReseauS = "false";
        }
    } else {
        jCheckBoxReseau.setSelected(false);
    }
}//GEN-LAST:event_jCheckBoxReseauItemStateChanged

private void ConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnexionActionPerformed
    try {

        Socket socket = new Socket(adrsIpServer.getText(), 1000);
        Connexion.setEnabled(false);
        jCheckBoxReseau.setSelected(true);

    } catch (UnknownHostException ex) {
        new MessageBox("Warning", "Adresse Host inconnue ou probleme de connexion", MessageBox.WARNING);

    } catch (IOException ex) {
        new MessageBox("Erreur de connexion", "Votre connexion a generer une exception, avez vous indique la bonne adresse ?", MessageBox.ERROR);

    }
}//GEN-LAST:event_ConnexionActionPerformed

private void adrsIpServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrsIpServerActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_adrsIpServerActionPerformed

private void objSize2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_objSize2StateChanged
    size2.setText("" + objSize2.getValue() + "%");
}//GEN-LAST:event_objSize2StateChanged

private void jButtonColCubeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonColCubeActionPerformed
    Color col = Color5.getBackground();

    Color color1 = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
    Color5.setBackground(color1);

    ChoixCouleur5 = color1;
}//GEN-LAST:event_jButtonColCubeActionPerformed

private void bkgdColorColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkgdColorColorChooserActionPerformed
    backGroundColor = JColorChooser.showDialog(
            this,
            "Couleur du fond",
            Color.GRAY);
    bkgdColorView.setBackground(backGroundColor);
}//GEN-LAST:event_bkgdColorColorChooserActionPerformed

private void jSliderYeuxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderYeuxStateChanged
    float ValTrans = (float) 0.00007;
    float valTemp = jSliderYeux.getValue() * ValTrans;
    setValTemp(valTemp);

    DecimalFormat df = new DecimalFormat("########.00000");
    size1.setText("" + (df.format(valTemp)));

    setValTemp(valTemp);
}//GEN-LAST:event_jSliderYeuxStateChanged

private void jCheckBoxStereoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxStereoActionPerformed
    if (jCheckBoxStereo.isSelected()) {
        jSliderYeux.setValue(50);
    } else {
        jSliderYeux.setValue(0);
    }
}//GEN-LAST:event_jCheckBoxStereoActionPerformed

private void jButtonPyr2Color1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPyr2Color1ActionPerformed
    Color col = Color3.getBackground();

    Color color = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
    Color3.setBackground(color);

    ChoixCouleur3 = color;
}//GEN-LAST:event_jButtonPyr2Color1ActionPerformed

private void jButtonPyr2Color2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPyr2Color2ActionPerformed
    Color col = Color4.getBackground();

    Color color = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
    Color4.setBackground(color);

    ChoixCouleur4 = color;
}//GEN-LAST:event_jButtonPyr2Color2ActionPerformed

private void jButtonEchelleCouleursBasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEchelleCouleursBasActionPerformed
    //    String nomAtt = attCouleurBas.getSelectedItem().toString();
    //    JDialogEchelleCouleurs frameEchelle = new JDialogEchelleCouleurs(true);
    //    HashMap<String, Color> mapValuesColor = mapAttributPalette.get(nomAtt);
    //    frameEchelle.setPalette(mapValuesColor);
    //    frameEchelle.setVisible(true);
    //    frameEchelle.getPalette(mapValuesColor);
}//GEN-LAST:event_jButtonEchelleCouleursBasActionPerformed

private void attCouleurBasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attCouleurBasActionPerformed
    if (evt.getActionCommand().equals("comboBoxChanged")) {
        // Verifier si l'attribut selectionne est de type num ou str et changer l'affichage du dessous en fonction
        String nomAttribut = attCouleurBas.getSelectedItem().toString();
        if (xmlParser.getVectNumParam().contains(nomAttribut)) {
            jButtonEchelleCouleursBas.setEnabled(false);
            Color4Label.setEnabled(true);
            Color3Label.setEnabled(true);
            jButtonPyr2Color1.setEnabled(true);
            jButtonPyr2Color2.setEnabled(true);
            Color3.setEnabled(true);
            Color4.setEnabled(true);
            modePaletteBas = false;
        } else {
            jButtonEchelleCouleursBas.setEnabled(true);
            Color4Label.setEnabled(false);
            Color3Label.setEnabled(false);
            jButtonPyr2Color1.setEnabled(false);
            jButtonPyr2Color2.setEnabled(false);
            Color3.setEnabled(false);
            Color4.setEnabled(false);
            modePaletteBas = true;
        }
    }
}//GEN-LAST:event_attCouleurBasActionPerformed

private void jButtonEchelleCouleursHautActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEchelleCouleursHautActionPerformed
    //    String nomAtt = attCouleurHaut.getSelectedItem().toString();
    //    JDialogEchelleCouleurs frameEchelle = new JDialogEchelleCouleurs(true);
    //    HashMap<String, Color> mapValuesColor = mapAttributPalette.get(nomAtt);
    //    frameEchelle.setPalette(mapValuesColor);
    //    frameEchelle.setVisible(true);
    //    frameEchelle.getPalette(mapValuesColor);
}//GEN-LAST:event_jButtonEchelleCouleursHautActionPerformed

private void jButtonPyr1Color1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPyr1Color1ActionPerformed
    Color col = Color1.getBackground();

    Color color = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
    Color1.setBackground(color);

    ChoixCouleur1 = color;
}//GEN-LAST:event_jButtonPyr1Color1ActionPerformed

private void jButtonPyr1Color2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPyr1Color2ActionPerformed
    Color col = jButtonPyr1Color2.getBackground();

    Color color = JColorChooser.showDialog(
            this,
            "Couleur du pyramdian du haut",
            col);
    Color2.setBackground(color);

    ChoixCouleur2 = color;
}//GEN-LAST:event_jButtonPyr1Color2ActionPerformed

private void attCouleurHautActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attCouleurHautActionPerformed
    if (evt.getActionCommand().equals("comboBoxChanged")) {
        // Verifier si l'attribut selectionne est de type num ou str et changer l'affichage du dessous en fonction
        String nomAttribut = attCouleurHaut.getSelectedItem().toString();
        if (xmlParser.getVectNumParam().contains(nomAttribut)) {
            jButtonEchelleCouleursHaut.setEnabled(false);
            Selected_UserObjectiveLabel.setEnabled(true);
            All_UserObjectiveLabel.setEnabled(true);
            jButtonPyr1Color1.setEnabled(true);
            jButtonPyr1Color2.setEnabled(true);
            Color1.setEnabled(true);
            Color2.setEnabled(true);
            modePaletteHaut = false;
        } else {
            jButtonEchelleCouleursHaut.setEnabled(true);
            Selected_UserObjectiveLabel.setEnabled(false);
            All_UserObjectiveLabel.setEnabled(false);
            jButtonPyr1Color1.setEnabled(false);
            jButtonPyr1Color2.setEnabled(false);
            Color1.setEnabled(false);
            Color2.setEnabled(false);
            modePaletteHaut = true;
        }
    }
}//GEN-LAST:event_attCouleurHautActionPerformed

private void voixItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_voixItemStateChanged
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
}//GEN-LAST:event_voixItemStateChanged

private void objSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_objSizeStateChanged
    size.setText("" + objSize.getValue());
}//GEN-LAST:event_objSizeStateChanged

private void afficherLiensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afficherLiensActionPerformed
    if (afficherLiens.isSelected()) {
        afficherLiensS = "true";
    } else {
        afficherLiensS = "false";
    }
}//GEN-LAST:event_afficherLiensActionPerformed

private void zRatioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zRatioStateChanged
    if (Integer.parseInt(zRatio.getValue().toString()) > 15 || (Integer.parseInt(zRatio.getValue().toString()) < 1)) {


        if (Integer.parseInt(zRatio.getValue().toString()) > 15) {
            zRatio.setValue(zRatio.getPreviousValue());
        }
        if ((Integer.parseInt(zRatio.getValue().toString()) < 1)) {
            zRatio.setValue(zRatio.getNextValue());
        }
    }
    float zLoctemp = (Float.parseFloat(zRatio.getValue().toString())) * 2.5f;
    zLoc.setValue(zLoctemp);
}//GEN-LAST:event_zRatioStateChanged

private void yRatioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_yRatioStateChanged
    if (Integer.parseInt(yRatio.getValue().toString()) > 15 || (Integer.parseInt(yRatio.getValue().toString()) < 1)) {


        if (Integer.parseInt(yRatio.getValue().toString()) > 15) {
            yRatio.setValue(yRatio.getPreviousValue());
        }
        if ((Integer.parseInt(yRatio.getValue().toString()) < 1)) {
            yRatio.setValue(yRatio.getNextValue());
        }
    }
    float yLoctemp = (Float.parseFloat(yRatio.getValue().toString())) * 2.5f;
    yLoc.setValue(yLoctemp);
}//GEN-LAST:event_yRatioStateChanged

private void xRatioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xRatioStateChanged
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
}//GEN-LAST:event_xRatioStateChanged

private void loadXLSfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadXLSfileActionPerformed
    try {
        int choix;
        boolean erreur = false;
        int mode = JFileChooser.FILES_ONLY;
        Filtre fi = new Filtre("Fichiers Excel", ".xls");
        String adrIP = InetAddress.getLocalHost().getHostAddress();
        //String adrIP="10.236.5.228";
        JFileChooser chooser = new JFileChooser("\\\\" + adrIP + "\\medias");
        chooser.setFileSelectionMode(mode);
        chooser.addChoosableFileFilter(fi);
        choix = chooser.showOpenDialog(null);
        final File Fichier;
        if (choix == JFileChooser.APPROVE_OPTION) {
            Fichier = chooser.getSelectedFile();
            filePathValue.setText(Fichier.getAbsolutePath());
            System.out.println("chemin du fichier: " + Fichier.getAbsolutePath());
            try {
                String f = Fichier.getAbsolutePath();
                System.out.println(f);
                new ExcelToXMLWriter(f).writeXMLFile();
            } catch (Exception ex) {
                System.err.println("Erreur! (" + ex.getMessage() + " ");
                new MessageBox("Erreur Excel", "Erreur de Lecture du fichier Excel", MessageBox.ERROR);

            }
            if (!erreur) {
                Thread t = new Thread() {

                    private Nuage3DVisuXMLReader xml;

                    @Override
                    public void run() {
                        try {
                            String f = Fichier.getAbsolutePath();
                            f = f.replaceAll(".xls", ".xml");
                            filePathName = f;
                            xml = new Nuage3DVisuXMLReader(f);
                            xml.setFileToRead(f);
                            xml.readXmlFile();
                            xml.parseData();
                            xml.setParsed(true);
                            try{
                                DataSetTableModel.setRowCount(0);
                                liste = new Matching().getListe(filePathName);
                                liste = new Matching().getListeTri(liste);
                                for(int i=0; i<liste.size();i++){
                                    DataSetTableModel.addRow(new Object[]{liste.get(i).getName(),liste.get(i).getImportance(),liste.get(i).getType()});
                                }
                                DataSetTable.setModel(DataSetTableModel);
                            } catch(Exception ex){
                                //TO DO
                            }

                        } catch (Exception ex) {
                            xml.getLoading().dispose();
                            new MessageBox("Erreur XML", "Erreur de Lecture du fichier XML", MessageBox.ERROR);
                        }

                    }
                };
                t.start();

            }
        }
    } catch (UnknownHostException ex) {
        JOptionPane.showMessageDialog(null, "erreur Loading XLS File : " + ex.getMessage());
    }

}//GEN-LAST:event_loadXLSfileActionPerformed

private void XMLLoadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XMLLoadItemActionPerformed
    LoadXMLDataActionPerformed(evt);
}//GEN-LAST:event_XMLLoadItemActionPerformed

private void XLSLoadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XLSLoadItemActionPerformed
    loadXLSfileActionPerformed(evt);
}//GEN-LAST:event_XLSLoadItemActionPerformed

private void DataSetDescriptionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataSetDescriptionItemActionPerformed
    thumbnailIconDimension = 220;
    VisualizationPanel.setPreferredSize(new Dimension(830, 530));
    //DB_Visualisations_ScrollPane.setPreferredSize(new Dimension(830, 310));
    DataSetPanel.setVisible(true);
    UserPreferencePanel.setVisible(true);
}//GEN-LAST:event_DataSetDescriptionItemActionPerformed

private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
    thumbnailIconDimension = 240;
    VisualizationPanel.setPreferredSize(new Dimension(1310, 530));
    DataSetPanel.setVisible(false);
    UserPreferencePanel.setVisible(false);
}//GEN-LAST:event_jMenuItem5ActionPerformed

private void UserPreferencesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserPreferencesButtonActionPerformed
        try {
            dialog.setVisible(true);
            objectivesWeight = dialog.getUserObjectivesVector();
            for(int j=0; j<objectivesWeight.size(); j++){
                System.out.println(objectivesWeight.get(j).toString());
            }
        } catch (Exception ex) {
            
        }
}//GEN-LAST:event_UserPreferencesButtonActionPerformed

    public void AddNewUserPofilSettings(int indexProfil, File xml, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph){

        SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        try {
        //On cr�e un nouveau document JDOM avec en argument le fichier XML
        document = sxb.build(xml);
        //On initialise un nouvel �l�ment racine avec l'�l�ment racine du document.
        racine = document.getRootElement();
        //ici on initialise les tableaux qui vont contenir les valeur max et min des attributs selectionn�e pour les axes x, y et z
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
        //On recr�e l'Element courant à chaque tour de boucle afin de
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
        }
        }

        

        Element visua = (Element) racine.getChild("visualizations");

        if (visua == null) {
        Element IGAName = new Element("visualizations");
        racine.addContent(IGAName);
        }

        //On cr�e une Liste contenant tous les noeuds "data" de l'Element racine
        Element visu = (Element) racine.getChild("visualizations");
        Element typeVisu = (Element) visu.getChild("nuage3D");
        if (typeVisu == null) {
            Element VisualizationName = new Element("nuage3D");
            visu.addContent(VisualizationName);
            typeVisu = (Element) visu.getChild("nuage3D");
        }
        Element profilDefaut = (Element) typeVisu.getChild("profilDefaut");
        //On fait un test sur le profil selectionn� par defaut à l'ouverture du fichier xml
        //s'il n'existe pas de profil par defaut on en cr�er un dans le fichier xml

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
                    j=0;
                    e.setText("Pas de texte");
                    
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

        //AXES
        Element XRatio = (Element) profil.getChild(NUAGE3D.X_RATIO_NAME);
        Element YRatio = (Element) profil.getChild(NUAGE3D.Y_RATIO_NAME);
        Element ZRatio = (Element) profil.getChild(NUAGE3D.Z_RATIO_NAME);
        XRatio = new Element(NUAGE3D.X_RATIO_NAME);
        YRatio = new Element(NUAGE3D.Y_RATIO_NAME);
        ZRatio = new Element(NUAGE3D.Z_RATIO_NAME);
        profil.addContent(XRatio);
        profil.addContent(YRatio);
        profil.addContent(ZRatio);
        XRatio.setText(xRatio.getValue().toString());
        YRatio.setText(yRatio.getValue().toString());
        ZRatio.setText(zRatio.getValue().toString());
        Element xColor = (Element) profil.getChild("xColor");
        Element yColor = (Element) profil.getChild("yColor");
        Element zColor = (Element) profil.getChild("zColor");
        xColor = new Element("xColor");
        yColor = new Element("yColor");
        zColor = new Element("zColor");
        profil.addContent(xColor);
        profil.addContent(yColor);
        profil.addContent(zColor);
        xColor.setText(getXColorView());
        yColor.setText(getYColorView());
        zColor.setText(getZColorView());
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
        AfficherLiens.setText(getAfficherLiens());
        Element attributLiens = new Element("attributLiens");
        profil.addContent(attributLiens);
        attributLiens.setText(getAttributLiens());

        //OBJET 3D
        Element Shape = (Element) profil.getChild("shape");
        Element Size = (Element) profil.getChild("size");
        Shape = new Element("shape");
        Size = new Element("size");
        profil.addContent(Shape);
        profil.addContent(Size);
        Shape.setText(ElemGraph);
        Size.setText(size.getText().toString());
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
        COlor1.setText(getColor1());
        COlor2.setText(getColor2());
        COlor3.setText(getColor3());
        COlor4.setText(getColor4());

        //CAMERA
        Element camera = (Element) profil.getChild("camera");
        camera = new Element("camera");
        profil.addContent(camera);
        camera.setText(getCamType());

        Element XLoc = (Element) profil.getChild("xLoc");
        Element YLoc = (Element) profil.getChild("yLoc");
        Element ZLoc = (Element) profil.getChild("zLoc");
        XLoc = new Element("xLoc");
        YLoc = new Element("yLoc");
        ZLoc = new Element("zLoc");
        profil.addContent(XLoc);
        profil.addContent(YLoc);
        profil.addContent(ZLoc);
        XLoc.setText(getxLoc());
        YLoc.setText(getyLoc());
        ZLoc.setText(getzLoc());

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

        AdrsIpServer.setText(getadrsIpServer());
        JCheckBoxReseau.setText(getjCheckBoxReseau());
        CheminLecteur.setText(getcheminLecteur());

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
        JCheckBoxStereo.setText(getjCheckBoxStereo());
        JSliderYeux.setText(getjSliderYeux());
        JTextFieldTaille.setText(getjTextFieldTaille());
        JTextFieldDistance.setText(getjTextFieldDistance());
        BkgdColorView.setText(getBkgdColorView());
        GrilleTerrain.setText(getGrilleTerrain());

        //GridEnabled.setText(getGridEnabled());
        PopUpInfo.setText(getpopUpInfo());
        COlor5.setText(getColor5());
        ObjSize2.setText(getObjSize2());

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

        CLusturing.setText(getClusturing());
//        if (!attClust.isEmpty()) {
//            attClust.setSelectedItem(attClust);
//        }
        //aTtClust.setText(attClust.getSelectedItem().toString());
        aTtClust.setText(getAttClust());
        cLustFact.setText(getclustFact());
        CLustVisib.setText(getClustVisib());
        cLustMethode.setText(getclustMethode());
        cLustObjet.setText(getclustObjet());
        EClairageSphere.setText(getEclairageSphere());
        oBjSize3.setText(getobjSize3());

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
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }


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
    public void AddNewUserPofilSettingsGA(int indexProfil, File xml, List<Visualisation> listVisualAtt, List<Appariement> ResultMEC, String ElemGraph){

        SAXBuilder sxb = new SAXBuilder();
        String baliseProfil = "profil" + indexProfil;
        float[] tabCoordMin;
        float[] tabCoordMax;
        float[] tabCoordMinListe;
        float[] tabCoordMaxListe;
        String typeDeVisu = getCurrentTypeVisu(indexProfil);
        // Pour
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
        if (typeDeVisu.equalsIgnoreCase("CoordonneesParalleles")) {
            for (Appariement appariement : ResultMEC){
                 Element  e = (Element) profil.getChild(appariement.getName_v_data());
                 e = new Element(appariement.getName_v_data());
                 profil.addContent(e);
                 e.setText(appariement.getName_data());
            }
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
            XRatio.setText(xRatio.getValue().toString());
            YRatio.setText(yRatio.getValue().toString());
            ZRatio.setText(zRatio.getValue().toString());
            Element xColor = (Element) profil.getChild("xColor");
            Element yColor = (Element) profil.getChild("yColor");
            Element zColor = (Element) profil.getChild("zColor");
            xColor = new Element("xColor");
            yColor = new Element("yColor");
            zColor = new Element("zColor");
            profil.addContent(xColor);
            profil.addContent(yColor);
            profil.addContent(zColor);
            xColor.setText(getXColorView());
            yColor.setText(getYColorView());
            zColor.setText(getZColorView());
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
            Size.setText(size.getText().toString());
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
            COlor1.setText(getColor1());
            COlor2.setText(getColor2());
            COlor3.setText(getColor3());
            COlor4.setText(getColor4());

            //CAMERA
            Element camera = (Element) profil.getChild("camera");
            camera = new Element("camera");
            profil.addContent(camera);
            camera.setText(getCamType());

            Element XLoc = (Element) profil.getChild("xLoc");
            Element YLoc = (Element) profil.getChild("yLoc");
            Element ZLoc = (Element) profil.getChild("zLoc");
            XLoc = new Element("xLoc");
            YLoc = new Element("yLoc");
            ZLoc = new Element("zLoc");
            profil.addContent(XLoc);
            profil.addContent(YLoc);
            profil.addContent(ZLoc);
            XLoc.setText(getxLoc());
            YLoc.setText(getyLoc());
            ZLoc.setText(getzLoc());

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

            AdrsIpServer.setText(getadrsIpServer());
            JCheckBoxReseau.setText(getjCheckBoxReseau());
            CheminLecteur.setText(getcheminLecteur());

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
            JCheckBoxStereo.setText(getjCheckBoxStereo());
            JSliderYeux.setText(getjSliderYeux());
            JTextFieldTaille.setText(getjTextFieldTaille());
            JTextFieldDistance.setText(getjTextFieldDistance());
            BkgdColorView.setText(getBkgdColorView());
            GrilleTerrain.setText(getGrilleTerrain());

            //GridEnabled.setText(getGridEnabled());
            PopUpInfo.setText(getpopUpInfo());
            COlor5.setText(getColor5());
            ObjSize2.setText(getObjSize2());

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

            CLusturing.setText(getClusturing());
    //        if (!attClust.isEmpty()) {
    //            attClust.setSelectedItem(attClust);
    //        }
            //aTtClust.setText(attClust.getSelectedItem().toString());
            aTtClust.setText(getAttClust());
            cLustFact.setText(getclustFact());
            CLustVisib.setText(getClustVisib());
            cLustMethode.setText(getclustMethode());
            cLustObjet.setText(getclustObjet());
            EClairageSphere.setText(getEclairageSphere());
            oBjSize3.setText(getobjSize3());

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
    } catch (Exception e) {
        System.err.println(e + " Erreur de lecture du xml");
        e.printStackTrace();
    }


    }



    public void MatchingResult() throws Exception {
        try{
        
        //r�cuperer la description des attributs de donn�es �partir de la partie structure du fichier XML charg�
        
        liste = new Matching().getListe(filePathName);
        DataAttribute_liste = liste;
        //System.out.println("Size of Data Attribute List : "+DataAttribute_liste.size());
         //Calculer la norme canonique de U (poids des attributs de donn�es)
        int somme = 0;
        somme = 0;
        int Norm_Can_DataAttribute = 0;
        for(int i=0; i<liste.size(); i++){
            somme = (int) Math.pow(liste.get(i).getImportance(), 2);
            Norm_Can_DataAttribute = Norm_Can_DataAttribute+somme;
        }
        Norm_Can_DataAttribute = (int) Math.sqrt(Norm_Can_DataAttribute);
        
        //Calculer la norme canonique de V (poids des attributs visuels)
        somme = 0;
        int Norm_Can_VisualAttribute = 0;
        for(int i=0; i<listdtm.size(); i++){
            somme = (int) Math.pow(listdtm.get(i).getImportance(), 2);
            Norm_Can_VisualAttribute = Norm_Can_VisualAttribute+somme;
        }
        Norm_Can_VisualAttribute = (int) Math.sqrt(Norm_Can_VisualAttribute);
        
        //trier les attributs de donn�es selon leur type puis leur importance
        liste = new Matching().getListeTri(liste);

        //r�cuperer le matching attributs de donn�es / attributs visuels
        MatchingTableModel = new Matching().getMatchingResult(liste, listdtm);

        //initialiser le DefaultTableModel pour viusaliser le r�sultat du matching attributs de donn�es / attributs visuels
        MatchingTableModelMEC = new DefaultTableModel(data, colNameDataSetMEC);
        
        /*Initialiser la liste du matching attributs de donn�es / attributs visuels pour d�rouler l'alogorithme g�n�tique*/
        individuMEC = new ArrayList<Appariement>();
        for(int i=0; i<MatchingTableModel.getRowCount(); i++){
        //Remplir le tableau Matching result qui traduit le r�sultat de la mise en correspondance
        MatchingTableModelMEC.addRow(new Object[]{MatchingTableModel.getValueAt(i, 0).toString(), MatchingTableModel.getValueAt(i, 3).toString()});
        
        //Cr�er et Remplir une liste de Matching result qui traduit le r�sultat de la mise en correspondance pour la passer en param�tre � l'Algo g�n�tique
        Appariement indivMec = new Appariement();
        indivMec.setName_v_data(MatchingTableModel.getValueAt(i, 0).toString());
        indivMec.setType_v_data(MatchingTableModel.getValueAt(i, 1).toString());
        indivMec.setImportance_v_data(Integer.valueOf(MatchingTableModel.getValueAt(i, 2).toString()));
        indivMec.setName_data(MatchingTableModel.getValueAt(i, 3).toString());
        indivMec.setType_data(MatchingTableModel.getValueAt(i, 4).toString());
        indivMec.setImportance_data(Integer.valueOf(MatchingTableModel.getValueAt(i, 5).toString()));

        individuMEC.add(indivMec);
        }
        //afficher r�sultat du matching
        MatchingTable.setModel(MatchingTableModelMEC);
        
        //Calculer le produit scalaire de V (poids des attributs visuels) et U (poids des attributs de donn�es)
        somme = 0;
        similarite = 0;
        for(int i=0; i<individuMEC.size(); i++){
            somme = (Integer.valueOf(individuMEC.get(i).getImportance_v_data()))*(Integer.valueOf(individuMEC.get(i).getImportance_data()));
            similarite = similarite+somme;
        }

        //calcule de la similarit� qui correspond au prduit scalaire : Cos(u,v)= (u�� v)/(?u?�?v?)    ? [0, 1
        similarite = (similarite/(Norm_Can_DataAttribute*Norm_Can_VisualAttribute))*100;
        
        //Affeceter la similarit� entre attributs de donn�es et attributs visuels � ProgressBar
        TauxSimilarite.setValue((int) similarite);
        TauxSimilarite.setStringPainted(true);

        }
        catch(Exception ex){
          JOptionPane.showMessageDialog(new JOptionPane(), "You must load data before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }
    }

    public List<Appariement> GenerateMatchingWithProfil(int indiceVisualisation) throws Exception {

        //r�cuperer la description des attributs de donn�es à partir de la partie structure du fichier XML
        List<Visualisation> dataAttributeliste = new Matching().getListe(filePathName);
        //trier les attributs de donn�es selon leur type puis leur importance
        dataAttributeliste = new Matching().getListeTri(dataAttributeliste);
        //r�cuperer les attributs visuels depuis la base de donn�es pour chaque visualisation
        List<Visualisation> visualAttributeliste = new LoadVisualizations().getIdMethode(indiceVisualisation);

        List<Appariement> resultaMEC;
        if (getCurrentTypeVisu(indiceVisualisation).equalsIgnoreCase("CoordonneesParalleles"))
            resultaMEC = this.getMatchingParalleleCoordinate(indiceVisualisation, dataAttributeliste, visualAttributeliste);
        else
            //r�cuperer le matching attributs de donn�es / attributs visuels
            resultaMEC = this.getMatching(dataAttributeliste, visualAttributeliste);
        //initialiser le DefaultTableModel pour viusaliser le r�sultat du matching attributs de donn�es / attributs visuels
        
        return resultaMEC;
        
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
                shape = VisualisationsFilesNames.get(i).getNom();
                //System.out.println("Id :"+ id+"  : Shape  "+shape);
                String identif = String.valueOf(id);
                icon = createImageIcon(imageDir+VisualisationsFilesNames.get(i).getNom()+".jpg", identif);
                //Action executer lors du clique sur l'icone
                ThumbnailAction thumbAction;
                if(icon != null){
                    //Dimension de l'icone (image) placer en bas de la fenetres (visualisations propos�es par le système contenus dans la base de donn�es
                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), thumbnailIconDimension, thumbnailIconDimension));
                    ImageIcon OverviewIcon = new ImageIcon(getScaledImage(icon.getImage(), 130, 125));
                    //param�tre affect� à thumbAction
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
                /* initailisation des Visualisation 3D */
                try{
//                initialisationNuage3D();
//                visu3D1.ConfigurationNuage3D(filePathName, "profil"+1);
//                visu3D1.createScene();
//                scrollpane1.setViewportView(visu3D1.getCustomCanvas3D());
                //DB_VisualizationToolBar.add(scrollpane1);
                //DB_VisualizationToolBar.add(jSeparator10);

                //DB_VisualizationToolBar.add(thumbButton, DB_VisualizationToolBar.getComponentCount());
                
//                DB_VisualizationToolBar.add(scrollpane1);
//                DB_VisualizationToolBar.add(scrollpane1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
            
            //Dimension de l'icone (image) placer en bas de la fenetres (visualisations propos�es par le système contenus dans la base de donn�es
            displayPhoto = photo;
            //displayPhoto = new ImageIcon(getScaledImage(photo.getImage(), 200, 200));

            // The short description becomes the tooltip of a button.
            putValue(SHORT_DESCRIPTION, desc);

            idtable = Integer.valueOf(desc);
            String Name = imageCaptions.get(idtable);

            putValue(SHORT_DESCRIPTION, Name);
            // The LARGE_ICON_KEY is the key for setting the
            // icon when an Action is applied to a button.
            putValue(LARGE_ICON_KEY, thumb);
        }

        /**
         * Shows the full image in the main area and sets the application title.
         */
        @Override
    public void actionPerformed(ActionEvent e) {
        
        OverviewVisualization.setIcon(displayPhoto);
        
            try {
                idMethode = idtable;
                //creer la nouvelle scene 3D de la visualisation choisie
                if(visu3D!=null){
                visu3D.destroy();
                visu3D = null;
                }
                updatePreview3D(filePathName, idMethode);
                shape = new LoadVisualizations().getIdElement(idMethode);
                VisualAttributeTableModel.setRowCount(0);
                Visualization_Name.setText("NUAGE 3D");
                GraphicElement_Name.setText(shape);
                listdtm = new LoadVisualizations().getIdMethode(idMethode);
                for(int i=0; i<listdtm.size();i++){
                VisualAttributeTableModel.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance()});
                }
                MatchingResult();
                Step3.setEnabled(true);
                AdjustIECButton.setEnabled(true);
                LaunchVisualizations.setEnabled(true);
                List<ExpertObjective> expert_objective_Liste = new LoadVisualizations().getAllExpertObjective();
                List<Categorie> categorie_Liste = new LoadVisualizations().getAllCategorie();
                List<CategorieVisualisation> categorievisual_Liste = new LoadVisualizations().getAllCategorieVisualisation();
                //List listeProfil = getProfilList(filePathName);
                ExpertObjectiveListModel.removeAllElements();
                CatgoryListModel.removeAllElements();
                VisualizationCatgoryListModel.removeAllElements();
                ProfilListModel.removeAllElements();
//                for(int i=0; i<categorievisual_Liste.size();i++){
//                ProfilListModel.addElement(listeProfil.get(i));
//                }
                for(int i=0; i<expert_objective_Liste.size();i++){
                ExpertObjectiveListModel.addElement(expert_objective_Liste.get(i).getDescription());
                }
                for(int i=0; i<categorie_Liste.size();i++){
                CatgoryListModel.addElement(categorie_Liste.get(i).getNom());
                }
                for(int i=0; i<categorievisual_Liste.size();i++){
                VisualizationCatgoryListModel.addElement(categorievisual_Liste.get(i).getNom());
                }
                } catch (Exception ex) {
                //JOptionPane.showMessageDialog(null, "erreur You must Choose One Visualization  : " + ex.getMessage());
                }
        }
    }

    /**
     * Methode pour la g�n�ration des visualisations avec le param�trage recommand� par le syst�me
     * @param XMLFilepath chemin du fichier xml � visualiser 
     * @throws Exception
     */
    private void AfficherIndividus(final String XMLFilepath) throws Exception {

        initialisationNuage3DEmpty();
        initialisationNuage3D();    
        initialisationVisu();
        int pro = 0;
        for(int j = 0; j< numMethode; j++){
            pro++;
            final int profil = pro;
            System.out.println("Profil: "+ profil);
            if (pro<numMethode){
                listVisu3D.get(j).ConfigurationNuage3D(XMLFilepath, "profil"+pro);
                listVisu3D.get(j).createScene();
                //jScrollPaneVisu3D1.setViewportView(listVisu3D.get(j).getCustomCanvas3D());
                listJScrollPaneVisu3D.get(j).setViewportView(listVisu3D.get(j).getCustomCanvas3D());
                listVisu3D.get(j).addPointOfViewListener(listVisu3D.get(j).getMainPointOfView().getName(), new PointOfViewMouseAdapter() {
                    @Override
                    public void onMouseLeftClick(MouseEvent m, PointOfView p, Object3D o){
                        updatePreview3D(filePathName,profil);//afficher le profil i : qui est pass� en param�tre
                    }
                });
                listVisu3D.get(j).getMainPointOfView().getControlManager().setCurrentMetaControl(new NoneVRMMetaControlInfo());
            }
            if(pro == numMethode){
                System.out.println(filePathName);
//                listVisu3D.get(j).createScene();
                parallelDisplay = new ParallelDisplay();
                listJScrollPaneVisu3D.get(j).setViewportView(null);
                String urltext = "file:///" + filePathName;
                XMLFile x = new XMLFile(new URL(urltext));
                x.readXMLContents(filePathName);
                parallelDisplay.setModel(x);
                parallelDisplay.setMinimumSize(new Dimension(100,100));
                parallelDisplay.setPreferredSize(new Dimension(120,100));
                parallelDisplay.setMaximumSize(OverviewPictureContainer.getMaximumSize());

                listJScrollPaneVisu3D.get(j).setViewportView(parallelDisplay);               
//                parallelDisplay.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mousePressed(MouseEvent me) {
//                             updatePreview3DForParallelCoordinate(filePathName, profil); //afficher le profil i : qui est pass� en param�tre
//                    }
//                });
               }
        }
        //OverviewPictureContainer.setViewportView(parallelDisplay);
    }

    /**
     * Methode pour la mise � jour de l'afichage de l'aper�u de la visualisation s�lectionn�e
     * @param path adresse du fichier xml � visualiser  
     * @param profil num�ro du profil � afficher dans la partie sup�rieur de l'interface
     */
    private void updatePreview3D(String path, int profil){

        try {
        shape = new LoadVisualizations().getIdElement(profil);
        VisualAttributeTableModel.setRowCount(0);
        Visualization_Name.setText("NUAGE 3D");
        GraphicElement_Name.setText(shape);
        listdtm = new LoadVisualizations().getIdMethode(profil);
        /*  Chargement des descriptions ainsi que les identificateurs de tous les objectives utilisateurs contenues dans la base de donn�es  */
        List<UserPreferences> dbuserObjectiveWeight = new LoadVisualizations().LoadUserObjectives();
        for(int i=0; i<listdtm.size();i++){
            VisualAttributeTableModel.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance()});
        }
        MatchingResult();
        Step3.setEnabled(true);
        AdjustIECButton.setEnabled(true);
        LaunchVisualizations.setEnabled(true);
        } catch (Exception ex) {
        ex.printStackTrace();
        }

        //ActionChooseMethod(profil);

        if(visu3D!=null){
        visu3D.destroy();
        visu3D = null;
        }

        visu3D = new Visualisation_Nuage_3D(0, 0, 0);
        visu3D.ConfigurationNuage3D(path, "profil"+profil);
        visu3D.createScene();
        OverviewPictureContainer.setViewportView(visu3D.getCustomCanvas3D());
        
    }
     private void updatePreview3DForParallelCoordinate(String path, int profil) {

        try {
            System.out.println(filePathName);
            shape = new LoadVisualizations().getIdElement(profil);
            VisualAttributeTableModel.setRowCount(0);
            Visualization_Name.setText("Coordonn�es Parall�les");
            GraphicElement_Name.setText(shape);
            listdtm = new LoadVisualizations().getIdMethode(profil);
            /*  Chargement des descriptions ainsi que les identificateurs de tous les objectives utilisateurs contenues dans la base de donn�es  */
            List<UserPreferences> dbuserObjectiveWeight = new LoadVisualizations().LoadUserObjectives();
            for(int i=0; i<listdtm.size();i++){
                VisualAttributeTableModel.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance()});
             }
            MatchingResult();
            Step3.setEnabled(true);
            AdjustIECButton.setEnabled(true);
            LaunchVisualizations.setEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        OverviewPictureContainer.setViewportView(parallelDisplay);
           
    }
    /**
     * Methode pour l'initilisation des sc�nes 3D
     * @throws Exception
     */
    private void initialisationNuage3D() throws Exception{        
        for (int i = 0; i<numMethode; i++)
            listVisu3D.set(i,new Visualisation_Nuage_3D(0, 0, 0)); 
    }
    private void initialisationVisu(){
        javax.swing.GroupLayout jPanelVisuLayout = new javax.swing.GroupLayout(jPanelVisu);
        jPanelVisu.setLayout(jPanelVisuLayout);
        DB_VisualizationToolBar.add(jPanelVisu);
        for(int i = 0; i< numMethode; i++){            
            JScrollPane  jScrollPaneVisu = new JScrollPane();
            listJScrollPaneVisu3D.add(jScrollPaneVisu);
            DB_VisualizationToolBar.add(listJScrollPaneVisu3D.get(i));
         }
    }

    /**
     * Methode utilis�e pour le vidage de la m�moire et lancer le garbage collector
     * @throws Exception
     */
    private void initialisationNuage3DEmpty() throws Exception{
        for (int i =0; i<numMethode;i++)
            if(listVisu3D.get(i) != null){
                 listVisu3D.get(i).destroy();
                 listVisu3D.set(i, null);
            }       
    }

    private void ActionChooseMethod(int idmethode){

        try {
                //creer la scene 3D
                if(visu3D!=null){
                Runtime r = Runtime.getRuntime(); // Cr�er un objet de type Runtime
                //System.out.println ( "Max : " + r.maxMemory());
                //System.out.println ( "Free : " + r.freeMemory());
                r.gc(); // Appel implicite au garbage collector
                visu3D.destroy();
                visu3D = null;
                r.gc(); // Appel implicite au garbage collector
                //System.out.println ("Free : " + r.freeMemory());
//                Runtime.getRuntime().gc();
//                Runtime.getRuntime().runFinalization();
                }
                //updatePreview3D(filePathName, idmethode);
                shape = new LoadVisualizations().getIdElement(idmethode);
                VisualAttributeTableModel.setRowCount(0);
                Visualization_Name.setText("NUAGE 3D");
                GraphicElement_Name.setText(shape);
                listdtm = new LoadVisualizations().getIdMethode(idmethode);
                for(int i=0; i<listdtm.size();i++){
                VisualAttributeTableModel.addRow(new Object[]{listdtm.get(i).getName(), listdtm.get(i).getType(), listdtm.get(i).getImportance()});
                }
                MatchingResult();
                Step3.setEnabled(true);
                AdjustIECButton.setEnabled(true);
                LaunchVisualizations.setEnabled(true);
                List<ExpertObjective> expert_objective_Liste = new LoadVisualizations().getAllExpertObjective();
                List<Categorie> categorie_Liste = new LoadVisualizations().getAllCategorie();
                List<CategorieVisualisation> categorievisual_Liste = new LoadVisualizations().getAllCategorieVisualisation();
                //List listeProfil = getProfilList(filePathName);
                ExpertObjectiveListModel.removeAllElements();
                CatgoryListModel.removeAllElements();
                VisualizationCatgoryListModel.removeAllElements();
                ProfilListModel.removeAllElements();
//                for(int i=0; i<categorievisual_Liste.size();i++){
//                ProfilListModel.addElement(listeProfil.get(i));
//                }
                for(int i=0; i<expert_objective_Liste.size();i++){
                ExpertObjectiveListModel.addElement(expert_objective_Liste.get(i).getDescription());
                }
                for(int i=0; i<categorie_Liste.size();i++){
                CatgoryListModel.addElement(categorie_Liste.get(i).getNom());
                }
                for(int i=0; i<categorievisual_Liste.size();i++){
                VisualizationCatgoryListModel.addElement(categorievisual_Liste.get(i).getNom());
                }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

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

    protected void fillNuage3DTab(String choixProfil) {
        xmlParser.RafraichissementFichier();
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

    public void RafraichissementFichier() {

        try {
            //On cr?e un nouveau document JDOM avec en argument le fichier XML
            document = new VRMXMLReader(filePathName).getXMLDocument();
        } catch (Exception e) {
            System.err.println(e + " Erreur de lecture du xml");
            new MessageBox("Erreur de lecture du fichier xml", e.getMessage(), 1);
        }
    }

    private void addProfils(){

        try{
        SAXBuilder sxb = new SAXBuilder();
        File fichier = new File(filePathName);
        document = sxb.build(fichier);
        racine = document.getRootElement();
        //Element visu = (Element) racine.getChild("visualizations");
        Element visu = (Element) racine.getChild("geneticalgorithm");
        int x = 0;
        x = (visu.getChild("nuage3D").getContentSize()-2)-1;
        /*r�cuperer tous les attributs visuels de CUBE_CHANEL v2 pour toutes les formes de nuages 3D */
        listAttribute = new LoadVisualizations().getIdMethode(1);
        listdtm = new Matching().getListe(filePathName);
        listdtm = new Matching().getListeTri(listdtm);
        try {
            new UpdateXMLFile(filePathName);
            } catch (Exception ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0; i<8; i++){
        //AddNewUserPofilSettings(i, fichier, listAttribute, individuMEC, shape);
        AddNewUserPofilSettingsGA(i, fichier, listAttribute, individuMEC, shape);
        //writeNewUserPofilSettings(x+i, "profil0", file, liste, individuMEC);
        enregistreFichier(filePathName);
        //Creer un fichier xml pour les 5 m�thodes de visualisation
        //enregistreFichier("Exemple"+filePathName);
        }
       // }
        }
          catch(Exception ex){
            JOptionPane.showMessageDialog(new JOptionPane(), "You must load data then matching them with the appropriate visualization before?", "Error Message", JOptionPane.WARNING_MESSAGE);
        }

    }
    
    private static Image scale(Image source, int width, int height) {
        BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buf.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return buf;
    }

    public String getBkgdColorView() {
        return "" + bkgdColorView.getBackground().getRGB();
    }

    public String getCamType() {
        return (String) camType.getSelectedItem();
    }

    public String getClassVar() {
        return (String) attCouleurHaut.getSelectedItem();
    }

    public String getSynthAtt1() {
        return (String) attSynthese.getSelectedItem();
    }

    public String getVoix() {
        return (String) voix.getSelectedItem();
    }

    public String getClassVar1() {
        return (String) attCouleurBas.getSelectedItem();
    }

//    public ArrayList getListMedia() {
//        ArrayList list = new ArrayList();
//        for (int i = 0; i < model_list.getSize(); i++) {
//            String list_String = model_list.getElementAt(i).toString();
//            list.add(list_String);
//        }
//        return list;
//    }

    /* public String getGridEnabled()
    {
    if(gridEnabled.isSelected())
    return "true";
    else
    return "false";
    }*/
    public String getGrilleTerrain() {
        if (grilleTerrain.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getObjSize() {
        return size.getText();
    }

    public String getXColorView() {
        return String.valueOf(xColorView.getBackground().getRGB());
    }

    public String getXRatio() {
        return String.valueOf(xRatio.getValue());
    }

    public String getxLoc() {
        return xLoc.getValue().toString();
    }

    public String getyLoc() {
        return yLoc.getValue().toString();
    }

    public String getzLoc() {
        return zLoc.getValue().toString();
    }

    public String getjCheckBoxReseau() {
        if (jCheckBoxReseau.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getadrsIpServer() {
        return adrsIpServer.getText();
    }
    //public String getAdrsIpMachFichier(){
    //return adrsIpMachFichier.getText();
    //}

    public String getYColorView() {
        return String.valueOf(yColorView.getBackground().getRGB());
    }

    public String getYRatio() {
        return String.valueOf(yRatio.getValue());
    }

    public String getZColorView() {
        return String.valueOf(zColorView.getBackground().getRGB());
    }

    public String getZRatio() {
        return String.valueOf(zRatio.getValue());
    }

    public String getAttClust() {
        return (String) attClust.getSelectedItem();
    }

    public void setValTemp(float valTemp) {
        ValEcartYeux = valTemp;
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

    public boolean getTerrain() {
        return terrain;
    }

    public String getColor1() {
        return String.valueOf(Color1.getBackground().getRGB());
    }

    public String getColor2() {
        return String.valueOf(Color2.getBackground().getRGB());
    }

    public String getColor3() {
        return String.valueOf(Color3.getBackground().getRGB());
    }

    public String getColor4() {
        return String.valueOf(Color4.getBackground().getRGB());
    }

    public String getjSliderYeux() {
        return String.valueOf(jSliderYeux.getValue());
    }

    public String getjTextFieldTaille() {
        return jTextFieldTaille.getText();
    }

    public String getjTextFieldDistance() {
        return jTextFieldDistance.getText();
    }

    public String getcheminLecteur() {
        return cheminLecteur.getText();
    }

    public String getpopUpInfo() {
        if (popUpInfo.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getColor5() {
        return String.valueOf(Color5.getBackground().getRGB());
    }

    public String getObjSize2() {
        return String.valueOf(objSize2.getValue());
    }

    public String getObjSize3() {
        return String.valueOf(objSize3.getValue());
    }

    public void setTerrain(boolean _terrain) {
        terrain = _terrain;
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

    public String getjCheckBoxStereo() {
        if (jCheckBoxStereo.isSelected()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String getAfficherLiens() {
        if (afficherLiens.isSelected()) {
            return "true";
        }
        return "false";
    }

    public String getAttributLiens() {
        return attLiens.getSelectedItem().toString();
    }

    public HashMap<String, HashMap> getAttributPalette() {
        return mapAttributPalette;
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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainInterface().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutItem;
    private javax.swing.JButton AdjustIECButton;
    private javax.swing.JButton AdjustPrefencesButton;
    private javax.swing.JLabel Adr_IP;
    private javax.swing.JLabel Adr_IPLabel;
    private javax.swing.JList All_UserObjective;
    private javax.swing.JLabel All_UserObjectiveLabel;
    private javax.swing.JPanel AxesPanel;
    private javax.swing.JButton BkgdImgChooser2;
    private javax.swing.JPanel CameraPanel;
    private javax.swing.JButton CloseButton;
    private javax.swing.JCheckBox ClustVisib;
    private javax.swing.JCheckBox Clusturing;
    private javax.swing.JPanel ClusturingPanel;
    private javax.swing.JTextField Color1;
    private javax.swing.JLabel Color1Label;
    private javax.swing.JTextField Color2;
    private javax.swing.JLabel Color2Label;
    private javax.swing.JTextField Color3;
    private javax.swing.JLabel Color3Label;
    private javax.swing.JTextField Color4;
    private javax.swing.JLabel Color4Label;
    private javax.swing.JTextField Color5;
    private javax.swing.JLabel Color5Label;
    private javax.swing.JButton Connexion;
    private javax.swing.JScrollPane DB_Visualisations_ScrollPane;
    private javax.swing.JToolBar DB_VisualizationToolBar;
    private javax.swing.JMenuItem DataSetDescriptionItem;
    private javax.swing.JLabel DataSetDescriptionLabel;
    private javax.swing.JLabel DataSetDescriptionLabel1;
    private javax.swing.JPanel DataSetPanel;
    private javax.swing.JTable DataSetTable;
    private javax.swing.JButton DeleteProfil;
    private javax.swing.JCheckBox EclairageSphere;
    private javax.swing.JButton GenerateNewProfil;
    private javax.swing.JLabel GraphicElement_Name;
    private javax.swing.JLabel JDrapeau;
    private javax.swing.JMenuItem LaunchVRMinerItem;
    private javax.swing.JButton LaunchVisualizations;
    private javax.swing.JButton LoadXMLData;
    private javax.swing.JLabel LocsLabel;
    private javax.swing.JScrollPane MatchingScrollPane;
    private javax.swing.JTable MatchingTable;
    private javax.swing.JPanel NetworkPanel;
    private javax.swing.JPanel Object3DPanel;
    private javax.swing.JPanel OthersPanel;
    private javax.swing.JScrollPane OverviewPictureContainer;
    private javax.swing.JLabel OverviewVisualization;
    private javax.swing.JPanel ProfilsPanel;
    private javax.swing.JButton RemoveSelectObjective;
    private javax.swing.JScrollPane SP_VisualAttributeDescription;
    private javax.swing.JButton SelectObjective;
    private javax.swing.JList Selected_UserObjective;
    private javax.swing.JLabel Selected_UserObjectiveLabel;
    private javax.swing.JPanel Space3DPanel;
    private javax.swing.JLabel Step1;
    private javax.swing.JLabel Step2;
    private javax.swing.JLabel Step3;
    private javax.swing.JLabel Step6;
    private javax.swing.JProgressBar TauxSimilarite;
    private javax.swing.JButton UpdateProfil;
    private javax.swing.JPanel UserObjectivePanel;
    private javax.swing.JPanel UserPreferencePanel;
    private javax.swing.JButton UserPreferencesButton;
    private javax.swing.JTable VisualAttributeTable;
    private javax.swing.JList VisualCategoryList;
    private javax.swing.JLabel VisualCategoryListLabel;
    private javax.swing.JList VisualDimensionList;
    private javax.swing.JLabel VisualDimensionListLabel;
    private javax.swing.JPanel VisualizationDescriptionPanel;
    private javax.swing.JPanel VisualizationPanel;
    private javax.swing.JLabel Visualization_Name;
    private javax.swing.JButton Visualize_Profil;
    private javax.swing.JMenuItem XLSLoadItem;
    private javax.swing.JMenuItem XMLLoadItem;
    private javax.swing.JTextField adrsIpServer;
    private javax.swing.JLabel adrsIpServerLabel;
    private javax.swing.JCheckBox afficherLiens;
    private javax.swing.JComboBox attClust;
    private javax.swing.JLabel attClustLabel;
    private javax.swing.JComboBox attCouleurBas;
    private javax.swing.JLabel attCouleurBasLabel;
    private javax.swing.JComboBox attCouleurHaut;
    private javax.swing.JLabel attCouleurHautLabel;
    private javax.swing.JComboBox attLiens;
    private javax.swing.JLabel attLiensLabel;
    private javax.swing.JComboBox attPyrBas;
    private javax.swing.JLabel attPyrBasLabel;
    private javax.swing.JComboBox attPyrHaut;
    private javax.swing.JLabel attPyrHautLabel;
    private javax.swing.JComboBox attSynthese;
    private javax.swing.JLabel attSyntheseLabel;
    private javax.swing.JComboBox attTextBas;
    private javax.swing.JLabel attTextBasLabel;
    private javax.swing.JComboBox attTextHaut;
    private javax.swing.JLabel attTextHautLabel;
    private javax.swing.JButton bkgdColorColorChooser;
    private javax.swing.JTextField bkgdColorView;
    private javax.swing.JLabel bkgdColorViewLabel;
    private javax.swing.JComboBox camType;
    private javax.swing.JLabel camTypeLabel;
    private javax.swing.JTextField cheminLecteur;
    private javax.swing.JLabel cheminLecteurLabel;
    private javax.swing.JSlider clustFact;
    private javax.swing.JLabel clustFactLabel;
    private javax.swing.JComboBox clustMethode;
    private javax.swing.JLabel clustMethodeLabel;
    private javax.swing.JComboBox clustObjet;
    private javax.swing.JLabel clustObjetLabel;
    private javax.swing.JLabel filePath;
    private javax.swing.JLabel filePathValue;
    private javax.swing.JCheckBox grilleTerrain;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonColCube;
    private javax.swing.JButton jButtonEchelleCouleursBas;
    private javax.swing.JButton jButtonEchelleCouleursHaut;
    private javax.swing.JButton jButtonPyr1Color1;
    private javax.swing.JButton jButtonPyr1Color2;
    private javax.swing.JButton jButtonPyr2Color1;
    private javax.swing.JButton jButtonPyr2Color2;
    private javax.swing.JCheckBox jCheckBoxReseau;
    private javax.swing.JCheckBox jCheckBoxStereo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelClust;
    private javax.swing.JList jListProfil;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelVisu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSlider jSliderYeux;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextFieldDistance;
    private javax.swing.JLabel jTextFieldDistanceLabel;
    private javax.swing.JLabel jTextFieldDistanceLabelMesure;
    private javax.swing.JTextField jTextFieldTaille;
    private javax.swing.JLabel jTextFieldTailleLabel;
    private javax.swing.JLabel jTextFieldTailleLabelMesure;
    private javax.swing.JButton jToggleButton1;
    private javax.swing.JButton loadXLSfile;
    private javax.swing.JSlider objSize;
    private javax.swing.JSlider objSize2;
    private javax.swing.JSlider objSize3;
    private javax.swing.JLabel objSize3Label;
    private javax.swing.JCheckBox popUpInfo;
    private javax.swing.JLabel size;
    private javax.swing.JLabel size1;
    private javax.swing.JLabel size1Label;
    private javax.swing.JLabel size1LabelMesure;
    private javax.swing.JLabel size2;
    private javax.swing.JLabel size3;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JLabel slideClust;
    private javax.swing.JScrollPane spDataSet;
    private javax.swing.JTextField statusBar;
    private javax.swing.JComboBox voix;
    private javax.swing.JLabel voixLabel;
    private javax.swing.JButton xAxisColor;
    private javax.swing.JTextField xColorView;
    private javax.swing.JSpinner xLoc;
    private javax.swing.JLabel xLocLabel;
    private javax.swing.JSpinner xRatio;
    private javax.swing.JLabel xRatioLabel;
    private javax.swing.JButton yAxisColor;
    private javax.swing.JTextField yColorView;
    private javax.swing.JSpinner yLoc;
    private javax.swing.JLabel yLocLabel;
    private javax.swing.JSpinner yRatio;
    private javax.swing.JLabel yRatioLabel;
    private javax.swing.JButton zAxisColor;
    private javax.swing.JTextField zColorView;
    private javax.swing.JSpinner zLoc;
    private javax.swing.JLabel zLocLabel;
    private javax.swing.JSpinner zRatio;
    private javax.swing.JLabel zRatioLabel;
    // End of variables declaration//GEN-END:variables

}
