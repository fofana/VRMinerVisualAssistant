/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.DataBase;

/**
 *
 * @author Abdelheq
 */
public class Methode {

    private int idmethode;
    private String nom;
    private String description;
    private String lien;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdmethode() {
        return idmethode;
    }

    public void setIdmethode(int idmethode) {
        this.idmethode = idmethode;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
