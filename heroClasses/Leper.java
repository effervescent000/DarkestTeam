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
import darkestteam.HeroClass;
import darkestteam.Managers;
import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public final class Leper implements HeroClass {

    private int resolveLvl;

    private final int[] maxHPArray = {35, 42, 49, 56, 63};
    private final double[] dodgeArray = {0, .05, .10, .15, .20};
    private final int[] speedArray = {2, 2, 3, 3, 4};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.025, .03, .035, .04, .045};
    private final double[] dmgArray = {12, 13.5, 15.5, 17.5, 19.5};

    private double stunRes = 0.6;
    private double moveRes = 0.6;
    private double blightRes = 0.4;
    private double bleedRes = 0.1;
    private double diseaseRes = 0.2;
    private double debuffRes = 0.4;
    private double deathRes = 0.67;
    private double trapRes = 0.1;

//    private static boolean religious = true;
    private Hero myHero;
    private Combat combat;

    private int chop;
    private int hew;
    private int purge;
    private int revenge;
    private int withstand;
    private int solemnity;
    private int intimidate;

    private int revengeUses;
    private int withstandUses;

    public Leper(Hero myHero) {
        this.myHero = myHero;

        chop = myHero.getMove1Rank() - 1;
        hew = myHero.getMove2Rank() - 1;
        purge = myHero.getMove3Rank() - 1;
        revenge = myHero.getMove4Rank() - 1;
        withstand = myHero.getMove5Rank() - 1;
        solemnity = myHero.getMove6Rank() - 1;
        intimidate = myHero.getMove7Rank() - 1;

        resetSpecials();

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

//    public int getResolveLvl() {
//        return resolveLvl;
//    }
    @Override
    public double getStunRes() {
        return stunRes;
    }

    @Override
    public double getTrapRes() {
        return trapRes;
    }

//    public boolean isReligious() {
//        return religious;
//    }
    @Override
    public void resetSpecials() {
        revengeUses = 0;
        withstandUses = 0;
    }

    public void useChop(Enemy t) {
        int amt = (int) myHero.getMeleeDmg();

        myHero.setAcc(.75 + .05 * chop);
        myHero.setCrit(.05 + .01 * chop);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
    }

    public void useHew() {
        int amt = (int) (myHero.getDmg() * .5);

        myHero.setAcc(.75 + .05 * hew);
        myHero.setCrit(-.04 + .01 * hew);

        combat.dmgEnemyMulti(1, 2, amt, myHero);
    }

    public void usePurge() {
        int amt = (int) (myHero.getDmg() * (1 - .4));

        myHero.setAcc(.85 + .05 * purge);
        myHero.setCrit(0 + .01 * purge);
        Enemy t = Combat.getEnemyInPosition(1);

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * purge);
            combat.moveAttack(myHero, t, 3);
            Managers.addHelpfulEffect(myHero, "Purge", 3, myHero);
        }
    }

    public void useRevenge() {
        //fake duration to make the buff last the entire fight
        Managers.addHelpfulEffect(myHero, "Revenge", 100, myHero);
        revengeUses++;
    }

    public void useWithstand() {
        Managers.addStatusEffect(myHero, "Marked", 3, null); //todo make sure this null doesn't cause issues
        Managers.addHelpfulEffect(myHero, "Withstand", 100, myHero);
        withstandUses++;
    }

    public void useSolemnity() {
        combat.healHero(myHero, myHero, (int) (6 + 1.5 * solemnity));
        myHero.setStressLvl(-5);
    }

    private void useIntimidate(Enemy t) {
        myHero.setAcc(.95 + .05 * intimidate);
        int amt;
        if (intimidate < 4) {
            amt = (int) (myHero.getMeleeDmg() * .15);
        } else {
            amt = (int) (myHero.getMeleeDmg() * .1);
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            myHero.setAcc(1 + .1 * intimidate);
            Managers.addStatusEffect(t, "Intimidate", 3, myHero);
            Managers.addStatusEffect(myHero, "Marked", 4, null); //todo make sure this null doesn't cause issues
            Managers.addHelpfulEffect(myHero, "Intimidate", 3, myHero);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //TODO figure out situations in which to use Revenge & Withstand & Intimidate
        //use Purge if the front-line enemy is high-danger
        if (purge != -1) {
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
        if (solemnity != -1) {
            if (myHero.getPosition() <= 2) {
                if (myHero.getCurHP() < myHero.getMaxHP() * .5 || myHero.getStressLvl() > 50) { //todo tweak this stress level
                    useSolemnity();
                    return;
                }
            }
        }
        //chop if there is a priority target in the front lines, otherwise hew
        if (myHero.getPosition() <= 2 && chop != -1 && hew != -1) {
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
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++
        System.out.println("Leper could not find a valid action.");
    }

}
