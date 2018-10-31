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
import darkestteam.RandomFunctions;

/**
 *
 * @author Tara
 */
public class BoneArbalest extends Enemy implements ICombatMethods {

    public BoneArbalest(String difficulty) {
        maxHP = 15;
        dodge = .05;
        speed = 5;

        unholy = true;

        stunRes = .1;
        blightRes = .1;
        bleedRes = 2;
        debuffRes = .15;
        moveRes = .25;

        curHP = maxHP;
        
        danger = 1;
        moveable = 2;

    }

    private void useQuarrel(Hero t) {
        setDmg(RandomFunctions.getRandomNumberInRange(3, 7));
        setAcc(.825);
        setCrit(.12);

        int amt = getDmg();
        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            amt = (int) (amt * 1.25);
        }
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
        }
    }

    private void useBayonetJab() {
        Hero t = ChooseTarget.chooseHero(1, 2);
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));
        setAcc(.725);
        setCrit(.02);

        int amt = getDmg();
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            combat.moveSelf(this, 1);
        }

    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;

        if (position >= 3) {
            Hero t = ChooseTarget.chooseHero(2, 4);
            useQuarrel(t);
        } else {
            useBayonetJab();
        }
    }

}
