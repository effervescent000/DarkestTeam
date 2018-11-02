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
import static darkestteam.Checker.checkEnemiesForDebuff;

/**
 *
 * @author Tara
 */
public class BountyHunter {

    private final int[] maxHPArray = {25, 30, 35, 40, 45};
    private final double[] dodgeArray = {.05, .10, .15, .20, .25};
    private final int[] speedArray = {5, 5, 6, 6, 7};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.05, .055, .06, .065, .07};
    private final double[] dmgArray = {7.5, 9, 10, 11, 12};

    private static boolean religious = false;

    private Hero myHero;
    private Combat combat;

    private int collectBounty;
    private int markForDeath;
    private int comeHither;
    private int uppercut;
    private int flashbang;
    private int finishHim;
    private int caltrops;

    public BountyHunter(Hero myHero) {
        this.myHero = myHero;

        collectBounty = myHero.getMove1Rank() - 1;
        markForDeath = myHero.getMove2Rank() - 1;
        comeHither = myHero.getMove3Rank() - 1;
        uppercut = myHero.getMove4Rank() - 1;
        flashbang = myHero.getMove5Rank() - 1;
        finishHim = myHero.getMove6Rank() - 1;
        caltrops = myHero.getMove7Rank() - 1;

    }

    public static boolean isReligious() {
        return religious;
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

    private void useCollectBounty(Enemy t) {
        myHero.setAcc(.85 + .05 * collectBounty);
        myHero.setCrit(.075 + .01 * collectBounty);
        int amt = (int) myHero.getDmg();

        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            amt = (int) (amt * 1.9);
        }
        if (t.isHuman()) {
            amt = (int) (amt * (1.2 + .05 * collectBounty));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }

    }

    private void useMarkForDeath(Enemy t) {
        myHero.setAcc(1 + .05 * markForDeath);
//        myHero.setCrit(0);
//        int amt = 0;

        if (Combat.tryAttackByHero(myHero, t)) {
            Managers.addStatusEffect(t, "Marked", 3, myHero); // TODO check duration
            Managers.addStatusEffect(t, "Mark For Death", 3, myHero); //TODO check duration
            Managers.addHelpfulEffect(myHero, "Mark For Death", 3); //TODO check duration
        }

    }

    private void useComeHither(Enemy t) {
        myHero.setAcc(.9 + .05 * comeHither);
        myHero.setCrit(0 + .01 * comeHither);
        int amt = (int) (myHero.getRangedDmg() * (1 - .67));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addStatusEffect(t, "Marked", 3, myHero); //TODO check duration
            myHero.setAcc(1 + .1 * comeHither);
            combat.moveAttack(myHero, t, -2);
        }

    }

    private void useUppercut(Enemy t) {
        myHero.setAcc(.9 + .05 * uppercut);
        myHero.setCrit(0 + .01 * uppercut);
        int amt = (int) (myHero.getDmg() * .33);
        
        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * uppercut);
            combat.moveAttack(myHero, t, 2);
            Managers.addStatusEffect(t, "Stun", 1, myHero); //TODO check duration
        }

    }

    private void useFlashbang(Enemy t) {

    }

    private void useFinishHim(Enemy t) {
//        int rank = myHero.getMove6Rank() - 1;
        myHero.setAcc(.85 + .05 * finishHim);
        myHero.setCrit(.05 + .01 * finishHim);
        int amt = (int) myHero.getMeleeDmg();

        //this should only be used against stunned targets but I still feel obligated to double-check
        if (Checker.checkSpecificForDebuff(t, "Stun")) {
            amt = (int) (amt * (1.25 + .0875 * finishHim)); //this is not an accurate representation of the scaling here but idc
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
    }

    private void useCaltrops() {

    }

    public void selectAction(Combat combat) {
        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        Enemy marked = checkEnemiesForDebuff("Marked", Combat.getEnemyRoster());

        Checker c = new Checker();

        //use Mark For Death if another hero can benefit from the mark (not sure the prot debuff is enough to merit using solely for that)
        if (markForDeath != -1) {
            if (marked == null) {
                Enemy t;
                if (Checker.checkPartyCompForMarked() > 1) {
                    t = c.getHighestDangerEnemy(Combat.getEnemyRoster());
//                    //only cast if the target doesn't have absurdly high prot
//                    if (t.getProt() < .75) {
                    useMarkForDeath(t);
                    return;
//                    }
                }
            }
        }

        //use Come Hither if there is a backline target that's vulnerable to movement
        if (comeHither != -1) {
            //the reason for doing this based on the whole roster, when the ability can only
            //target the backline, is to prevent the code from just shuffling the group forever.
            //instead it will prioritize the most dangerous enemy
            Enemy t = c.getMostMoveableEnemy(Combat.getEnemyRoster());
            if (t.getPosition() >= 3) {
                if (t.getMoveable() > 1) { //tweak this number
                    useComeHither(t);
                    return;
                }
            }
        }

        //use uppercut if there is a frontline target that's vulnerable to movement
        if (uppercut != -1 && myHero.getPosition() <= 2) {
            //TODO ensure that if both this and Come Hither are enabled, they don't just alternate and move the same mob back and forth forever
            Enemy t = c.getMostMoveableEnemy(Combat.getEnemyRoster());
            if (t != null) {
                if (t.getPosition() <= 2 && t.getDanger() > 1) {
                    useUppercut(t);
                    return;
                }
            }
        }

        //use Finish Him against a stunned target
        if (finishHim != -1 && myHero.getPosition() <= 3) {
            ArrayList list = new ArrayList();
            list.add(pos1);
            list.add(pos2);
            list.add(pos3);
            Enemy t = checkEnemiesForDebuff("Stun", list); //TODO check syntax, is it Stun elsewhere or Stunned?
            if (t != null) {
                useFinishHim(t);
                return;
            }
        }

        //use Collect Bounty if one of the two frontline targets is marked; if there is no marked target
        //then just pick a collect Bounty target
        if (collectBounty != -1 && myHero.getPosition() <= 3) {
            if (marked != null) {
                useCollectBounty(marked);
                return;
            } else {
                ArrayList<Enemy> l = new ArrayList<>();
                l.add(pos1);
                l.add(pos2);
                l.add(pos3);
                Enemy t = c.getLowestHealthEnemy(l);
                if (t != null) {
                    useCollectBounty(t);
                    return;
                }
            }
        }

        //++++++++++++++++++
        System.out.println("Bounty Hunter couldn't find a valid action");
    }

}