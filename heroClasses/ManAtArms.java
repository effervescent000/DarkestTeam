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

/**
 *
 * @author Tara
 */
public class ManAtArms {

    private int resolveLvl;
//    private String heroName;

    private final int[] maxHPArray = {31, 37, 43, 49, 55};
    private final double[] dodgeArray = {.05, .10, .15, .20, .25};
    private final int[] speedArray = {3, 3, 4, 4, 5};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.037, .043, .047, .053, .057};
    private final double[] dmgArray = {7.5, 9, 10, 11, 12};

    private static boolean religious = false;

    private Combat combat;
    private Hero myHero;

    private int bolsterUses;

    public ManAtArms(int resolveLvl) {

        bolsterUses = 0;

    }

    public int getResolveLvl() {
        return resolveLvl;
    }

    public static boolean isReligious() {
        return religious;
    }

    private void useCrush(Enemy t) {

    }

    private void useRampart(Enemy t) {

    }

    private void useBellow(Enemy t) {

    }

    private void useDefender(Hero t) {

    }

    private void useRetribution(Enemy t) {

    }

    private void useCommand() {

    }

    private void useBolster() {

    }

    public void selectAction(Combat combat) {
        this.combat = combat;

        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Man-At-Arms"))).forEach((hero) -> {
            this.myHero = hero;
        });

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        int crush = myHero.getMove1Rank() - 1;
        int rampart = myHero.getMove2Rank() - 1;
        int bellow = myHero.getMove3Rank() - 1;
        int defender = myHero.getMove4Rank() - 1;
        int retribution = myHero.getMove5Rank() - 1;
        int command = myHero.getMove6Rank() - 1;
        int bolster = myHero.getMove7Rank() - 1;

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

    public void resetSpecials() {
        bolsterUses = 0;
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
