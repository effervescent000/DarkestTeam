/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.ChooseTarget;
import darkestteam.Combat;
import static darkestteam.Combat.calcHit;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class BrigandFusilier extends Enemy implements ICombatMethods {

    public BrigandFusilier(String difficulty) {
        maxHP = 12;
        prot = 0;
        dodge = .075;
        speed = 6;

        danger = 1;

        stunRes = .25;
        blightRes = .2;
        bleedRes = .2;
        debuffRes = .15;
        moveRes = .25;

        curHP = maxHP;
    }

    public void useBlanketFire() {
        crit = 0;
        acc = .725;

        for (Hero hero : Combat.getHeroRoster()) {
            acc = .725;
            if (Combat.tryAttackByEnemy(this, hero)) {
                combat.dmgEnemy(this, getRandomNumberInRange(1, 3), hero);
                acc = 1;
                addStatusEffect(hero, "Blanket Fire", 1, this); //TODO check duration

            }
        }

    }

    public void useRushedShot() {
        crit = .06;
        acc = .625;

        Hero target = ChooseTarget.chooseHero(1, 3);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgEnemy(this, getRandomNumberInRange(2, 4), target);
            combat.moveSelf(this, 1);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position == 1) {
            useRushedShot();
        } else {
            useBlanketFire();
        }
    }

}
