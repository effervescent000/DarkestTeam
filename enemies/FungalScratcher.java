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
import static darkestteam.RandomFunctions.getRandomNumberInRange;
import java.io.IOException;

/**
 *
 * @author Tara
 */
public class FungalScratcher extends Enemy implements ICombatMethods {

    public FungalScratcher(String difficulty) {
        maxHP = 19;
        curHP = maxHP;
        prot = .33;
        speed = 0;

        blightRes = .6;
        bleedRes = .2;
        
        eldritch = true;
    }

//    public FungalScratcher() {
//        maxHP = 19;
//        curHP = maxHP;
//        prot = .33;
//        speed = 0;
//
//        blightRes = .6;
//        bleedRes = .2;
//    }

    public void useHarmlessSwipe(Combat combat) {
        this.acc = .425;
        this.crit = 0;

        Hero target = ChooseTarget.chooseHero(1, 2);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, getRandomNumberInRange(2, 4), this);
            combat.moveSelf(this, -1);
        }
    }

    public void useRendTheMarked(Combat combat, Hero hero) {
        this.acc = .83;
        this.crit = .06;

        //because of the need to check for a marked target first, target selection
        //is handled in the selectAction method and the target is passed directly
        //to this method
        if (Combat.tryAttackByEnemy(this, hero)) {
            combat.dmgHero(
                    hero,
                    (int) ((int) getRandomNumberInRange(3, 5) * 1.5),
                    this);
        }
    }

    public void useGropingSwipe(Combat combat) {
        this.acc = .725;
        this.crit = .06;

        Hero target = ChooseTarget.chooseHero(1, 2);
        if (Combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, getRandomNumberInRange(3, 5), this);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        Hero markedTarget = Checker.checkHeroesForDebuff("Marked", combat.getHeroRoster());

        try {
            if (position >= 3) {
                useHarmlessSwipe(combat);
            } else if (markedTarget != null) {
                if (markedTarget.getPosition() == (1 | 2)) {
                    useRendTheMarked(combat, markedTarget);
                }
            } else {
                useGropingSwipe(combat);
            }
        } catch (Exception e) {

        }
    }
}
