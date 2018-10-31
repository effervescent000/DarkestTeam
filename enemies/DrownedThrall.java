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
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public class DrownedThrall extends Enemy implements ICombatMethods {

    public DrownedThrall(String difficulty) {
        switch (difficulty) {
            case "Apprentice":
                this.maxHP = 14;
                this.speed = 0;
                this.crit = .02;

                this.stunRes = .5;
                this.blightRes = .1;
                this.debuffRes = .1;
                break;
            case "Veteran":
                this.maxHP = 20;
                this.dodge = .0875;
                this.speed = 1;

                this.stunRes = .7;
                this.blightRes = .3;
                this.bleedRes = .4;
                this.debuffRes = .3;
                this.moveRes = .45;
                break;
        }
        curHP = maxHP;
        unholy = true;
        eldritch = true;

    }

    public void useExplode() {
//        this.multiAttack = true;
//        int[] validTargets = {1, 2, 3, 4};
        acc = .725;
        int amt = getRandomNumberInRange(5, 13);

        ArrayList<Hero> list = combat.dmgHeroMulti(1, 4, amt, this);
        for (Hero hero : list) {
            combat.dmgStress(this, hero, 5);
        }
        curHP = 0;
    }

    public void useGarglingGrab() {
        acc = .625;
        int amt = RandomFunctions.getRandomNumberInRange(5, 9);

        Hero target = ChooseTarget.chooseHero(1, 2);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            combat.dmgStress(this, target, 10);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        //the Thrall will always using Gargling Grab on the first round and then explode
        //on the second round (assuming it isn't incapacitated between then).

        this.combat = combat;

        if (combat.getRound() == 0) {
            useGarglingGrab();
        } else {
            useExplode();
        }
    }

}
