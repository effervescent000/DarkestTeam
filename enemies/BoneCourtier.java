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
import static java.lang.Math.random;

/**
 *
 * @author Tara
 */
public class BoneCourtier extends Enemy implements ICombatMethods {

    public BoneCourtier(String difficulty) {
        maxHP = 10;
        dodge = .125;
        speed = 8;

        unholy = true;

        stunRes = .1;
        blightRes = .1;
        bleedRes = 2;
        debuffRes = .15;
        moveRes = .1;

        danger = 2;
        moveable = 3;

    }

    private void useTemptingGoblet() {
        Hero t = ChooseTarget.chooseHero(1, 4);
        int amt = getDmg();
        setAcc(.925);
        setCrit(0);

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
            combat.dmgStress(this, t, 15);
        }
    }

    private void useKnifeInTheDark() {
        Hero t = ChooseTarget.chooseHero(1, 2);
        int amt = getDmg();
        setAcc(.625);
        setCrit(.06);

        if (Combat.tryAttackByEnemy(this, t)) {
            combat.dmgHero(t, amt, this);
        }
    }

    @Override
    public void selectAction(Combat combat) {
        this.combat = combat;
        setDmg(RandomFunctions.getRandomNumberInRange(2, 4));

        //if in position 3+, spam tempting goblet. if in position 1, spam knife if the dark.
        //if in position 2, flip a  coin
        if (position >= 3) {
            useTemptingGoblet();
        } else if (position == 1) {
            useKnifeInTheDark();
        } else if (random() < .5) {
            useTemptingGoblet();
        } else {
            useKnifeInTheDark();
        }
    }

}
