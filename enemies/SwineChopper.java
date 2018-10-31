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
public class SwineChopper extends Enemy implements ICombatMethods {

    public SwineChopper(String difficulty) {
        maxHP = 21;
        dodge = 0;
        prot = .15;
        speed = 3;

        beast = true;
        human = true;

        stunRes = .25;
        blightRes = .6;
        bleedRes = .2;
        debuffRes = .15;
        moveRes = .5;
    }

    private void useButcherCut() {
        Hero t = ChooseTarget.chooseHero(1, 3);
        setDmg(RandomFunctions.getRandomNumberInRange(3, 7));
        setAcc(.825);
        setCrit(.12);

        int amt = getDmg();
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            Managers.addBleed(t, 2, 3, this);
        }
    }

    private void useBallAndChain() {
        Hero t = ChooseTarget.chooseHero(3, 4);
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));
        setAcc(.825);
        setCrit(.12);

        int amt = getDmg();

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            setAcc(1);
            Managers.addStatusEffect(t, "Stun", 1, this);
            combat.moveAttack(this, t, 1);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position >= 3) {
            useBallAndChain();
        } else if (random() < .5) {
            useButcherCut();
        } else {
            useBallAndChain();
        }
    }

}
