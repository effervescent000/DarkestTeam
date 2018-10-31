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
public class Gargoyle extends Enemy implements ICombatMethods {

    public Gargoyle(String difficulty) {
        maxHP = 12;
        curHP = maxHP;
        
        unholy = true;
        beast = true;
        
        dodge = .1625;
        prot = .5;
        speed = 9;
        stunRes = .3;
        blightRes = .4;
        bleedRes = 1.2;
        debuffRes = .35;
        moveRes = .3;
    }

    
    
    @Override
    public void selectAction(Combat combat) {
        
    }
    
}
