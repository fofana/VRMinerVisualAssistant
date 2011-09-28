/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualassistantfacv;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdelheq
 */
public class TirageAleatoire {

    public TirageAleatoire(int taille){
        int insertedValue = 0;
        //initialisation du vecteur contenant la position des gèens qui subiront la mutation lors de l'itération aléatoire
        List<Integer> liste = new ArrayList<Integer>();
        while (insertedValue <taille) {
        Integer random = new Integer((int) Math.ceil(Math.random() * taille));
        if(!liste.contains(random-1)) {
        liste.add(random-1);
        insertedValue++;
        }
        }
        for(int i=0; i<liste.size(); i++){
          System.out.println(liste.get(i).toString());
      }
    }

    public static void main(String[] args){

    new TirageAleatoire(9); 
    }
}
