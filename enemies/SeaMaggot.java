/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.ChooseTarget;
import darkestteam.Combat;
import static darkestteam.Combat.calcHit;
import darkestteam.Debuff;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;

/**
 *
 * @author Tara
 */
public class SeaMaggot extends Enemy implements ICombatMethods {


    public SeaMaggot(String difficulty) {
        maxHP = 5;
        dodge = 0;
        prot = .75;
        speed = -5;
        stunRes = .5;
        blightRes = .2;
        bleedRes = .4;
        debuffRes = .1;
        moveRes = .1;

        curHP = maxHP;
        
        eldritch = true;
    }

    public void useBrine(Combat combat) {
        this.acc = .625;
        this.crit = .02;

        Hero target = ChooseTarget.chooseHero(1, 4);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 3, this);
            if (calcHit(target.getDebuffRes(), acc)) {
                target.getDebuffs().add(new Debuff("Brine", 3, target));
            }
            
        }
    }

    @Override
    public void selectAction(Combat combat) {
        useBrine(combat);
    }

}
