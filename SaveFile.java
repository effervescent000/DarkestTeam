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

}
