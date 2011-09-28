/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AxesPanel.java
 *
 * Created on 13 janv. 2011, 16:09:52
 */

package VisualAssistantFDM.configuration;

import java.awt.Color;
import javax.swing.JColorChooser;

/**
 *
 * @author Abdelheq
 */
public class AxesPanel extends javax.swing.JPanel {

    private Color[] axisColor;
    

    /** Creates new form AxesPanel */
    public AxesPanel() {
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

        jPanel1 = new javax.swing.JPanel();
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
        jPanel2 = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(xRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(zRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(yRatioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(zRatio, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(yRatio, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(xRatio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(xRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(zColorView)
                            .addComponent(yColorView)
                            .addComponent(xColorView, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(attLiensLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(afficherLiensCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addGap(38, 38, 38)
                        .addComponent(attLiens, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {xRatioBtn, yRatioBtn, zRatioBtn});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(xRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xRatioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                            .addComponent(xColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(yRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatioBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(yColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                            .addComponent(zRatioLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zRatioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zColorView, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(attLiensLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afficherLiensCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(attLiens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 430, 180));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Axes"));

        LocLabel.setText("Position initiale :");

        xLocLabel.setText("X :");

        yLocLabel.setText("Y :");

        zLocLabel.setText("Z :");

        camTypeLabel.setText("Type de la camera par defaut");

        camType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Clavier et souris" }));
        camType.setPreferredSize(new java.awt.Dimension(200, 22));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(camTypeLabel)
                    .addComponent(camType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {xLoc, yLoc, zLoc});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(camTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(camType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LocLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(zLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yLocLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLocLabel))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {xLoc, yLoc, zLoc});

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 430, 150));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera"));
    }// </editor-fold>//GEN-END:initComponents

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

    private void yRatioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_yRatioStateChanged
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
    }//GEN-LAST:event_yRatioStateChanged

    private void zRatioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zRatioStateChanged
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
    }//GEN-LAST:event_zRatioStateChanged

    private void xRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xRatioBtnActionPerformed
        axisColor[0] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.GREEN);
        xColorView.setBackground(axisColor[0]);
    }//GEN-LAST:event_xRatioBtnActionPerformed

    private void yRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yRatioBtnActionPerformed
        axisColor[1] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.RED);
        yColorView.setBackground(axisColor[1]);
    }//GEN-LAST:event_yRatioBtnActionPerformed

    private void zRatioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zRatioBtnActionPerformed
        axisColor[2] = JColorChooser.showDialog(
            this,
            "Couleur de l'Axe ",
            Color.BLUE);
        zColorView.setBackground(axisColor[2]);
    }//GEN-LAST:event_zRatioBtnActionPerformed

    public String getAfficherLiens() {
        if (afficherLiensCheckBox.isSelected()) {
            return "true";
        }
        return "false";
    }

    public String getAttributLiens() {
        return attLiens.getSelectedItem().toString();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LocLabel;
    private javax.swing.JCheckBox afficherLiensCheckBox;
    private javax.swing.JComboBox attLiens;
    private javax.swing.JLabel attLiensLabel;
    private javax.swing.JComboBox camType;
    private javax.swing.JLabel camTypeLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField xColorView;
    private javax.swing.JSpinner xLoc;
    private javax.swing.JLabel xLocLabel;
    private javax.swing.JSpinner xRatio;
    private javax.swing.JButton xRatioBtn;
    private javax.swing.JLabel xRatioLabel;
    private javax.swing.JTextField yColorView;
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
    // End of variables declaration//GEN-END:variables

}