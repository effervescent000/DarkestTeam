/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import darkestteam.ChooseTarget;
import darkestteam.Combat;
import darkestteam.Debuff;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.Managers;
import darkestteam.RandomFunctions;

/**
 *
 * @author Tara
 */
public class Occultist {

//    private int resolveLvl;
    private final int[] maxHPArray = {19, 23, 27, 31, 35};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {6, 6, 7, 7, 8};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.075, .08, .085, .09, .095};
    private final double[] dmgArray = {5.5, 6.5, 8, 8.5, 10};

    private static boolean religious = false;

    private Hero myHero;
    private Combat combat;

    public Occultist(int resolveLvl) {

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

    public static boolean isReligious() {
        return religious;
    }

    private void useSacrificialStab(Enemy t) {
        int rank = myHero.getMove1Rank() - 1;
        myHero.setAcc(.8 + .05 * rank);
        myHero.setCrit(.1 + .01 * rank);
        int amt = (int) myHero.getMeleeDmg();

        if (t.isEldritch()) {
            amt = (int) (amt * (1.15 + .05 * rank));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);

        }
    }

    private void useAbyssalArtillery() {
        int rank = myHero.getMove2Rank() - 1;
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(0 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .33));

        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        if (pos3.isEldritch() && pos4.isEldritch()) {
            amt = (int) (amt * (1.15 + .05 * rank));
        }

        combat.dmgEnemyMulti(3, 4, amt, myHero);
    }

    private void useWeakeningCurse(Enemy t) {
        int rank = myHero.getMove3Rank() - 1;
        myHero.setAcc(.95 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .75));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addStatusEffect(t, "Weakening Curse", 3, myHero); //TODO check duration
        }

    }

    private void useWyrdReconstruction(Hero t) {
        int rank = myHero.getMove4Rank() - 1;
        int upper = (int) (13 + 2.25 * rank); //this is again not perfect but w/e
        int amt = RandomFunctions.getRandomNumberInRange(0, upper);

        combat.healHero(myHero, t, amt);

        //I'm basically just going to copy the bleed check code here b/c I don't want to make
        //a new hero-on-hero method for this single ability
        myHero.setAcc(.6 + .0625 * rank);
        if (Combat.calcHit(t.getBleedRes(), myHero.getAcc() + myHero.getAccMod())) {
            amt = (int) (1 + .5 * rank);
            t.getDebuffs().add(new Debuff("Bleed", 3, amt));
        }
    }

    private void useVulnerabilityHex(Enemy t) {
        int rank = myHero.getMove5Rank() - 1;
        myHero.setAcc(.95 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .9));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addStatusEffect(t, "Marked", 3, myHero); //TODO check duration
            myHero.setAcc(1 + .1 * rank);
            Managers.addStatusEffect(t, "Vulnerability Hex", 3, myHero); //TODO check duration
        }

    }

    private void useHandsFromTheAbyss(Enemy t) {

    }

    private void useDaemonsPull(Enemy t) {
        int rank = myHero.getMove7Rank() - 1;
        myHero.setAcc(.9 + .05 * rank);
        myHero.setCrit(.05 + .01 * rank);
        int amt = (int) (myHero.getRangedDmg() * (1 - .5));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * rank);
            combat.moveAttack(myHero, t, -2);
            //TODO add code for clearing corpses once I implement them
        }
    }

    public void selectAction(Combat combat) {
        this.combat = combat;

        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Occultist"))).forEach((hero) -> {
            this.myHero = hero;
        });

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //use Wyrd Recon if someone needs healing
        if (myHero.getMove4Rank() > 0) {
            Checker c = new Checker();
            Hero t = c.getLowestHealthHero(Combat.getHeroRoster());
            if (t.getCurHP() < t.getMaxHP() * .5) {
                useWyrdReconstruction(t);
                return;
            }
        }

        //use Abyssal Artillery if both backline targets are eldritch
        if (myHero.getMove2Rank() > 0) {
            if (myHero.getPosition() >= 3) {
                if (pos3 != null && pos4 != null) {
                    if (pos3.isEldritch() && pos4.isEldritch()) {
                        useAbyssalArtillery();
                        return;
                    }
                }
            }
        }

        //use Vulnerability Hex if another Hero can benefit from Mark
        //prioritize higher danger targets
        if (myHero.getMove5Rank() > 0) {
            if (Checker.checkPartyCompForMarked() > 1) {
                Checker c = new Checker();
                Enemy t = c.getHighestDangerEnemy(Combat.getEnemyRoster());
                if (t.getDanger() > 1) {
                    useVulnerabilityHex(t);
                    return;
                } else {
                    t = ChooseTarget.chooseEnemy(1, 4);
                    useVulnerabilityHex(t);
                    return;
                }
            }
        }

        //not exactly sure how to implement Hands from the Abyss rn
        //use Daemon's Pull if there is a backline target that's vulnerable to movement
        if (myHero.getMove7Rank() > 0) {
            if (myHero.getPosition() >= 2) {
                Checker c = new Checker();

                //the reason for doing this based on the whole roster, when the ability can only
                //target the backline, is to prevent the code from just shuffling the group forever.
                //instead it will prioritize the most dangerous enemy
                Enemy t = c.getMostMoveableEnemy(Combat.getEnemyRoster());
                if (t.getPosition() >= 3) {
                    if (t.getMoveable() > 1) { //tweak this number
                        useDaemonsPull(t);
                        return;
                    }
                }
            }

        }

        //use Weakening Curse against a high danger or high prot target
        if (myHero.getMove3Rank() > 0) {
            Checker c = new Checker();
            Enemy t = c.getHighestProtEnemy(Combat.getEnemyRoster());
            if (!Checker.checkSpecificForDebuff(t, "Weakening Curse")) {
                if (t.getProt() > .15 && t.getProt() < .6) {
                    useWeakeningCurse(t);
                    return;
                }
            }
            c = new Checker();
            t = c.getHighestDangerEnemy(Combat.getEnemyRoster());
            if (!Checker.checkSpecificForDebuff(t, "Weakening Curse")) {
                if (t.getDanger() > 1) {
                    useWeakeningCurse(t);
                    return;
                    //this isn't perfect b/c danger can also be based
                    //on stress/debuffs but it will do for now
                }
            }

        }

        //use Sacrificial Stab against an eldritch target
        if (myHero.getMove1Rank() > 0) {
            if (myHero.getPosition() <= 3) {
                Enemy t = Checker.checkEnemiesForType("Eldritch", Combat.getEnemyRoster());
                if (t != null) {
                    if (t.getPosition() <= 3) {
                        useSacrificialStab(t);
                        return;
                    }
                    //otherwise just use sacrificial stab on w/e
                    t = ChooseTarget.chooseEnemy(1, 3);
                    useSacrificialStab(t);
                    return;
                }
            }
        }

        //+++++++++++++++++
        System.out.println("Occultist could not find a valid action");
    }

}
