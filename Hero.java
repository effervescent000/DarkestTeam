/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package darkestteam;

import darkestteam.heroClasses.*;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tara
 */
@XmlRootElement
public class Hero extends Creature implements ICombatMethods {

    private SimpleStringProperty heroClass;
    private SimpleStringProperty heroName;
    private SimpleIntegerProperty resolveLvl;

    protected double acc;
    protected double accMod;

    protected double crit;
    protected double critMod;

    private int attacksMade;
    private int crits;
    private int damageDealtDOT;
    private int damageDealtDirect;
    private int damageTakenDOT;
    private int damageTakenDirect;
    private int healingDone;
    private int misses;

    protected double dmg;
    protected double dmgMod;
    protected double meleeDmg;
    protected double rangedDmg;

    private int move1Rank;
    private int move2Rank;
    private int move3Rank;
    private int move4Rank;
    private int move5Rank;
    private int move6Rank;
    private int move7Rank;

    private int stressLvl;
    private double stressMod; // this will be 0 by default but will be modified by quirks that are +/- stress %

    private ArrayList<String> statQuirks = new ArrayList<>();
    private ArrayList<String> otherQuirks = new ArrayList<>();
    private ArrayList<String> stressQuirks = new ArrayList<>();
    private ArrayList<String> townQuirks = new ArrayList<>();

    protected double deathRes;
    protected double diseaseRes;
    protected double trapRes;

    private Hero guardian;

    private HeroClass myHero;

    private int startingPosition;

    public Hero() {
        this.heroName = new SimpleStringProperty();
        this.heroClass = new SimpleStringProperty();
        this.resolveLvl = new SimpleIntegerProperty();
    }

    public Hero(String heroName, String heroClass, int resolveLvl) {
        //this method can be private maybe

        this.heroName = new SimpleStringProperty(heroName);
        this.heroClass = new SimpleStringProperty(heroClass);
        this.resolveLvl = new SimpleIntegerProperty(resolveLvl);

        initMyHero();
        
    }

    public HeroClass getMyHero() {
        return myHero;
    }
    
    public void initMyHero() {
        this.dmgMod = 1;
        int resolve = getResolveLvl();

        int arraySlot;

        if (heroClass != null) {
            switch (resolve) {
                case 0:
                    arraySlot = 0;
                    break;
                case 6:
                    arraySlot = 4;
                    break;
                default:
                    arraySlot = resolve - 1;
                    break;
            }

            switch (getHeroClass()) {
                case "Abomination":
                    myHero = new Abomination(this);
                    break;
                case "Antiquarian":
                    myHero = new Antiquarian(this);
                    break;
                case "Arbalest":
                    myHero = new Arbalest(this);
                    break;
                case "Bounty Hunter":
                    myHero = new BountyHunter(this);
                    break;
                case "Crusader":
                    myHero = new Crusader(this);
                    break;
                case "Flagellant":
                    myHero = new Flagellant(this);
                    break;
                case "Grave Robber":
                    myHero = new GraveRobber(this);
                    break;
                case "Hellion":
                    myHero = new Hellion(this);
                    break;
                case "Highwayman":
                    myHero = new Highwayman(this);
                    break;
                case "Houndmaster":
                    myHero = new HoundMaster(this);
                    break;
                case "Jester":
                    myHero = new Jester(this);
                    break;
                case "Leper":
                    myHero = new Leper(this);
                    break;
                case "Man-at-Arms":
                    myHero = new ManAtArms(this);
                    break;
                case "Occultist":
                    myHero = new Occultist(this);
                    break;
                case "Plague Doctor":
                    myHero = new PlagueDoctor(this);
                    break;
                case "Shieldbreaker":
                    myHero = new Shieldbreaker(this);
                    break;
                case "Vestal":
                    myHero = new Vestal(this);
                    break;
                default:
                    System.out.println("Invalid hero class, try again");
                    break;
            }

            maxHP = myHero.getMaxHPArray()[arraySlot];
            dodge = myHero.getDodgeArray()[arraySlot];
            speed = myHero.getSpeedArray()[arraySlot];
            accMod = myHero.getAccModArray()[arraySlot];
            critMod = myHero.getCritModArray()[arraySlot];
            dmg = myHero.getDmgArray()[arraySlot];

            stunRes = myHero.getStunRes() + .1 * resolve;
            moveRes = myHero.getMoveRes() + .1 * resolve;
            blightRes = myHero.getBlightRes() + .1 * resolve;
            bleedRes = myHero.getBleedRes() + .1 * resolve;
            diseaseRes = myHero.getDiseaseRes() + .1 * resolve;
            debuffRes = myHero.getDebuffRes() + .1 * resolve;
            trapRes = myHero.getTrapRes() + .1 * resolve;

            deathRes = myHero.getDeathRes();

            //this is still within the IF statement so it won't trigger if the class was not set properly
            curHP = maxHP;
            //the specific damage types only matter once I bring quirks into it which can modify them individually
            //until then they are exactly the same as each other and as base dmg
            meleeDmg = dmg;
            rangedDmg = dmg;
        }
    }
    

