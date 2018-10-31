/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import static darkestteam.Rosters.getHeroRoster;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        damageDealtData = FXCollections.observableArrayList(
                new PieChart.Data(getHeroRoster().get(0).getHeroDesc(), getHeroRoster().get(0).getDamageDealtDirect() + getHeroRoster().get(0).getDamageDealtDOT()),
                new PieChart.Data(getHeroRoster().get(1).getHeroDesc(), getHeroRoster().get(1).getDamageDealtDirect() + getHeroRoster().get(1).getDamageDealtDOT()),
                new PieChart.Data(getHeroRoster().get(2).getHeroDesc(), getHeroRoster().get(2).getDamageDealtDirect() + getHeroRoster().get(2).getDamageDealtDOT()),
                new PieChart.Data(getHeroRoster().get(3).getHeroDesc(), getHeroRoster().get(3).getDamageDealtDirect() + getHeroRoster().get(3).getDamageDealtDOT()));
        damageTakenData = FXCollections.observableArrayList(
                new PieChart.Data(getHeroRoster().get(0).getHeroDesc(), getHeroRoster().get(0).getDamageTakenDirect() + getHeroRoster().get(0).getDamageTakenDOT()),
                new PieChart.Data(getHeroRoster().get(1).getHeroDesc(), getHeroRoster().get(1).getDamageTakenDirect() + getHeroRoster().get(1).getDamageTakenDOT()),
                new PieChart.Data(getHeroRoster().get(2).getHeroDesc(), getHeroRoster().get(2).getDamageTakenDirect() + getHeroRoster().get(2).getDamageTakenDOT()),
                new PieChart.Data(getHeroRoster().get(3).getHeroDesc(), getHeroRoster().get(3).getDamageTakenDirect() + getHeroRoster().get(3).getDamageTakenDOT()));

        damageDealt.setData(damageDealtData);
        damageTaken.setData(damageTakenData);

        damageDoneLabel.setText(Integer.toString(getHeroRoster().get(0).getDamageDealtDirect() + getHeroRoster().get(0).getDamageDealtDOT()
                + getHeroRoster().get(1).getDamageDealtDirect() + getHeroRoster().get(1).getDamageDealtDOT()
                + getHeroRoster().get(2).getDamageDealtDirect() + getHeroRoster().get(2).getDamageDealtDOT()
                + getHeroRoster().get(3).getDamageDealtDirect() + getHeroRoster().get(3).getDamageDealtDOT()));
        damageTakenLabel.setText(Integer.toString(getHeroRoster().get(0).getDamageTakenDirect() + getHeroRoster().get(0).getDamageTakenDOT()
                + getHeroRoster().get(1).getDamageTakenDirect() + getHeroRoster().get(1).getDamageTakenDOT()
                + getHeroRoster().get(2).getDamageTakenDirect() + getHeroRoster().get(2).getDamageTakenDOT()
                + getHeroRoster().get(3).getDamageTakenDirect() + getHeroRoster().get(3).getDamageTakenDOT()));

    }

}
