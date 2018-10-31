/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.ChooseTarget;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;

/**
 *
 * @author Tara
 */
public class SwineWretch extends Enemy implements ICombatMethods {

    public SwineWretch(String difficulty) {
        maxHP = 12;
        dodge = .125;
        speed = 8;

        beast = true;

        stunRes = .1;
        blightRes = .4;
        bleedRes = .1;
        debuffRes = .15;
        moveRes = 0;

        danger = 3;

        //setting these here b/c this mob only has 1 ability
        acc = .825;
        crit = .12;
    }

    private void useVomit() {
        Hero t = ChooseTarget.chooseHero(1, 4);
        //not setting damage b/c it only does 1

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgEnemy(this, 1, t);
            //TODO 33% chance to add a random Disease
            combat.dmgStress(this, t, 10);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        useVomit();
    }

}