    @XmlTransient
    public int getStartingPosition() {
        return startingPosition;
    }

    public void resetPosition(Combat combat) {

        if (startingPosition != position) {
            int movement;

            //I'm not sure about this code, we will see how it works
            movement = position - startingPosition;
            combat.moveSelf(this, movement);
        }
    }

    @XmlTransient
    public double getAcc() {
        return acc;
    }

    @XmlTransient
    public double getAccMod() {
        return accMod;
    }

    @XmlTransient
    public int getAttacksMade() {
        return attacksMade;
    }

    @XmlTransient
    public double getCrit() {
        return crit;
    }

    @XmlTransient
    public double getCritMod() {
        return critMod;
    }

    @XmlTransient
    public int getCrits() {
        return crits;
    }

    @XmlTransient
    public int getDamageDealtDOT() {
        return damageDealtDOT;
    }

    @XmlTransient
    public int getDamageDealtDirect() {
        return damageDealtDirect;
    }

    @XmlTransient
    public int getDamageTakenDOT() {
        return damageTakenDOT;
    }

    @XmlTransient
    public int getDamageTakenDirect() {
        return damageTakenDirect;
    }

    @XmlTransient
    public double getDeathRes() {
        return deathRes;
    }

    @XmlTransient
    public double getDiseaseRes() {
        return diseaseRes;
    }

    @XmlTransient
    public double getDmg() {
        return dmg * dmgMod;
    }

    @XmlTransient
    public double getDmgMod() {
        return dmgMod;
    }

    @XmlTransient
    public Hero getGuardian() {
        return guardian;
    }

    @XmlTransient
    public int getHealingDone() {
        return healingDone;
    }

    @XmlAttribute
    public String getHeroClass() {
        return heroClass.get();
    }

    @XmlTransient
    public String getHeroDesc() {
        return getHeroClass() + " " + getHeroName();
    }

    @XmlAttribute
    public String getHeroName() {
        return heroName.get();
    }

    @XmlTransient
    public double getMeleeDmg() {
        return meleeDmg * dmgMod;
    }

    @XmlTransient
    public int getMisses() {
        return misses;
    }

    @XmlElement
    public int getMove1Rank() {
        return move1Rank;
    }

    @XmlElement
    public int getMove2Rank() {
        return move2Rank;
    }

    @XmlElement
    public int getMove3Rank() {
        return move3Rank;
    }

    @XmlElement
    public int getMove4Rank() {
        return move4Rank;
    }

    @XmlElement
    public int getMove5Rank() {
        return move5Rank;
    }

    @XmlElement
    public int getMove6Rank() {
        return move6Rank;
    }

    @XmlElement
    public int getMove7Rank() {
        return move7Rank;
    }

    public ArrayList<String> getOtherQuirks() {
        return otherQuirks;
    }

    @XmlTransient
    public double getRangedDmg() {
        return rangedDmg * dmgMod;
    }

    @XmlAttribute
    public int getResolveLvl() {
        return resolveLvl.get();
    }

    public ArrayList<String> getStatQuirks() {
        return statQuirks;
    }

    @XmlTransient
    public int getStressLvl() {
        return stressLvl;
    }

    @XmlTransient
    public double getStressMod() {
        return stressMod;
    }

    public ArrayList<String> getStressQuirks() {
        return stressQuirks;
    }

    public ArrayList<String> getTownQuirks() {
        return townQuirks;
    }

