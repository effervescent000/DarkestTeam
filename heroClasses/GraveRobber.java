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
import darkestteam.HeroClass;
import darkestteam.Managers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tara
 */
public class GraveRobber implements HeroClass {

//    private int resolveLvl;
    private final int[] maxHPArray = {20, 24, 28, 32, 36};
    private final double[] dodgeArray = {.10, .15, .20, .25, .30};
    private final int[] speedArray = {8, 8, 9, 9, 10};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.05, .055, .06, .065, .07};
    private final double[] dmgArray = {6.5, 8, 9.5, 10, 11.5};

    private double stunRes = 0.2;
    private double moveRes = 0.2;
    private double blightRes = 0.5;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double deathRes = 0.67;
    private double trapRes = 0.5;

//    private static boolean religious = false;
    private Hero myHero;
    private Combat combat;

    private int pickToTheFace;
    private int lunge;
    private int flashingDaggers;
    private int shadowFade;
    private int thrownDagger;
    private int poisonDart;
    private int toxinTrickery;

    public GraveRobber(Hero myHero) {
        this.myHero = myHero;

        pickToTheFace = myHero.getMove1Rank() - 1;
        lunge = myHero.getMove2Rank() - 1;
        flashingDaggers = myHero.getMove3Rank() - 1;
        shadowFade = myHero.getMove4Rank() - 1;
        thrownDagger = myHero.getMove5Rank() - 1;
        poisonDart = myHero.getMove6Rank() - 1;
        toxinTrickery = myHero.getMove7Rank() - 1;

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
        System.out.println("Grave Robber has no specials configured");
    }

