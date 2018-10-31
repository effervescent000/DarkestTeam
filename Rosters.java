/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tara
 */
public class Rosters {

    private static ObservableList<Hero> heroArray = FXCollections.observableArrayList();
    private static ObservableList<Hero> heroRoster = FXCollections.observableArrayList();

    private static HashMap<Integer, Hero> heroMap = new HashMap(4);

//    private String heroName;
//    private String heroClass;
//    private int lvl;
    public Rosters() {
    }

    public void addHero(String heroName, String heroClass, int Lvl) {
        heroArray.add(new Hero(heroName, heroClass, Lvl));
    }

    public static ObservableList<Hero> getHeroArray() {
        return heroArray;
    }

    public static ObservableList<Hero> getHeroRoster() {
        return heroRoster;
    }

    public static void removeHero(Hero hero) {
        if (heroArray.contains(hero)) {
            heroArray.remove(hero);
            if (heroRoster.contains(hero)) {
                heroRoster.remove(hero);
            }
        } else {
            System.out.println("removeHero: Hero is not valid!");
        }
    }

    public static void setHeroRoster(ObservableList<Hero> heroRoster) {
//        Rosters.heroRoster.setAll(heroRoster);
        Rosters.heroRoster = heroRoster;

        setStartingPositions();
    }

    public static void setStartingPositions() {
        //ok I was at first trying to do this so that the code would select preferred
        //positions based on hero class and it was a huge pain and clunky and bad
        //so I am just pulling it from the heroRoster array. For the future:
        //TODO add buttons in the UI to move heroes around in the Roster, since it
        //determines initial position.
        if (heroRoster != null) {
            for (int i = 0; i < heroRoster.size(); i++) {
                heroRoster.get(i).setPosition(i + 1);
//                heroMap.put(i + 1, heroRoster.get(i));
            }
        } else {
            System.out.println("Somehow heroRoster is null????");
        }

    }

    public static HashMap getHeroMap() {
        return heroMap;
    }

    public void setHeroArray(ObservableList<Hero> heroArray) {
//        Rosters.heroArray.setAll(heroArray);
        Rosters.heroArray = heroArray;
    }

//    public static Hero getHeroInPosition(int pos) {
//        Hero hero = heroRoster.get(pos -1);
//        if (hero != null) {
//            return hero;
//        }
//        return null;
//    }
}
