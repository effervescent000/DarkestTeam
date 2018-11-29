/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import static darkestteam.CombatLog.addLog;
import static java.lang.Math.random;
import java.util.ArrayList;
import darkestteam.enemies.*;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tara
 */
public class Zone {

    private String length;
    private String difficulty;
    private String zone;

    private int lightLvl;

    private int totalIterations;
    private int curIterations;
//    public ObservableValue<Double> progress;

    private int encounters;

    private int numHallSquares = 4;
    //this is the number of hall squares PER ROOM, not total
    private int numRooms;

    private ArrayList<Enemy> sharedEnemies = new ArrayList<>();
    private ArrayList<Enemy> coveEnemies = new ArrayList<>();
    private ArrayList<Enemy> ruinsEnemies = new ArrayList<>();
    private ArrayList<Enemy> warrensEnemies = new ArrayList<>();
    private ArrayList<Enemy> wealdEnemies = new ArrayList<>();

    private static ObservableList<Enemy> enemyRoster;

//    private Log cLog;
    public Zone(String length, String difficulty, String zone, Integer iterations) {

        this.length = length;
        this.difficulty = difficulty;
        this.zone = zone;
        this.totalIterations = iterations;
        curIterations = 0;
        encounters = 0;

        try {
            if (enemyRoster == null) {
                enemyRoster = FXCollections.observableArrayList();
            }

            System.out.println("Zone constructor called");

            if (null != length && difficulty != null && zone != null) {
                switch (length) {
                    case "Short":
                        this.numRooms = RandomFunctions.getRandomNumberInRange(3, 7);
                        //this is a complete guess, I don't know how this works
                        break;
                    case "Medium":
                        this.numRooms = RandomFunctions.getRandomNumberInRange(6, 12);
                        break;
                    case "Long":
                        this.numRooms = RandomFunctions.getRandomNumberInRange(8, 15);
                        break;
                    default:
                        System.out.println("Zone length variable not set properly");
                        break;
                }
            } else {
                System.out.println("Dungeon settings are invalid!");
            }

            //set Hero starting positions
            ObservableList<Hero> r = Rosters.getHeroRoster();
            for (int i = 0; i < Rosters.getHeroRoster().size(); i++) {
                r.get(i).setStartingPosition(i + 1);
            }

            Analyzer a = new Analyzer(totalIterations);
            while (curIterations < totalIterations) {
                runDungeon();
                a.getValues();
                Managers.resetAll();
                if (encounters > 1) {
                    curIterations++;
//                    progress = curIterations/totalIterations;
                }
            }
            CombatLog.closeLog();
            a.calcFinalValues();
            a.dumpFinalValues();
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }

    private void runDungeon() {
        //TODO this is basically the doRound() of the Zone class. This will
        //run through the map generate events. For the purposes of this sim,
        //I am just pretending the map is completely linear, without any
        //branching paths or w/e.

        addLog("Dungeon run started");
        for (int i = 0; i < numRooms; i++) {
            for (int j = 0; j < numHallSquares; j++) {
                addLog("Checking for encounters...\n");
                if (random() < .3) {
                    addLog("Random encounter!");
                    generateEnemies();
                    encounters++;
                    Combat combat = new Combat();
                }
            }
        }
//        addLog("Random encounter!");
//        generateEnemies();
//        Combat combat = new Combat();

        System.out.println("runDungeon() completed");

//        displayResults();
    }

    private void displayResults() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("darkestteam/AnalysisWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Results");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private void generateEnemies() {
        switch (zone) {
            case "Cove":
                fillRoster(coveEnemies);
                break;
            case "Ruins":
                fillRoster(ruinsEnemies);
                break;
            case "Warrens":
                fillRoster(warrensEnemies);
                break;
            case "Weald":
                fillRoster(wealdEnemies);
                break;
            default:
                System.out.println("Invalid zone");
                break;
        }
        //this sets position, because fillroster does not take position into account
        //their initial positions are effectively random but... w/e I don't wanna
        //deal with it right now
        //TODO add something to set up initial positions in a way that makes actual sense
        for (int i = 0; i < enemyRoster.size(); i++) {
            enemyRoster.get(i).setPosition(i + 1);
//            System.out.println(enemyRoster.get(i).getClass().getName() + " generated");
//            enemyMap.put(i + 1, enemyRoster.get(i));
        }
    }

    private void fillRoster(ArrayList<Enemy> zoneEnemies) {
        //the reason for clearing the arrays after use is to force the code
        //to generate a new instance of the enemies, in case we end up
        //selecting the same enemy more than once (eg two Cultist Brawlers)
        try {
//            if (enemyRoster.isEmpty()) {
//                populateArrays();
//            }
            while (enemyRoster.size() < 4) {
                populateArrays();
                int selection = (int) ((sharedEnemies.size() + zoneEnemies.size()) * random());
                if (selection > sharedEnemies.size() - 1) {
                    selection = selection - sharedEnemies.size();
                    enemyRoster.add(zoneEnemies.get(selection));
                    zoneEnemies.clear();
                } else {
                    enemyRoster.add(sharedEnemies.get(selection));
                    sharedEnemies.clear();
                }

            }
            for (Enemy enemy : enemyRoster) {
                if (enemy.getCurHP() < enemy.getMaxHP()) {
                    enemy.setCurHP(enemy.getMaxHP());
                }
            }

            //TODO reject duplicates of certain enemy types
            //-----------------------------------------------------------------
            //TODO factor in enemy size into this (so that Large enemies count
            //for 2, somehow
            //-----------------------------------------------------------------
            //TODO if the difficulty is set to Apprentice, we will need to reject
            //enemies which only appear on higher difficulties
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void populateArrays() {
        //these arrays are ordered in the same way as the lists are ordered on
        //the DD wiki by default
        if (sharedEnemies.isEmpty()) {
            sharedEnemies.add(new CultistBrawler(difficulty));
            sharedEnemies.add(new CultistAcolyte(difficulty));
            sharedEnemies.add(new BrigandCutthroat(difficulty));
            sharedEnemies.add(new BrigandFusilier(difficulty));
            sharedEnemies.add(new BrigandBloodletter(difficulty));
            sharedEnemies.add(new BrigandRaider(difficulty));
            sharedEnemies.add(new BrigandHunter(difficulty));
            sharedEnemies.add(new Madman(difficulty));
            sharedEnemies.add(new Maggot(difficulty));
            sharedEnemies.add(new Webber(difficulty));
            sharedEnemies.add(new Spitter(difficulty));
            sharedEnemies.add(new BoneRabble(difficulty));
//            sharedEnemies.add(new Ghoul(difficulty));
//            sharedEnemies.add(new Gargoyle(difficulty));
        }

        switch (zone) {
            case "Cove":
                if (coveEnemies.isEmpty()) {
                    coveEnemies.add(new PelagicGrouper(difficulty));
                    coveEnemies.add(new PelagicShaman(difficulty));
                    coveEnemies.add(new PelagicGuardian(difficulty));
                    coveEnemies.add(new SeaMaggot(difficulty));
                    coveEnemies.add(new DeepStinger(difficulty));
                    coveEnemies.add(new DrownedThrall(difficulty));
//                    coveEnemies.add(new UcaMajor(difficulty));
//                    coveEnemies.add(new SquiffyGhast(difficulty));
                }
                break;
            case "Ruins":
                if (ruinsEnemies.isEmpty()) {
                    ruinsEnemies.add(new BoneSoldier(difficulty));
                    ruinsEnemies.add(new BoneCourtier(difficulty));
                    ruinsEnemies.add(new BoneArbalest(difficulty));
                    ruinsEnemies.add(new BoneDefender(difficulty));
//                    ruinsEnemies.add(new BoneSpearman(difficulty));
//                    ruinsEnemies.add(new BoneCaptain(difficulty));
//                    ruinsEnemies.add(new BoneBearer(difficulty));
                }
                break;
            case "Warrens":
                if (warrensEnemies.isEmpty()) {
                    warrensEnemies.add(new SwineChopper(difficulty));
                    warrensEnemies.add(new SwineSlasher(difficulty));
                    warrensEnemies.add(new SwineWretch(difficulty));
                    warrensEnemies.add(new SwineDrummer(difficulty));
                    warrensEnemies.add(new CarrionEater(difficulty));
//                    warrensEnemies.add(new LargeCarrionEater(difficulty));
//                    warrensEnemies.add(new Swinetaur(difficulty));
//                    warrensEnemies.add(new SwineSkiver(difficulty));
                }
                break;
            case "Weald":
                if (wealdEnemies.isEmpty()) {
                    wealdEnemies.add(new Ectoplasm(difficulty));
                    wealdEnemies.add(new LargeEctoplasm(difficulty));
                    wealdEnemies.add(new RabidGnasher(difficulty));
                    wealdEnemies.add(new FungalScratcher(difficulty));
                    wealdEnemies.add(new FungalArtillery(difficulty));
//                    wealdEnemies.add(new Crone(difficulty));
//                    wealdEnemies.add(new UncleanGiant(difficulty));
//                    wealdEnemies.add(new HatefulVirago(difficulty));
//                    wealdEnemies.add(new NecroticFungus(difficulty));
                }
                break;
            default:
                System.out.println("Invalid zone");
                break;
        }
    }

    public String getDifficulty() {
        return difficulty;
    }

    public static ObservableList<Enemy> getEnemyRoster() {
        return enemyRoster;
    }

    public int getCurIterations() {
        return curIterations;
    }

    public int getLightLvl() {
        return lightLvl;
    }

    public int getTotalIterations() {
        return totalIterations;
    }

    public String getZone() {
        return zone;
    }
}
