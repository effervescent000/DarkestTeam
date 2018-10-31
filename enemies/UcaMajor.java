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
public class UcaMajor extends Enemy implements ICombatMethods {

    public UcaMajor(String difficulty) {
        maxHP = 61;
        dodge = .875;
        prot = .5;
        speed = 1;
        
        eldritch = true;
        
        stunRes = .7;
        blightRes = .3;
        bleedRes = .8;
        debuffRes = .4;
        moveRes = .7;
        
    }

    
    
    @Override
    public void selectAction(Combat combat) {
        
    }
    
}
