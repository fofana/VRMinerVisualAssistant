/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import VisualAssistantFDM.io.LoadVisualizations;
import VisualAssistantFDM.userObjectives.UserPreferences;


/**
 *
 * @author Abdelheq
 */
public class Visual_Assistant_UserObj_IHM extends JFrame{

    /*
     Décalaration des variables et d'objets
     */
    private JPanel radioPanel, controlPanel;
    private List<UserPreferences> UserObjectives, Output_UserObjectives, Input_UserObjectives;
    private double[] Pref_tab;
    private ButtonGroup bgroup;
    private JRadioButton aucune_ImpotButton, faible_ImpotButton, moyenne_ImpotButton, forte_ImpotButton;
    private JButton ValidateBtn;


    public Visual_Assistant_UserObj_IHM() throws Exception{

        this.setTitle("User Objectives Interface");
        this.setLocation(370, 300);

        /*  Chargement des descriptions ainsi que les identificateurs de tous les objectives utilisateurs contenues dans la base de données  */
        UserObjectives = new LoadVisualizations().LoadUserObjectives();
        Pref_tab = new double[UserObjectives.size()];
        System.out.println(Pref_tab.length);

        /* Chargement des importances ainsi que les identificateurs de tous les objectives utilisateurs de la visualisation choisie depuis la base de données */
        Input_UserObjectives =  new LoadVisualizations().getUserObjectives(5);

        for(int j = 0; j<UserObjectives.size(); j++){
        System.out.println(UserObjectives.get(j).getIdObjectif());
        //System.out.println(UserObjectives.get(j).getImportance());
        }
        
        radioPanel = new JPanel();
        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "User Objectives List "));
        for(int i=0; i<UserObjectives.size(); i++){
        radioPanel.setLayout(new GridLayout(UserObjectives.size(), 6));
        radioPanel.add(new JLabel(UserObjectives.get(i).getDescription()));
        radioPanel.add(new JLabel("     "));
        aucune_ImpotButton = new JRadioButton("Aucune Importance"  , true);
        faible_ImpotButton = new JRadioButton("Faible Importance"   , false);
        moyenne_ImpotButton = new JRadioButton("Moyenne Importance", false);
        forte_ImpotButton = new JRadioButton("Forte Importance", false);
        bgroup = new ButtonGroup();
        bgroup.add(aucune_ImpotButton);
        bgroup.add(faible_ImpotButton);
        bgroup.add(moyenne_ImpotButton);
        bgroup.add(forte_ImpotButton);
        radioPanel.add(aucune_ImpotButton);
        radioPanel.add(faible_ImpotButton);
        radioPanel.add(moyenne_ImpotButton);
        radioPanel.add(forte_ImpotButton);
        this.add(radioPanel);
        }

        controlPanel = new JPanel();
        ValidateBtn = new JButton("Ok ! ");
        ValidateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Pref_tab = getWeightObjectivesSelect(Pref_tab);
        }
        });
        controlPanel.add(ValidateBtn);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public double[] getWeightObjectivesSelect(double [] UserObjecSelect) {

        
        for(int i=0; i<UserObjecSelect.length; i++){
        if(aucune_ImpotButton.isSelected()){
            UserObjecSelect[i] = 0;
            System.out.println("Aucune : "+UserObjecSelect[i]);
        }
        if(faible_ImpotButton.isSelected()){
            UserObjecSelect[i] = 0.33;
            System.out.println("Faible : "+UserObjecSelect[i]);
        }
        if(moyenne_ImpotButton.isSelected()){
            UserObjecSelect[i] = 0.66;
            System.out.println("Moyenne : "+UserObjecSelect[i]);
        }
        if(forte_ImpotButton.isSelected()){
            UserObjecSelect[i] = 1;
            System.out.println("Forte : "+UserObjecSelect[i]);
        }

        }
        
        return UserObjecSelect;
    }

    public List<UserPreferences> getUserPreferences(){
        List<UserPreferences> result =  new ArrayList<UserPreferences>();
        return result;
    }


    public static void main(String[] args) throws Exception{
        new Visual_Assistant_UserObj_IHM();
    }

}
