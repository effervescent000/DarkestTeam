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
public class PelagicGrouper extends Enemy implements ICombatMethods {

    public PelagicGrouper(String difficulty) {
        switch (difficulty) {
            case "Apprentice":
                maxHP = 14;
                dodge = .05;
                speed = 6;
                stunRes = .1;
                blightRes = .1;
                bleedRes = .5;
                debuffRes = .1;
                moveRes = .1;
                acc = .775;
                break;
            case "Veteran":
                maxHP = 20;
                dodge = .1375;
                speed = 7;
                stunRes = .3;
                blightRes = .3;
                bleedRes = .7;
                debuffRes = .3;
                moveRes = .3;
                break;
        }
        curHP = maxHP;
        
        eldritch = true;
    }

    public void useSeawardSlash(Combat combat) {
        this.acc = .775;
        this.crit = .02;

        Hero target = ChooseTarget.chooseHero(1, 2);
        if (combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, RandomFunctions.getRandomNumberInRange(5, 9), this);
        }
    }

    public void useSpearfishing(Combat combat) {
        this.acc = .775;
        this.crit = .06;

        Hero target = ChooseTarget.chooseHero(3, 4);
        if (combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, RandomFunctions.getRandomNumberInRange(3, 7), this);
        }

    }

    @Override
    public void selectAction(Combat combat) {
        if (position <= 2) {
            useSeawardSlash(combat);
        } else {
            useSpearfishing(combat);
        }
    }

}
