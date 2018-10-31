/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Combat;

/**
 *
 * @author Tara
 */
public class Jester {

    private int resolveLvl;

    private final int[] maxHPArray = {19, 23, 27, 31, 35};
    private final double[] dodgeArray = {.15, .20, .25, .30, .35};
    private final int[] speedArray = {7, 7, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.075, .08, .085, .09, .095};
    private final double[] dmgArray = {5.5, 6.5, 8, 8.5, 10};

    private static boolean religious = false;
//
//
//    private double stunRes = 0.2;
//    private double moveRes = 0.2;
//    private double blightRes = 0.4;
//    private double bleedRes = 0.3;
//    private double diseaseRes = 0.2;
//    private double debuffRes = 0.4;
//    private double deathRes = 0.67;
//    private double trapRes = 0.3;

    public Jester(int resolveLvl) {
       
//        this.resolveLvl = resolveLvl;
//
//        int arraySlot = resolveLvl - 1;
//
//        this.maxHP = maxHPArray[arraySlot];
//        this.dodge = dodgeArray[arraySlot];
//        this.speed = speedArray[arraySlot];
//        this.accMod = accModArray[arraySlot];
//        this.critMod = critModArray[arraySlot];
//        this.dmg = dmgArray[arraySlot];

    }

    public int[] getMaxHPArray() {
        return maxHPArray;
    }

    public double[] getDodgeArray() {
        return dodgeArray;
    }

    public int[] getSpeedArray() {
        return speedArray;
    }

    public double[] getAccModArray() {
        return accModArray;
    }

    public double[] getCritModArray() {
        return critModArray;
    }

    public double[] getDmgArray() {
        return dmgArray;
    }

    
    
    public int getResolveLvl() {
        return resolveLvl;
    }

    public static boolean isReligious() {
        return religious;
    }

//    public int getMaxHP() {
//        return maxHP;
//    }
//
//    public double getDodge() {
//        return dodge;
//    }
//
//    public int getSpeed() {
//        return speed;
//    }
//
//    public double getAccMod() {
//        return accMod;
//    }
//
//    public double getCritMod() {
//        return critMod;
//    }
//
//    public double getDmg() {
//        return dmg;
//    }
//
//    public double getProt() {
//        return prot;
//    }

//    public double getStunRes() {
//        return stunRes;
//    }
//
//    public double getMoveRes() {
//        return moveRes;
//    }
//
//    public double getBlightRes() {
//        return blightRes;
//    }
//
//    public double getBleedRes() {
//        return bleedRes;
//    }
//
//    public double getDiseaseRes() {
//        return diseaseRes;
//    }
//
//    public double getDebuffRes() {
//        return debuffRes;
//    }
//
//    public double getDeathRes() {
//        return deathRes;
//    }
//
//    public double getTrapRes() {
//        return trapRes;
//    }

    public void selectAction(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
