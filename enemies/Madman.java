/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam.enemies;

import darkestteam.Checker;
import darkestteam.ChooseTarget;
import darkestteam.Combat;
import static darkestteam.Combat.calcHit;
import darkestteam.Enemy;
import darkestteam.Hero;
import darkestteam.ICombatMethods;
import static java.lang.Math.random;
import static darkestteam.Managers.addStatusEffect;

/**
 *
 * @author Tara
 */
public class Madman extends Enemy implements ICombatMethods {

    public Madman(String difficulty) {
        this.difficulty = difficulty;
        this.maxHP = 14;
        this.dodge = .2;
        this.prot = 0;
        this.speed = 9;

        this.human = true;

        this.stunRes = 1.;
        this.blightRes = .1;
        this.bleedRes = .1;
        this.debuffRes = .15;
        this.moveRes = .1;

        this.curHP = maxHP;

        danger = 2;

    }

    private void useDoomsay() {
        this.acc = 1.025;
        this.crit = 0;
        //I need to add code for multi-stress-attacks before I can do this
    }

    private void useAccusation() {
        this.acc = 1.025;
        this.crit = 0;

        Checker check = new Checker();
        Hero target = check.getHighestStressHero(combat.getHeroRoster());

        switch (difficulty) {
            //the higher the difficulty, the more likely this mob will target a high-stress
            //Hero for this attack. But the wiki says just that it's 2x,3x,4x more likely
            //respectively, which isn't really clarifying. May need to dig into the
            //code to see if I can get this to make sense? Right now I'm just taking
            //a guess
            case "Apprentice":
                if (random() < .25) {
                    if (Combat.tryAttackByEnemy(this, target)) {
                        combat.dmgHero(target, 1, this);
                        addStatusEffect(target, "Horror", 4, this);
                        this.acc = 1;
                        addStatusEffect(target, "Accusation", 1000, this);
                    }
                } else {
                    target = ChooseTarget.chooseHero(1, 4);
                    if (Combat.tryAttackByEnemy(this, target)) {
                        combat.dmgHero(target, 1, this);
                        addStatusEffect(target, "Horror", 4, this);
                        this.acc = 1;
                        addStatusEffect(target, "Accusation", 1000, this);
                    }
                }
        }

    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
//        Checker check = new Checker();
//        Hero target = check.getHighestStressHero(combat.getHeroRoster());
        if (random() < .5) {
            useDoomsay();
        } else {
            useAccusation();
        }

    }

}
