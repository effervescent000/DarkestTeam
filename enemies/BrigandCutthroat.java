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
import java.util.ArrayList;
import static darkestteam.Managers.addBleed;
import static darkestteam.Managers.addHelpfulEffect;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class BrigandCutthroat extends Enemy implements ICombatMethods {

    public BrigandCutthroat(String difficulty) {
        maxHP = 12;
        curHP = maxHP;

        prot = .025;
        dodge = .15;
        speed = 3;
        stunRes = .25;
        bleedRes = .2;
        blightRes = .2;
        debuffRes = .15;
        moveRes = .25;

        human = true;
    }

    public void useSliceAndDice() {
        acc = .725;
        crit = .12;

        int amt = RandomFunctions.getRandomNumberInRange(3, 5);
        ArrayList<Hero> heroes = combat.dmgHeroMulti(1, 2, amt, this);

        acc = 1;
        if (heroes != null) {
            for (Hero hero : heroes) {
                addStatusEffect(hero, "Slice and Dice", 3, this); //TODO check duration
            }
        }
    }

    public void useUppercutSlice() {
        acc = .725;
        crit = .06;

        int amt = RandomFunctions.getRandomNumberInRange(2, 4);
        Hero target = ChooseTarget.chooseHero(1, 3);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            acc = 1;
            combat.moveAttack(this, target, 1);
        }
    }

    public void useShank() {
        acc = .725;
        crit = .06;

        int amt = RandomFunctions.getRandomNumberInRange(4, 8);
        Hero target = ChooseTarget.chooseHero(1, 4);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            acc = 1;
            if (Combat.calcHit(target.getBleedRes(), acc)) {
                addBleed(target, 3, 1, this); //TODO check duration
            }
        }
    }

    public void useHarmlessPoke() {
        acc = .425;
        crit = 0;

        Hero target = ChooseTarget.chooseHero(1, 4);
        int amt = RandomFunctions.getRandomNumberInRange(2, 4);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            combat.moveSelf(this, -1);
            addHelpfulEffect(this, "Harmless Poke", 1); //TODO check duration
        }

    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position == 4) {
            useHarmlessPoke();
        } else if (position <= 2) {
            int rand = RandomFunctions.getRandomNumberInRange(1, 3);
            switch (rand) {
                case 1:
                    useSliceAndDice();
                    break;
                case 2:
                    useUppercutSlice();
                    break;
                case 3:
                    useShank();
                    break;
            }
        } else {
            int rand = RandomFunctions.getRandomNumberInRange(1, 2);
            switch (rand) {
                case 1:
                    useSliceAndDice();
                    break;
                case 2:
                    useShank();
                    break;
            }
        }
    }

}
