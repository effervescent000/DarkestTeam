/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import static darkestteam.CombatLog.addLog;
import static darkestteam.Managers.checkStress;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;
import static darkestteam.Checker.checkSpecificForDebuff;
import static darkestteam.Managers.checkDebuffs;

/**
 *
 * @author Tara
 */
public class Combat {

    private static ObservableList<Hero> heroRoster;
    private static ObservableList<Enemy> enemyRoster;
    private ObservableList<Creature> combatRoster = FXCollections.observableArrayList();
    private int round;

    private final int critStressDmg = 5;
    //I don't remember how much this actually is but it isn't a ton

//    private final Log cLog;
    public Combat() {

        try {
            System.out.println("combat constructor called");

            this.round = 1;

            heroRoster = Rosters.getInstance().getSelectedHeroes();
            enemyRoster = Zone.getEnemyRoster();

//            combatRoster = FXCollections.observableArrayList();
            initiative();
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }

    }

    private void initiative() {
        //this method should combine the two rosters into one array and
        //sorted by their getSpeed() methods. at the end it will send the new
        //ArrayList to method to do a round of combat. I'm not sure this will
        //work as-is

        try {
            if (heroRoster != null) {
                heroRoster.stream().filter((hero) -> (!combatRoster.contains(hero))).forEach((hero) -> {
                    combatRoster.add(hero);
                });
            } else {
                System.out.println("heroRoster is not generating properly");
            }
            if (enemyRoster != null) {
                enemyRoster.stream().filter((enemy) -> (!combatRoster.contains(enemy))).forEach((enemy) -> {
                    combatRoster.add(enemy);
                });
            }

            sortBySpeed(combatRoster);

            doRound();
        } catch (Throwable ex) {
            System.err.println("Uncaught exception - " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private void doRound() {
        try {
            addLog("Starting round " + round);

            sortBySpeed(combatRoster);

            for (int i = 0; i < combatRoster.size(); i++) {
                if (combatRoster.get(i).getCurHP() > 0) {
                    if (heroRoster.size() > 0 && enemyRoster.size() > 0) {
                        if (combatRoster.get(i).getClass().getName().equals("darkestteam.Hero")) {
                            checkDebuffs((Hero) combatRoster.get(i));
                        } else {
                            checkDebuffs((Enemy) combatRoster.get(i));
                        }
                        combatRoster.get(i).selectAction(this);
                    }
                }
            }
            checkEncounterStatus();
        } catch (Throwable ex) {
            System.err.println("Uncaught exception - " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    private void checkEncounterStatus() {
        try {
            if (round > 50) {
                //end combat if it hits 50 rounds because a round count that high probably means
                //something's gone wrong
                addLog("Combat has passed 50 rounds and is ending.");
            } else if (heroRoster.size() < enemyRoster.size()) {
                addLog("Heroes are fleeing combat.");
            } else if (enemyRoster.isEmpty()) {
                addLog("All enemies are dead, combat is ending.");
            } else if (heroRoster.isEmpty()) {
                addLog("All heroes are dead, combat is ending.");
            } else {
                round++;
                doRound();
                //this return statement is to prevent the debuff purge below from firing if combat is continuing
                return;
            }

            //this should only be triggered if combat is over; if none of the return statements above are true, the return
            //statement will trigger and block this
            for (Hero hero : heroRoster) {
                Managers.removeTempDebuffs(hero);
//                hero.resetPosition(this);
            }
            //the resetPosition call can't be included in the for loop b/c it throws a concurrent modification exception
            Managers.resetAllPositions(this);
        } catch (Throwable ex) {
            System.err.println("Uncaught exception - " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
    }

    public void dmgHero(Hero target, int amt, Enemy attacker) {
        int curHP = target.getCurHP();
        double crit = attacker.getCritMod();

        amt = (int) (amt * (1 - target.getProt()));
        if (calcCrit(crit)) {
            amt = (int) (amt * 1.5);
            target.setCurHP(curHP - amt);
            for (Hero hero : heroRoster) {
                if (random() < .75) {
                    dmgStress(attacker, hero, critStressDmg);
                }
            }
            addLog(attacker.getClass().toString() + " crit " + target.getHeroDesc() + " for " + amt + " damage");
            target.setDamageTakenDirect(amt);
        } else {
            target.setCurHP(curHP - amt);
            addLog(attacker.getClass().toString() + " hit " + target.getHeroDesc() + " for " + amt + " damage");
            target.setDamageTakenDirect(amt);
        }

        //this code checks for Death's Door (both if a deathblow check is needed
        //and if Death's Door needs to be applied)
        if (target.getCurHP() < 1) {
            if (checkSpecificForDebuff(target, "Death's Door")) {
                if (calcRes(target.getDeathRes())) {
                    target.setCurHP(0);
                    addLog(target.getHeroName() + " survived the death blow.");
                } else {
                    purgeTheDead(target);
                }
            } else {
                target.setCurHP(0);
                Managers.addHelpfulEffect(target, "Death's Door", 100, target);
                //this isn't actually a helpful effect but it doesn't have a caster and can't miss
                //this is set to 100 so that it will functionally last until the Hero dies
                //or is healed.
            }
        }
    }

    public void dmgEnemy(Enemy target, int amt, Hero attacker, boolean ignoreProt) {
        int curHP = target.getCurHP();
        double crit = attacker.getCrit() + attacker.getCritMod();
        if (!ignoreProt) {
            amt = (int) (amt * (1 - target.getProt()));
        }
//            double crit = attacker.getCritTotal();

        if (calcCrit(crit)) {
            amt = (int) (amt * 1.5);
            target.setCurHP(curHP - amt);
            addLog(attacker.getHeroDesc() + " crit " + target.getClass().toString() + " for " + amt + " damage");
            attacker.setDamageDealtDirect(amt);
            attacker.setCrits(1);
        } else {
            target.setCurHP(curHP - amt);
            addLog(attacker.getHeroDesc() + " hit " + target.getClass().toString() + " for " + amt + " damage");
            attacker.setDamageDealtDirect(amt);
        }

        if (target.getCurHP() < 1) {
            purgeTheDead(target);
        }
    }

    public void dmgEnemy(Enemy target, int amt, Hero attacker) {
        dmgEnemy(target, amt, attacker, false);
    }

    private void purgeTheDead(Hero hero) {
        System.out.println("purgeTheDead called for " + hero.getHeroName());
//        heroRoster.remove(hero);
        combatRoster.remove(hero);
        addLog(hero.getHeroClass() + " " + hero.getHeroName() + " has died!");
    }

    private void purgeTheDead(Enemy enemy) {
//        System.out.println("purgeTheDead called for " + enemy.getClass().getName());
        enemyRoster.remove(enemy);
        combatRoster.remove(enemy);
        addLog(enemy.getClass().getName() + " has died!");
    }

    public static boolean tryAttackByHero(Hero attacker, Enemy target) {
        attacker.setAttacksMade(1);
        if (target != null) {
            boolean result = calcHit(target.getDodge(), attacker.getAcc() + attacker.getAccMod());
            if (result == false) {
                addLog(attacker.getHeroClass() + " " + attacker.getHeroName() + " missed " + target.getClass().getName());
                attacker.setMisses(1);
            }
            return result;
        }
        return false;
    }

    public static boolean tryAttackByEnemy(Enemy attacker, Hero target) {
        if (target != null) {
            boolean result = calcHit(target.getDodge(), attacker.getAccMod() + attacker.getAcc());
            if (result == false) {
                addLog(attacker.getClass().toString() + " missed " + target.getHeroDesc());
            }
            return result;
        }
        return false;
    }

    /**
     * This method attempts to hit heroes in the specified positions.
     *
     *
     * @param front    The foremost position that is being attacked.
     * @param back     The hindmost position that is being attacked.
     * @param amt
     * @param attacker
     * @return An arraylist containing the Heroes that were hit by the attack.
     */
    public ArrayList<Hero> dmgHeroMulti(int front, int back, int amt, Enemy attacker) {
//        dmgHeroMulti(front, back, amt, attacker, 0);
        ArrayList<Hero> hits = new ArrayList();
        for (int i = front - 1; i < back - 1; i++) {
            Hero target = heroRoster.get(i);
            if (tryAttackByEnemy(attacker, target)) {
                hits.add(target);
                dmgHero(target, amt, attacker);
            }
        }
        return hits;
    }

    public ArrayList<Enemy> dmgEnemyMulti(int front, int back, int amt, Hero attacker) {
        ArrayList<Enemy> hits = new ArrayList();
        //validation in case the range is outside of the current roster
        if (enemyRoster.size() < back - front) {
            front = 0;
            back = enemyRoster.size() - 1;
        }
        for (int i = front - 1; i < back - 1; i++) {
            if (i < enemyRoster.size()) {
                Enemy target = enemyRoster.get(i);
                if (tryAttackByHero(attacker, target)) {
                    hits.add(target);
                    dmgEnemy(target, amt, attacker);
                }
            }

        }
        return hits;
    }

    public void healHero(Hero user, Hero target, int amt) {
        int curHP = target.getCurHP();
        double crit = user.getCrit() + user.getCritMod();

        if (calcCrit(crit)) {
            target.setCurHP((int) (curHP + amt * 1.5));
            user.setHealingDone((int) (amt * 1.5));
        } else {
            target.setCurHP(curHP + amt);
            user.setHealingDone(amt);
        }
        addLog(user.getHeroClass() + " " + user.getHeroName() + " did " + amt + " healing to " + target.getHeroName() + " (" + target.getHeroClass() + ")");

        if (target.getCurHP() > target.getMaxHP()) {
            target.setCurHP(target.getMaxHP());
        }
    }

    public void healEnemy(Enemy user, Enemy target, int amt) {
        int curHP = target.getCurHP();
        double crit = user.getCritMod() + user.getCrit();

        if (calcCrit(crit)) {
            target.setCurHP((int) (curHP + amt * 1.5));
        } else {
            target.setCurHP(curHP + amt);
        }
        addLog(user.getClass().toString() + " healed " + target.getClass().toString() + " for " + amt + " .");

        if (target.getCurHP() > target.getMaxHP()) {
            target.setCurHP(target.getMaxHP());
        }
    }

    public void dmgStress(Enemy attacker, Hero target, int amt) {
        //TODO this will need to take into account the target's stress related traits,
        //plus torch level eventually (I think). It will, once it's done, call a 
        //method to check the target's current stress level.

        target.setStressLvl(target.getStressLvl() + amt);
        checkStress(target);
        addLog(target.getHeroClass() + " " + target.getHeroName() + " suffered " + amt + " stress damage!");

    }

    public void moveAttack(Hero attacker, Enemy target, int move) {
        if (calcHit(attacker.getAcc() + attacker.getAccMod(), target.getMoveRes())) {
            moveSelf(target, move);
        }
    }

    public void moveAttack(Enemy attacker, Hero target, int move) {
        if (calcHit(attacker.getAcc() + attacker.getAccMod(), target.getMoveRes())) {
            moveSelf(target, move);
        }
    }

    public void moveSelf(Hero user, int move) {

        int curPos = heroRoster.indexOf(user) + 1;
        if (curPos > 4) {
            curPos = 4;
        } else if (curPos < 1) {
            curPos = 1;
        }
        heroRoster.remove(user);

        int newPos = curPos + move - 1;
        if (newPos > heroRoster.size()) {
            heroRoster.add(user);
        } else {
            heroRoster.add(curPos + move - 1, user);
        }
//        user.setPosition(heroRoster.indexOf(user) + 1);
        Managers.fixPositionsHeroes(heroRoster);

    }

    public void moveSelf(Enemy user, int move) {

        if (enemyRoster.size() > 1) {
            int curPos = enemyRoster.indexOf(user) + 1;
            if (curPos > 4) {
                curPos = 4;
            } else if (curPos < 1) {
                curPos = 1;
            }

            enemyRoster.remove(user);

            int newPos = curPos + move - 1;
            if (newPos > enemyRoster.size()) {
                enemyRoster.add(user);
            } else {
                enemyRoster.add(curPos + move - 1, user);
            }
//        user.setPosition(enemyRoster.indexOf(user) + 1);
        }

        Managers.fixPositionsEnemies(enemyRoster);
    }

    /**
     * This should only be used for un-opposed resistance checks (which I think
     * only means traps, unless they have accuracy too.
     *
     *
     * @param res Resistance
     * @return
     */
    public static boolean calcRes(double res) { //Calc Percent Chance
        return random() < res;
    }

    public static boolean calcCrit(double crit) {
        return random() < crit;
    }

    /**
     * The method that checks if an attack actually hits. It can also be used to
     * calculate an opposed resistance check.
     *
     *
     * @param dodge The target's dodge or resistance % as a decimal.
     * @param acc   The attacker's accuracy for the move as a decimal.
     * @return
     */
    public static boolean calcHit(double dodge, double acc) {
        //TODO Heroes have an extra hidden +5 ACC that I'm not accounting for right now.
        //not sure what the best way to deal with this is.
        double hitChance = (acc - dodge < .95) ? acc - dodge : .95;
        return random() < hitChance;
    }

    public boolean checkGroupHealth() {
        //this is sort of a misnomer, the goal is to return true if the group is essentially
        //less than topped off.

        int needHealing = 0;

//        needHealing = heroRoster.stream().filter((hero) -> (hero.getCurHP() - 1 < hero.getMaxHP())).map((_item) -> 1).reduce(needHealing, Integer::sum);
        for (Hero hero : heroRoster) {
            if (hero.getCurHP() < hero.getMaxHP()) {
                needHealing++;
            }
        }

        return needHealing >= 3;
    }

    private void sortBySpeed(ObservableList<Creature> roster) {
        Comparator<Creature> speedComp = (o1, o2) -> o2.getSpeed() - o1.getSpeed();
        roster.sort(speedComp);
//        System.out.println("Sorted by speed successfully");
    }

    public int getRound() {
        return round;
    }

    public static ObservableList<Hero> getHeroRoster() {
        return heroRoster;
    }

    public static ObservableList<Enemy> getEnemyRoster() {
        return enemyRoster;
    }

//    public HashMap<Integer, Hero> getHeroMap() {
//        return heroMap;
//    }
//    public HashMap<Integer, Enemy> getEnemyMap() {
//        return enemyMap;
//    }
    public static Hero getHeroInPosition(int pos) {
        if (pos > heroRoster.size()) {
            return null;
        } else {
            Hero hero = heroRoster.get(pos - 1);
            return hero;
        }

    }

    public static Enemy getEnemyInPosition(int pos) {
        if (pos > enemyRoster.size()) {
            return null;
        } else {
            return enemyRoster.get(pos - 1);
        }
    }

}
