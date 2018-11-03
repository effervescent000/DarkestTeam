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
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import darkestteam.RandomFunctions;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class CultistBrawler extends Enemy implements ICombatMethods {

    private Combat combat;
    private String difficulty;

    public CultistBrawler(String difficulty) {
        this.difficulty = difficulty;
        switch (difficulty) {
            case "Apprentice":
                maxHP = 15;
                dodge = 0;
                speed = 5;
                break;
            case "Veteran":
                maxHP = 22;
                dodge = .0875;
                speed = 6;
                stunRes = .45;
                blightRes = .4;
                bleedRes = .4;
                debuffRes = .35;
                moveRes = .45;
            case "Champion":
                maxHP = 31;
                dodge = .225;
                speed = 7;
                stunRes = .7;
                blightRes = .65;
                bleedRes = .65;
                debuffRes = .6;
                moveRes = .7;
        }
        curHP = maxHP;

        human = true;
    }

    public void useRendForTheOldGods() {
        acc = .725;
        crit = .12;

        Hero target = ChooseTarget.chooseHero(1, 2);
        int amt = RandomFunctions.getRandomNumberInRange(2, 4);

        if (Combat.tryAttackByEnemy(this, target)) {
            if (Checker.checkSpecificForDebuff(target, "Marked")) {
                amt = (int) (amt * 1.5);
            }
            combat.dmgHero(target, amt, this);
            combat.moveSelf(this, -1);
            acc = 1;
            addBleed(target, 3, 1, this);
            addStatusEffect(target, "Rend for the Old Gods", 3, this);
        }

    }

    public void useStumblingScratch() {
        acc = .425;
        crit = 0;

        Hero target = ChooseTarget.chooseHero(1, 2);
        int amt = RandomFunctions.getRandomNumberInRange(2, 4);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            combat.moveSelf(this, -1);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        try {
            if (position >= 3) {
                useRendForTheOldGods();
            } else {
                useStumblingScratch();
            }
        } catch (Exception e) {

        }
    }

}
