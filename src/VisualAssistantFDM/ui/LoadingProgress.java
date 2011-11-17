/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.ui;

import java.awt.*;
import javax.swing.*;
public class LoadingProgress
{
    JProgressBar progress;
    JFrame frame;
    JPanel panel;
    JLabel currenTask;

    public void paint()
    {
        // Création de l'interface
        frame = new JFrame("En cours de chargement");
        JLabel texte = new JLabel("Veuillez patienter pendant le chargement...");
        progress = new JProgressBar(0, 100);
        panel = new JPanel();
        panel.add("Center", progress);
        panel.add("Center", texte);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(275,85);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Création de thread
//        monThread= new Thread(new MonRunnable());
//        monThread.start();
    }
//        public class MonRunnable implements Runnable
//        {
//            public void run()
//            {
//            for (int j = 1; j < 100; j++){ // on fait une boucle pour que la JProgressBar "avance"
//
//                progress.setValue(j);
//                try{
//                    monThread.sleep(rappidite);
//                }
//                catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//            cadre.dispose(); //on ferme le cadre (le chergement est fini!)
//            }
    //}
    public static void main(String[] args)
    {
        LoadingProgress prgs= new LoadingProgress();
        prgs.paint();
    }
} 