/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.Checker;
import static darkestteam.Checker.checkHeroesForDebuff;
import darkestteam.ChooseTarget;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import static java.lang.Math.random;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class Webber extends Enemy implements ICombatMethods {

    public Webber(String difficulty) {
        maxHP = 7;
        dodge = .15;
        speed = 5;

        beast = true;

        stunRes = .25;
        blightRes = .2;
        bleedRes = .2;
        debuffRes = .1;
        moveRes = .1;
    }

    private void useWeb(Hero target) {
        acc = .825;
        crit = .06;

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 1, this);
            addStatusEffect(target, "Marked", 1, this); //TODO check duration

            acc = 1;
            if (Combat.calcHit(target.getStunRes(), acc)) {
                addStatusEffect(target, "Stun", 1, this);
            }
            if (Combat.calcHit(target.getDebuffRes(), acc)) {
                addStatusEffect(target, "Web", 2, this); //TODO check duration
            }

        }
    }

    private void useWeb() {
        Hero target = ChooseTarget.chooseHero(1, 4);
        useWeb(target);
    }

    public void useBite() {
        Hero target = ChooseTarget.chooseHero(1, 4);

        acc = .725;
        crit = .02;

        if (Combat.tryAttackByEnemy(this, target)) {
            if (Checker.checkSpecificForDebuff(target, "Marked")) {
                combat.dmgHero(target, getRandomNumberInRange(1, 3) * 2, this);
            } else {
                combat.dmgHero(target, getRandomNumberInRange(1, 3), this);
                
            }
            acc = 1;
            if (Combat.calcHit(target.getBlightRes(), acc)) {
                addBlight(target, 2, 1, this); //TODO check duration
            }
        }
    }

    @Override
    public void selectAction(Combat combat) {

        this.combat = combat;

        Hero marked = checkHeroesForDebuff("Marked", combat.getHeroRoster());

        if (checkHeroesForDebuff("Marked", combat.getHeroRoster()) == null) {
            useWeb(marked);
        } else if (random() < .5) {
            useWeb();
        } else {
            useBite();
        }

    }

}
