/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import static darkestteam.Checker.checkEnemiesForType;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.Managers;
import java.util.ArrayList;
import static darkestteam.Checker.checkEnemiesForDebuff;
import darkestteam.ChooseTarget;
import darkestteam.HeroClass;

/**
 *
 * @author Tara
 */
public class HoundMaster implements HeroClass {

//    private int resolveLvl;
    private final int[] maxHPArray = {21, 25, 29, 33, 37};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {6, 6, 7, 7, 8};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.05, .055, .06, .065, .07};
    private final double[] dmgArray = {5.5, 6.5, 8, 8.5, 10};

    private double stunRes = 0.4;
    private double moveRes = 0.4;
    private double blightRes = 0.4;
    private double bleedRes = 0.4;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double deathRes = 0.67;
    private double trapRes = 0.4;

//    private static boolean religious = false;
    private Hero myHero;
    private Combat combat;

    private int houndsRush;
    private int houndsHarry;
    private int targetWhistle;
    private int cryHavoc;
    private int guardDog;
    private int lickWounds;
    private int blackjack;

    public HoundMaster(Hero myHero) {
        this.myHero = myHero;

        houndsRush = myHero.getMove1Rank() - 1;
        houndsHarry = myHero.getMove2Rank() - 1;
        targetWhistle = myHero.getMove3Rank() - 1;
        cryHavoc = myHero.getMove4Rank() - 1;
        guardDog = myHero.getMove5Rank() - 1;
        lickWounds = myHero.getMove6Rank() - 1;
        blackjack = myHero.getMove7Rank() - 1;

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

    @Override
    public void resetSpecials() {
        System.out.println("Hound Master has no specials configured");
    }

    private void useHoundsRush(Enemy t) {

        myHero.setAcc(.85 + .05 * houndsRush);
        myHero.setCrit(-.03 + .01 * houndsRush);

        int amt = (int) myHero.getRangedDmg();

        if (t.isBeast()) {
            amt = (int) (amt * (1.15 + .05 * houndsRush));
        }
        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            amt = (int) (amt * (1.6 + .1 * houndsRush));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * houndsRush);
            amt = (int) (1 + .34 * houndsRush);
            Managers.addBleed(t, 3, amt, myHero);
        }

    }

    private void useHoundsHarry() {

        myHero.setAcc(.85 + .05 * houndsHarry);
        myHero.setCrit(-.05 + .01 * houndsHarry);
        int amt = (int) (myHero.getRangedDmg() * (1 - .75));

        ArrayList<Enemy> hits = combat.dmgEnemyMulti(1, 4, amt, myHero);
        myHero.setAcc(1 + .1 * houndsHarry);
        hits.stream().forEach((Enemy e) -> {
            Managers.addBleed(e, 3, (int) (1 + .5 * houndsHarry), myHero);
        });
    }

    private void useTargetWhistle(Enemy t) {

        myHero.setAcc(1 + .05 * targetWhistle);

        if (Combat.tryAttackByHero(myHero, t)) {
            //this deals no damage
            myHero.setAcc(1.3 + .1 * targetWhistle);
            Managers.addStatusEffect(t, "Marked", 3, myHero);
            Managers.addStatusEffect(t, "Target Whistle", 3, myHero);
        }
    }

    private void useCryHavoc() {
        //TODO fill out ability code here
    }

    private void useGuardDog() {
        //TODO fill out ability code here
    }

    private void useLickWounds() {

        int amt = 4 + 1 * lickWounds;

        combat.healHero(myHero, myHero, amt);
    }

    private void useBlackjack() {
//TODO fill out ability code here
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        //use target whistle if there's another hero that can benefit from the mark OR
        //if there is a high prot target
        if (targetWhistle != -1) {
            if (checkEnemiesForDebuff("Marked", Combat.getEnemyRoster()) == null) {
                Enemy t;
                Checker c = new Checker();
                if (Checker.checkPartyCompForMarked() > 1) {
                    t = c.getHighestProtEnemy(Combat.getEnemyRoster());
                    //only cast if the target doesn't have absurdly high prot
                    if (t != null) {
                        if (t.getProt() < .75) {
                            useTargetWhistle(t);
                            return;
                        }
                    }

                } else {
                    t = c.getHighestProtEnemy(Combat.getEnemyRoster());
                    //don't bother casting this if the prot is too low or too high
                    if (t != null) {
                        if (t.getProt() < .75 && t.getProt() > .15 + .25 * targetWhistle) {
                            useTargetWhistle(t);
                            return;
                        }
                    }

                }
            }
        }

        //use Lick Wounds if low health
        if (lickWounds != -1) {
            if (myHero.getPosition() >= 2) {
                if (myHero.getCurHP() < myHero.getMaxHP() * .4) {
                    useLickWounds();
                    return;
                }
            }
        }

        //try to use Hound's Harry if (at least 3) targets are vulnerable to bleed
        if (Combat.getEnemyRoster().size() == 4) {
            if (houndsHarry != -1) {
                int count = 0;
                for (Enemy e : Combat.getEnemyRoster()) {
                    if (e.getBleedRes() < .4) {
                        count++;
                    }
                }
                if (count >= 3) {
                    useHoundsHarry();
                    return;
                }
            }
        }

        //try Hound's Rush on a marked target, otherwise on a beast, otherwise on a random target
        if (houndsRush != -1) {
            if (myHero.getPosition() >= 2) {
                Enemy t = checkEnemiesForDebuff("Marked", Combat.getEnemyRoster());
                if (t != null) {
                    useHoundsRush(t);
                    return;
                } else {
                    t = checkEnemiesForType("beast", Combat.getEnemyRoster());
                    if (t != null) {
                        useHoundsRush(t);
                        return;
                    } else {
                        t = ChooseTarget.chooseEnemy(1, 4);
                        useHoundsRush(t);
                        return;
                    }
                }
            }
        }
        
        //otherwise we need to move
        if (myHero.getPosition() == 1) {
            combat.moveSelf(myHero, 1);
            return;
        } else if (myHero.getPosition() == 4) {
            combat.moveSelf(myHero, -1);
            return;
        }

        //++++++++++++++++++
        System.out.println("Hound Master could not find a valid action");
    }

}
