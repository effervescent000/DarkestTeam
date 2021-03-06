/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import darkestteam.ChooseTarget;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.HeroClass;

/**
 *
 * @author Tara
 */
public final class ManAtArms implements HeroClass {

//    private int resolveLvl;

    private final int[] maxHPArray = {31, 37, 43, 49, 55};
    private final double[] dodgeArray = {.05, .10, .15, .20, .25};
    private final int[] speedArray = {3, 3, 4, 4, 5};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.037, .043, .047, .053, .057};
    private final double[] dmgArray = {7.5, 9, 10, 11, 12};

    private double stunRes = 0.5;
    private double moveRes = 0.5;
    private double blightRes = 0.3;
    private double bleedRes = 0.4;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double deathRes = 0.67;
    private double trapRes = 0.1;

//    private static boolean religious = false;
    private Combat combat;
    private Hero myHero;

    private int bolsterUses;

    private int crush;
    private int rampart;
    private int bellow;
    private int defender;
    private int retribution;
    private int command;
    private int bolster;

    public ManAtArms(Hero myHero) {

        this.myHero = myHero;

        bolsterUses = 0;

        crush = myHero.getMove1Rank() - 1;
        rampart = myHero.getMove2Rank() - 1;
        bellow = myHero.getMove3Rank() - 1;
        defender = myHero.getMove4Rank() - 1;
        retribution = myHero.getMove5Rank() - 1;
        command = myHero.getMove6Rank() - 1;
        bolster = myHero.getMove7Rank() - 1;

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
    public double getMoveRes() {
        return moveRes;
    }

//    public int getResolveLvl() {
//        return resolveLvl;
//    }

//    public static boolean isReligious() {
//        return religious;
//    }

    @Override
    public double getStunRes() {
        return stunRes;
    }

    @Override
    public double getTrapRes() {
        return trapRes;
    }

    private void useCrush(Enemy t) {
        //todo fill out ability code here
    }

    private void useRampart(Enemy t) {
//todo fill out ability code here
    }

    private void useBellow(Enemy t) {
//todo fill out ability code here
    }

    private void useDefender(Hero t) {
//todo fill out ability code here
    }

    private void useRetribution(Enemy t) {
//todo fill out ability code here
    }

    private void useCommand() {
//todo fill out ability code here
    }

    private void useBolster() {
//todo fill out ability code here
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //use Defender to protect squishies (maybe if there is one in particular who gets low?
        if (defender != -1) {
            if (!Checker.amIGuarding(myHero)) {
                /**
                 * I'm not sure how to pick this. Based on class first maybe (to
                 * prioritize healers or squishier classes, or lowest max
                 * health, or lowest current health?
                 */
            }
        }

        //use Command if the buff isn't active
        if (command != -1) {
            if (!Checker.checkSpecificForDebuff(myHero, "Command")) {
                useCommand();
                return;
            }
        }

        //use Bolster if the buff isn't active
        if (bolster != -1) {
            if (bolsterUses == 0) {
                useBolster();
                return;
            }
        }

        //use Bellow if I guess there is a moderate or higher danger enemy who is dodgy?
        //use Retribution if the group can benefit from Marked. Also have to figure out how this works as Riposte
        //use Rampart if nothing else has fired and we want to move forward to Crush
        if (rampart != -1) {
            if (myHero.getPosition() == 3) {
                Enemy t = ChooseTarget.chooseEnemy(1, 2);
                useRampart(t);
                return;
            }
        }

        //use Crush if nothing else available
        if (crush != -1) {
            if (myHero.getPosition() <= 2) {
                Enemy t = ChooseTarget.chooseEnemy(1, 3);
                useCrush(t);
                return;
            }
        }

        //++++++++++++++++++
        System.out.println("Man-At-Arms could not find a valid action");
    }

    @Override
    public void resetSpecials() {
        bolsterUses = 0;
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

}
