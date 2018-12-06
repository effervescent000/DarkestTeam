/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Tara
 */
public class Checker {

    private Enemy enemyValue;
    private Hero heroValue;
    private int highest = Integer.MIN_VALUE;
    private int lowest = Integer.MAX_VALUE;

    public Checker() {
    }

    public static boolean amIGuarding(Hero user) {
        for (Hero hero : Rosters.getInstance().getSelectedHeroes()) {
            if (hero.getGuardian().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public static boolean amIGuarding(Enemy user) {
        for (Enemy enemy : Zone.getEnemyRoster()) {
            if (enemy != null
                    && enemy.getGuardian() != null
                    && enemy.getGuardian().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public static Enemy checkEnemiesForDebuff(String name, ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return checkEnemiesForDebuff(name, eList);
    }

    public static Enemy checkEnemiesForDebuff(String name, List<Enemy> list) {
        ArrayList<Debuff> d;
        for (Enemy enemy : list) {
            if (enemy != null) {
                d = enemy.getDebuffs();
                if (d != null && !d.isEmpty()) {
                    for (Debuff debuff : d) {
                        if (debuff.getName().equals(name)) {
                            return enemy;
                        }
                    }
                }
            }

        }
        return null;

//        for (int i = 0; i < list.size(); i++) {
//            ArrayList<Debuff> debuff = list.get(i).getDebuffs();
//            if (debuff != null) {
//                for (int j = 0; j < debuff.size(); j++) {
//                    if (debuff.get(j).getName().equals(name)) {
//                        return list.get(i);
//                    }
//                }
//            }
//        }
//        return null;
    }

    public static Enemy checkEnemiesForType(String type, ObservableList<Enemy> enemyRoster) {
        type = type.toLowerCase();
        for (Enemy enemy : enemyRoster) {
            switch (type) {
                case "human":
                    if (enemy.isHuman()) {
                        return enemy;
                    }
                    break;
                case "beast":
                    if (enemy.isBeast()) {
                        return enemy;
                    }
                    break;
                case "eldritch":
                    if (enemy.isEldritch()) {
                        return enemy;
                    }
                    break;
                case "unholy":
                    if (enemy.isUnholy()) {
                        return enemy;
                    }
                    break;
                default:
                    System.out.println("checkEnemiesForType problem!");
            }
        }
        return null;
    }

    public static Hero checkHeroesForDebuff(String name, ObservableList<Hero> heroRoster) {
        //this code is not great but it... suffices. The problem right now is
        //that it will only return the first match it finds, even if multiple
        //Heroes are valid matches. But I don't know how to do that right now,
        //maybe an ArrayList?
        for (int i = 0; i < heroRoster.size(); i++) {
            ArrayList<Debuff> debuff = heroRoster.get(i).getDebuffs();
            if (checkSpecificForDebuff(heroRoster.get(i), name)) {
                return heroRoster.get(i);
            }
        }
        return null;
    }

    /**
     * This method counts the number of bleed-causing abilities present in your
     * party. The goal of this is to enable Heroes to prioritize bleed resists
     * in parties with high bleed reliance (but to assign the same a lower
     * priority if a party uses bleeds only incidentally).
     *
     * @return The number of bleed-causing abilities in the roster (maximum 7).
     */
    public static int checkPartyCompForBleeds() {
        int count = 0;
        for (Hero hero : Rosters.getInstance().getSelectedHeroes()) {
            String hc = hero.getHeroClass();
            switch (hc) {
                case "Hellion":
                    if (hero.getMove4Rank() > 0) {
                        count++;
                    }
                    if (hero.getMove7Rank() > 0) {
                        count++;
                    }
                    break;
                case "Highwayman":
                    if (hero.getMove7Rank() > 0) {
                        count++;
                    }
                    break;
                case "Hound Master":
                    if (hero.getMove1Rank() > 0) {
                        count++;
                    }
                    if (hero.getMove2Rank() > 0) {
                        count++;
                    }
                    break;
                case "Jester":
                    if (hero.getMove2Rank() > 0) {
                        count++;
                    }
                    if (hero.getMove5Rank() > 0) {
                        count++;
                    }
                    break;
                case "Plague Doctor":
                    if (hero.getMove4Rank() > 0) {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    /**
     * This method counts the number of blight-causing abilities present in your
     * party. The goal of this is to enable Heroes to prioritize blight resists
     * in parties with high blight reliance (but to assign the same a lower
     * priority if a party uses blights only incidentally).
     *
     * Note that there are fewer blight-causing abilities in game than bleed, so
     * the maximum return value of this is lower than its bleed counterpart, but
     * also that (particularly with the Plague Doctor) blights also deal more
     * damage on average than bleeds.
     *
     * @return The number of blight-causing abilities in the roster (maximum 5).
     */
    public static int checkPartyCompForBlights() {
        int count = 0;
        for (Hero hero : Rosters.getInstance().getSelectedHeroes()) {
            String hc = hero.getHeroClass();
            switch (hc) {
                case "Grave Robber":
                    if (hero.getMove2Rank() > 0) {
                        //this move does not deal blight damage but it has +25% damage vs blighted enemies so it counts
                        count++;
                    }
                    if (hero.getMove5Rank() > 0) {
                        //this move does not deal blight damage but it has +25% damage vs blighted enemies so it counts
                        count++;
                    }
                    if (hero.getMove6Rank() > 0) {
                        count++;
                    }
                    break;
                case "Plague Doctor":
                    if (hero.getMove1Rank() > 0) {
                        count++;
                    }
                    if (hero.getMove2Rank() > 0) {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    /**
     * This method counts the number of Heroes in your party that have extra
     * modifiers when attacking marked-targets. Because most heroes that can
     * cast mark can themselves benefit from it, it's good to set the check for
     * this > 1 to prevent it from returning true solely from the caster
     * themselves.
     *
     *
     * @return The number of abilities in your party that have special
     *         interactions when used vs a marked target.
     */
    public static int checkPartyCompForMarked() {
        int count = 0;
        for (Hero hero : Rosters.getInstance().getSelectedHeroes()) {
            String hc = hero.getHeroClass();
            switch (hc) {
                case "Arbalest":
                    if (hero.getMove1Rank() > 0) {
                        count++;
                    }
                    break;
                case "Bounty Hunter":
                    if (hero.getMove1Rank() > 0) {
                        count++;
                    }
                    break;
                case "Grave Robber":
                    if (hero.getMove5Rank() > 0) {
                        count++;
                    }
                    break;
                case "Highwayman":
                    if (hero.getMove2Rank() > 0) {
                        count++;
                    }
                    break;
                case "Hound Master":
                    if (hero.getMove1Rank() > 0) {
                        count++;
                    }
                    break;
                case "Man-At-Arms":
                    if (hero.getMove3Rank() > 0) {
                        //this does not do more damage directly, but it gives the target a debuff that makes them more vulnerable to crits
                        count++;
                    }
                    break;
            }
        }
        return count;
    }

    public static boolean checkSpecificForDebuff(Hero hero, String name) {
        ArrayList<Debuff> debuffs = hero.getDebuffs();
        if (debuffs != null) {
            for (int j = 0; j < debuffs.size(); j++) {
                if (debuffs.get(j).getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkSpecificForDebuff(Enemy enemy, String name) {
        ArrayList<Debuff> debuffs = enemy.getDebuffs();
        if (debuffs != null) {
            for (int j = 0; j < debuffs.size(); j++) {
                if (debuffs.get(j).getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Enemy getHighestDangerEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getHighestDangerEnemy(eList);
    }

    public Enemy getHighestDangerEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null && enemy.getDanger() > highest) {
                highest = enemy.getDanger();
                enemyValue = enemy;
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Enemy getHighestHealthEnemy(ObservableList<Enemy> enemyRoster) {
        for (Enemy enemy : enemyRoster) {
            if (enemy != null && enemy.getCurHP() > highest) {
                this.highest = enemy.getCurHP();
                this.enemyValue = enemy;
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Enemy getHighestProtEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getHighestProtEnemy(eList);
    }

    public Enemy getHighestProtEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null && enemy.getProt() > highest) {
                highest = (int) (enemy.getProt() * 100);
                enemyValue = enemy;
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Hero getHighestStressHero(ObservableList<Hero> heroRoster) {
        for (Hero hero : heroRoster) {
            if (hero.getStressLvl() < lowest) {
                lowest = hero.getStressLvl();
                this.heroValue = hero;
            }
        }
        resetDefaults();
        return heroValue;
    }

    public Enemy getLowestBleedResEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getLowestBleedResEnemy(eList);
    }

    public Enemy getLowestBleedResEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null) {
                int res = (int) (enemy.getBleedRes() * 100);
                if (res < lowest) {
                    lowest = res;
                    enemyValue = enemy;
                }
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Enemy getLowestBlightResEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getLowestBlightResEnemy(eList);
    }

    public Enemy getLowestBlightResEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null) {
                int res = (int) (enemy.getBlightRes() * 100);
                if (res < lowest) {
                    lowest = res;
                    enemyValue = enemy;
                }
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Enemy getLowestProtEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getLowestProtEnemy(eList);
    }

    public Enemy getLowestProtEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null) {
                int prot = (int) (enemy.getProt() * 100);
                if (prot < lowest) {
                    lowest = prot;
                    enemyValue = enemy;
                }
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Enemy getLowestHealthEnemy(ObservableList<Enemy> enemyRoster) {
        ArrayList<Enemy> el = new ArrayList<>();
        el.addAll(enemyRoster);
        return getLowestHealthEnemy(el);
    }

    public Enemy getLowestHealthEnemy(ArrayList<Enemy> el) {
        for (Enemy enemy : el) {
            if (enemy != null && enemy.getCurHP() < lowest) {
                this.lowest = enemy.getCurHP();
                this.enemyValue = enemy;
            }
        }
        resetDefaults();
        return enemyValue;
    }

    public Hero getLowestHealthHero(ObservableList<Hero> heroRoster) {
        for (Hero hero : heroRoster) {
            if (hero.getCurHP() < lowest) {
                this.lowest = hero.getCurHP();
                this.heroValue = hero;
            }
        }
        resetDefaults();
        return heroValue;
    }

    public Enemy getMostMoveableEnemy(ObservableList<Enemy> list) {
        ArrayList<Enemy> eList = new ArrayList();
        eList.addAll(list);
        return getMostMoveableEnemy(eList);
    }

    public Enemy getMostMoveableEnemy(List<Enemy> list) {
        for (Enemy enemy : list) {
            if (enemy != null && enemy.getMoveable() > highest) {
                highest = enemy.getMoveable();
                enemyValue = enemy;
            }
        }
        resetDefaults();
        return enemyValue;
    }

    private void resetDefaults() {
        highest = Integer.MIN_VALUE;
        lowest = Integer.MAX_VALUE;
    }

}
