/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VisualAssistantFDM.userObjectives;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdelheq
 */
public class UserPreferences {

    private int IdObjectif;
    private String Description;
    private int Importance;

    public int getIdObjectif() {
        return IdObjectif;
    }

    public void setIdObjectif(int IdObjectif) {
        this.IdObjectif = IdObjectif;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getImportance() {
        return Importance;
    }

    public void setImportance(int Importance) {
        this.Importance = Importance;
    }

    public UserPreferences clone(){
        UserPreferences result = new UserPreferences();
        result.Description = this.Description;
        result.Importance = this.Importance;

        return result;
    }

    public List<UserPreferences> cloneWeight(List<UserPreferences> userPref){

        List<UserPreferences> resultList = new ArrayList<UserPreferences>();

        for(int i = 0; i<userPref.size(); i++){
        UserPreferences result = new UserPreferences();
        result.Description = this.Description;
        result.Importance = userPref.get(i).getImportance();
        resultList.add(result);
        }

        return resultList;
    }

}
