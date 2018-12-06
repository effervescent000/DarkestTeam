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
import javax.xml.bind.annotation.XmlElements;
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

    private ObservableList<Hero> heroBench = FXCollections.observableArrayList();
    private ObservableList<Hero> selectedHeroes = FXCollections.observableArrayList();

    private HashMap<Integer, Hero> heroMap = new HashMap(4);

//    private String heroName;
//    private String heroClass;
//    private int lvl;
    private Rosters() {
    }

    public Hero addHero(String heroName, String heroClass, int Lvl) {
        Hero newHero = new Hero(heroName, heroClass, Lvl);
        heroBench.add(newHero);
        return newHero;
    }

    @XmlElements({
        @XmlElement(name = "Hero")})
    public ObservableList<Hero> getHeroBench() {
        return heroBench;
    }

    public ObservableList<Hero> getSelectedHeroes() {
        return selectedHeroes;
    }

    public void removeHero(Hero hero) {
        if (heroBench.contains(hero)) {
            heroBench.remove(hero);
            if (selectedHeroes.contains(hero)) {
                selectedHeroes.remove(hero);
            }
        } else {
            System.out.println("removeHero: Hero is not valid!");
        }
    }

    public static void setSelectedHeroes(ObservableList<Hero> selectedHeroes) {
//        Rosters.heroRoster.setAll(heroRoster);
        selectedHeroes.forEach((hero) -> {
            if (hero.getMyHero() == null) {
                hero.initMyHero();
            }
        });
        getInstance().selectedHeroes = selectedHeroes;

        getInstance().setStartingPositions();
    }

    public void setStartingPositions() {
        //ok I was at first trying to do this so that the code would select preferred
        //positions based on hero class and it was a huge pain and clunky and bad
        //so I am just pulling it from the heroRoster array.
        if (selectedHeroes != null) {
            for (int i = 0; i < selectedHeroes.size(); i++) {
                selectedHeroes.get(i).setPosition(i + 1);
//                heroMap.put(i + 1, heroRoster.get(i));
            }
        } else {
            System.out.println("Somehow heroRoster is null????");
        }

    }

    public HashMap getHeroMap() {
        return heroMap;
    }

    public static void setHeroBench(ObservableList<Hero> heroBench) {
//        Rosters.heroArray.setAll(heroArray);
        getInstance().heroBench = heroBench;
    }

}
