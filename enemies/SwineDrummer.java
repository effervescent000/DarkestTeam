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
import static java.lang.Math.random;
import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public class SwineDrummer extends Enemy implements ICombatMethods {

    public SwineDrummer(String difficulty) {
        maxHP = 15;
        dodge = 0;
        speed = 7;

        beast = true;
        human = true;

        stunRes = .1;
        blightRes = .4;
        bleedRes = .2;
        debuffRes = .15;
        moveRes = .25;

        danger = 2;
    }

    private void useDrumOfDoom() {
        setCrit(.02);
        setAcc(.825);

        ArrayList<Hero> hits = combat.dmgHeroMulti(1, 4, 1, this);
        setAcc(.7);
        for (Hero h : hits) {
            combat.dmgStress(this, h, 5);
        }
    }

    private void useDrumOfDebilitation() {
        setCrit(.06);
        setAcc(.825);

        Hero t = ChooseTarget.chooseHero(1, 4);
        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, 1, this);
            setAcc(1);
            Managers.addStatusEffect(t, "Drum of Debilitation", 3, this);
            Managers.addStatusEffect(t, "Marked", 3, this);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        if (random() < .5) {
            useDrumOfDoom();
        } else {
            useDrumOfDebilitation();
        }
    }

}
