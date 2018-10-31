/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.ChooseTarget;
import darkestteam.Combat;
import static darkestteam.Combat.calcHit;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import darkestteam.RandomFunctions;
import java.io.IOException;

/**
 *
 * @author Tara
 */
public class BoneSoldier extends Enemy implements ICombatMethods {

    public BoneSoldier(String difficulty) {
        maxHP = 10;
        prot = .15;
        speed = 2;

        stunRes = .25;
        blightRes = .1;
        bleedRes = 2;
        debuffRes = .15;
        moveRes = .2;

        unholy = true;

        curHP = maxHP;
    }

    public void useGraveyardStumble() {
        setDmg(RandomFunctions.getRandomNumberInRange(2, 5));
        setAcc(.425);
        setCrit(0);

        Hero target = ChooseTarget.chooseHero(1, 2);
        int amt = getDmg();

        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, amt, this);
            combat.moveSelf(this, -1);
        }
    }

    public void useGraveyardSlash() {
        this.acc = .825;
        this.crit = .06;

        Hero target = ChooseTarget.chooseHero(1, 3);

        if (calcHit(target.getDodge(), acc)) {
            combat.dmgHero(target, RandomFunctions.getRandomNumberInRange(3, 8), this);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        
        if (position == 4) {
            useGraveyardStumble();
        } else {
            useGraveyardSlash();
        }
    }

}
