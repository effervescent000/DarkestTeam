/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.Managers;
import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public class Leper {

    private int resolveLvl;

    private final int[] maxHPArray = {35, 42, 49, 56, 63};
    private final double[] dodgeArray = {0, .05, .10, .15, .20};
    private final int[] speedArray = {2, 2, 3, 3, 4};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.025, .03, .035, .04, .045};
    private final double[] dmgArray = {12, 13.5, 15.5, 17.5, 19.5};

    private static boolean religious = true;

    private Hero myHero;
    private Combat combat;

    public Leper(int resolveLvl) {
//        
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

    public boolean isReligious() {
        return religious;
    }

    public void useChop(Enemy t) {

        int rank = myHero.getMove1Rank() - 1;
        int amt = (int) myHero.getMeleeDmg();

        myHero.setAcc(.75 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
    }

    public void useHew() {
        int rank = myHero.getMove2Rank() - 1;
        int amt = (int) (myHero.getDmg() * .5);

        myHero.setAcc(.75 + .05 * rank);
        myHero.setCrit(-.04 + .01 * rank);

        combat.dmgEnemyMulti(1, 2, amt, myHero);
    }

    public void usePurge() {
        int rank = myHero.getMove3Rank() - 1;
        int amt = (int) (myHero.getDmg() * (1 - .4));

        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(0 + .01 * rank);
        Enemy t = Combat.getEnemyInPosition(1);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * rank);
            combat.moveAttack(myHero, t, 3);
            Managers.addHelpfulEffect(myHero, "Purge", 3); //TODO check duration
        }
    }

    public void useRevenge() {

    }

    public void useWithstand() {

    }

    public void useSolemnity() {

    }

    private void useIntimidate() {

    }

    public void selectAction(Combat combat) {
        this.combat = combat;

        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Leper"))).forEach((hero) -> {
            this.myHero = hero;
        });

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //use Purge if the front-line enemy is high-danger
        if (myHero.getMove3Rank() > 0) {
            if (myHero.getPosition() == 1) {
                if (pos1 != null) {
                    if (pos1.getDanger() > 1) { //tweak this number
                        usePurge();
                        return;
                    }
                }
            }
        }
        //if the leper's health is low and/or his stress is high
        if (myHero.getMove6Rank() >= 1) {
            if (myHero.getPosition() <= 2) {
                if (myHero.getCurHP() < myHero.getMaxHP() * .5 || myHero.getStressLvl() > .7) {
                    useSolemnity();
                    return;
                }
            }
        }
        //chop if there is a priority target in the front lines, otherwise hew
        if (myHero.getPosition() <= 2 && myHero.getMove1Rank() >= 1 && myHero.getMove2Rank() >= 1) {
            if (pos1 != null && pos2 != null) {
                ArrayList<Enemy> list = new ArrayList();
                list.add(pos1);
                list.add(pos2);
                Checker c = new Checker();
                Enemy t = c.getHighestDangerEnemy(list);
                if (t.getDanger() > 1) {
                    useChop(t);
                    return;
                }
                //chop if you can one-shot a target with it but not with hew
                if (pos1.getCurHP() > myHero.getDmg() * .5 && pos1.getCurHP() < myHero.getDmg()) {
                    useChop(pos1);
                    return;
                }
                if (pos2.getCurHP() > myHero.getDmg() * .5 && pos2.getCurHP() < myHero.getDmg()) {
                    useChop(pos2);
                    return;
                }
                useHew();
                return;
            }
        }

        System.out.println("Leper could not find a valid action.");
    }

}
