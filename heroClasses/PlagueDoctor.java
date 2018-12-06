/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import static darkestteam.Checker.checkHeroesForDebuff;
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
public final class PlagueDoctor implements HeroClass {

//    private int resolveLvl;
    private final int[] maxHPArray = {22, 26, 30, 34, 38};
    private final double[] dodgeArray = {.05, .10, .15, .20, .25};
    private final int[] speedArray = {7, 7, 8, 8, 9};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {1.025, 1.03, 1.035, 1.04, 1.045};
    private final double[] dmgArray = {5.5, 6.5, 8, 8.5, 10};

    private double stunRes = 0.2;
    private double moveRes = 0.2;
    private double blightRes = 0.6;
    private double bleedRes = 0.2;
    private double diseaseRes = 0.5;
    private double debuffRes = 0.5;
    private double deathRes = 0.67;
    private double trapRes = 0.2;

//    private boolean religious = false;
    private Hero myHero;
    private Combat combat;

    private int blindingGasUses;
    private int emboldeningVapoursUses;

    private int noxiousBlast;
    private int plagueGrenade;
    private int blindingGas;
    private int incision;
    private int battlefieldMedicine;
    private int emboldeningVapours;
    private int disorientingBlast;

    public PlagueDoctor(Hero myHero) {

        this.myHero = myHero;

        blindingGasUses = 0;

        noxiousBlast = myHero.getMove1Rank() - 1;
        plagueGrenade = myHero.getMove2Rank() - 1;
        blindingGas = myHero.getMove3Rank() - 1;
        incision = myHero.getMove4Rank() - 1;
        battlefieldMedicine = myHero.getMove5Rank() - 1;
        emboldeningVapours = myHero.getMove6Rank() - 1;
        disorientingBlast = myHero.getMove7Rank() - 1;

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

//    public boolean isReligious() {
//        return religious;
//    }
    private void useNoxiousBlast(Enemy t) {
        myHero.setAcc(.95 + .05 * noxiousBlast);
        myHero.setCrit(.05 + .01 * noxiousBlast);
        int amt = (int) (myHero.getDmg() * (1 - .8));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);

            myHero.setAcc(1 + .1 * noxiousBlast);
            amt = (int) (5 + .5 * noxiousBlast);
            Managers.addBlight(t, 3, amt, myHero);
            myHero.setAcc(1);
            Managers.addStatusEffect(t, "Noxious Blast", 3, myHero);
        }

    }

    private void usePlagueGrenade() {
        myHero.setAcc(.95 + .05 * plagueGrenade);
        myHero.setCrit(0 + .01 * plagueGrenade);
        int amt = (int) (myHero.getDmg() * (1 - .9));

        ArrayList<Enemy> hits = combat.dmgEnemyMulti(3, 4, amt, myHero);
        if (hits != null && !hits.isEmpty()) {
            myHero.setAcc(1 + .1 * plagueGrenade);
            amt = (int) (4 + .5 * plagueGrenade);
            for (Enemy enemy : hits) {
                Managers.addBlight(enemy, 3, amt, myHero);
            }
        }

    }

    private void useBlindingGas() {

        myHero.setAcc(.95 + .05 * blindingGas);

        ArrayList<Enemy> hits = combat.dmgEnemyMulti(3, 4, 0, myHero);
        if (hits != null && !hits.isEmpty()) {
            myHero.setAcc(1 + .1 * blindingGas);
            for (Enemy hit : hits) {
                Managers.addStatusEffect(hit, "Stun", 1, myHero);
            }
        }
        blindingGasUses++;

    }

    private void useIncision(Enemy t) {
        myHero.setAcc(.85 + .05 * incision);
        myHero.setCrit(.05 + .01 * incision);
        int amt = (int) myHero.getDmg();

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            myHero.setAcc(1 + .1 * incision); //this is not a perfect representation but it's close
            Managers.addBleed(t, 3, 1, myHero);
        }

    }

    private void useBattlefieldMedicine(Hero h) {

        int amt = (int) (1 + .5 * battlefieldMedicine); //this is not a 100% representation but it's pretty close
        combat.healHero(myHero, h, amt);
        Managers.purgeBlights(h);
        Managers.purgeBleeds(h);

    }

    private void useEmboldeningVapours(Hero t) {
        emboldeningVapours = myHero.getMove6Rank() - 1;

        Managers.addHelpfulEffect(t, "Emboldening Vapours", 1000, myHero); //lasts for 1 battle

        emboldeningVapoursUses++;

    }

    private void useDisorientingBlast() {
        //todo fill out ability code here
    }

    @Override
    public void resetSpecials() {
        blindingGasUses = 0;
        emboldeningVapoursUses = 0;
    }

    @Override
    public void selectAction(Combat combat) {

        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        Checker c = new Checker();

        //first, try to use battlefield medicine
        if (battlefieldMedicine != -1) {
            if (myHero.getPosition() >= 3) {
                Hero bleedTarget = checkHeroesForDebuff("Bleed", Combat.getHeroRoster());
                Hero blightTarget = checkHeroesForDebuff("Blight", Combat.getHeroRoster());
                //I'm doing it in this order b/c I feel like blights are using higher damage than bleeds. not sure tho
                if (blightTarget != null) {
                    useBattlefieldMedicine(blightTarget);
                    return;
                }
                if (bleedTarget != null) {
                    useBattlefieldMedicine(bleedTarget);
                    return;
                }
            }
        }

        //use Emboldening Vapors on the heaviest hitters
        if (emboldeningVapours != -1 && emboldeningVapoursUses < 2) {
            Hero hPos1 = Combat.getHeroInPosition(1);
            Hero hPos2 = Combat.getHeroInPosition(2);
            //first check for hellions b/c I'm biased
            if (hPos1.getHeroClass().equals("Hellion")) {
                useEmboldeningVapours(hPos1);
                return;
            } else if (hPos2.getHeroClass().equals("Hellion")) {
                useEmboldeningVapours(hPos2);
                return;
            }

            //next check for crusaders
            if (hPos1.getHeroClass().equals("Crusader")) {
                useEmboldeningVapours(hPos1);
                return;
            } else if (hPos2.getHeroClass().equals("Crusader")) {
                useEmboldeningVapours(hPos2);
                return;
            }

            //next Lepers
            if (hPos1.getHeroClass().equals("Leper")) {
                useEmboldeningVapours(hPos1);
                return;
            } else if (hPos2.getHeroClass().equals("Leper")) {
                useEmboldeningVapours(hPos2);
                return;
            }

            //TODO more here
        }

        //use noxious blast if there is a high-danger front-line target (for the damage and the debuff)
        if (noxiousBlast != -1) {
            if (myHero.getPosition() >= 2) {
                Enemy t;
                if (pos1 != null && pos2 != null) {
                    List<Enemy> list = Arrays.asList(pos1, pos2);
                    t = c.getHighestDangerEnemy(list);
                    if (t != null) {
                        if (t.getDanger() > 3 && t.getCurHP() > 15) {
                            useNoxiousBlast(t);
                            return;
                        }
                    }
                }
            }
        }

        //use Blinding Gas if there are high-danger targets in the backline that are susceptible to stuns
        if (blindingGas != -1 && blindingGasUses < 3) {
            if (myHero.getPosition() >= 3) {
                if (pos3 != null && pos4 != null) {
                    List<Enemy> list = Arrays.asList(pos3, pos4);
                    Enemy t = c.getHighestDangerEnemy(list);
                    if (t.getDanger() > 3 && t.getStunRes() < .4) {
                        useBlindingGas();
                        return;
                    }
                }
            }
        }

        //use plague grenade if back-line targets are susceptible to blight
        if (plagueGrenade != -1) {
            if (myHero.getPosition() >= 3) {
                if (pos3 != null && pos4 != null) {
                    //blight resist here can be stupid high and still be better than incision
                    if (pos3.getBlightRes() < .7 && pos4.getBlightRes() < .7) {
                        usePlagueGrenade();
                        return;
                    }
                }
            }
        }

        //use noxious blast, preferentially against a higher danger target due to its acc debuff
        if (noxiousBlast != -1) {
            if (myHero.getPosition() >= 2) {
                Enemy t;
                if (pos1 != null && pos2 != null) {
                    List<Enemy> list = Arrays.asList(pos1, pos2);
                    if (pos1.getDanger() == pos2.getDanger()) {
                        t = c.getLowestBlightResEnemy(list);
                        if (t.getBlightRes() < .55) { //higher than this and incision is better
                            useNoxiousBlast(t);
                            return;
                        }
                    } else {
                        t = c.getHighestDangerEnemy(list);
                        if (t.getBlightRes() < .55) {
                            useNoxiousBlast(t);
                            return;
                        }
                    }
                }
            }
            //if none of the above is true but we don't have Incision as a fall-back:
            if (incision == -1) {
                List<Enemy> list = Arrays.asList(pos1, pos2);
                Enemy t = c.getLowestBlightResEnemy(list);
                useNoxiousBlast(t);
                return;
            }
        }

        //use incision if all available targets have high blight resist
        if (incision != -1) {
            if (myHero.getPosition() <= 3) {
                List<Enemy> list = Arrays.asList(pos1, pos2);
                Enemy t = c.getLowestBleedResEnemy(list);
                //I'm not including a bleed res check b/c the code should only get down here as a last resort
                useIncision(t);
                return;
            }
        }

        //otherwise we need to move
        if (myHero.getPosition() == 1) {
            combat.moveSelf(myHero, 1);
            return;
        }

        //++++++++++++++++++++++++++
        System.out.println("Plague Doctor could not find a valid action");
    }
}