    @XmlTransient
    public double getTrapRes() {
        return trapRes;
    }

    public void resetCombatStats() {
        crits = 0;
        misses = 0;
        attacksMade = 0;
        damageDealtDOT = 0;
        damageDealtDirect = 0;
        damageTakenDOT = 0;
        damageTakenDirect = 0;
        healingDone = 0;
    }

    @Override
    public void selectAction(Combat combat) {
        if (myHero != null) {
            myHero.selectAction(combat);
        } else {
            System.out.println("myHero is null for " + getHeroDesc());
        }
        
    }

    /**
     * This method is used to reset class abilities that are "X uses per
     * battle".
     *
     *
     */
    public void resetSpecials() {

        myHero.resetSpecials();
    }

    public void setAcc(double acc) {
        this.acc = acc;
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

    public void setAttacksMade(int attacksMade) {
        this.attacksMade += attacksMade;
    }

    public void setCrit(double crit) {
        this.crit = crit;
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

    public void setCrits(int crits) {
        this.crits += crits;
    }

    public void setDamageDealtDOT(int damageDealtDOT) {
        this.damageDealtDOT += damageDealtDOT;
    }

    public void setDamageDealtDirect(int damageDealtDirect) {
        this.damageDealtDirect += damageDealtDirect;
    }

    public void setDamageTakenDOT(int damageTakenDOT) {
        this.damageTakenDOT += damageTakenDOT;
    }

    public void setDamageTakenDirect(int damageTakenDirect) {
        this.damageTakenDirect += damageTakenDirect;
    }

    /**
     * This method should ONLY be used for global modifiers from buffs/debuffs!
     * For abilities that have an innate damage adjustment (such as -80%
     * damage), the calculation should be done in the useAbility method directly
     * (eg, dmg * .2).
     *
     *
     * @param dmgMod The raw modifier (so for -25% damage, this should be -.25).
     */
    public void setDmgMod(double dmgMod) {
        this.dmgMod += dmgMod;
    }

    public void setGuardian(Hero guardian) {
        this.guardian = guardian;
    }

    public void setHealingDone(int healingDone) {
        this.healingDone += healingDone;
    }

    public void setHeroClass(String heroClass) {
        this.heroClass.set(heroClass);
    }

    public void setHeroName(String heroName) {
        this.heroName.set(heroName);
    }

    public void setMeleeDmg(double meleeDmg) {
        this.meleeDmg = meleeDmg;
    }

    public void setMisses(int misses) {
        this.misses += misses;
    }

    public void setMove1Rank(int move1Rank) {
        this.move1Rank = move1Rank;
    }
    
    public void setMove1Rank(String move1Rank) {
        this.move1Rank = Integer.parseInt(move1Rank);
    }
    

    public void setMove2Rank(int move2Rank) {
        this.move2Rank = move2Rank;
    }

    public void setMove3Rank(int move3Rank) {
        this.move3Rank = move3Rank;
    }

    public void setMove4Rank(int move4Rank) {
        this.move4Rank = move4Rank;
    }

    public void setMove5Rank(int move5Rank) {
        this.move5Rank = move5Rank;
    }

    public void setMove6Rank(int move6Rank) {
        this.move6Rank = move6Rank;
    }

    public void setMove7Rank(int move7Rank) {
        this.move7Rank = move7Rank;
    }

    public void setRangedDmg(double rangedDmg) {
        this.rangedDmg = rangedDmg;
    }

    public void setResolveLvl(int resolveLvl) {
        this.resolveLvl = new SimpleIntegerProperty(resolveLvl);
    }

    public void setStartingPosition(int startingPosition) {
        this.startingPosition = startingPosition;
    }

    /**
     * Modifies the Hero's stress level (does *not* change it outright). Sets
     * Hero's stress to zero if it goes negative.
     *
     * @param stressLvl
     */
    public void setStressLvl(int stressLvl) {
        this.stressLvl = stressLvl;
        if (this.stressLvl < 0) {
            this.stressLvl = 0;
        }
    }

    /**
     * This method should ONLY be used for global modifiers from buffs/debuffs!
     *
     *
     * @param stressMod The raw modifier, turned into a decimal
     */
    public void setStressMod(double stressMod) {
        this.stressMod += stressMod;
    }

    
    
}
