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
public class BrigandRaider extends Enemy implements ICombatMethods {

    public BrigandRaider(String difficulty) {
        maxHP = 25;
        dodge = .2625;
        prot = .25;
        speed = 5;

        human = true;

        stunRes = .725;
        blightRes = .675;
        bleedRes = .675;
        debuffRes = .625;
        moveRes = .73;
    }

    @Override
    public void selectAction(Combat combat) {

    }

}
