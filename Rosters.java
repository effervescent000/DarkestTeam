/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tara
 */
@XmlRootElement
public final class Rosters {

    private static Rosters INSTANCE;

    public static Rosters getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Rosters();
        }
        return INSTANCE;
    }

    private ObservableList<Hero> heroArray = FXCollections.observableArrayList();
    private ObservableList<Hero> heroRoster = FXCollections.observableArrayList();

    private HashMap<Integer, Hero> heroMap = new HashMap(4);

//    private String heroName;
//    private String heroClass;
//    private int lvl;
    private Rosters() {
    }

    public Hero addHero(String heroName, String heroClass, int Lvl) {
        Hero newHero = new Hero(heroName, heroClass, Lvl);
        heroArray.add(newHero);
        return newHero;
    }

    public ObservableList<Hero> getHeroArray() {
        return heroArray;
    }

    @XmlElement
    public ObservableList<Hero> getHeroRoster() {
        return heroRoster;
    }

    public void removeHero(Hero hero) {
        if (heroArray.contains(hero)) {
            heroArray.remove(hero);
            if (heroRoster.contains(hero)) {
                heroRoster.remove(hero);
            }
        } else {
            System.out.println("removeHero: Hero is not valid!");
        }
    }

    public void setHeroRoster(ObservableList<Hero> heroRoster) {
//        Rosters.heroRoster.setAll(heroRoster);
        this.heroRoster = heroRoster;

        setStartingPositions();
    }

    public void setStartingPositions() {
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

    public HashMap getHeroMap() {
        return heroMap;
    }

    public void setHeroArray(ObservableList<Hero> heroArray) {
//        Rosters.heroArray.setAll(heroArray);
        this.heroArray = heroArray;
    }

}
