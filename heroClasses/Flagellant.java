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
public class Flagellant {
    
    //todo literally everything with the flagellant

    private int resolveLvl;

    private final int[] maxHPArray = {22, 26, 30, 34, 38};
    private final double[] dodgeArray = {0, .05, .10, .15, .20};
    private final int[] speedArray = {6, 6, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.025, .03, .035, .04, .045};
    private final double[] dmgArray = {4.5, 5.5, 6, 7.5, 8};

//    private static boolean religious = true;

    private Hero myHero;
    private Combat combat;

    public Flagellant(Hero myHero) {

        this.myHero = myHero;

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

//    public static boolean isReligious() {
//        return religious;
//    }

    public void selectAction(Combat combat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
