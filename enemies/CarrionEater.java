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
public class CarrionEater extends Enemy implements ICombatMethods {

    public CarrionEater(String difficulty) {
        maxHP = 9;
        dodge = 0;
        speed = 4;

        beast = true;

        stunRes = .5;
        blightRes = 1;
        bleedRes = .6;
        debuffRes = .4;
        moveRes = .5;

    }

    private void useMunch() {
        Hero t = ChooseTarget.chooseHero(1, 4);
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));
        setAcc(.725);
        setCrit(.12);

        int amt = getDmg();

        if (Checker.checkSpecificForDebuff(t, "Marked")) {
            amt = amt * 2;
        }

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgEnemy(this, amt, t);
            setAcc(1);
            Managers.addBlight(t, 3, 1, this);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        useMunch();
    }

}
