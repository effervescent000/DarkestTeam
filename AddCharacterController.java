/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tara
 */
public class AddCharacterController implements Initializable {

    private Hero myHero;
    private static Rosters roster;

    final ObservableList<String> classList = FXCollections.observableArrayList(
            "Abomination", "Antiquarian", "Arbalest", "Bounty Hunter",
            "Crusader", "Flagellant,", "Grave Robber", "Hellion",
            "Highwayman", "Houndmaster", "Jester", "Leper",
            "Man-at-Arms", "Occultist", "Plague Doctor", "Shieldbreaker",
            "Vestal"
    );

    private final ObservableList<String> resolveList = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6");
    private final ObservableList<Integer> rankList = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);

    @FXML
    private ComboBox classListBox, resolveListBox, move1Rank, move2Rank, move3Rank, move4Rank, move5Rank, move6Rank, move7Rank;

    @FXML
    private Button ConfirmButton, CancelButton;

    @FXML
    private TextField NameField;

    public AddCharacterController() {
        // Constructors confuse me in this case, I'm not sure why I need this? Do I need this?
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        classListBox.setItems(classList);
        resolveListBox.setItems(resolveList);

        move1Rank.setItems(rankList);
        move2Rank.setItems(rankList);
        move3Rank.setItems(rankList);
        move4Rank.setItems(rankList);
        move5Rank.setItems(rankList);
        move6Rank.setItems(rankList);
        move7Rank.setItems(rankList);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (event.getSource().equals(ConfirmButton)) {
            String selClass = (String) classListBox.getSelectionModel().getSelectedItem();
            int selResolve = Integer.parseInt((String) resolveListBox.getSelectionModel().getSelectedItem());
            String heroName = (String) NameField.getText();

            if (myHero != null) {
                //can't change class from here. If you want to change the class you might as well make a new hero.
                myHero.setHeroName(heroName);
                myHero.setResolveLvl(selResolve);
                myHero.setMove1Rank((int) move1Rank.getSelectionModel().getSelectedItem());
                myHero.setMove2Rank((int) move2Rank.getSelectionModel().getSelectedItem());
                myHero.setMove3Rank((int) move3Rank.getSelectionModel().getSelectedItem());
                myHero.setMove4Rank((int) move4Rank.getSelectionModel().getSelectedItem());
                myHero.setMove5Rank((int) move5Rank.getSelectionModel().getSelectedItem());
                myHero.setMove6Rank((int) move6Rank.getSelectionModel().getSelectedItem());
                myHero.setMove7Rank((int) move7Rank.getSelectionModel().getSelectedItem());
            } else {
                if (roster == null) {
                    roster = Rosters.getInstance();
                }
                myHero = roster.addHero(heroName, selClass, selResolve);
            }

            Stage stage = (Stage) ConfirmButton.getScene().getWindow();
            stage.close();
        } else if (event.getSource().equals(CancelButton)) {
            Stage stage = (Stage) CancelButton.getScene().getWindow();
            stage.close();
        }

    }

    public void setMyHero(Hero myHero) {
        this.myHero = myHero;

        classListBox.getSelectionModel().select(myHero.getHeroClass());
        NameField.setText(myHero.getHeroName());
        resolveListBox.getSelectionModel().select(myHero.getResolveLvl());

        move1Rank.getSelectionModel().select(myHero.getMove1Rank());
        move2Rank.getSelectionModel().select(myHero.getMove2Rank());
        move3Rank.getSelectionModel().select(myHero.getMove3Rank());
        move4Rank.getSelectionModel().select(myHero.getMove4Rank());
        move5Rank.getSelectionModel().select(myHero.getMove5Rank());
        move6Rank.getSelectionModel().select(myHero.getMove6Rank());
        move7Rank.getSelectionModel().select(myHero.getMove7Rank());
    }

//    public void setClassListBox(ComboBox classListBox) {
//        this.classListBox = classListBox;
//    }
//
//    public void setResolveListBox(ComboBox resolveListBox) {
//        this.resolveListBox = resolveListBox;
//    }
//    public void setMove1Rank(ComboBox move1Rank) {
//        this.move1Rank = move1Rank;
//    }
//
//    public void setMove2Rank(ComboBox move2Rank) {
//        this.move2Rank = move2Rank;
//    }
//
//    public void setMove3Rank(ComboBox move3Rank) {
//        this.move3Rank = move3Rank;
//    }
//
//    public void setMove4Rank(ComboBox move4Rank) {
//        this.move4Rank = move4Rank;
//    }
//
//    public void setMove5Rank(ComboBox move5Rank) {
//        this.move5Rank = move5Rank;
//    }
//
//    public void setMove6Rank(ComboBox move6Rank) {
//        this.move6Rank = move6Rank;
//    }
//
//    public void setMove7Rank(ComboBox move7Rank) {
//        this.move7Rank = move7Rank;
//    }
//
//    public void setNameField(TextField NameField) {
//        this.NameField = NameField;
//    }
}
