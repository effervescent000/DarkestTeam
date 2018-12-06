/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Tara
 */
public class SaveFile {

    private static final String fn = "roster.xml";

    private ObservableList<Hero> heroArray = FXCollections.observableArrayList();
    private Rosters roster;

    public SaveFile() {
        if (roster == null) {
            roster = Rosters.getInstance();
        }
        if (roster.getHeroBench() != null) {
            heroArray = roster.getHeroBench();
        }
//        roster = new Rosters();
    }

    public boolean buildXML(Rosters roster) {
        try {
            File file = new File(fn);
            JAXBContext jax = JAXBContext.newInstance(roster.getClass());
            Marshaller m = jax.createMarshaller();

            //this sets pretty printing
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            m.marshal(roster, file);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Rosters readXML() {
        try {
            File file = new File(fn);
            JAXBContext jax = JAXBContext.newInstance(Rosters.class);

            Unmarshaller u = jax.createUnmarshaller();
            roster = (Rosters) u.unmarshal(file);
            return roster;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public void dumpHeroes() {
        //the goal here is to make sure that the save file is empty before we write to it
        try (FileWriter save = new FileWriter("savefile.txt")) {
            save.write("");
            save.close();
        } catch (IOException ex) {
            Logger.getLogger(Rosters.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (FileWriter save = new FileWriter("savefile.txt", true)) {
            for (Hero hero : heroArray) {
//                save.write("BEGIN HERO:\n");
                save.write(hero.getHeroClass() + "," + hero.getHeroName() + "," + hero.getResolveLvl() + ",");
                save.write(hero.getMove1Rank() + "," + hero.getMove2Rank() + "," + hero.getMove3Rank()
                        + "," + hero.getMove4Rank() + "," + hero.getMove5Rank() + "," + hero.getMove6Rank()
                        + "," + hero.getMove7Rank() + "\n");
                //Don't need to bother exporting base acc/crit rates because
                //they are tied to abilities, so they'll be set every time an ability
                //is used. Don't need totals either because we can just calculate
                //that
//                export.write("Acc: " + hero.getAccMod() + "\n");
//                export.write("Crit: " + hero.getCritMod() + "\n");
                //TODO finish all this shit
            }
        } catch (Exception ex) {
            Logger.getLogger(Rosters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void parseSave() {

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("savefile.txt")))) {
            scanner.useDelimiter(",");

            if (heroArray != null || !heroArray.isEmpty()) {
                heroArray.clear();
            }

            int counter = 0;

            while (scanner.hasNextLine()) {

                String input = scanner.nextLine();
                String[] data = input.split(",");
                String heroClass = data[0];
                String heroName = data[1];
                int resolveLvl = Integer.parseInt(data[2]);
                int move1Rank = Integer.parseInt(data[3]);
                int move2Rank = Integer.parseInt(data[4]);
                int move3Rank = Integer.parseInt(data[5]);
                int move4Rank = Integer.parseInt(data[6]);
                int move5Rank = Integer.parseInt(data[7]);
                int move6Rank = Integer.parseInt(data[8]);
                int move7Rank = Integer.parseInt(data[9]);

                heroArray.add(new Hero(heroName, heroClass, resolveLvl));
                Hero hero = heroArray.get(counter);
                hero.setMove1Rank(move1Rank);
                hero.setMove2Rank(move2Rank);
                hero.setMove3Rank(move3Rank);
                hero.setMove4Rank(move4Rank);
                hero.setMove5Rank(move5Rank);
                hero.setMove6Rank(move6Rank);
                hero.setMove7Rank(move7Rank);
                counter++;
//                data = null;
            }
            
            Rosters.setHeroBench(heroArray);
        } catch (IOException ex) {
            Logger.getLogger(Rosters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
