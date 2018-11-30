/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tara
 */
public class CombatLog {

    private static BufferedWriter log;

    public CombatLog() {
        try {
            CombatLog.log = new BufferedWriter(new FileWriter("combatlog.txt"));
        } catch (IOException ex) {
            Logger.getLogger(CombatLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addLog(String text) {
        try {
            if (log == null) {
                CombatLog.log = new BufferedWriter(new FileWriter("combatlog.txt"));
            }
            log.write(text + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeLog() {
        try {
            log.close();
        } catch (IOException ex) {
            Logger.getLogger(CombatLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
