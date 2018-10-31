/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.ICombatMethods;

/**
 *
 * @author Tara
 */
public class Ghoul extends Enemy implements ICombatMethods {

    public Ghoul(String difficulty) {
        this.maxHP = 42;
        this.dodge = .1375;
        this.prot = .4;
        this.speed = 6;
        
        this.unholy = true;
        
        this.stunRes = .7;
        this.blightRes = .4;
        this.bleedRes = .4;
        this.debuffRes = .4;
        this.moveRes = .82;
    }

    
    
    @Override
    public void selectAction(Combat combat) {
        
    }
    
}
