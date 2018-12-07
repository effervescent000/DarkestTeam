/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.Checker;
import darkestteam.ChooseTarget;
import darkestteam.Combat;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static darkestteam.Managers.addBlight;
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;
import static darkestteam.Managers.addBlight;

/**
 *
 * @author Tara
 */
public class Spitter extends Enemy implements ICombatMethods {

    public Spitter(String difficulty) {
        maxHP = 7;
        curHP = maxHP;

        dodge = .15;
        speed = 4;
        beast = true;

        stunRes = .25;
        blightRes = .2;
        bleedRes = .2;
        debuffRes = .1;
        moveRes = .1;
    }

    private void useSpit() {
        Hero target = ChooseTarget.chooseHero(1, 4);
        useSpit(target);
    }

    private void useSpit(Hero target) {
        acc = .825;
        crit = .12;

        if (Combat.tryAttackByEnemy(this, target)) {
            if (Checker.checkSpecificForDebuff(target, "Marked")) {
                combat.dmgHero(target, getRandomNumberInRange(3, 5) * 2, this);
            } else {
                combat.dmgHero(target, getRandomNumberInRange(3, 5), this);
            }

            acc = 1;
            if (Combat.calcHit(target.getBlightRes(), acc)) {
                addBlight(target, 2, 1, this);
            }
        }
    }

    private void useBite(Hero target) {
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
                addBlight(target, 2, 1, this);
            }
        }
    }

    private void useBite() {
        Hero target = ChooseTarget.chooseHero(1, 4);
        useBite(target);

    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        
        Hero marked = Checker.checkHeroesForDebuff("Marked", combat.getHeroRoster());

        if (position <= 2) {
            if (marked == null) {
                useBite();
            } else {
                useBite(marked);
            }
        } else if (marked == null) {
            useSpit();
        } else {
            useSpit(marked);
        }

    }

}
