/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.heroClasses;

import darkestteam.Checker;
import static darkestteam.ChooseTarget.chooseEnemy;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import darkestteam.Managers;
import darkestteam.RandomFunctions;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tara
 */
public class Vestal implements ICombatMethods {

    private final int[] maxHPArray = {24, 29, 34, 39, 44};
    private final double[] dodgeArray = {0, .05, .10, .15, .20};
    private final int[] speedArray = {4, 4, 5, 5, 6};
    private final double[] accModArray = {0, 0, 0, 0, 0};
    private final double[] critModArray = {.025, .03, .035, .04, .045};
    private final double[] dmgArray = {6.5, 8, 9.5, 10, 11.5};

    private static boolean religious = true;

    private Hero myHero;
    private Combat combat;

    private int maceBash;
    private int judgement;
    private int dazzlingLight;
    private int divineGrace;
    private int divineComfort;
    private int illumination;
    private int handOfLight;

    public Vestal(Hero myHero) {

        this.myHero = myHero;

        maceBash = myHero.getMove1Rank() - 1;
        judgement = myHero.getMove2Rank() - 1;
        dazzlingLight = myHero.getMove3Rank() - 1;
        divineGrace = myHero.getMove4Rank() - 1;
        divineComfort = myHero.getMove5Rank() - 1;
        illumination = myHero.getMove6Rank() - 1;
        handOfLight = myHero.getMove7Rank() - 1;

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

    @Override
    public void resetSpecials() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void useMaceBash(Enemy t) {
        
        myHero.setAcc(.85 + .05 * maceBash);
        myHero.setCrit(0 + .01 * maceBash);
        int amt = (int) myHero.getMeleeDmg();

        if (t.isUnholy()) {
            amt = (int) (amt * (1.15 + .05 * maceBash));
        }

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
        }
    }

