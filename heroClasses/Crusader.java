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

    private final boolean religious = true;

//    private double stunRes = 0.4;
//    private double moveRes = 0.4;
//    private double blightRes = 0.3;
//    private double bleedRes = 0.3;
//    private double diseaseRes = 0.3;
//    private double debuffRes = 0.3;
//    private double deathRes = 0.67;
//    private double trapRes = 0.1;

    private Hero myHero;
    private Combat combat;

    public Crusader(int resolveLvl) {
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

//    public int getResolveLvl() {
//        return resolveLvl;
//    }

    public boolean isReligious() {
        return religious;
    }

//    public double getStunRes() {
//        return stunRes;
//    }
//
//    public double getMoveRes() {
//        return moveRes;
//    }
//
//    public double getBlightRes() {
//        return blightRes;
//    }
//
//    public double getBleedRes() {
//        return bleedRes;
//    }
//
//    public double getDiseaseRes() {
//        return diseaseRes;
//    }
//
//    public double getDebuffRes() {
//        return debuffRes;
//    }
//
//    public double getDeathRes() {
//        return deathRes;
//    }
//
//    public double getTrapRes() {
//        return trapRes;
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
    
    

    public void useSmite(Enemy target) {
        int rank = myHero.getMove1Rank() - 1;
        int amt = (int) myHero.getDmg();
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(0 + .01 * rank);

        if (Combat.tryAttackByHero(myHero, target)) {
            if (target.isUnholy()) {
                amt = (int) (amt * (1.15 + .05 * rank));
                combat.dmgEnemy(target, amt, myHero);
            } else {
                combat.dmgEnemy(target, amt, myHero);
            }
        }
    }

    public void useZealousAccusation() {
        int rank = myHero.getMove2Rank() - 1;
        int amt = (int) (myHero.getDmg() * (1 - .4));
        myHero.setAcc(.85 + .05 * rank);
        myHero.setCrit(0 + .01 * rank);

        combat.dmgEnemyMulti(1, 3, amt, myHero);
    }

    public void useStunningBlow(Combat combat) {

    }

    public void useBulwarkOfFaith(Combat combat) {

    }

    public void useBattleHeal(Hero target) {
        int rank = myHero.getMove5Rank() - 1;
        int amt = RandomFunctions.getRandomNumberInRange(2 + 1 * rank, 3 + 1 * rank);
        //this is definitely not how the healing is actually calculated but short of
        //using a switch statement to set the amounts manually I don't know how to replicate it

        combat.healHero(myHero, target, amt);

    }

    public void useHolyLance() {
        myHero.setAcc(.85);
        myHero.setCrit(.05);

        Enemy target = ChooseTarget.chooseEnemy(3, 4);

        if (Combat.tryAttackByHero(myHero, target)) {
            if (target.isUnholy()) {
                int tempDmg = (int) (myHero.getDmg() * .15);
                combat.dmgEnemy(target, tempDmg, myHero);
            } else {
                combat.dmgEnemy(target, (int) myHero.getDmg(), myHero);
            }
        }
        combat.moveSelf(myHero, -1);
    }

    public void useInspiringCry(Combat combat) {

    }

    public void selectAction(Combat combat) {

        this.combat = combat;

        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Crusader"))).forEach((hero) -> {
            this.myHero = hero;
        });

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //if someone is very low health and we have Battle Heal, use it
        if (myHero.getMove5Rank() >= 1) {
            Checker c = new Checker();
            Hero t = c.getLowestHealthHero(Combat.getHeroRoster());
            if (t.getCurHP() < t.getMaxHP() * .25) {
                useBattleHeal(t);
                return;
            }
        }

        //if in a back position, use Holy Lance
        if (myHero.getPosition() >= 3) {
            if (myHero.getMove6Rank() >= 1) {
                useHolyLance();
                return;
            }
        }

        //if there is an Unholy target, use Smite
        if (myHero.getPosition() <= 2) {
            if (myHero.getMove1Rank() >= 1) {
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
            
            if (myHero.getMove2Rank() >= 1) {
                if (pos1 != null && pos2 != null) {
                    useZealousAccusation();
                    return;
                }
            }
            
            if (myHero.getMove1Rank() >= 1) {
                Enemy t = ChooseTarget.chooseEnemy(1, 2);
                useSmite(t);
                return;
            }
            
        }
        //++++++++++++++++
        System.out.println("Crusader could not find a valid action.");
    }

}
