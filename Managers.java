/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import static darkestteam.CombatLog.addLog;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Tara
 */
public class Managers {

    private static Rosters roster;

    public static void checkStress(Hero hero) {
        int stress = hero.getStressLvl();
        if (stress >= 100) {
            //baseline is 75% chance of an affliction, 25% chance of a virtue.
            //TODO affliction/virtue code here.
        }
    }

    /**
     * This method should be called to add status effects *only* (*not* DOTs)!
     * It includes a resist check (so it should not be wrapped in a calcHit)
     *
     *
     * @param target
     * @param name
     * @param duration
     * @param attacker The Enemy object applying the status effect
     */
    public static void addStatusEffect(Hero target, String name, int duration, Enemy attacker) {
        //marked debuffs never miss
        if (name.equals("Marked")) {
            target.getDebuffs().add(new Debuff(name, duration, target));
        } else if (name.equals("Stun")) {
            if (Combat.calcHit(target.stunRes, attacker.getAcc() + attacker.getAccMod())) {
                target.getDebuffs().add(new Debuff(name, duration, target));
            }
        } else if (Combat.calcHit(target.getDebuffRes(), attacker.getAccMod() + attacker.getAcc())) {
            target.getDebuffs().add(new Debuff(name, duration, target));
        }
    }

    /**
     * Use this when applying a status effect to an enemy type, by a hero type.
     * It includes a resist check (so it should not be wrapped in a calcHit)
     *
     *
     * @param target   the value of target
     * @param name     the value of name
     * @param duration the value of duration
     * @param attacker the value of attacker
     */
    public static void addStatusEffect(Enemy target, String name, int duration, Hero attacker) {
        if (name.equals("Marked")) { //TODO does Marked have a resist chance?
            target.getDebuffs().add(new Debuff(name, duration, target));
        } else if (name.equals("Stun")) {
            if (Combat.calcHit(target.stunRes, attacker.getAcc() + attacker.getAccMod())) {
                target.getDebuffs().add(new Debuff(name, duration, target, attacker));
            }
        } else if (Combat.calcHit(target.getDebuffRes(), attacker.getAcc() + attacker.getAccMod())) {
            target.getDebuffs().add(new Debuff(name, duration, target, attacker));
        }
    }

    public static void addHelpfulEffect(Hero target, String name, int duration, Hero user) {
        target.getDebuffs().add(new Debuff(name, duration, target, user));
    }

    public static void addHelpfulEffect(Enemy target, String name, int duration) {
        target.getDebuffs().add(new Debuff(name, duration, target));
    }

    private static void addDOT(Hero target, String type, int duration, int dmg) {
        if (type.equals("Bleed") || type.equals("Blight")) {
            target.getDebuffs().add(new Debuff(type, duration, dmg));
        } else {
            System.out.println("addDOT (hero type) called with a bad string");
        }
    }

    public static void addBleed(Hero target, int duration, int dmg, Enemy attacker) {
        if (Combat.calcHit(target.getBleedRes(), attacker.getAcc() + attacker.getAccMod())) {
            addDOT(target, "Bleed", duration, dmg);
        }
    }

    public static void addBlight(Hero target, int duration, int dmg, Enemy attacker) {
        if (Combat.calcHit(target.getBlightRes(), attacker.getAcc() + attacker.getAccMod())) {
            addDOT(target, "Blight", duration, dmg);
        }
    }

    private static void addDOT(Enemy target, String type, int duration, int dmg, Hero attacker) {
        if (type.equals("Bleed") || type.equals("Blight")) {
            if (attacker == null) {
                target.getDebuffs().add(new Debuff(type, duration, dmg));
            }
            target.getDebuffs().add(new Debuff(type, duration, dmg, attacker));
        } else {
            System.out.println("addDOT (enemy type) called with a bad string");
        }

    }

    public static void addBleed(Enemy target, int duration, int dmg, Hero attacker) {
        if (Combat.calcHit(target.getBleedRes(), attacker.getAccMod() + attacker.getAcc())) {
            addDOT(target, "Bleed", duration, dmg, attacker);
        }
    }

    public static void addBlight(Enemy target, int duration, int dmg, Hero attacker) {
        if (Combat.calcHit(target.getBlightRes(), attacker.getAccMod() + attacker.getAcc())) {
            addDOT(target, "Blight", duration, dmg, attacker);
        }
    }

    public static void checkDebuffs(Hero target) {

        for (int i = 0; i < target.getDebuffs().size(); i++) {
            Debuff d = target.getDebuffs().get(i);
            d.setDuration(-1);
            if (d.getDuration() < 1) {
                d.removeDebuff(target);
                //TODO I don't remember if DOTs tick before they fall off
            } else if (d.getDmg() > 0) {
                target.setCurHP(target.getCurHP() - d.getDmg());
                addLog(target.getHeroDesc() + " suffered " + d.getDmg() + " DOT damage");
                target.setDamageTakenDOT(d.getDmg());
            }
        }
    }

