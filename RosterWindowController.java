/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tara
 */
public class RosterWindowController implements Initializable {

    @FXML
    private TableView<Hero> rosterDump, selRosterTable;

    @FXML
    private TableColumn<Hero, String> nameColumn, classColumn, nameSelectedCol, classSelectedCol;
    @FXML
    private TableColumn<Hero, Integer> lvlColumn, lvlSelectedCol;

    private ObservableList<Hero> selRosterList;

    private Rosters roster;
    private IOSave ioSave = new IOSave();

    @FXML
    private Button addToRosterButton, removeFromRosterButton, finalizeButton, 
            removeButton, editButton, exportButton, importButton, upButton, downButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        testSetUp();
//        ioSave = new IOSave();
        roster = Rosters.getInstance();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("heroName"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("heroClass"));
        lvlColumn.setCellValueFactory(new PropertyValueFactory<>("resolveLvl"));
        rosterDump.setItems(roster.getHeroArray());

        nameSelectedCol.setCellValueFactory(new PropertyValueFactory<>("heroName"));
        classSelectedCol.setCellValueFactory(new PropertyValueFactory<>("heroClass"));
        lvlSelectedCol.setCellValueFactory(new PropertyValueFactory<>("resolveLvl"));

        if (roster.getHeroRoster().isEmpty()) {
            if (selRosterList == null) {
                selRosterList = FXCollections.observableArrayList();
            }
            selRosterTable.setItems(selRosterList);
        } else {
            selRosterTable.setItems(roster.getHeroRoster());
        }

    }

    private void testSetUp() {
        roster.addHero("Melania", "Hellion", 1);
        roster.addHero("Max", "Crusader", 1);
        roster.addHero("Mary", "Vestal", 2);
        roster.addHero("Miri", "Arbalest", 1);

        roster.setHeroRoster(roster.getHeroArray());

    }

    @FXML
    private void handleMouse(MouseEvent event) {
        // if I can figure out how to work this, then double-clicking a hero
        // should open an editing pane
    }

    @FXML
    private void handleButtonEvent(ActionEvent event) {
        if (event.getSource().equals(addToRosterButton)) {
            Hero selection = rosterDump.getSelectionModel().getSelectedItem();
            if (!selRosterList.contains(selection)) {
                selRosterList.add(selection);
            }
        } else if (event.getSource().equals(removeFromRosterButton)) {
            Hero selection = selRosterTable.getSelectionModel().getSelectedItem();
            selRosterList.remove(selection);
        } else if (event.getSource().equals(finalizeButton)) {
            roster.setHeroRoster(selRosterList);

            Stage stage = (Stage) finalizeButton.getScene().getWindow();
            stage.close();

        } else if (event.getSource().equals(removeButton)) {
            Hero selection = rosterDump.getSelectionModel().getSelectedItem();
            Rosters.getInstance().removeHero(selection);
        } else if (event.getSource().equals(editButton)) {
            try {
                Hero myHero = rosterDump.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCharacterWindow.fxml"));
                GridPane gridPane = loader.load();
                AddCharacterController controller = loader.getController();
                //as I understand it, now that I've done that I should be able to set variables in the controller
                controller.setMyHero(myHero);
                Scene scene = new Scene(gridPane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(RosterWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (event.getSource().equals(exportButton)) {
            ioSave.buildXML();
            ioSave.dumpHeroes();
        } else if (event.getSource().equals(importButton)) {
//            ioSave.readXML();
            ioSave.parseSave();
        }
    }

}
