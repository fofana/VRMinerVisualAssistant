package visualassistantfacv;



import VisualAssistantFDM.visualisation.ui.MEC;
import VisualAssistantFDM.visualisation.ui.Matching;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.visualisation.ui.Appariement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abdelheq
 */
public class IHM_MatchingAdjuset_IEC extends JFrame{

    public IHM_MatchingAdjuset_IEC(List<Appariement> MatchingResult) throws Exception{
        initComponents();
        initialisation();
        MatchingResultInitiale = MatchingResult;
        Pop = new ArrayList<Visualisation>();
        CreateInitialPopulation(MatchingResult);
//        Set<Visualisation> fruitsSet = new HashSet<Visualisation>(Pop);
//        for (Visualisation theFruit : fruitsSet){
//            System.out.println("Here : "+theFruit.getName());
//        }
        Population = GenererNewPopulation(Pop);
        //AfficherIndividus(MatchingResultInitiale, fils);
        for(int i=0; i<Population.size(); i++){
            for(int j=0; j<Population.get(0).size(); j++){
            System.out.println("Paramétrage de la population : indice visuel "+MatchingResultInitiale.get(j).getName_v_data()+" : attribut de données "+Population.get(i).get(j).getName());
            }
        }
        AfficherIndividus(Population);

        //AfficherIndividus(MatchingResult, Population);
        //population = new AGI().initialisation(MatchingResult);
        
       
        //listdeVisual = Visuallistdata;

    }

