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
import darkestteam.Managers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tara
 */
public class Highwayman {

//    private int resolveLvl;
//    private String heroName;
    private final int[] maxHPArray = {23, 28, 33, 38, 43};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {5, 5, 6, 6, 7};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.05, .055, .06, .065, .07};
    private final double[] dmgArray = {7.5, 9, 10, 11.5, 12.5};

    private static boolean religious = false;

    private Hero myHero;
    private Combat combat;

    public Highwayman(int resolveLvl) {

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

    public static boolean isReligious() {
        return religious;
    }

    private void useWickedSlice(Enemy t) {
        int rank = myHero.getMove1Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);
        int amt = (int) (myHero.getMeleeDmg() * 1.15);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }

    }

    private void usePistolShot(Enemy t) {
        int rank = myHero.getMove2Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(.09 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .15));

        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            //this ability has a damage buff that does not scale linearly with rank so I'm fudging it
            amt = (int) (amt + (6.25 * rank));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
    }

    private void usePointBlankShot(Enemy t) {
        int rank = myHero.getMove3Rank() - 1;
        myHero.setAcc(.95 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * 1.5);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * rank);
            combat.moveAttack(myHero, t, 1);
            combat.moveSelf(myHero, 1);
        }
    }

    private void useGrapeshotBlast() {
        int rank = myHero.getMove4Rank() - 1;
        myHero.setAcc(.75 + .05 * rank);
        myHero.setCrit(-.09 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .5));

        ArrayList<Enemy> hits = combat.dmgEnemyMulti(1, 3, amt, myHero);
        //TODO figure out how to deal with the debuff this applies (+x% crits received). I guess the best way would be
        //to put something in the calcCrit checker to give the attacker a bonus if the tagret has this debuff?
    }

    private void useTrackingShot() {
        int rank = myHero.getMove5Rank() - 1;
        myHero.setAcc(.95 + .05 * rank);
        myHero.setCrit(0 + .025 * rank); //this is not quite accurate but idc
        int amt = (int) (myHero.getRangedDmg() * (1 - .8));

        Enemy t = ChooseTarget.chooseEnemy(2, 4);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
        //I don't know what happens if the ability misses, for now I am assuming that the buff
        //still applies and so I'm putting it outside of the miss check
        Managers.addHelpfulEffect(myHero, "Tracking Shot", 1000);

    }

    private void useDuelistsAdvance() {

    }

    private void useOpenVein(Enemy t) {
        int rank = myHero.getMove7Rank() - 1;
        myHero.setAcc(.95 + .05 * rank);
        myHero.setCrit(0 + .01 * rank); //this is not quite accurate but idc
        int amt = (int) (myHero.getMeleeDmg() * (1 - .15));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * rank);
            amt = (int) (2 + .5 * rank);
            Managers.addBleed(t, 3, amt, myHero);
            Managers.addStatusEffect(t, "Open Vein", 3, myHero); //TODO check duration
        }
    }

    public void selectAction(Combat combat) {
        this.combat = combat;

        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Highwayman"))).forEach((hero) -> {
            this.myHero = hero;
        });

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //use tracking shot for the buff
        //TODO add code to select for stealthed targets once I implement stealth lol
        if (myHero.getMove5Rank() > 0) {
            /**
             * this ability can only be used once per battle, but I'm not
             * tracking that specifically instead I'm using the buff it applies
             * as a proxy since it falls off at the end of combat
             */
            if (!Checker.checkSpecificForDebuff(myHero, "Tracking Shot")) {
                //not choosing a specific target right now. call a chooseEnemy inside the ability method
                useTrackingShot();
                return;
            }
        }

        //use Open Vein if all available targets have high prot
        if (myHero.getMove7Rank() > 0) {
            if (myHero.getPosition() <= 3) {
                Checker c = new Checker();
                Enemy t = c.getLowestProtEnemy(Combat.getEnemyRoster());
                if (t != null) { //I don't know why ^^^^ returns null sometimes but it does
                    if (t.getProt() > .25) {
                        if (t.getPosition() <= 2) {
                            /**
                             * setting the bleed res check higher than usual b/c
                             * the ability itself applies a res debuff so if we
                             * get it through then subsequent applications
                             * becomes easier
                             */
                            if (t.getBleedRes() < .5) {
                                useOpenVein(t);
                                return;
                            }
                        }
                    }
                }
            }
        }

        //use Point Blank Shot if hero is in position 1
        if (myHero.getPosition() == 1) {
            if (myHero.getMove3Rank() > 0) {
                usePointBlankShot(pos1);
                return;
            }
        }

        //use Open Vein if available targets have only moderate bleed res
        if (myHero.getMove7Rank() > 0) {
            if (myHero.getPosition() <= 3) {
                Checker c = new Checker();
                List<Enemy> list = Arrays.asList(pos1, pos2);
                Enemy t = c.getLowestBleedResEnemy(list);
                if (t != null) {
                    if (t.getBleedRes() < .55) { //based on napkin math, bleed res above this makes this worse than grape shot
                        useOpenVein(t);
                        return;
                    }
                }
            }
        }

        //use grapeshot blast for aoe
        if (myHero.getMove4Rank() > 0) {
            if (myHero.getPosition() == 2 || myHero.getPosition() == 3) {
                if (Combat.getEnemyRoster().size() >= 3) {
                    useGrapeshotBlast();
                    return;
                }
            }
        }

        //if there is a marked target, then use Pistol Shot
        if (myHero.getMove2Rank() > 0) {
            if (myHero.getPosition() >= 2) {
                Enemy t = Checker.checkEnemiesForDebuff("Marked", Combat.getEnemyRoster());
                if (t != null) {
                    usePistolShot(t);
                    return;
                }
            }
        }

        //use Wicked Slice if nothing better is available
        if (myHero.getMove1Rank() > 0) {
            if (myHero.getPosition() <= 3) {
                List<Enemy> list = Arrays.asList(pos1, pos2);
                Checker c = new Checker();
                Enemy t = c.getLowestProtEnemy(list);
                useWickedSlice(t);
                return;
            }
        }

        //use Open Vein if nothing better is available
        if (myHero.getMove7Rank() > 0) {
            if (myHero.getPosition() <= 3) {
                Enemy t = ChooseTarget.chooseEnemy(1, 2);
                useOpenVein(t);
                return;
            }
        }

        //use Pistol Shot if nothing better is available
        if (myHero.getMove2Rank() > 0) {
            if (myHero.getPosition() >= 2) {
                Enemy t = ChooseTarget.chooseEnemy(2, 4);
                usePistolShot(t);
                return;
            }
        }

        //TODO I'm not sure where Duelist's Advance should go here or how it works, need
        //to playtest
        //+++++++++++++++++++++
        System.out.println("Highwayman could not find a valid action");
    }

}
