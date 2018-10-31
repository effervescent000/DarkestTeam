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
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Tara
 */
public class CultistAcolyte extends Enemy implements ICombatMethods {

    public CultistAcolyte(String difficulty) {
        switch (difficulty) {
            case "Apprentice":
                this.maxHP = 13;
                this.curHP = maxHP;
                this.dodge = .125;
                this.acc = .825;
                this.debuffRes = .4;
                this.moveRes = .1;
                break;
            case "Veteran":
                this.maxHP = 19;
                this.curHP = maxHP;
                this.dodge = .2125;
                this.speed = 8;
                this.stunRes = .45;
                this.blightRes = .4;
                this.bleedRes = .4;
                this.debuffRes = .6;
                this.moveRes = .3;
                break;
            case "Champion":
                this.maxHP = 27;
                this.curHP = maxHP;
                this.dodge = .35;
                this.speed = 9;
                this.stunRes = .7;
                this.blightRes = .65;
                this.bleedRes = .65;
                this.debuffRes = .85;
                this.moveRes = .55;
                break;
        }
        human = true;

    }

    public void useStressfulIncantation(Combat combat) {
        Hero target = ChooseTarget.chooseHero(1, 4);

        this.crit = 0;
        if (combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 1, this);
            combat.dmgStress(this, target, 15);
        }
    }

    public void useEldritchPull(Combat combat) {
        Hero target = ChooseTarget.chooseHero(3, 4);

        crit = .06;
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 1, this);
            combat.moveAttack(this, target, -2);
        }
    }

    public void useEldritchPush(Combat combat) {

        Hero target = ChooseTarget.chooseHero(1, 2);

        this.crit = .06;
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 1, this);
            combat.moveAttack(this, target, 2);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        Random rand = new Random();

        try {
            if (position == 1) {
                int target = rand.nextInt(2);
                switch (target) {
                    case 1:
                        useStressfulIncantation(combat);
                        break;
                    case 2:
                        useEldritchPull(combat);
                        break;
                }
            } else {
                int target = rand.nextInt(3);
                switch (target) {
                    case 1:
                        useStressfulIncantation(combat);
                        break;
                    case 2:
                        useEldritchPull(combat);
                        break;
                    case 3:
                        useEldritchPush(combat);
                        break;
                }
            }
        } catch (Exception e) {

        }
    }

}
