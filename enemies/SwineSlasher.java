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
import darkestteam.RandomFunctions;

/**
 *
 * @author Tara
 */
public class SwineSlasher extends Enemy implements ICombatMethods {

    public SwineSlasher(String difficulty) {
        maxHP = 8;
        dodge = .075;
        prot = .25;
        speed = 5;

        beast = true;
        human = true;

        stunRes = .1;
        blightRes = .4;
        bleedRes = .4;
        debuffRes = .15;
        moveRes = .25;

        danger = 1;

        //setting these here b/c this mob only has 1 ability
        acc = .825;
        crit = .16;
    }

    private void useHookWhereItHurts() {
        Hero t = ChooseTarget.chooseHero(1, 3);
        setDmg(RandomFunctions.getRandomNumberInRange(3, 7));
        int amt = getDmg();

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            combat.moveSelf(this, 1);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        useHookWhereItHurts();
    }

}
