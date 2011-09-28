/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DescriptionPanel.java
 *
 * Created on 13 janv. 2011, 15:34:43
 */

package VisualAssistantFDM.configuration;

import VisualAssistantFDM.configuration.DescriptionMethodePanel;
import VisualAssistantFDM.configuration.Espace3DPanel;
import VisualAssistantFDM.configuration.CameraPanel;
import VisualAssistantFDM.configuration.AxesPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import visualassistantfacv.ClusteringPanel;
import VisualAssistantFDM.visualisation.DataBase.Object3DPanel;

/**
 *
 * @author Abdelheq
 */
public class DescriptionPanel extends javax.swing.JTabbedPane {

    /** Creates new form DescriptionPanel */
    public DescriptionPanel() {
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        
        JTabbedPane UserPreferences = new JTabbedPane();
        JTabbedPane AdvancedSettings = new JTabbedPane();
        JTabbedPane Profils = new JTabbedPane();
        //Description de la visualisation
        //r�cuperer les donn�es depuis la base de donn�es
        //Declaration des tab des param�tres de la visualisation
        AdvancedSettings.addTab("Axes Settings", new AxesPanel());
        AdvancedSettings.addTab("Camera", new CameraPanel());
        AdvancedSettings.addTab("Reseau", new ReseauPanel());
        AdvancedSettings.addTab("Object 3D", new Object3DPanel());
        AdvancedSettings.addTab("Clustering", new ClusteringPanel());
        AdvancedSettings.addTab("Espace 3D", new Espace3DPanel());
        //Declaration des tab des pr�ferences utilisateurs
        //UserObjectives.add(new ExpertObjectPanel());
        
        UserPreferences.addTab("Visualization Description", new DescriptionMethodePanel());
        UserPreferences.addTab("Advanced Settings", AdvancedSettings);
        UserPreferences.addTab("User Objectives", new ExpertObjectPanel());
        UserPreferences.addTab("Profils", new ProfilsPanel());
        
        //advancedTb.setLayout(new BorderLayout());
        panel.add(UserPreferences);
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
