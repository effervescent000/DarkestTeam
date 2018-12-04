/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tara
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button addCharacterButton, RosterButton, ConfigButton, combatSimButton;

    @FXML
    private ComboBox zoneSelect, difficultySelect, lengthSelect, iterationsBox;

    private ObservableList<String> difficulty = FXCollections.observableArrayList();
    private ObservableList<String> zone = FXCollections.observableArrayList();
    private ObservableList<String> length = FXCollections.observableArrayList();
    private ObservableList<Integer> iterations = FXCollections.observableArrayList();

    @FXML
    private ProgressBar pb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        length.addAll("Short", "Medium", "Long");
        difficulty.addAll("Apprentice", "Veteran", "Champion");
        zone.addAll("Cove", "Ruins", "Warrens", "Weald");
        iterations.addAll(1, 10, 50, 100, 500, 1000);

        lengthSelect.setItems(length);
        zoneSelect.setItems(zone);
        difficultySelect.setItems(difficulty);
        iterationsBox.setItems(iterations);
        
//        pb.progressProperty().addListener(listener);

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(addCharacterButton)) {
//            System.out.println("You clicked the Add Character button");
            Parent root;
            try {
//                System.out.println("Made it to the try block");
                root = FXMLLoader.load(getClass().getClassLoader().getResource("darkestteam/AddCharacterWindow.fxml"));
                Stage stage = new Stage();
                stage.setTitle("New Hero");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        } else if (event.getSource().equals(RosterButton)) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("darkestteam/RosterWindow.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Current roster");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        } else if (event.getSource().equals(combatSimButton)) {
            Zone z = new Zone((String) lengthSelect.getSelectionModel().getSelectedItem(),
                    (String) difficultySelect.getSelectionModel().getSelectedItem(),
                    (String) zoneSelect.getSelectionModel().getSelectedItem(),
                    (Integer) iterationsBox.getSelectionModel().getSelectedItem(), 
                    this);
        }
        // TODO add elseif block here for GameConfig settings (need to actually create this fxml file first)
    }

    @FXML
    public void setPB(double percent) {
        pb.setProgress(percent);
    }
}
