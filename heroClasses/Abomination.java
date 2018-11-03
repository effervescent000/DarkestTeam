/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Combat;
import darkestteam.Hero;

/**
 *
 * @author Tara
 */
public class Abomination {

    private int resolveLvl;

    private final int[] maxHPArray = {26, 31, 36, 41, 46};
    private final double[] dodgeArray = {.075, .125, .175, .225, .275};
    private final int[] speedArray = {7, 7, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {1.025, 1.03, 1.035, 1.04, 1.045};
    private final double[] dmgArray = {8.5, 10, 11.5, 14, 15.5};

//    private final static boolean religious = false;
    private int maxHP;
    private double dodge;
    private int speed;
    private double accMod;
    private double critMod;
    private double dmg;
    private double prot;

    private double stunRes = 0.4;
    private double moveRes = 0.4;
    private double blightRes = 0.6;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.2;
    private double debuffRes = 0.2;
    private double deathRes = 0.67;
    private double trapRes = 0.1;

    private Hero myHero;
    private Combat combat;

    public Abomination(Hero myHero) {

        this.myHero = myHero;

    }

    public int getResolveLvl() {
        return resolveLvl;
    }

//    public static boolean isReligious() {
//        return religious;
//    }
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

}
