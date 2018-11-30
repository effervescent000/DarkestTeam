/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.HeroClass;

/**
 *
 * @author Tara
 */
public class Jester implements HeroClass {

    private int resolveLvl;

    private final int[] maxHPArray = {19, 23, 27, 31, 35};
    private final double[] dodgeArray = {.15, .20, .25, .30, .35};
    private final int[] speedArray = {7, 7, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.075, .08, .085, .09, .095};
    private final double[] dmgArray = {5.5, 6.5, 8, 8.5, 10};

    private double stunRes = 0.2;
    private double moveRes = 0.2;
    private double blightRes = 0.4;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.2;
    private double debuffRes = 0.4;
    private double deathRes = 0.67;
    private double trapRes = 0.3;

//    private static boolean religious = false;
    private Combat combat;
    private Hero myHero;

    private int dirkStab;
    private int harvest;
    private int finale;
    private int solo;
    private int sliceOff;
    private int battleBallad;
    private int inspiringTune;

    public Jester(Hero myHero) {
        this.myHero = myHero;

        dirkStab = myHero.getMove1Rank() - 1;
        harvest = myHero.getMove2Rank() - 1;
        finale = myHero.getMove3Rank() - 1;
        solo = myHero.getMove4Rank() - 1;
        sliceOff = myHero.getMove5Rank() - 1;
        battleBallad = myHero.getMove6Rank() - 1;
        inspiringTune = myHero.getMove7Rank() - 1;

    }

    @Override
    public double getBleedRes() {
        return bleedRes;
    }

    @Override
    public double getBlightRes() {
        return blightRes;
    }

    @Override
    public double getDeathRes() {
        return deathRes;
    }

    @Override
    public double getDebuffRes() {
        return debuffRes;
    }

    @Override
    public double getDiseaseRes() {
        return diseaseRes;
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
    public double getMoveRes() {
        return moveRes;
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
//
//    public int getResolveLvl() {
//        return resolveLvl;
//    }

    @Override
    public double getStunRes() {
        return stunRes;
    }

    @Override
    public double getTrapRes() {
        return trapRes;
    }

    @Override
    public void resetSpecials() {
        System.out.println("Jester has no specials configured");
    }

//    public static boolean isReligious() {
//        return religious;
//    }
    private void useDirkStab(Enemy t) {
        //todo add ability code here
    }

    private void useHarvest() {

    }

    private void useFinale(Enemy t) {
        //todo add ability code here
    }

    private void useSolo() {
        //todo add ability code here
    }

    private void useSliceOff(Enemy t) {
        //todo add ability code here   
    }

    private void useBattleBallad() {
        //todo add ability code here   
    }

    private void useInspiringTune(Hero t) {
        //todo add ability code here   
    }

    @Override
    public void selectAction(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
