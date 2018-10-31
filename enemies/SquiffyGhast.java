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
public class SquiffyGhast extends Enemy implements ICombatMethods {

    public SquiffyGhast(String difficulty) {
        maxHP = 55;
        dodge = .335;
        prot = 0;
        speed = 8;
        
        unholy = true;
        
        stunRes = 1.15;
        blightRes = .6;
        bleedRes = 1.45;
        debuffRes = .6;
        moveRes = .5;

    }

    @Override
    public void selectAction(Combat combat) {

    }

}