    private void initComponents() {
        PopulationPanel = new javax.swing.JPanel();
        Individu1Panel = new javax.swing.JPanel();
        checkbox1 = new javax.swing.JCheckBox();
        pane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        Individu2Panel = new javax.swing.JPanel();
        checkbox2 = new javax.swing.JCheckBox();
        pane2 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        Individu3Panel = new javax.swing.JPanel();
        checkbox3 = new javax.swing.JCheckBox();
        pane3 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        Individu5Panel = new javax.swing.JPanel();
        checkbox7 = new javax.swing.JCheckBox();
        pane7 = new javax.swing.JScrollPane();
        Table5 = new javax.swing.JTable();
        Individu4Panel = new javax.swing.JPanel();
        checkbox4 = new javax.swing.JCheckBox();
        pane4 = new javax.swing.JScrollPane();
        Table4 = new javax.swing.JTable();
        Individu2Panel1 = new javax.swing.JPanel();
        checkbox5 = new javax.swing.JCheckBox();
        pane5 = new javax.swing.JScrollPane();
        Table6 = new javax.swing.JTable();
        Individu2Panel2 = new javax.swing.JPanel();
        checkbox6 = new javax.swing.JCheckBox();
        pane6 = new javax.swing.JScrollPane();
        Table7 = new javax.swing.JTable();
        Individu2Panel3 = new javax.swing.JPanel();
        checkbox9 = new javax.swing.JCheckBox();
        pane9 = new javax.swing.JScrollPane();
        Table8 = new javax.swing.JTable();
        Individu2Panel4 = new javax.swing.JPanel();
        checkbox8 = new javax.swing.JCheckBox();
        pane8 = new javax.swing.JScrollPane();
        Table9 = new javax.swing.JTable();
        ControlPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MutationTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        NewPopulation = new javax.swing.JButton();
        CrossOver = new javax.swing.JButton();
        CrossOverTextField = new javax.swing.JTextField();
        Launch = new javax.swing.JButton();
        Mutation = new javax.swing.JButton();
        close = new javax.swing.JButton();
        close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Individu1Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox1.setText("individu1");
        Individu1Panel.add(checkbox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane1.setViewportView(Table1);
        Individu1Panel.add(pane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu2Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox2.setText("individu2");
        Individu2Panel.add(checkbox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane2.setViewportView(Table2);
        Individu2Panel.add(pane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu3Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox3.setText("individu3");
        Individu3Panel.add(checkbox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane3.setViewportView(Table3);
        Individu3Panel.add(pane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu5Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox7.setText("individu7");
        Individu5Panel.add(checkbox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane7.setViewportView(Table7);
        Individu5Panel.add(pane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu4Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox4.setText("individu4");
        Individu4Panel.add(checkbox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane4.setViewportView(Table4);
        Individu4Panel.add(pane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu2Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox5.setText("individu5");
        Individu2Panel1.add(checkbox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane5.setViewportView(Table5);
        Individu2Panel1.add(pane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu2Panel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox6.setText("individu6");
        Individu2Panel2.add(checkbox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane6.setViewportView(Table6);
        Individu2Panel2.add(pane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu2Panel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox9.setText("individu9");
        Individu2Panel3.add(checkbox9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane9.setViewportView(Table9);
        Individu2Panel3.add(pane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        Individu2Panel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        checkbox8.setText("individu8");
        Individu2Panel4.add(checkbox8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, -1, -1));
        pane8.setViewportView(Table8);
        Individu2Panel4.add(pane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 150));
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(PopulationPanel);
        PopulationPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Individu1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(Individu4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Individu2Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(Individu2Panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Individu3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Individu3Panel, Individu4Panel, Individu5Panel});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Individu3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Individu1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Individu4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Individu5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Individu2Panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setText("Croisement :");

        jLabel2.setText("Mutation : ");

        NewPopulation.setText("New Population");
        NewPopulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

        CrossOver.setText("CrossOver");
        CrossOver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IndividuSelectInitialisation();
                individuSelect = getIndividuSelect(individuSelect);
                generateNewPopulation(individuSelect);
                for(int i=0; i<individuSelect.length; i++){
                System.out.println(i+" State is : "+individuSelect[i]);
                }
                checkbox1.setSelected(false);
                checkbox2.setSelected(false);
                checkbox3.setSelected(false);
                checkbox4.setSelected(false);
                checkbox5.setSelected(false);
                checkbox6.setSelected(false);
                checkbox7.setSelected(false);
                checkbox8.setSelected(false);
                checkbox9.setSelected(false);
                try {
                    croisement(individuSelect, Population);
                } catch (Exception ex) {
                    Logger.getLogger(IHM_MatchingAdjuset_IEC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        CrossOverTextField.setText("");

        Launch.setText("Launch");
        Launch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

        Mutation.setText("Mutation");
        Mutation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

            }
        });

        close.setText("Close");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
            }
        });

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MutationTextField)
                    .addComponent(CrossOverTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Mutation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CrossOver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Launch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NewPopulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrossOverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Mutation)
                    .addComponent(NewPopulation)
                    .addComponent(close))
                .addGap(19, 19, 19)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MutationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CrossOver, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(Launch)
                    .addComponent(close))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PopulationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ControlPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PopulationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-753)/2, (screenSize.height-735)/2, 753, 735);
        pack();
        setVisible(true);
    }

    public void initialisation() throws Exception{

        individu1 = new DefaultTableModel(Data, colNames);
        individu2 = new DefaultTableModel(Data, colNames);
        individu3 = new DefaultTableModel(Data, colNames);
        individu4 = new DefaultTableModel(Data, colNames);
        individu5 = new DefaultTableModel(Data, colNames);
        individu6 = new DefaultTableModel(Data, colNames);
        individu7 = new DefaultTableModel(Data, colNames);
        individu8 = new DefaultTableModel(Data, colNames);
        individu9 = new DefaultTableModel(Data, colNames);
        individuSelect = new boolean[9];
        for(int i=0; i<individuSelect.length; i++){
            individuSelect[i] = false;
        }
        checkbox1.setSelected(false);
        checkbox2.setSelected(false);
        checkbox3.setSelected(false);
        checkbox4.setSelected(false);
        checkbox5.setSelected(false);
        checkbox6.setSelected(false);
        checkbox7.setSelected(false);
        checkbox8.setSelected(false);
        checkbox9.setSelected(false);
      }

    public void CreateInitialPopulation(List<Appariement> firstMatching){
        for(int i=0; i<firstMatching.size(); i++){
           Visualisation MEC = new Visualisation();
           MEC.setName(firstMatching.get(i).getName_data());
           //System.out.println(firstMatching.get(i).getName_data()+" :: "+firstMatching.get(i).getType_data()+" :: "+firstMatching.get(i).getImportance_data());
           MEC.setType(firstMatching.get(i).getType_data());
           MEC.setImportance(firstMatching.get(i).getImportance_data());
           Pop.add(MEC);
        }
    }

    public void CreateNewPopulation(List<Appariement> firstMatching){
        int nbSelectionnes =0;
        List<List<Visualisation>> al = new ArrayList<List<Visualisation>>();
        List<List<Visualisation>> newPop = new ArrayList<List<Visualisation>>();
        for (int i = 0; i < individuSelect.length; i++) {
            if(individuSelect[i]) {
                nbSelectionnes++;
                //newPop.add(Population.get(i));
                //al.add(i, Population.get(i));
                al.add(Population.get(i));
             }
//            else {
//                newPop.add(i, null);
//             }
        }
        if(nbSelectionnes == 0){

        }
        CrossOver(al);
//        if(newPop.contains(null)){
//            //ajouter l'individu � la position vide
//        }
        System.out.println("La taille de la nouvelle population est : "+al.size());
        System.out.println("Le nombre d'individus sélectionnés est de : "+al.size());
        int j = 0;
        for(int i=0; i<newPop.size(); i++){
           if(newPop.get(i).isEmpty()){
           newPop.add(i, al.get(j));
           al.remove(j);
           }
           
        }
    }

    public void CrossOver(List<List<Visualisation>> individuSelectionnees){
//        int CrossOverPx = individuSelectionnees.get(0).size()/2;
//        List<Visualisation> pere = new ArrayList<Visualisation>();
//        List<Visualisation> mere = new ArrayList<Visualisation>();
//        List<Visualisation> subListpere = individuSelectionnees.get(0).subList(CrossOverPx,individuSelectionnees.get(0).size());
//        List<Visualisation> subListmere = individuSelectionnees.get(1).subList(CrossOverPx,individuSelectionnees.get(1).size());
//
//        for(int i=0; i< CrossOverPx ; i++){
//           Visualisation newMec = new Visualisation();
//           Visualisation newMec1 = new Visualisation();
//           newMec1.setName(individuSelectionnees.get(0).get(i).getName());
//           newMec.setName(individuSelectionnees.get(0).get(i).getName());
//           newMec1.setType(individuSelectionnees.get(0).get(i).getType());
//           newMec.setType(individuSelectionnees.get(0).get(i).getType());
//           newMec1.setImportance(individuSelectionnees.get(0).get(i).getImportance());
//           newMec.setImportance(individuSelectionnees.get(0).get(i).getImportance());
//           mere.add(newMec1);
//           pere.add(newMec);
//        }
//        //mere.add(0, subListpere);
//        //pere.add(0, subListmere);

    }

    //Initialisation du vecteur des individus à sélectionnées
   public void IndividuSelectInitialisation(){
        individuSelect = new boolean[9];
        nbIndivSelect = 0;
        for(int i=0; i<individuSelect.length; i++){
            individuSelect[i] = false;
        }
    }

    //R�cuperer le vecteur des individus s�lectionn�es
    public boolean[] getIndividuSelect(boolean [] indSelect) {
        
        if(checkbox1.isSelected()){
            indSelect[0] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox2.isSelected()){
            indSelect[1] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox3.isSelected()){
            indSelect[2] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox4.isSelected()){
            indSelect[3] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox5.isSelected()){
            indSelect[4] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox6.isSelected()){
            indSelect[5] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox7.isSelected()){
            indSelect[6] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox8.isSelected()){
            indSelect[7] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        if(checkbox9.isSelected()){
            indSelect[8] = true;
            nbIndivSelect = nbIndivSelect+1;
        }
        System.out.println(nbIndivSelect);
        return indSelect;
    }

    public List<Integer> TirageAleatoire(int taille) throws Exception{

        int insertedValue = 0;
        //initialisation du vecteur contenant la position des gèens qui subiront la mutation lors de l'itération aléatoire
        List<Integer> liste = new ArrayList<Integer>();
        while (insertedValue < Population.size()) {
        Integer random = new Integer((int) Math.ceil(Math.random() * Population.size()));
        if(!liste.contains(random-1)) {
        liste.add(random-1);
        insertedValue++;
        }
        }
        return liste;
    }

//    public List<Integer> TirageAleatoireGeneIndividu(List<Visualisation> individu) throws Exception{
//
//        int insertedValue = 0;
//        //initialisation du vecteur contenant la position des gèens qui subiront la mutation lors de l'itération aléatoire
//        List<Integer> liste = new ArrayList<Integer>();
//        while (insertedValue < individu.size()) {
//        Integer random = new Integer((int) Math.ceil(Math.random() * individu.size()));
//        if(!liste.contains(random-1)) {
//        liste.add(random-1);
//        insertedValue++;
//        }
//        }
//        return liste;
//    }

    //Croisment
    public void croisement(boolean[] individus, List<List<Visualisation>> Population) throws Exception{

//        for(int i=0; i<individus.length; i++){
//            if(individus[i]){
//                for(int j=0; j<Population.get(i).size(); j++){
//                System.out.println(Population.get(i).get(j).getName()+"  "+Population.get(i).get(j).getType()+"  "+Population.get(i).get(j).getImportance());
//                }
//            }
//        }
        //nouvelle instance d'une population
        newPopulation = new ArrayList<List<Visualisation>>();
        //On récupère tous les individus séléctionnées dans une liste
        int nbSelectionnes = 0;
        
        if(nbIndivSelect == 0){
            //I'm here
            List<Integer> vecteurChoix = new ArrayList<Integer>();
            newPopulation = Population;
            vecteurChoix = TirageAleatoire(PopSize);
            for(int i=0; i<vecteurChoix.size(); i++){
               //I'm here
                //NewPopulation.add();
                //System.out.println(vecteurChoix.get(i)+"Individu"+Population.get(vecteurChoix.get(i)));
            //newPopulation = GenererNewPopulation(Population.get(vecteurChoix.get(i)));
            }
            generateNewPopulation(individuSelect);
        } else if(nbIndivSelect == Population.size()){
            generateSamePopulation();
        } else if(nbIndivSelect == 1){
            /*
             *localiserPositionIndividu();
             *genererNewPopulationWithIndividu_i();  generer une nouvelle population avec en entree l'individu sélectionner
             */
        } else if(nbIndivSelect == 2){

            int pointCrossOver = Pop.size()/2;

            List<Visualisation> pere = Population.get(0).subList(pointCrossOver,(Pop.size()));
            List<Visualisation> mere = Population.get(1).subList(pointCrossOver,(Pop.size()));
            for(int i=0; i< pere.size() ; i++){
            System.out.println("ooooooooooooooooo : "+pere.get(i).getName().toString());
            System.out.println("ppppppppppppppppp : "+mere.get(i).getName().toString());
            }
            //Population.get(0).remo
            Population.get(0).add(pointCrossOver, (Visualisation) mere);
            Population.get(1).add(pointCrossOver, (Visualisation) pere);
            for(int j=0; j<Population.get(0).size() ; j++){
            System.out.println("ooooooooooooooooo : "+Population.get(0).get(j).getName().toString());
            System.out.println("ppppppppppppppppp : "+Population.get(1).get(j).getName().toString());
            }

//            for(int i=0; i<Population.size(); i++){
//
//            }

            //display elements of sub list.
            //System.out.println("la sous liste contient : "+lst.size());
//            for(int i=0; i< lst.size() ; i++){
//            System.out.println(lst.get(i).getName().toString());
//            }
            /*
             * List<List<Visualisation>> lst = al.subList(1,3);
            //display elements of sub list.
            System.out.println("la sous liste contient : "+lst.size());
//            for(int i=0; i< lst.size() ; i++){
//            System.out.println(lst.get(i).getName().toString());
//            }
             *localiserPositionIndividu();
             *genererNewPopulationWithIndividu_i();  generer une nouvelle population avec en entree les 2 individus sélectionner puis echanger deux subList entre eux, pour le reste generer a partir des fils des individus muter legerement.
             */
        }
    }

    public List<Visualisation> GenererNewIndividu(List<Visualisation> chromosome) throws Exception {
        List<Visualisation> newIndividu = new ArrayList<Visualisation>();
        vecTirageAleatoireChromosome = TirageAleatoire(chromosome.size());
        for(int i=0; i<chromosome.size(); i++){
            Visualisation individu = new Visualisation();
            individu.setName(chromosome.get(i).getName());
            System.out.println(chromosome.get(i).getName()+" :: "+chromosome.get(i).getType()+" :: "+chromosome.get(i).getImportance());
            individu.setType(chromosome.get(i).getType());
            individu.setImportance((int) ((chromosome.get(Integer.valueOf(vecTirageAleatoire.get(i).toString())).getImportance()) * (Pmute)));
            newIndividu.add(individu);
            
        }
        return newIndividu;
    }

    public List<List<Visualisation>> GenererNewPopulation(List<Visualisation> chromosome) throws Exception {

        List<List<Visualisation>> chromosomes = new ArrayList<List<Visualisation>>();
        int insertedValues = 0;
        vecTirageAleatoire = TirageAleatoire(chromosome.size());
        for(int i=0; i<vecTirageAleatoire.size(); i++){
            System.out.println("Valeur du vecteur aléatoire : "+vecTirageAleatoire.get(i).toString());
        }
        int j = vecTirageAleatoire.get(insertedValues);
        while (insertedValues < PopSize) {
            System.out.println("J : "+j);
            List<Visualisation> fils = new ArrayList<Visualisation>();
            for(int i=0; i<chromosome.size(); i++){
                Visualisation newInd = new Visualisation();
                newInd.setName(chromosome.get(i).getName());
                newInd.setType(chromosome.get(i).getType());
                if(i == j){
                System.out.println("Avant : "+chromosome.get(Integer.valueOf(vecTirageAleatoire.get(i).toString())).getImportance());
                newInd.setImportance((int) ((chromosome.get(Integer.valueOf(vecTirageAleatoire.get(i).toString())).getImportance()) * (Pmute)));
                System.out.println("Apres : "+((int) chromosome.get(Integer.valueOf(vecTirageAleatoire.get(i).toString())).getImportance()) * (Pmute));
                } else{
                  newInd.setImportance(chromosome.get(i).getImportance());
                }
                fils.add(newInd);
            }
            fils  = new Matching().getListeTri(fils);
            chromosomes.add(fils);
//            for(int i=0; i<fils.size(); i++){
//            System.out.println("Nouveau paramétrage : "+MatchingResultInitiale.get(i).getName_v_data().toString()+" : "+fils.get(i).getName());
//            }
            insertedValues++;
            //choisir un gene pour la mutation sans dépassé la taille du matching liste
            if(j>=vecTirageAleatoire.size()-1){
              j=0;
            } else {
            j++;
            }
          }
        //AfficherIndividus(MatchingResultInitiale, chromosomes);
          return chromosomes;
    }

    public List<MEC> getNewMatching(List<Visualisation> newMatch){
        
        for(int i=0; i<newMatch.size(); i++){
        MEC indivMec = new MEC();
        indivMec.setData_attribute(newMatch.get(i).getName());
        indivMec.setVisual_attribute(MatchingResultInitiale.get(i).getName_v_data());
        mec1.add(indivMec);
        }
        return mec1;
    }

    public void AfficherIndividus(List<List<Visualisation>> genotypes) {

        individu1 = Affichage(individu1, MatchingResultInitiale, genotypes.get(0));
        individu2 = Affichage(individu2, MatchingResultInitiale, genotypes.get(1));
        individu3 = Affichage(individu3, MatchingResultInitiale, genotypes.get(2));
        individu4 = Affichage(individu4, MatchingResultInitiale, genotypes.get(3));
        individu5 = Affichage(individu5, MatchingResultInitiale, genotypes.get(4));
        individu6 = Affichage(individu6, MatchingResultInitiale, genotypes.get(5));
        individu7 = Affichage(individu7, MatchingResultInitiale, genotypes.get(6));
        individu8 = Affichage(individu8, MatchingResultInitiale, genotypes.get(7));
        individu9 = Affichage(individu9, MatchingResultInitiale, genotypes.get(8));
        Table1.setModel(individu1);
        pane1.setViewportView(Table1);
        Table2.setModel(individu2);
        pane2.setViewportView(Table2);
        Table3.setModel(individu3);
        pane3.setViewportView(Table3);
        Table4.setModel(individu4);
        pane4.setViewportView(Table4);
        Table5.setModel(individu5);
        pane5.setViewportView(Table5);
        Table6.setModel(individu6);
        pane6.setViewportView(Table6);
        Table7.setModel(individu7);
        pane7.setViewportView(Table7);
        Table8.setModel(individu8);
        pane8.setViewportView(Table8);
        Table9.setModel(individu9);
        pane9.setViewportView(Table9);

        //affecter le nouveau résultat pour l'afficher sur l'interface

    }

    public DefaultTableModel Affichage(DefaultTableModel individu, List<Appariement> Matching, List<Visualisation> chromosomes) {
            individu.setRowCount(0);
        for(int i=0; i<chromosomes.size(); i++){
            individu.addRow(new Object[]{Matching.get(i).getName_v_data(), chromosomes.get(i).getName(), chromosomes.get(i).getImportance()});
        }
        return individu;
        //affecter le nouveau résultat pour l'afficher sur l'interface
    }

    public void generateNewPopulation(boolean [] individuSelect){
        
        int nbSelectionnes =0;
        List<List<Visualisation>> al = new ArrayList<List<Visualisation>>();
        for (int i = 0; i < individuSelect.length; i++) {
            if(individuSelect[i]) {
                nbSelectionnes++;
                //al.add(i, Population.get(i));
                al.add(Population.get(i));
             }
//            else {
//                al.add(i, null);
//             }
        }
        System.out.println("La taille de la nouvelle population est : "+al.size());
        System.out.println("Le nombre d'individus sélectionnés est de : "+al.size());
        
    }

    public void generateSamePopulation(){
            AfficherIndividus(Population);
    }


    /*public static void main(String[] args) throws Exception{
        new IHM_MatchingAdjuset_IEC();
    }*/

    

    // Variables declaration - do not modify
    private javax.swing.JPanel Individu1Panel;
    private javax.swing.JPanel Individu2Panel;
    private javax.swing.JPanel Individu2Panel1;
    private javax.swing.JPanel Individu2Panel2;
    private javax.swing.JPanel Individu2Panel3;
    private javax.swing.JPanel Individu2Panel4;
    private javax.swing.JPanel Individu3Panel;
    private javax.swing.JPanel Individu4Panel;
    private javax.swing.JPanel Individu5Panel;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table3;
    private javax.swing.JTable Table4;
    private javax.swing.JTable Table5;
    private javax.swing.JTable Table6;
    private javax.swing.JTable Table7;
    private javax.swing.JTable Table8;
    private javax.swing.JTable Table9;
    private javax.swing.JCheckBox checkbox1;
    private javax.swing.JCheckBox checkbox2;
    private javax.swing.JCheckBox checkbox3;
    private javax.swing.JCheckBox checkbox4;
    private javax.swing.JCheckBox checkbox5;
    private javax.swing.JCheckBox checkbox6;
    private javax.swing.JCheckBox checkbox7;
    private javax.swing.JCheckBox checkbox8;
    private javax.swing.JCheckBox checkbox9;
    private javax.swing.JButton CrossOver;
    private javax.swing.JButton NewPopulation;
    private javax.swing.JButton Launch;
    private javax.swing.JButton Mutation;
    private javax.swing.JButton close;
    private javax.swing.JButton Close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel PopulationPanel;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JTextField MutationTextField;
    private javax.swing.JTextField CrossOverTextField;
    private javax.swing.JScrollPane pane1;
    private javax.swing.JScrollPane pane2;
    private javax.swing.JScrollPane pane3;
    private javax.swing.JScrollPane pane4;
    private javax.swing.JScrollPane pane5;
    private javax.swing.JScrollPane pane6;
    private javax.swing.JScrollPane pane7;
    private javax.swing.JScrollPane pane8;
    private javax.swing.JScrollPane pane9;
    DefaultTableModel individu1, individu2, individu3, individu4, individu5, individu6, individu7, individu8, individu9;
    public Object[][] Data = null;
    String colNames[] = {"Indice visuel", "Attribut de données", "Importance"};
    public List<Visualisation> listdeVisual, Pop;
    List<Integer> vecTirageAleatoire, vecTirageAleatoireChromosome;
    double Pmute = 0.7;
    public List<Appariement> MatchingResultInitiale, NewMatchingResult;
    public List<MEC> mec, mec1;
    public List<List<Visualisation>> Population, newPopulation;
    boolean[] individuSelect;
    public int nbIndivSelect, PopSize = 9;;
    // End of variables declaration

}
