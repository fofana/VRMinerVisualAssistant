/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.visualassistantfacv;

import java.lang.Math;
import java.util.Random;

/**
 *
 * @author Abdelheq
 */
public class GenerateRandomMutationValue {
    private static double higher = 20, low = 10, PmuteRate, Pmute, NoiseValue, NewValue, OldValue = 76, RandomValue;
    private static boolean MutationType = false; // Si MuMutationType = false  Y' = Y + Delta (t, r(k)-y) *** Si MuMutationType = false  Y' = Y - Delta (t, y - r(k))
    private static Random r;
    

    public static void main(String[] args){

//     proba = Math.abs(r.nextDouble()); // doit-on appliquer une mutation au gene ?
//     if(proba <= mutationRate) {
//     ok = true;
//     }
        //    long l = rand.nextLong();
//    System.out.println("L :"+l);
//    float f = rand.nextFloat(); // 0.0 <= f < 1.0
//    System.out.println("F :"+f);
//    double d = rand.nextDouble(); // 0.0 <= d < 1.0
//    System.out.println("D :"+d);

    
    
    r = new Random();
    PmuteRate = Math.abs(r.nextDouble());
    RandomValue = (Math.random() * 1);
    Pmute = Math.random() * (-higher)+low;
    NoiseValue = (int) ((RandomValue - 0.5) + 2 * Pmute);
    NewValue = OldValue + NoiseValue;
//    if(PmuteRate <= 0.1) {
//    NewValue = OldValue + NoiseValue;
//    } 
    if(NewValue>100){
         NewValue = 100;
    }
    if(NewValue<0){
         NewValue = 0;
    }
    System.out.println(" Old Value :  "+OldValue +" New Value :  "+NewValue + " Mutation Probability :"+Pmute + " Noise Value :"+NoiseValue);
    System.out.println("Random Value :"+PmuteRate);
    
    }

    }