//    public static boolean isReligious() {
//        return religious;
//    }
    private void usePickToTheFace(Enemy t) {

    }

    private void useLunge() {

        Enemy t = ChooseTarget.chooseEnemy(1, 3);
        useLunge(t);

    }

    private void useLunge(Enemy t) {
        myHero.setAcc(.95 + .05 * lunge);
        myHero.setCrit(.1 + .01 * lunge);
        int amt = (int) (myHero.getDmg() * 1.4);
        if (Checker.checkSpecificForDebuff(t, "Blight")) {
            amt = (int) (amt * (1.2 + 3.2 * lunge));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            combat.moveSelf(myHero, -2);
        }

    }

    private void useFlashingDaggers() {
        myHero.setAcc(.9 + .05 * flashingDaggers);
        myHero.setCrit(-.05 + .01 * flashingDaggers);
        int amt = (int) (myHero.getDmg() * (1 - .33));

        ArrayList<Enemy> list = combat.dmgEnemyMulti(2, 3, amt, myHero);
        myHero.setAcc(1 + (.1 * flashingDaggers));
        for (Enemy enemy : list) {
            Managers.addStatusEffect(enemy, "Flashing Daggers", 3, myHero);
        }
    }

    private void useShadowFade() {

    }

    private void useThrownDagger() {
        Enemy t = ChooseTarget.chooseEnemy(2, 4);
        useThrownDagger(null);

    }

    private void useThrownDagger(Enemy t) {
        myHero.setAcc(.9 + .05 * thrownDagger);
        myHero.setCrit(.1 + .01 * thrownDagger);
        int amt = (int) (myHero.getDmg() * (1 - .1));
        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            amt = (int) (amt * (1.25 + (3.5 * thrownDagger)));
        }
        if (Checker.checkSpecificForDebuff(t, "Blight")) {
            amt = (int) (amt * (1.2 + .032 * thrownDagger));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addHelpfulEffect(myHero, "Thrown Dagger", 3);
        }
    }

    private void usePoisonDart() {
        Enemy t = ChooseTarget.chooseEnemy(1, 4);
        usePoisonDart(t);

    }

    private void usePoisonDart(Enemy t) {
        myHero.setAcc(.95 + .05 * poisonDart);
        myHero.setCrit(.075 + .01 * poisonDart);
        int amt = (int) (myHero.getDmg() * (1 - .6));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * poisonDart);
            Managers.addBlight(t, 3, (int) (2.75 + (.33 * poisonDart)), myHero);
            Managers.addStatusEffect(t, "Poison Dart", 3, myHero);
        }
    }

    private void useToxinTrickery() {

    }

    @Override
    public void selectAction(Combat combat) {

        //TODO find another way to do this. maybe in the zone method, at the start of a run do a method for all the heroes to set their variables?
        //this is just a huge waste of time to run on every single round
        this.combat = combat;
//
//        Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Grave Robber"))).forEach((hero) -> {
//            this.myHero = hero;
//        });
//
//        pickToTheFace = myHero.getMove1Rank() - 1;
//        lunge = myHero.getMove2Rank() - 1;
//        flashingDaggers = myHero.getMove3Rank() - 1;
//        shadowFade = myHero.getMove4Rank() - 1;
//        thrownDagger = myHero.getMove5Rank() - 1;
//        poisonDart = myHero.getMove6Rank() - 1;
//        toxinTrickery = myHero.getMove7Rank() - 1;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        //use flashing daggers if we have party members who can benefit from the 
        //bleed res debuff and the available targets have moderate bleed res (not super high or super low)
        if (flashingDaggers != -1 && myHero.getPosition() != 1) {
            if (Checker.checkPartyCompForBleeds() > 1) {//TODO tweak this number
                if (pos2 != null && pos3 != null) {
                    if ((pos2.getBleedRes() < .7 && pos2.getBleedRes() > .25) || (pos3.getBleedRes() < .7 && pos3.getBleedRes() > .25)) {
                        useFlashingDaggers();
                        return;
                    }
                }
            }
        }

        //use Poison Dart if a target does not super high blight res
        //TODO may want to make this higher priority since it can hit all positions
        if (myHero.getPosition() >= 2 && poisonDart != -1) {
            Checker c = new Checker();
            Enemy t = c.getHighestDangerEnemy(Combat.getEnemyRoster());
            if (t.getDanger() < 2) {
                //if the highest danger target isn't a huge threat then just pick a backline target
                //randomly, since they're generally harder to get to
                c = new Checker();
                t = c.getLowestBlightResEnemy(Combat.getEnemyRoster());
                if (t.getBlightRes() < .4) { //napkin math says this is the point where it's about even with Thrown Dagger
                    usePoisonDart(t);
                    return;
                }
            } else if (t.getBlightRes() < .4) {
                usePoisonDart(t);
                return;
            }
        }

        //use Thrown Dagger if there is a target that is both blighted and marked
        if (myHero.getPosition() >= 2 && thrownDagger != -1) {
            List<Enemy> list = Arrays.asList(pos2, pos3, pos4);
            //less likely to have a marked target than a blighted one, so we'll start from there
            Enemy t = Checker.checkEnemiesForDebuff("Marked", list);
            if (t != null) {
                if (Checker.checkSpecificForDebuff(t, "Blight")) {
                    useThrownDagger(t);
                    return;
                } else {
                    useThrownDagger(t);
                    return;
                }
            } else {
                t = Checker.checkEnemiesForDebuff("Blight", list);
                if (t != null) {
                    useThrownDagger(t);
                    return;
                }
            }
        }

        //if a valid target has prot and blight resistance, use pick to the face
        if (myHero.getPosition() <= 3 && pickToTheFace != -1) {
            Checker c = new Checker();
            List<Enemy> list = Arrays.asList(pos1, pos2);
            Enemy t = c.getHighestDangerEnemy(list);
            //if our highest danger target isn't particularly dangerous then check out the available targets
            //to see if anyone has both high blight resist and high prot
            if (t != null) {
                if (t.getDanger() > 1) {
                    //the numbers here are some combinanations I found in my spreadsheet
                    //where pick to the face becomes better than poison dart, which is generally its closest
                    //competition. Below about .15 prot Thrown Dagger becomes better.
                    if ((t.getProt() > .25 && t.getBlightRes() > .5) || (t.getProt() > .15 && t.getBlightRes() > .55)) {
                        usePickToTheFace(t);
                        return;
                    }
                }
            }
            if (pos1 != null) {
                t = pos1;
                if ((t.getProt() > .25 && t.getBlightRes() > .5) || (t.getProt() > .15 && t.getBlightRes() > .55)) {
                    usePickToTheFace(t);
                    return;
                }
            }
            if (pos2 != null) {
                t = pos2;
                if ((t.getProt() > .25 && t.getBlightRes() > .5) || (t.getProt() > .15 && t.getBlightRes() > .55)) {
                    usePickToTheFace(t);
                    return;
                }
            }
        }

        //if the pick to the face code above did not fire, then just use Thrown Dagger
        if (myHero.getPosition() >= 2 && thrownDagger != -1) {
            Enemy t = ChooseTarget.chooseEnemy(2, 4);
            if (t != null) {
                useThrownDagger(t);
            }
        }

        //use Lunge if Hero is in the backline
        if (myHero.getPosition() == 4) {
            if (lunge != -1) {
                //preferentially target a blighted enemy
                List<Enemy> list = Arrays.asList(pos1, pos2, pos3);
                Enemy t = Checker.checkEnemiesForDebuff("Blight", list);
                if (t != null) {
                    useLunge(t);
                    return;
                }
                //otherwise just pick someone, since we don't want to be in the back
                t = ChooseTarget.chooseEnemy(1, 3);
                if (t != null) {
                    useLunge(t);
                    return;
                }
                //if we don't have lunge, just move
            } else {
                combat.moveSelf(myHero, -2);
                return;
            }
        }

        //if we're in the backline and presumably don't have lunge
        //+++++++++++++++++++++++++++++++++++++++
        System.out.println("Grave Robber could not find a valid action");
    }

}
