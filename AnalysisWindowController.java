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
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tara
 */
public class AnalysisWindowController implements Initializable {

    @FXML
    private PieChart damageDealt, damageTaken;

    @FXML
    private Label damageDoneLabel, damageTakenLabel;

    private ObservableList<PieChart.Data> damageDealtData, damageTakenData;
    private static Rosters roster;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (roster == null) {
            roster = Rosters.getInstance();
        }
        damageDealtData = FXCollections.observableArrayList(
                new PieChart.Data(roster.getHeroRoster().get(0).getHeroDesc(), roster.getHeroRoster().get(0).getDamageDealtDirect() + roster.getHeroRoster().get(0).getDamageDealtDOT()),
                new PieChart.Data(roster.getHeroRoster().get(1).getHeroDesc(), roster.getHeroRoster().get(1).getDamageDealtDirect() + roster.getHeroRoster().get(1).getDamageDealtDOT()),
                new PieChart.Data(roster.getHeroRoster().get(2).getHeroDesc(), roster.getHeroRoster().get(2).getDamageDealtDirect() + roster.getHeroRoster().get(2).getDamageDealtDOT()),
                new PieChart.Data(roster.getHeroRoster().get(3).getHeroDesc(), roster.getHeroRoster().get(3).getDamageDealtDirect() + roster.getHeroRoster().get(3).getDamageDealtDOT()));
        damageTakenData = FXCollections.observableArrayList(
                new PieChart.Data(roster.getHeroRoster().get(0).getHeroDesc(), roster.getHeroRoster().get(0).getDamageTakenDirect() + roster.getHeroRoster().get(0).getDamageTakenDOT()),
                new PieChart.Data(roster.getHeroRoster().get(1).getHeroDesc(), roster.getHeroRoster().get(1).getDamageTakenDirect() + roster.getHeroRoster().get(1).getDamageTakenDOT()),
                new PieChart.Data(roster.getHeroRoster().get(2).getHeroDesc(), roster.getHeroRoster().get(2).getDamageTakenDirect() + roster.getHeroRoster().get(2).getDamageTakenDOT()),
                new PieChart.Data(roster.getHeroRoster().get(3).getHeroDesc(), roster.getHeroRoster().get(3).getDamageTakenDirect() + roster.getHeroRoster().get(3).getDamageTakenDOT()));

        damageDealt.setData(damageDealtData);
        damageTaken.setData(damageTakenData);

        damageDoneLabel.setText(Integer.toString(roster.getHeroRoster().get(0).getDamageDealtDirect() + roster.getHeroRoster().get(0).getDamageDealtDOT()
                + roster.getHeroRoster().get(1).getDamageDealtDirect() + roster.getHeroRoster().get(1).getDamageDealtDOT()
                + roster.getHeroRoster().get(2).getDamageDealtDirect() + roster.getHeroRoster().get(2).getDamageDealtDOT()
                + roster.getHeroRoster().get(3).getDamageDealtDirect() + roster.getHeroRoster().get(3).getDamageDealtDOT()));
        damageTakenLabel.setText(Integer.toString(roster.getHeroRoster().get(0).getDamageTakenDirect() + roster.getHeroRoster().get(0).getDamageTakenDOT()
                + roster.getHeroRoster().get(1).getDamageTakenDirect() + roster.getHeroRoster().get(1).getDamageTakenDOT()
                + roster.getHeroRoster().get(2).getDamageTakenDirect() + roster.getHeroRoster().get(2).getDamageTakenDOT()
                + roster.getHeroRoster().get(3).getDamageTakenDirect() + roster.getHeroRoster().get(3).getDamageTakenDOT()));

    }

}
