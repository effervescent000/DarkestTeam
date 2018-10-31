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

/**
 *
 * @author Tara
 */
public class BoneRabble extends Enemy implements ICombatMethods {

    public BoneRabble(String difficulty) {
        maxHP = 8;
        curHP = maxHP;
        dodge = 0;
        speed = 1;
        
        unholy = true;
        
        stunRes = .1;
        blightRes = .1;
        bleedRes = 2;
        debuffRes = .15;
        moveRes = .1;
    }

    
    private void useBumpInTheNight() {
        acc = .625;
        crit = .02;
        
        Hero target = ChooseTarget.chooseHero(1, 2);
        
        if (Combat.tryAttackByEnemy(this, target)) {
            int amt = RandomFunctions.getRandomNumberInRange(2, 5);
            combat.dmgHero(target, amt, this);
        }
    }
    
    private void useTicToc() {
        acc = .425;
        crit = 0;
        
        Hero target = ChooseTarget.chooseHero(1, 2);
        
        if (Combat.tryAttackByEnemy(this, target)) {
            int amt = RandomFunctions.getRandomNumberInRange(2, 5);
            combat.dmgHero(target, amt, this);
            combat.moveSelf(this, -1);
        }
    }
    
    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        
        if (position == 4) {
            useTicToc();
        } else {
            useBumpInTheNight();
        }
    }
    
}
