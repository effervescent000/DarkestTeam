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
public class Antiquarian {

    private int resolveLvl;

    private final int[] maxHPArray = {17, 20, 23, 26, 29};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {5, 5, 6, 6, 7};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {1.025, 1.03, 1.035, 1.04, 1.045};
    private final double[] dmgArray = {4, 5, 5.5, 6.5, 7};

    private static boolean religious = false;

    private int maxHP;
    private double dodge;
    private int speed;
    private double accMod;
    private double critMod;
    private double dmg;
    private double prot;

    private double stunRes = 0.3;
    private double moveRes = 0.3;
    private double blightRes = 0.3;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double deathRes = 0.67;
    private double trapRes = 0.1;

    public Antiquarian(int resolveLvl) {

        this.resolveLvl = resolveLvl;

        int arraySlot = resolveLvl - 1;

        this.maxHP = maxHPArray[arraySlot];
        this.dodge = dodgeArray[arraySlot];
        this.speed = speedArray[arraySlot];
        this.accMod = accModArray[arraySlot];
        this.critMod = critModArray[arraySlot];
        this.dmg = dmgArray[arraySlot];

    }

    public int getResolveLvl() {
        return resolveLvl;
    }

    public static boolean isReligious() {
        return religious;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public double getDodge() {
        return dodge;
    }

    public int getSpeed() {
        return speed;
    }

    public double getAccMod() {
        return accMod;
    }

    public double getCritMod() {
        return critMod;
    }

    public double getDmg() {
        return dmg;
    }

    public double getProt() {
        return prot;
    }

    public double getStunRes() {
        return stunRes;
    }

    public double getMoveRes() {
        return moveRes;
    }

    public double getBlightRes() {
        return blightRes;
    }

    public double getBleedRes() {
        return bleedRes;
    }

    public double getDiseaseRes() {
        return diseaseRes;
    }

    public double getDebuffRes() {
        return debuffRes;
    }

    public double getDeathRes() {
        return deathRes;
    }

    public double getTrapRes() {
        return trapRes;
    }

    public void selectAction(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
    
}
