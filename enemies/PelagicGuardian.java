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
import darkestteam.Managers;
import darkestteam.RandomFunctions;

/**
 *
 * @author Tara
 */
public class PelagicGuardian extends Enemy implements ICombatMethods {

    public PelagicGuardian(String difficulty) {
        switch (difficulty) {
            case "Apprentice":
                maxHP = 25;
                dodge = 0;
                prot = .33;
                speed = 0;
                stunRes = .25;
                blightRes = .1;
                bleedRes = .6;
                debuffRes = .1;
                moveRes = .5;
                break;
            default:
                System.out.println("Difficulty level " + difficulty + " invalid or not coded yet");
        }
        curHP = maxHP;

        eldritch = true;
    }

    private void useOctocestus() {
        acc = .775;
        crit = .06;

        int amt = RandomFunctions.getRandomNumberInRange(3, 5);
        Hero target = ChooseTarget.chooseHero(1, 2);

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgEnemy(this, amt, target);
            Managers.addBleed(target, 3, 2, this);
        }
    }

    private void useBarnacleBarrier() {
        Enemy target = ChooseTarget.chooseEnemy(1, 4);
        while (target == this) {
            target = ChooseTarget.chooseEnemy(1, 4);
        }

        Managers.addHelpfulEffect(target, "Guard", 3);
        target.setGuardian(this);
        Managers.addHelpfulEffect(this, "Barnacle Barrier", 3);
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position == 4) {
            useBarnacleBarrier();
            return;
        }
        if (Checker.amIGuarding(this)) {
            useOctocestus();
            return;
        }
        if (combat.getEnemyRoster().size() == 1) {
            useOctocestus();
            return;
        }
        useBarnacleBarrier();

    }

}
