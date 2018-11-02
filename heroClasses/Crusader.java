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
import darkestteam.RandomFunctions;

/**
 *
 * @author Tara
 */
public class Crusader {

//    private int resolveLvl;
    private final int[] maxHPArray = {33, 40, 47, 54, 61};
    private final double[] dodgeArray = {.05, .10, .15, .20, .25};
    private final int[] speedArray = {1, 1, 2, 2, 3};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.05, .055, .06, .065, .07};
    private final double[] dmgArray = {9, 10.5, 12, 13, 14.5};

//    private final boolean religious = true;
    private Hero myHero;
    private Combat combat;

    private int smite;
    private int zealousAccusation;
    private int stunningBlow;
    private int bulwarkOfFaith;
    private int battleHeal;
    private int holyLance;
    private int inspiringCry;

    public Crusader(Hero myHero) {
        this.myHero = myHero;

        smite = myHero.getMove1Rank() - 1;
        zealousAccusation = myHero.getMove2Rank() - 1;
        stunningBlow = myHero.getMove3Rank() - 1;
        bulwarkOfFaith = myHero.getMove4Rank() - 1;
        battleHeal = myHero.getMove5Rank() - 1;
        holyLance = myHero.getMove6Rank() - 1;
        inspiringCry = myHero.getMove7Rank() - 1;
    }

//    public boolean isReligious() {
//        return religious;
//    }
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

    private void useSmite(Enemy target) {
        int amt = (int) myHero.getDmg();
        myHero.setAcc(.85 + .05 * smite);
        myHero.setCrit(0 + .01 * smite);

        if (Combat.tryAttackByHero(myHero, target)) {
            if (target.isUnholy()) {
                amt = (int) (amt * (1.15 + .05 * smite));
                combat.dmgEnemy(target, amt, myHero);
            } else {
                combat.dmgEnemy(target, amt, myHero);
            }
        }
    }

    private void useZealousAccusation() {
        int amt = (int) (myHero.getDmg() * (1 - .4));
        myHero.setAcc(.85 + .05 * zealousAccusation);
        myHero.setCrit(0 + .01 * zealousAccusation);

        combat.dmgEnemyMulti(1, 3, amt, myHero);
    }

    private void useStunningBlow() {
        //TODO add ability code here
    }

    private void useBulwarkOfFaith() {
        //TODO add ability code here
    }

    private void useBattleHeal(Hero target) {
        int battleHeal = myHero.getMove5Rank() - 1;
        int amt = RandomFunctions.getRandomNumberInRange(2 + 1 * battleHeal, 3 + 1 * battleHeal);
        //this is definitely not how the healing is actually calculated but short of
        //using a switch statement to set the amounts manually I don't know how to replicate it

        combat.healHero(myHero, target, amt);

    }

    private void useHolyLance() {
        //TODO add target selection to selectAction method, pass target as argument here
        myHero.setAcc(.85 + .05 * holyLance);
        myHero.setCrit(.065 + .01 * holyLance);
        int amt = (int) myHero.getDmg();

        Enemy target = ChooseTarget.chooseEnemy(3, 4);

        if (Combat.tryAttackByHero(myHero, target)) {
            if (target.isUnholy()) {
                amt = (int) (amt * (1.15 + .05 * holyLance));
            }
            combat.dmgEnemy(target, amt, myHero);
        }
        combat.moveSelf(myHero, -1);
    }

    private void useInspiringCry() {
        //TODO add ability code here

    }

    public void selectAction(Combat combat) {

        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //if someone is very low health and we have Battle Heal, use it
        if (battleHeal != -1) {
            Checker c = new Checker();
            Hero t = c.getLowestHealthHero(Combat.getHeroRoster());
            if (t.getCurHP() < t.getMaxHP() * .25) {
                useBattleHeal(t);
                return;
            }
        }

        //if in a back position, use Holy Lance
        if (myHero.getPosition() >= 3) {
            if (holyLance != -1) {
                useHolyLance();
                return;
            }
        }

        //if there is an Unholy target, use Smite
        if (myHero.getPosition() <= 2) {
            if (smite != -1) {
                if (pos1 != null) {
                    if (pos1.isUnholy()) {
                        useSmite(pos1);
                        return;
                    }
                } else if (pos2 != null) {
                    if (pos2.isUnholy()) {
                        useSmite(pos2);
                        return;
                    }
                }
            }
            
            //if there is not a priority target in the front line, then use Zealous accusation
            if (zealousAccusation != -1) {
                if (pos1 != null && pos2 != null) {
                    useZealousAccusation();
                    return;
                }
            }
            //else, smite a random valid target
            if (smite != -1) {
                Enemy t = ChooseTarget.chooseEnemy(1, 2);
                useSmite(t);
                return;
            }

        }
        //++++++++++++++++
        System.out.println("Crusader could not find a valid action.");
    }

}