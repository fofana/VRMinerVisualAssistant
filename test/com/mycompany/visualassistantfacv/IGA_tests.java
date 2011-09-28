/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.visualassistantfacv;

/**
 *
 * @author Abdelheq
 */
public class IGA_tests {

    int Father[] = new int[] {1,2,2,1,3};
    int Mother[] = new int[] {4,4,2,1,1};

    public IGA_tests(){

     int result[] = reproduce(Father, Mother);

     for(int i=0; i<result.length; i++){
     System.out.println(result[i]);
     }

    }

    public int[] reproduce(int father[], int mother[]) {

          int[] child= new int[father.length];
          int crossPoint = (int) (Math.random() * father.length); //make a crossover point
          for (int i=0;i<father.length;++i)
          {
            if (i<crossPoint)
              child[i]=father[i];
            else
              child[i]=mother[i];
          }
          return child;
    }

    public static void main(String[] args){
        new IGA_tests();
    }

}
