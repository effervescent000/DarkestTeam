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
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class Maggot extends Enemy implements ICombatMethods {

    public Maggot(String difficulty) {
        this.maxHP = 6;
        this.curHP = maxHP;
        this.dodge = 0;
        this.speed = 3;
        
        this.beast = true;
        
        this.stunRes = 1;
        this.blightRes = .4;
        this.bleedRes = 4.;
        this.debuffRes = .6;
        this.moveRes = 0;
                
    }

    public void useGraveNibble() {
        acc = .625;
        crit = .12;
        
        Hero target = ChooseTarget.chooseHero(1, 4);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, RandomFunctions.getRandomNumberInRange(2, 4), this);
            combat.dmgStress(this, target, 5);
            acc = .75;
            addStatusEffect(target, "Stun", 1, this);
            //TODO 15% chance to cause a random disease
        }
        
    }
    
    
    
    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        
        useGraveNibble();
    }
    
}
