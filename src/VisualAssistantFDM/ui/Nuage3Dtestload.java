/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Nuage3Dtestload.java
 *
 * Created on 19 ao�t 2011, 03:13:40
 */
package VisualAssistantFDM.ui;

import VisualAssistantFDM.io.FiltreExtensible;
import VisualAssistantFDM.io.LoadVisualizations;
import VisualAssistantFDM.io.VisualizationReader;
import VisualAssistantFDM.visualisation.ui.Appariement;
import VisualAssistantFDM.visualisation.ui.Matching;
import VisualAssistantFDM.visualisation.ui.Normalisation;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import VisualAssistantFDM.xml.UpdateXMLFile;
import VisualAssistantFDM.xml.VisuXMLReader;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import visualisation3d.vrmNuage3D.Visualisation_Nuage_3D;
import visualisation3d.xml.Nuage3DVisuXMLReader;
import vrminerlib.core.VRMinerFramework;
import vrminerlib.utils.MessageBox;

/**
 *
 * @author Abdelheq
 */
public class Nuage3Dtestload extends javax.swing.JFrame {
    
    private String filePathName;
    private Document document;
    
    /** Creates new form Nuage3Dtestload */
    public Nuage3Dtestload() {
        VRMinerFramework.initializeFramework();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        filenameTextField = new javax.swing.JTextField();
        loadXMLfile = new javax.swing.JButton();
        jScrollPaneVisu3D1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Datasets :");

        loadXMLfile.setText("Load file ...");
        loadXMLfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadXMLfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneVisu3D1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadXMLfile)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(filenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadXMLfile))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneVisu3D1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void loadXMLfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadXMLfileActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FiltreExtensible Filtre = new FiltreExtensible("Fichier xml");
        Filtre.addExtension(".xml");
        chooser.addChoosableFileFilter(Filtre);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filePathName = chooser.getSelectedFile().getAbsolutePath().toString();
            filenameTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            try{
            List<Visualisation> liste = new Matching().getListe(filePathName);
            liste = new Matching().getListeTri(liste);
            List<Normalisation> listNormalisation = new ArrayList<Normalisation>();
            for(int i=0; i<liste.size(); i++){
                Normalisation result = new Normalisation();
                if(liste.get(i).getType().equals("numeric")){
                result.setNom(liste.get(i).getName());
                result.setMin(0);
                result.setMax(0);
                listNormalisation.add(result);
                }
            }

            final File Fichier;
            Fichier = chooser.getSelectedFile();
            Thread t = new Thread() {
                            private VisuXMLReader xml;
                            private VisuXMLReader xmlParser;
                            @Override
                            public void run() {

                                try {
                                    String f = Fichier.getAbsolutePath();
                                    xml = new VisuXMLReader(f);
                                    xml.setNameFile(Fichier.getName());
                                    xml.readXmlFile();
                                    xml.parseData();
                                    xml.setParsed(true);
                                    xmlParser = xml;
				}
                                catch (Exception ex) {
                                    xml.getLoading().dispose();
                                    new MessageBox("Erreur XML", "Erreur de Lecture du fichier XML", MessageBox.ERROR);

                                }
                            }
                        };
            t.start();

            
            File fichier = new File(filePathName);
            List<Visualisation> listViusaAttribute = new LoadVisualizations().getIdMethode(1);
            try {
            new UpdateXMLFile(filePathName);
            } catch (Exception ex) {
            Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            VisualizationReader reader = new VisualizationReader(filePathName);
            
                List<Appariement> individuResultatMEC = reader.GenerateMatchingWithProfil(1);
                String shape = new LoadVisualizations().getIdElement(1);
                reader.AddNewUserPofilSettingsGA(1, fichier, listViusaAttribute, individuResultatMEC, shape, listNormalisation);
                reader.enregistreFichier();
            //}
            //AfficherIndividus(filePathName);
            Visualisation_Nuage_3D visu3D1 = new Visualisation_Nuage_3D(0, 0, 0);
            visu3D1.ConfigurationNuage3D(filePathName, "profil"+1);
            visu3D1.createScene();
            jScrollPaneVisu3D1.setViewportView(visu3D1.getCustomCanvas3D());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "erreur Loading XML File : " + ex.getMessage());
            }

            
        }
    
}//GEN-LAST:event_loadXMLfileActionPerformed
    
    public List<Appariement> GenerateMatchingWithProfil(int indiceVisualisation) throws Exception {

        //r�cuperer la description des attributs de donn�es à partir de la partie structure du fichier XML
        List<Visualisation> dataAttributeliste = new Matching().getListe(filePathName);
        //trier les attributs de donn�es selon leur type puis leur importance
        dataAttributeliste = new Matching().getListeTri(dataAttributeliste);
        //r�cuperer les attributs visuels depuis la base de donn�es pour chaque visualisation
        List<Visualisation> visualAttributeliste = new LoadVisualizations().getIdMethode(indiceVisualisation);
        //r�cuperer le matching attributs de donn�es / attributs visuels
        List<Appariement> resultaMEC = this.getMatching(dataAttributeliste, visualAttributeliste);
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
    
    //On enregsitre notre nouvelle arborescence dans le fichier d'origine dans un format classique.
    public void enregistreFichier(String fichier) throws Exception
   {
         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
         sortie.output(document, new FileOutputStream(fichier));

   }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Nuage3Dtestload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nuage3Dtestload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nuage3Dtestload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nuage3Dtestload.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Nuage3Dtestload().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField filenameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPaneVisu3D1;
    private javax.swing.JButton loadXMLfile;
    // End of variables declaration//GEN-END:variables
}
