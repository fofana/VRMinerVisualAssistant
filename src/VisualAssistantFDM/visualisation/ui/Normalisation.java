/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.ui;

import java.util.List;

/**
 *
 * @author Abdelheq
 */
public class Normalisation {

    private String nom;
    private float Max;
    private float Min;

    public float getMax() {
        return Max;
    }

    public void setMax(float Max) {
        this.Max = Max;
    }

    public float getMin() {
        return Min;
    }

    public void setMin(float Min) {
        this.Min = Min;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Normalisation clone(){
        Normalisation result = new Normalisation();
        result.nom = this.nom;
        result.Min = this.Min;
        result.Max = this.Max;
        return result;
    }

}
