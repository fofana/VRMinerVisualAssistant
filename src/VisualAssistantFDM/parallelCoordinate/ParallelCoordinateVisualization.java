/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ParallelCoordinateVisualization.java
 *
 * Created on 16 ao�t 2011, 21:01:01
 */
package VisualAssistantFDM.parallelCoordinate;

import VisualAssistantFDM.file.XMLFile;
import VisualAssistantFDM.visualisation.ui.Visualisation;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.mediavirus.parvis.file.STFFile;

/**
 *
 * @author Abdelheq
 */
public class ParallelCoordinateVisualization extends javax.swing.JFrame {
    private List<Visualisation> listAttribut;
    private List<Visualisation> listAttributNumeric;
    private Document document;
    private Element racine;
    File currentPath = null;
    /** Creates new form ParallelCoordinateVisualization */
    public ParallelCoordinateVisualization() {
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

        datasourceLabel = new javax.swing.JLabel();
        parallelDisplay = new org.mediavirus.parvis.gui.ParallelDisplay();
        urlField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        fileName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        datasourceLabel.setFont(new java.awt.Font("Dialog", 0, 10));
        datasourceLabel.setText("Datasource: ");

        parallelDisplay.setPreferredSize(new java.awt.Dimension(800, 500));

        urlField.setFont(new java.awt.Font("Dialog", 0, 10));
        urlField.setMargin(new java.awt.Insets(0, 0, 0, 5));
        urlField.setMinimumSize(null);
        urlField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlFieldActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 0, 10));
        jButton1.setText("Load File...");
        jButton1.setMargin(new java.awt.Insets(0, 5, 0, 0));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1openItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(datasourceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(parallelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(70, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datasourceLabel)
                    .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(fileName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(508, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(parallelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void urlFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlFieldActionPerformed
        try {
            STFFile f = new STFFile(new URL(urlField.getText()));
                        
            f.readContents();
        
            parallelDisplay.setModel(f);
            setTitle("Parvis - " + f.getName());
        }
        catch (Exception e){
            System.out.println(e.toString() + e.getMessage());
        }
}//GEN-LAST:event_urlFieldActionPerformed

 public  void getAttributNumeric(){
     listAttributNumeric = new ArrayList<Visualisation>();
     for (Visualisation visualisation : listAttribut) {
         if (visualisation.getType().equals("numeric"))
             listAttributNumeric.add(visualisation);
     }
 }

private void jButton1openItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1openItemActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
                public boolean accept(File f){
                    return(f.isDirectory() || f.getName().endsWith(".xml"));
                }
                public String getDescription(){
                    //return "STF (Simple Table Format) Data Files";
                    return "XML (eXtended Markup Language) Data Files";
                }
            });
        if (currentPath == null){
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        }
        else {
            chooser.setCurrentDirectory(currentPath);
        }
        
        int option = chooser.showOpenDialog(this);
        
        if (option == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile() != null){
                currentPath = chooser.getSelectedFile().getParentFile();
                String urltext = "file:///" + chooser.getSelectedFile().getAbsolutePath();
                String filePath = chooser.getSelectedFile().getAbsolutePath().toString();
                //final File Fichier = chooser.getSelectedFile();
                File file= new File(filePath);               
                urltext = urltext.replace('\\','/');
                urlField.setText(urltext);
                try {
//                    XMLFile xml = new XMLFile(new URL(urltext));
//                    listAttribut = xml.getStructure(filePath);
//                    getAttributNumeric();
//                    getData(file, listAttribut);
                    //---------------------------------------------
                    XMLFile x = new XMLFile(new URL(urltext));
                    x.readXMLContents(filePath);
                    //int width = x.getNumDimensions() * ;
                    parallelDisplay.setModel(x);
                    //parallelDisplay.setSize(WIDTH, option)
                    setTitle("Parvis - " + x.getName());
                    fileName.setText(x.getName());
                }
                catch (Exception e){
                    System.out.println(e.toString() + e.getMessage());
                }
                
            }
        }
}//GEN-LAST:event_jButton1openItemActionPerformed
    
    
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
            java.util.logging.Logger.getLogger(ParallelCoordinateVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParallelCoordinateVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParallelCoordinateVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParallelCoordinateVisualization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ParallelCoordinateVisualization().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel datasourceLabel;
    private javax.swing.JLabel fileName;
    private javax.swing.JButton jButton1;
    private org.mediavirus.parvis.gui.ParallelDisplay parallelDisplay;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
}