    public static void checkDebuffs(Enemy target) {
        for (int i = 0; i < target.getDebuffs().size(); i++) {
            Debuff d = target.getDebuffs().get(i);
            d.setDuration(-1);
            if (d.getDuration() < 1) {
                d.removeDebuff(target);
                //TODO I don't remember if DOTs tick before they fall off
            } else if (d.getDmg() > 0) {
                target.setCurHP(target.getCurHP() - d.getDmg());
                addLog(target.getClass().getName() + " suffered " + d.getDmg() + " DOT damage");
                d.getAttacker().setDamageDealtDOT(d.getDmg());
            }
        }
    }

    public static void purgeBleeds(Hero t) {
        ArrayList<Debuff> debuffs = t.getDebuffs();
        if (!debuffs.isEmpty()) {
            for (int i = 0; i < debuffs.size(); i++) {
                Debuff d = debuffs.get(i);
                if (d.getName().equals("Bleed")) {
                    d.removeDebuff(t);
                }
            }
        }
    }

    public static void purgeBlights(Hero t) {
        ArrayList<Debuff> debuffs = t.getDebuffs();
        if (!debuffs.isEmpty()) {
            for (int i = 0; i < debuffs.size(); i++) {
                Debuff d = debuffs.get(i);
                if (d.getName().equals("Blight")) {
                    d.removeDebuff(t);
                }
            }
        }
    }

    /**
     * This method should only be called at the end of combat, when we need to
     * purge temporary/short-duration buffs/debuffs from the heroes. This does
     * NOT remove long-duration debuffs such as Death's Door recovery, which are
     * only removed at the end of a dungeon run or upon camping.
     *
     * This code isn't great b/c the debuffs should realistically fall off as
     * the Heroes as the progress through the dungeon but for now I'm just doing
     * this.
     *
     *
     * @param t
     */
    public static void removeTempDebuffs(Hero t) {
        try {
            ArrayList<Debuff> debuffs = t.getDebuffs();
            if (!debuffs.isEmpty()) {
                for (int i = 0; i < debuffs.size(); i++) {
                    Debuff d = debuffs.get(i);
                    if (!d.getName().equals("Death's Door Recovery")) {
                        d.removeDebuff(t);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * This method is called at the end of a dungeon run to remove ALL
     * buffs/debuffs.
     *
     *
     * @param t
     */
    public static void removeAllDebuffs(Hero t) {
        ArrayList<Debuff> debuffs = t.getDebuffs();
        if (!debuffs.isEmpty()) {
            for (int i = 0; i < debuffs.size(); i++) {
                Debuff d = debuffs.get(i);
                d.removeDebuff(t);
            }
        }
    }

    /**
     * This is called to reset all heroes to their default state in preparation
     * for another sim iteration.
     *
     *
     */
    public static void resetAll() {
        if (roster == null) {
            roster = Rosters.getInstance();
        }

        roster.getSelectedHeroes().forEach((hero) -> {
            hero.setStressLvl(0);
            removeAllDebuffs(hero);
            hero.resetCombatStats();
            hero.setCurHP(hero.getMaxHP());
            //not calling the resetPosition method here, instead we are calling it at the end of every combat
        });
    }

    public static void resetAllPositions(Combat combat) {
        ObservableList<Hero> r = Combat.getHeroRoster();
        //also I'm intentionally writing out the roster position stuff every time to force
        //the code to keep checking it instead of just saving whichever hero was in position x at the beginning

        try {
            if (r.get(0) != null) {
                while (r.get(0).getPosition() != r.get(0).getStartingPosition()) {
                    r.get(0).resetPosition(combat);
                }
            }
            if (r.get(1) != null) {
                while (r.get(1).getPosition() != r.get(1).getStartingPosition()) {
                    r.get(1).resetPosition(combat);
                }
            }
            if (r.get(2) != null) {
                while (r.get(2).getPosition() != r.get(2).getStartingPosition()) {
                    r.get(2).resetPosition(combat);
                }
            }
            if (r.get(3) != null) {
                while (r.get(3).getPosition() != r.get(3).getStartingPosition()) {
                    r.get(3).resetPosition(combat);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }

    public static void fixPositionsEnemies(ObservableList<Enemy> roster) {
        for (int i = 0; i < roster.size(); i++) {
            roster.get(i).setPosition(i + 1);
        }
    }

    public static void fixPositionsHeroes(ObservableList<Hero> roster) {
        for (int i = 0; i < roster.size(); i++) {
            roster.get(i).setPosition(i + 1);
        }
    }
}
