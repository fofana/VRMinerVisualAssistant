/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.DataBase;

/**
 *
 * @author Abdelheq
 */
public class ElementGraphique {

    private int idelement;
    private String nom;
    private String evenement;

    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public int getIdelement() {
        return idelement;
    }

    public void setIdelement(int idelement) {
        this.idelement = idelement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
