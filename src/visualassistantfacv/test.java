/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualassistantfacv;

/**
 *
 * @author Abdelheq
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.Transform3D;
import javax.media.j3d.View;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Vector3d;
import visualisation3d.vrmNuage3D.Visualisation_Nuage_3D;
import vrminerlib.core.VRMinerFramework;
import vrminerlib.object3d.Object3D;
import vrminerlib.object3d.common.BoxVRMObject3D;
import vrminerlib.scene.PointOfView;
import vrminerlib.scene.Scene3D;
import vrminerlib.scene.exception.ObjectNameAlreadyAdded;

/**
 * Hello world!
 *
 */
public class test extends Scene3D {

    public static void main(String[] args) {
        VRMinerFramework.initializeFramework();
        test myApp = new test();
//        try {
//            myApp.add(new BoxVRMObject3D(null, new Transform3D(), new Vector3d(1, 1, 1), Color.BLACK, null) {
//
//                @Override
//                public void setRotating(boolean on) {
//                    //                throw new UnsupportedOperationException("Not supported yet.");
//                }
//
//                @Override
//                public void onMouseEvent(MouseEvent m) {
//                    //                throw new UnsupportedOperationException("Not supported yet.");
//                }
//            });
//        } catch (ObjectNameAlreadyAdded ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //for (int i = 1; i <= 4; i++) {
            JFrame myJFrame = new JFrame("Interface assistant visuel");
            myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myJFrame.setLayout(new BorderLayout());
            JPanel panel1 = new JPanel();
            panel1.setLayout(new BorderLayout());
            panel1.setSize(100,100);
            
//            JPanel panel2 = new JPanel();
//            JPanel panel3 = new JPanel();



//        myJFrame.add(myApp.getCustomCanvas3D());

            Visualisation_Nuage_3D visu3D = new Visualisation_Nuage_3D(0, 0, 0);
//            Visualisation_Nuage_3D visu3D1 = new Visualisation_Nuage_3D(panel2, 0, 0, 0);
//            Visualisation_Nuage_3D visu3D2 = new Visualisation_Nuage_3D(panel3, 0, 0, 0);
            visu3D.ConfigurationNuage3D("C:\\Users\\Abdelheq\\Desktop\\IRIS.xml", "profil"+1);
            panel1.add(new JButton("coucou"),BorderLayout.EAST);
            panel1.add(visu3D.getCustomCanvas3D(),BorderLayout.CENTER);
//            visu3D1.ConfigurationNuage3D("C:\\Users\\Abdelheq\\Desktop\\IRIS.xml", "profil"+1);
//            visu3D2.ConfigurationNuage3D("C:\\Users\\Abdelheq\\Desktop\\IRIS.xml", "profil"+1);
//            panel1.add(new Visualisation_Nuage_3D(panel1, 0, 0, 0));
////            panel1.add(new ColorCube3D());
////            panel1.add(new ColorCube3D());
//            myJFrame.add(panel1, BorderLayout.NORTH);
//            myJFrame.add(panel2, BorderLayout.CENTER);
//            myJFrame.add(panel3, BorderLayout.SOUTH);
            //visu3D.createScene();
//            visu3D1.createScene();
//            visu3D2.createScene();
            JButton b = new JButton();
            myJFrame.add(panel1, BorderLayout.NORTH);
            myJFrame.add(b, BorderLayout.SOUTH);
            myJFrame.setSize(1200, 800);
            myJFrame.setVisible(true);
        //}

        

//        myJFrame.add(visu3D.getCustomCanvas3D());



        myApp.draw();
    }

    @Override
    public void focusVRMObject3D(Object3D object3D) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
