/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Combat;
import darkestteam.Hero;
import darkestteam.HeroClass;

/**
 *
 * @author Tara
 */
public class Shieldbreaker implements HeroClass {
//
//    private int resolveLvl;
//    private String heroName;

    private final int[] maxHPArray = {20, 24, 28, 32, 36};
    private final double[] dodgeArray = {.075, .125, .175, .225, .275};
    private final int[] speedArray = {5, 6, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.075, .08, .085, .09, .095};
    private final double[] dmgArray = {7.5, 9, 10.5, 12, 13.5};

//    private static boolean religious = false;
    private double stunRes = 0.5;
    private double moveRes = 0.5;
    private double blightRes = 0.2;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double deathRes = 0.67;
    private double trapRes = 0.2;

    private Hero myHero;
    private Combat combat;

    public Shieldbreaker(Hero myHero) {
//
        this.myHero = myHero;
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
//        
//        System.out.println("Arbalest named " + heroName + " created. She has " + maxHP + " health.");
    }

    @Override
    public int[] getMaxHPArray() {
        return maxHPArray;
    }

    @Override
    public double[] getDodgeArray() {
        return dodgeArray;
    }

    @Override
    public int[] getSpeedArray() {
        return speedArray;
    }

    @Override
    public double[] getAccModArray() {
        return accModArray;
    }

    @Override
    public double[] getCritModArray() {
        return critModArray;
    }

    @Override
    public double[] getDmgArray() {
        return dmgArray;
    }

//    public static boolean isReligious() {
//        return religious;
//    }
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

    @Override
    public void resetSpecials() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void selectAction(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
