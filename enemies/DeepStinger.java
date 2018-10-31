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
public class DeepStinger extends Enemy implements ICombatMethods {

    public DeepStinger(String difficulty) {
        maxHP = 10;
        curHP = maxHP;
        eldritch = true;
        dodge = .125;
        speed = 7;
        stunRes = .5;
        blightRes = .1;
        bleedRes = .5;
        debuffRes = .1;
        moveRes = .1;
    }

    @Override
    public void selectAction(Combat combat) {
        
    }

}
