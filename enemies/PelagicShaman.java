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
import java.io.IOException;

/**
 *
 * @author Tara
 */
public class PelagicShaman extends Enemy implements ICombatMethods {

    private boolean eldritch = true;

    public PelagicShaman(String difficulty) {
        switch (difficulty) {
            case "Apprentice":
                maxHP = 12;
                dodge = .075;
                speed = 10;
                stunRes = .1;
                blightRes = .1;
                bleedRes = .5;
                debuffRes = .2;
                moveRes = .1;
                break;
            default:
                System.out.println("Difficulty level " + difficulty + " invalid or not coded yet");
        }
        curHP = maxHP;
    }

    public void useCallOfTheDeep(Combat combat) {
        this.acc = .825;
        this.crit = 0;
    }

    public void useCeremonialCut(Combat combat) {
        this.acc = .625;
        this.crit = .02;

        Hero target = ChooseTarget.chooseHero(1, 2);
        if (combat.tryAttackByEnemy(this, target)) {
            combat.dmgHero(target, 3, this);
        }
    }

    public void useSeaBreeze(Combat combat) {
        
        Enemy target = ChooseTarget.chooseEnemy(1, 4);
        int amt = RandomFunctions.getRandomNumberInRange(6, 12);

        //this move cannot target the caster but I don't feel like coding that RN TODO
        combat.healEnemy(this, target, amt);
    }

    public void useStressWave(Combat combat) {
        this.acc = .825;
        this.crit = 0;

        //this is not actually an accurate representation of how this ability works
        //but I don't want to fuck with it. It hits a single hero +1, it is not
        //just hte two front-liners.
        combat.dmgHeroMulti(1, 2, 1, this);
        //because of that I'm not sure how to deal with the stress debuff
        combat.getHeroRoster().stream().filter((hero) -> (hero.getPosition() <= 2)).forEach((hero) -> {
            combat.dmgStress(this, hero, 15);
        });
    }

    @Override
    public void selectAction(Combat combat) {

        if (position == 1) {
            useCeremonialCut(combat);
        } else {
            int i = RandomFunctions.getRandomNumberInRange(1, 3);
            switch (i) {
                case 1:
                    useStressWave(combat);
                    break;
                case 2:
                    useCallOfTheDeep(combat);
                    break;
                case 3:
                    useSeaBreeze(combat);
                    break;
            }
        }
    }

}