    public void useJudgement() {
        
        myHero.setAcc(.8 + .05 * judgement);
        myHero.setCrit(.05 + .01 * judgement);
        Enemy t = chooseEnemy(1, 4);
        int amt = (int) (myHero.getRangedDmg() * (1 - .25));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            combat.healHero(myHero, myHero, (int) (4 + .5 * judgement));
        }

    }

    public void useDazzlingLight(Enemy t) {
        
        myHero.setAcc(.9 + .05 * dazzlingLight);
        myHero.setCrit(.05 + .01 * dazzlingLight);
        int amt = (int) ((int) myHero.getRangedDmg() * (1 - .75));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addStatusEffect(t, "Stun", 1, myHero);
        }

    }

    public void useDivineGrace(Hero target) {
        
        int lower = 4 + 1 * divineGrace;
        int upper = 5 + 1 * divineGrace;
        int amt = RandomFunctions.getRandomNumberInRange(lower, upper);

        combat.healHero(myHero, target, amt);
    }

    public void useDivineComfort() {
        
        int lower = (int) (1.25 + .75 * divineComfort);
        int upper = (int) (3 + .75 * divineComfort);
        int amt;

        for (Hero h : Combat.getHeroRoster()) {
            amt = RandomFunctions.getRandomNumberInRange(lower, upper);
            combat.healHero(myHero, h, amt);
        }
    }

    public void useIllumination(Enemy t) {
        
        myHero.setAcc(.9 + .05 * illumination);
        myHero.setCrit(0);
        int amt = (int) (myHero.getRangedDmg() * (1 - .5));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addStatusEffect(t, "Illumination", 3, myHero); //TODO check duration
        }
    }

    public void useHandOfLight(Enemy t) {
        
        myHero.setAcc(.85 + .05 * handOfLight);
        myHero.setCrit(0 + .01 * handOfLight);
        int amt = (int) (myHero.getRangedDmg() * (1 - .5));

        if (Combat.tryAttackByHero(myHero, t)) {
            combat.dmgEnemy(t, amt, myHero);
            Managers.addHelpfulEffect(myHero, "Hand of Light", 3); //TODO check duration

        }

    }

    @Override
    public void selectAction(Combat combat) {

        this.combat = combat;

        Enemy pos1 = Combat.getEnemyInPosition(1);
        Enemy pos2 = Combat.getEnemyInPosition(2);
        Enemy pos3 = Combat.getEnemyInPosition(3);
        Enemy pos4 = Combat.getEnemyInPosition(4);

        Checker c = new Checker();
        Hero healTarget = c.getLowestHealthHero(Combat.getHeroRoster());

        //use Divine Grace if there is someone critically low. If we are out of position then straight-up move
        if (divineGrace != -1) {
            if (healTarget.getCurHP() < healTarget.getMaxHP() * .25) {
                if (myHero.getPosition() >= 3) {
                    useDivineGrace(healTarget);
                    return;
                } else {
                    combat.moveSelf(myHero, 1);
                    return;
                }
            }
        }

        //use Divine Comfort if at least 3 people need healing
        if (divineComfort != -1) {
            if (myHero.getPosition() >= 2) {
                if (combat.checkGroupHealth()) {
                    useDivineComfort();
                    return;
                } else {
                    combat.moveSelf(myHero, 1);
                    return;
                }
            }
        }

        //use Divine Grace to spot heal
        if (divineGrace != -1) {
            if (healTarget.getCurHP() < healTarget.getMaxHP() * .8) {
                if (myHero.getPosition() >= 3) {
                    useDivineGrace(healTarget);
                    return;
                } else {
                    combat.moveSelf(myHero, 1);
                    return;
                }
            }
        }

        //use Hand of Light if we're in position and there is at least 1 unholy enemy
        //(using it for the self-buff so it doesn't actually matter which target we attack)
        if (handOfLight != -1) {
            if (myHero.getPosition() <= 2) {
                Enemy t = chooseEnemy(1, 3);
                useHandOfLight(t);
                return;
            }
        }

        //use Illumination against highest danger target
        if (illumination != -1) {
            if (myHero.getPosition() <= 3) {
                c = new Checker();
                Enemy t = c.getHighestDangerEnemy(Combat.getEnemyRoster());
                if (t != null) {
                    useIllumination(t);
                    return;
                }
            }
        }

        //use Dazzling Lihgt against highest danger target
        if (dazzlingLight != -1) {
            if (myHero.getPosition() >= 2) {
                c = new Checker();
                List<Enemy> list = Arrays.asList(pos1, pos2, pos3);
                Enemy t = c.getHighestDangerEnemy(list);
                if (t != null) {
                    useDazzlingLight(t);
                    return;
                }
            }
        }

        //use Mace Bash if we're in position (even just vs unholy it's better than judgment)
        if (maceBash != -1) {
            if (myHero.getPosition() <= 2) {
                Enemy t = Checker.checkEnemiesForType("Unholy", Combat.getEnemyRoster());
                if (t != null) {
                    if (t.getPosition() <= 2) {
                        useMaceBash(t);
                        return;
                    }
                }
                // if there isn't an Unholy enemy then just pick something
                t = chooseEnemy(1, 2);
                if (t != null) {
                    useMaceBash(t);
                    return;
                }
            }
        }

        //use Judgement otherwise. Treating the healing from it as fluff
        if (judgement != -1) {
            if (myHero.getPosition() >= 3) {
                useJudgement();
                return;
            }
        }

//        if (healTarget.getCurHP() < healTarget.getMaxHP() * 0.75 && myHero.getPosition() >= 3 && myHero.getMove4Rank() >= 1) {
//            useDivineGrace(healTarget);
//
//        } else if (combat.checkGroupHealth() && myHero.getPosition() >= 2 && myHero.getMove5Rank() >= 1) {
//            useDivineComfort();
//        } else if (myHero.getPosition() >= 3 && myHero.getMove2Rank() >= 1) {
//            useJudgement();
//        } else {
//            System.out.println("Vestal could not find a valid action.");
//        }
        //+++++++++++++++++
        System.out.println("Vestal could not find a valid action");
    }

}
