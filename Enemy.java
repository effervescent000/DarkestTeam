/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

/**
 *
 * @author Tara
 */
public class Enemy extends Creature {

    protected double crit;
    protected double acc;
    //these two are not actually stats on enemies, they are
    //instead tied directly to abilities, but I need to make
    //them variables attached to the enemy class so they can be
    //extracted, rather than having to enter them manually

    protected double accMod = 0;
    protected double critMod = 0;
    //these two should be zero UNLESS modified by buffs/debuffs

    protected int actionsPerRound = 1;
    protected boolean multiAttack = false;
    protected String difficulty;

    protected boolean human = false;
    protected boolean eldritch = false;
    protected boolean beast = false;
    protected boolean unholy = false;
    protected boolean bloodsucker = false;

    protected int danger = 0;
    //this variable helps with the sim's calculations of who to attack -- higher danger targets
    //are prioritized
    protected int moveable;
    //this variable tells the sim how vulnerable a mob is to movement abilities

    protected Combat combat;
    protected Enemy guardian;

    protected int dmg;
    protected double dmgMod = 1;

    public double getAcc() {
        return acc;
    }

    public double getCrit() {
        return crit;
    }

    public double getCritMod() {
        return crit;
    }

    public double getAccMod() {
        return acc;
    }

    public int getActionsPerRound() {
        return actionsPerRound;
    }

    public int getDmg() {
        return (int) (dmg * dmgMod);
    }

    public double getDmgMod() {
        return dmgMod;
    }

    public int getMoveable() {
        return moveable;
    }

    public boolean isMultiAttack() {
        return multiAttack;
    }

    public void setCrit(double crit) {
        this.crit = critMod;
    }

    public void setAcc(double acc) {
        this.acc = acc;
    }

    public boolean isHuman() {
        return human;
    }

    public boolean isEldritch() {
        return eldritch;
    }

    public boolean isBeast() {
        return beast;
    }

    public boolean isUnholy() {
        return unholy;
    }

    public boolean isBloodsucker() {
        return bloodsucker;
    }

    public int getDanger() {
        return danger;
    }

    public Enemy getGuardian() {
        return guardian;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public void setDmgMod(double dmgMod) {
        this.dmgMod = dmgMod;
    }

    public void setGuardian(Enemy guardian) {
        this.guardian = guardian;
    }

    /**
     * This method should ONLY be used for global modifiers from buffs/debuffs!
     *
     * @param accMod The raw modifier, turned into a decimal (so -20 acc should
     *               be -.2)
     */
    public void setAccMod(double accMod) {
        this.accMod += accMod;
    }

    /**
     * This method should ONLY be used for global modifiers from buffs/debuffs!
     *
     * @param critMod The raw modifier, turned into a decimal (so -25% should be
     *                -.25)
     */
    public void setCritMod(double critMod) {
        this.critMod += critMod;
    }

}
