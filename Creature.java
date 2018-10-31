/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import java.util.ArrayList;

/**
 *
 * @author Tara
 */
public class Creature implements ICombatMethods {

    protected int maxHP;
    protected int curHP;

    protected double dodge;
    protected double prot = 0;
    protected int speed;

    protected int position; // 1-4, for Heroes, 1 is frontline and 4 is backline

    protected double stunRes;
    protected double moveRes;
    protected double blightRes;
    protected double bleedRes;
    protected double debuffRes;

    protected ArrayList<Debuff> debuffs = new ArrayList<>();

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurHP() {
        return curHP;
    }

    public void setCurHP(int curHP) {
        this.curHP = curHP;
    }

    public double getDodge() {
        return dodge;
    }

    public double getProt() {
        return prot;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     * I don't think there's anything that actually sets prot in the way that
     * accuracy gets set so I don't have a "normal" setter for this.
     *
     */
    public void setProt(double prot) {
        this.prot += prot;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     *
     * @param speed The raw modifier (so if a debuff gives -5 speed, then the
     *              arg here is -5)
     */
    public void setSpeed(int speed) {
        this.speed += speed;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     *
     * @param dodge The raw modifier, turned into a decimal
     */
    public void setDodge(double dodge) {
        if (dodge > 2 || dodge < -2) {
            this.dodge += dodge;
        } else {
            this.dodge += dodge / 100;
        }

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getStunRes() {
        return stunRes;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     */
    public void setStunRes(double stunRes) {
        this.stunRes += stunRes;
    }

    public double getMoveRes() {
        return moveRes;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     */
    public void setMoveRes(double moveRes) {
        this.moveRes += moveRes;
    }

    public double getBlightRes() {
        return blightRes;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     */
    public void setBlightRes(double blightRes) {
        this.blightRes += blightRes;
    }

    public double getBleedRes() {
        return bleedRes;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     */
    public void setBleedRes(double bleedRes) {
        this.bleedRes += bleedRes;
    }

    public double getDebuffRes() {
        return debuffRes;
    }

    /**
     * This should only be used for global modifiers from buffs/debuffs!
     *
     */
    public void setDebuffRes(double debuffRes) {
        this.debuffRes += debuffRes;
    }

    public ArrayList<Debuff> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(ArrayList<Debuff> debuffs) {
        this.debuffs = debuffs;
    }

    public void selectAction(Combat combat) {
        System.out.println("somehow the Creature selectAction method got called???");
    }

}
