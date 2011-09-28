/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.visualisation.ui;

import java.util.List;


public class Visualisation {

    private int importance;
    private String name;
    private String type;

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Visualisation clone(){
        Visualisation result = new Visualisation();
        result.name = this.name;
        result.type = this.type;
        result.importance = this.importance;
        return result;
    }

    public Visualisation cloneVide(){
        Visualisation result = new Visualisation();
        result.name = "0";
        result.type = "O";
        result.importance = 0;
        return result;
    }

    public Visualisation UpdateImportance(int newImportance){
        Visualisation result = new Visualisation();
        result.importance = newImportance;
        return result;
    }
    
}


