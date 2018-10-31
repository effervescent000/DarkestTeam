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
import darkestteam.ICombatMethods;
import darkestteam.Managers;
import static darkestteam.Managers.addHelpfulEffect;
import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public class Hellion implements ICombatMethods {

//    private int resolveLvl;
    private final int[] maxHPArray = {26, 31, 36, 41, 46};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {4, 4, 5, 5, 6};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.025, .03, .035, .04, .045};
    private final double[] dmgArray = {9, 10.5, 12, 13, 14.5};

    private static boolean religious = false;

    private Hero myHero;

    private Combat combat;

//    private Enemy target;
    public Hellion(int resolveLvl) {

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

    public void useWickedHack() {
        int rank = myHero.getMove1Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);

        Enemy target = ChooseTarget.chooseEnemy(1, 2);

        if (Combat.tryAttackByHero(myHero, target)) {
            combat.dmgEnemy(target, (int) myHero.getDmg(), myHero);
        }
    }

    public void useIronSwan() {
        int rank = myHero.getMove2Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);

        Enemy target = Combat.getEnemyInPosition(4);

        if (Combat.tryAttackByHero(myHero, target)) {
            combat.dmgEnemy(target, (int) myHero.getDmg(), myHero);
        }
    }

    public void useBarbaricYAWP() {

    }

    public void useIfItBleeds(Enemy target) {
        int rank = myHero.getMove4Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(0 + .01 * rank);

        if (Combat.tryAttackByHero(myHero, target)) {
            int amt = (int) (myHero.getDmg() * (1 - .35));
            combat.dmgEnemy(target, amt, myHero);
            myHero.setAcc(1 + .1 * rank);
            Managers.addBleed(target, 3, 2, myHero);
        }
    }

    public void useBreakthrough() {
        int rank = myHero.getMove5Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(-.01 + .01 * rank);

        int amt = (int) (myHero.getDmg() * (1 - .5));

        combat.dmgEnemyMulti(1, 3, amt, myHero);
        combat.moveSelf(myHero, -1);
        addHelpfulEffect(myHero, "Breakthrough", 3); // TODO check duration

    }

    private void useAdrenalineRush() {
        int rank = myHero.getMove6Rank() - 1;

        Managers.purgeBleeds(myHero);
        Managers.purgeBlights(myHero);
        combat.healHero(myHero, myHero, 1 + 1 * rank);
        addHelpfulEffect(myHero, "Adrenaline Rush", 3);
    }

    public void useBleedOut() {

    }

    @Override
    public void selectAction(Combat combat) {
        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Hellion"))).forEach((hero) -> {
            this.myHero = hero;
        });

        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //first use Adrenaline Rush if it isn't up
        if (myHero.getMove6Rank() >= 1 && !Checker.checkSpecificForDebuff(myHero, "Adrenaline Rush")) {
            useAdrenalineRush();
            return;
        }

        //first try Iron Swan
        if (myHero.getPosition() == 1) {
            if (myHero.getMove2Rank() >= 1) {
                if (pos4 != null) {
                    if (pos4.getProt() <= .25) {//TODO tweak this number
                        useIronSwan();
                        return;
                    }
                }
            }
        }
        //next try If It Bleeds
        if (myHero.getPosition() <= 3) {
            if (myHero.getMove4Rank() >= 1) {
                //target selection if both 2 and 3 exist
                //TODO include some kind of prioritzation for higher health targets to maximize DOT uptime
                if (pos2 != null && pos3 != null) {
                    ArrayList<Enemy> list = new ArrayList();
                    list.add(pos2);
                    list.add(pos3);
                    Enemy t;
                    Checker c = new Checker();
                    //if there is not a priority target based on danger, then pick the lower Bleed Res one
                    if (pos2.getDanger() == pos3.getDanger()) {
                        t = c.getLowestBleedResEnemy(list);
                        if (t.getBleedRes() < .3) { //TODO tweak this number
                            useIfItBleeds(t);
                            return;
                        }
                    } else {
                        t = c.getHighestDangerEnemy(list);
                        if (t.getBleedRes() < .3) { //TODO tweak this number
                            useIfItBleeds(t);
                            return;
                        }
                    }
                } else if (pos2 != null) {
                    //simpler target selection
                    if (pos2.getBleedRes() < .3) {
                        useIfItBleeds(pos2);
                        return;
                    }
                } else if (pos3 != null) {
                    if (pos3.getBleedRes() < .3) {
                        useIfItBleeds(pos3);
                        return;
                    }
                }
            }
        }
        //next try breakthrough
        if (myHero.getPosition() >= 2) {
            if (myHero.getMove5Rank() >= 1) {
                useBreakthrough();
                return;
            }
        }

        //finally: use Wicked Hack if nothing else is available
        if (myHero.getPosition() <= 2) {
            if (myHero.getMove1Rank() >= 1) {
                useWickedHack();
                return;
            }
        }

        //+++++++++++++++
        System.out.println("Hellion could not find a valid action.");

//        if (myHero.getMove2Rank() >= 1 && myHero.getPosition() == 1 && pos4 != null && pos4.getProt() <= .25) {
//            useIronSwan();
//        } else if ((combat.getEnemyMap().get(2) != null && combat.getEnemyMap().get(3) != null) && myHero.getMove4Rank() >= 1) {
//            //TODO also add something to the elseif ^^^ for "if I think these two enemies will live longer than 3 rounds (the duration of If It Bleeds)
//            if (combat.getEnemyMap().get(2).getBleedRes() < .4 && combat.getEnemyMap().get(3).getBleedRes() < .4) {
//                useIfItBleeds();
//            }
//        } else if (myHero.getPosition() <= 2) {
//            useWickedHack();
//        } else {
//            System.out.println("Hellion could not find a valid action.");
//        }
    }

}
