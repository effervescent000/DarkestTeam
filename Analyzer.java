/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.util.ArrayList;
import java.util.TreeMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Tara
 */
public class Analyzer {

    private int totalDmgDealt;
    private int totalDmgTaken;
    private int avgEndingStress;
    private int totalStress;
    private int totalHealingDone;

    private int iterations;

    private Hero pos1;
    private Hero pos2;
    private Hero pos3;
    private Hero pos4;

    /**
     * I'm not 100% sure I want to use arrays for this, in the short term I
     * think just adding everything into a single int and then dividing that
     * would be simpler, BUT I am trying to keep in mind future expansion for
     * this, where I might be doing some kind of more detailed analysis of the
     * results, in which case I need to preserve each value, so for now at least
     * I'm leaving it like this
     */
    private ArrayList<Integer> cumulativeDmgDealt = new ArrayList();
    private ArrayList<Integer> cumulativeDmgTaken = new ArrayList();
    private ArrayList<Integer> cumulativeAvgEndingStress = new ArrayList();
    private ArrayList<Integer> cumulativeStress = new ArrayList();
    private ArrayList<Integer> cumulativeHealingDone = new ArrayList();
//    private TreeMap<Integer, ArrayList<Hero>> iterationMap = new TreeMap<>();

    public Analyzer(int iterations) {
        this.iterations = iterations;
        ObservableList<Hero> r = Rosters.getInstance().getSelectedHeroes();

        pos1 = r.get(0);
        pos2 = r.get(1);
        pos3 = r.get(2);
        pos4 = r.get(3);

    }

    public void getValues() {
//        ObservableList<Hero> r = Combat.getHeroRoster();

        totalDmgDealt = pos1.getDamageDealtDirect() + pos1.getDamageDealtDOT()
                + pos2.getDamageDealtDirect() + pos2.getDamageDealtDOT()
                + pos3.getDamageDealtDirect() + pos3.getDamageDealtDOT()
                + pos4.getDamageDealtDirect() + pos4.getDamageDealtDOT();

        totalDmgTaken = pos1.getDamageTakenDirect() + pos1.getDamageTakenDOT()
                + pos2.getDamageTakenDirect() + pos2.getDamageTakenDOT()
                + pos3.getDamageTakenDirect() + pos3.getDamageTakenDOT()
                + pos4.getDamageTakenDirect() + pos4.getDamageTakenDOT();

        totalStress = pos1.getStressLvl() + pos2.getStressLvl() + pos3.getStressLvl() + pos4.getStressLvl();

        avgEndingStress = totalStress / 4;

        totalHealingDone = pos1.getHealingDone() + pos2.getHealingDone() + pos3.getHealingDone() + pos4.getHealingDone();

        addValues();
    }

    public void addValues() {
        cumulativeDmgDealt.add(totalDmgDealt);
        cumulativeDmgTaken.add(totalDmgTaken);
        cumulativeAvgEndingStress.add(avgEndingStress);
        cumulativeStress.add(totalStress);
        cumulativeHealingDone.add(totalHealingDone);
    }

    public void calcFinalValues() {
        int dmgDone = 0;
        int dmgTaken = 0;
        int avgStress = 0;
        int ttlStress = 0;
        int healing = 0;

        for (Integer i : cumulativeDmgDealt) {
            dmgDone += i;
        }

        for (Integer i : cumulativeDmgTaken) {
            dmgTaken += i;
        }

        for (Integer i : cumulativeAvgEndingStress) {
            avgStress += i;
        }

        for (Integer i : cumulativeStress) {
            ttlStress += i;
        }

        for (Integer i : cumulativeHealingDone) {
            healing += i;
        }

        totalDmgDealt = dmgDone / iterations;
        totalDmgTaken = dmgTaken / iterations;
        avgEndingStress = avgStress / iterations;
        totalStress = ttlStress / iterations;
        totalHealingDone = healing / iterations;
    }

    public void dumpFinalValues() {
        System.out.println("Average damage dealt: " + totalDmgDealt);
        System.out.println("Average damage taken: " + totalDmgTaken);
        System.out.println("Average healing done: " + totalHealingDone);
        System.out.println("Average ending stress: " + avgEndingStress);
        System.out.println("Average total stress: " + totalStress);
    }

    public ArrayList<Integer> getCumulativeDmgDealt() {
        return cumulativeDmgDealt;
    }

    public void setAvgEndingStress(int avgEndingStress) {
        this.avgEndingStress = avgEndingStress;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setTotalDmgDealt(int totalDmgDealt) {
        this.totalDmgDealt = totalDmgDealt;
    }

    public void setTotalDmgTaken(int totalDmgTaken) {
        this.totalDmgTaken = totalDmgTaken;
    }

    public void setTotalHealingDone(int totalHealingDone) {
        this.totalHealingDone = totalHealingDone;
    }

    public void setTotalStress(int totalStress) {
        this.totalStress = totalStress;
    }

}
