/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import static darkestteam.ChooseTarget.chooseHero;
import darkestteam.Combat;
import static darkestteam.Combat.calcHit;
import darkestteam.Debuff;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import static java.lang.Math.random;
import java.util.ArrayList;
import static darkestteam.Managers.addBleed;

/**
 *
 * @author Tara
 */
public class BrigandBloodletter extends Enemy implements ICombatMethods {

    public BrigandBloodletter(String difficulty) {
        maxHP = 35;
        curHP = maxHP;
        dodge = 0;
        speed = 1;

        human = true;

        stunRes = .5;
        blightRes = .2;
        bleedRes = .2;
        debuffRes = .15;
        moveRes = .75;
    }

    public void useRainOfWhips() {
        this.acc = .825;
        this.crit = 0;

//        combat.dmgHeroMulti(1, 4, 1, this, 5);
        ArrayList<Hero> hits = combat.dmgHeroMulti(1, 4, 1, this);

        if (hits != null) {
            this.acc = 1;
            for (Hero hero : hits) {
                combat.dmgStress(this, hero, 5);
                if (calcHit(hero.getBleedRes(), acc)) {
                    addBleed(hero, 2, 1, this); //TODO check duration
                }
            }
        }
        //TODO this should also add bleed damage
    }

    public void usePunishment() {
        this.acc = .825;
        this.crit = .12;

        Hero target = chooseHero(1, 4);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgEnemy(this, getRandomNumberInRange(2, 4), target);
            combat.dmgStress(this, target, 5);
            this.acc = 1;
            addBleed(target, 2, 2, this); //TODO check duration
        }
    }

    public void usePointBlankShot() {
        this.acc = .825;
        this.crit = .16;

        Hero target = Combat.getHeroInPosition(1);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, getRandomNumberInRange(4, 11), this);
            this.acc = 1;
            combat.moveAttack(this, target, 1);
            debuffs.add(new Debuff("Point Blank Shot Debuff", 1, this));
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        double rand = random();
        switch (position) {
            case 1:
                //enemy has a higher chance to use point blank shot. It's not supposed to use it
                //twice in a row but I don't feel like coding that right now
                if (rand < .5) {
                    usePointBlankShot();
                } else if (rand < .75) {
                    useRainOfWhips();
                } else {
                    usePunishment();
                }
                break;
            case 2:
                if (rand < .5) {
                    useRainOfWhips();
                } else {
                    usePunishment();
                }
                break;
            default:
                usePunishment();
        }

    }

}
