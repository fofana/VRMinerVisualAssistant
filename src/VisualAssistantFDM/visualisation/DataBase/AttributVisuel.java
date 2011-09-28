/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.DataBase;

/**
 *
 * @author Abdelheq
 */
public class AttributVisuel {

    private int idattribut;
    private String nom;
    private int nbutilisation;
    private int importance;
    private int ilimite;

    public int getIdattribut() {
        return idattribut;
    }

    public void setIdattribut(int idattribut) {
        this.idattribut = idattribut;
    }

    public int getIlimite() {
        return ilimite;
    }

    public void setIlimite(int ilimite) {
        this.ilimite = ilimite;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getNbutilisation() {
        return nbutilisation;
    }

    public void setNbutilisation(int nbutilisation) {
        this.nbutilisation = nbutilisation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
