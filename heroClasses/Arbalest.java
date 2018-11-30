/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import static darkestteam.Checker.checkHeroesForDebuff;
import static darkestteam.ChooseTarget.chooseEnemy;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static darkestteam.CombatLog.addLog;
import javafx.collections.ObservableList;
import static darkestteam.Checker.checkEnemiesForDebuff;
import darkestteam.HeroClass;
import darkestteam.Managers;
import static darkestteam.Managers.addHelpfulEffect;
import static darkestteam.Managers.addStatusEffect;
import darkestteam.RandomFunctions;
import java.util.ArrayList;
import static darkestteam.Checker.checkEnemiesForDebuff;
import static darkestteam.Managers.addHelpfulEffect;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class Arbalest implements ICombatMethods, HeroClass {

    private final int[] maxHPArray = {27, 32, 37, 42, 47};
    private final double[] dodgeArray = {0, .05, .10, .15, .20};
    private final int[] speedArray = {3, 3, 4, 4, 5};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.075, .08, .085, .09, .095};
    private final double[] dmgArray = {6.5, 8, 9.5, 10, 11.5};

    // RELIGIOUS commented out as it no longer serves a purpose
//    private static boolean RELIGIOUS = false;
    private double stunRes = 0.4;
    private double moveRes = 0.4;
    private double blightRes = 0.3;
    private double bleedRes = 0.3;
    private double diseaseRes = 0.3;
    private double debuffRes = 0.3;
    private double trapRes = 0.1;
    private double deathRes = .67;

    private Hero myHero;

    private int sniperShot;
    private int suppressingFire;
    private int snipersMark;
    private int bola;
    private int blindfire;
    private int battlefieldBandage;
    private int rallyingFlare;

    private Combat combat;

    public Arbalest(Hero myHero) {

        this.sniperShot = myHero.getMove1Rank() - 1;
        this.suppressingFire = myHero.getMove2Rank() - 1;
        this.snipersMark = myHero.getMove3Rank() - 1;
        this.bola = myHero.getMove4Rank() - 1;
        this.blindfire = myHero.getMove5Rank() - 1;
        this.battlefieldBandage = myHero.getMove6Rank() - 1;
        this.rallyingFlare = myHero.getMove7Rank() - 1;
    }

    @Override
    public void resetSpecials() {
        System.out.println("Arbalest has no specials to reset");
    }

