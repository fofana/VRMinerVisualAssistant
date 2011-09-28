/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualizationMethods;

import VisualAssistantFDM.visualisation.DataBase.VisualizationMethod;

/**
 *
 * @author Abdelheq
 */
public class ParallelCoordinates implements VisualizationMethod{

    public void getMethodName() {
        System.out.println ("Parallel coordinate visualization");
    }

    
    public void getSpatialCategoryName() {
        System.out.println ("Temporal visualization");
    }

    public void getVisualCategoryName() {
        System.out.println ("3D visualization");
    }

    public void getGraphicElementName() {
        System.out.println ("broken line graphic element");
    }

    public void getBibliographyName() {

    }

    public void getExpertObjectivesName() {

    }

    public static void main(String args[]){
        new ParallelCoordinates().getGraphicElementName();
        double[] tab = {1.4, 9.2, 10.4};

        for (double nb : tab)
        {
            System.out.println (nb);
        }
    }

}
