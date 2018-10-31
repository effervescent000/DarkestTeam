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
import darkestteam.Managers;
import darkestteam.RandomFunctions;
import static java.lang.Math.random;

/**
 *
 * @author Tara
 */
public class BoneDefender extends Enemy implements ICombatMethods {

    public BoneDefender(String difficulty) {
        maxHP = 15;
        dodge = 0;
        prot = .25;
        speed = 0;

        unholy = true;

        stunRes = .25;
        blightRes = .1;
        bleedRes = 2;
        debuffRes = .15;
        moveRes = .5;

        curHP = maxHP;
        danger = 0;
    }

    private void useAxeblade() {
        Hero t = ChooseTarget.chooseHero(1, 2);
        setDmg(RandomFunctions.getRandomNumberInRange(3, 5));
        setAcc(.725);
        setCrit(.06);

        int amt = getDmg();
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
        }
    }

    private void useDeadWeight() {
        Hero t = ChooseTarget.chooseHero(1, 2);
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));
        setAcc(.825);
        setCrit(.06);

        int amt = getDmg();
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgEnemy(this, amt, t);
            setAcc(1);
            Managers.addStatusEffect(t, "Stun", 1, this);
            combat.moveAttack(this, t, 1);
        }

    }

    private void useClumsyAxeblade() {
        Hero t = ChooseTarget.chooseHero(1, 2);
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));
        setAcc(.425);
        setCrit(0);

        int amt = getDmg();
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            combat.moveSelf(this, -1);
        }

    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position >= 3) {
            useClumsyAxeblade();
        } else if (random() < .5) {
            useAxeblade();
        } else {
            useDeadWeight();
        }
    }

}