//    public static boolean isReligious() {
//        return RELIGIOUS;
//    }
    private void useSniperShot(Enemy target) {
        myHero.setAcc(.95 + 5 * sniperShot);
        myHero.setCrit(.05 + .01 * sniperShot);

        int amt = (int) myHero.getDmg();

        if (Combat.tryAttackByHero(myHero, target)) {
            if (Checker.checkSpecificForDebuff(target, "Marked")) {
                myHero.setCrit(myHero.getCrit() + (.1 + .01 * sniperShot));
                combat.dmgEnemy(target, amt, myHero);
            } else {
                combat.dmgEnemy(target, amt, myHero);
            }
        }
    }

    private void useSniperShot() {
        Enemy target = chooseEnemy(2, 4);
        useSniperShot(target);
    }

    private void useSuppressingFire() {
        myHero.setAcc(.95 + .05 * suppressingFire);
        myHero.setCrit(-.1 + .01 * suppressingFire);

        int amt = (int) (myHero.getDmg() * .2);

        ArrayList<Enemy> el;
        el = combat.dmgEnemyMulti(3, 4, amt, myHero);
        myHero.setAcc(1 + .1 * suppressingFire);
        for (Enemy enemy : el) {
            Managers.addStatusEffect(enemy, "Suppressing Fire", 3, myHero);
        }

    }

    private void useSnipersMark(Enemy target) {

        int rank = myHero.getMove3Rank() - 1;
        myHero.setAcc(1 + .05 * rank);
        myHero.setCrit(0);

        if (Combat.tryAttackByHero(myHero, target)) {
            addHelpfulEffect(target, "Marked", 3);
            addLog(myHero.getHeroName() + " has marked " + target.toString() + " !");
            myHero.setAcc(1 + .1 * rank);
            addStatusEffect(target, "Sniper's Mark Debuff", 3, myHero);
        }
    }

    private void useBola() {
        myHero.setAcc(.95 + .05 * bola);
        myHero.setCrit(.02 + .01 * bola);
        int amt = (int) (myHero.getDmg() * .5);

        ArrayList<Enemy> el;
        el = combat.dmgEnemyMulti(1, 2, amt, myHero);
        myHero.setAcc(.75 + .1 * bola);
        for (Enemy enemy : el) {
            combat.moveAttack(myHero, enemy, -1);
        }

    }

    private void useBlindfire() {
        myHero.setAcc(.75 + .05 * blindfire);
        myHero.setCrit(0 + .01 * blindfire);
        int amt = (int) (myHero.getDmg() * .9);

        Enemy t = chooseEnemy(1, 4);
        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
        Managers.addHelpfulEffect(myHero, "Blindfire", 3);

    }

    private void useBattlefieldBandage(Hero t) {
        int amt = RandomFunctions.getRandomNumberInRange(2, 3); //TODO this needs to take skill rank into account

        combat.healHero(myHero, t, amt);
        Managers.addHelpfulEffect(t, "Battlefield Bandage", 3);

    }

    private void useRallyingFlare(Hero target) {

    }

    @Override
    public void selectAction(Combat combat) {
        try {
            this.combat = combat;

            Combat.getHeroRoster().stream().filter((Hero hero) -> (hero.getHeroClass().equals("Arbalest"))).forEach((hero) -> {
                this.myHero = hero;
            });

            ObservableList<Hero> heroRoster = Combat.getHeroRoster();

            Hero stunned = checkHeroesForDebuff("Stun", heroRoster);
            Enemy marked = checkEnemiesForDebuff("Marked", Combat.getEnemyRoster());

            //use battlefield bandage (on self or others) if someone is very low health
            if (battlefieldBandage != -1) {
                Checker c = new Checker();
                Hero t = c.getLowestHealthHero(heroRoster);
                if (t.getCurHP() < t.getMaxHP() * .25) {
                    useBattlefieldBandage(t);
                    return;
                }
            }

            if (myHero.getPosition() <= 2) {
                //if in a front line position and has a valid ability learned, try to
                //one of those. If not, then spend turn moving.
                if (stunned != null && rallyingFlare != -1) {
                    useRallyingFlare(stunned);
                    return;
                } else if (checkHeroesForDebuff("Marked", heroRoster) != null && rallyingFlare != -1) {
                    useRallyingFlare(checkHeroesForDebuff("Marked", heroRoster));
                    return;
                } else if (blindfire != -1) {
                    useBlindfire();
                    return;
                } else {
                    combat.moveSelf(myHero, 2);
                    return;
                }
            }
            // if NOT in a frontline position then:
            if (snipersMark != -1) {
                if (marked == null) {
                    if (Checker.checkPartyCompForMarked() > 1) {
                        Enemy target = chooseEnemy(2, 4);
                        //TODO actual target selection here rather than picking randomly
                        useSnipersMark(target);
                        return;
                    }
                } else if (marked.getPosition() >= 2) {
                    useSniperShot(marked);
                    return;
                } else if (marked.getPosition() == 1) {
                    useSniperShot();
                    return;
                }
            }

            //if there is a high danger enemy, use Suppressing Fire or Bola to debuff/cc it
            Checker c = new Checker();
            Enemy e = c.getHighestHealthEnemy(Combat.getEnemyRoster());

            if (e != null && e.getDanger() > 1) { //TODO tweak this number
                if (e.getPosition() >= 3 && suppressingFire != -1) {
                    useSuppressingFire();
                    return;
                }
                //only use Bola if the target is actually vulnerable to movement
                if (e.getPosition() <= 2 && e.getMoveable() > 1 && bola != -1) {
                    useBola();
                    return;
                }
            }

            System.out.println("Arbalest could not find a valid action.");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
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
    public double getBleedRes() {
        return bleedRes;
    }

    @Override
    public double getBlightRes() {
        return blightRes;
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
    public double getMoveRes() {
        return moveRes;
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
    public double getDeathRes() {
        return deathRes;
    }

}
