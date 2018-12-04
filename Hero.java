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

/**
 *
 * @author Tara
 */
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

    //TODO there must be a better way to associate each Hero with a specific instance of its class.
//    private Abomination abomination;
//    private Antiquarian antiquarian;
////    private Arbalest arbalest;
//    private BountyHunter bountyHunter;
//    private Flagellant flagellant;
//    private GraveRobber graveRobber;
//    private Hellion hellion;
//    private Highwayman highwayman;
//    private HoundMaster houndMaster;
//    private Jester jester;
//    private Leper leper;
//    private ManAtArms manAtArms;
//    private Occultist occultist;
//    private PlagueDoctor plagueDoctor;
//    private Shieldbreaker shieldbreaker;
//    private Vestal vestal;
    private HeroClass myHero;

    private int startingPosition;

    public Hero(String heroName, String heroClass, int resolveLvl) {
        //this method can be private maybe

        this.heroName = new SimpleStringProperty(heroName);
        this.heroClass = new SimpleStringProperty(heroClass);
        this.resolveLvl = new SimpleIntegerProperty(resolveLvl);

        this.dmgMod = 1;

        int arraySlot;

        if (heroClass != null) {
            switch (resolveLvl) {
                case 0:
                    arraySlot = 0;
                    break;
                case 6:
                    arraySlot = 4;
                    break;
                default:
                    arraySlot = resolveLvl - 1;
                    break;
            }

            switch (heroClass) {
                case "Abomination":
                    myHero = new Abomination(this);
                    break;
                case "Antiquarian":
                    myHero = new Antiquarian(this);

//                    maxHP = antiquarian.getMaxHPArray()[arraySlot];
//                    dodge = antiquarian.getDodgeArray()[arraySlot];
//                    speed = antiquarian.getSpeedArray()[arraySlot];
//                    accMod = antiquarian.getAccModArray()[arraySlot];
//                    critMod = antiquarian.getCritModArray()[arraySlot];
//                    dmg = antiquarian.getDmgArray()[arraySlot];
//
//                    stunRes = 0.3 + .1 * resolveLvl;
//                    moveRes = 0.3 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    trapRes = 0.1 + .1 * resolveLvl;
//
//                    deathRes = 0.67;
                    break;
                case "Arbalest":
//                    arbalest = new Arbalest(this);
                    myHero = new Arbalest(this);

//                    maxHP = myHero.getMaxHPArray()[arraySlot];
//                    dodge = myHero.getDodgeArray()[arraySlot];
//                    speed = myHero.getSpeedArray()[arraySlot];
//                    accMod = myHero.getAccModArray()[arraySlot];
//                    critMod = myHero.getCritModArray()[arraySlot];
//                    dmg = myHero.getDmgArray()[arraySlot];
//
//                    stunRes = 0.4 + .1 * resolveLvl;
//                    moveRes = 0.4 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    trapRes = 0.1 + .1 * resolveLvl;
//
//                    deathRes = 0.67;
                    break;
                case "Bounty Hunter":
                    myHero = new BountyHunter(this);
//                    maxHP = bountyHunter.getMaxHPArray()[arraySlot];
//                    dodge = bountyHunter.getDodgeArray()[arraySlot];
//                    speed = bountyHunter.getSpeedArray()[arraySlot];
//                    accMod = bountyHunter.getAccModArray()[arraySlot];
//                    critMod = bountyHunter.getCritModArray()[arraySlot];
//                    dmg = bountyHunter.getDmgArray()[arraySlot];
//
//                    stunRes = 0.4 + .1 * resolveLvl;
//                    moveRes = 0.4 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.2 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    trapRes = 0.4 + .1 * resolveLvl;
//
//                    deathRes = 0.67;
                    break;
                case "Crusader":
                    myHero = new Crusader(this);
//                    maxHP = crusader.getMaxHPArray()[arraySlot];
//                    dodge = crusader.getDodgeArray()[arraySlot];
//                    speed = crusader.getSpeedArray()[arraySlot];
//                    accMod = crusader.getAccModArray()[arraySlot];
//                    critMod = crusader.getCritModArray()[arraySlot];
//                    dmg = crusader.getDmgArray()[arraySlot];
//
//                    stunRes = 0.4 + .1 * resolveLvl;
//                    moveRes = 0.4 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.1 + .1 * resolveLvl;
                    break;
                case "Flagellant":
                    myHero = new Flagellant(this);
//                    maxHP = flagellant.getMaxHPArray()[arraySlot];
//                    dodge = flagellant.getDodgeArray()[arraySlot];
//                    speed = flagellant.getSpeedArray()[arraySlot];
//                    accMod = flagellant.getAccModArray()[arraySlot];
//                    critMod = flagellant.getCritModArray()[arraySlot];
//                    dmg = flagellant.getDmgArray()[arraySlot];
//
//                    stunRes = 0.5 + .1 * resolveLvl;
//                    moveRes = 0.5 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.65 + .1 * resolveLvl;
//                    diseaseRes = 0.4 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.73;
//                    trapRes = 0 + .1 * resolveLvl;
                    break;
                case "Grave Robber":
                    myHero = new GraveRobber(this);
//                    maxHP = graveRobber.getMaxHPArray()[arraySlot];
//                    dodge = graveRobber.getDodgeArray()[arraySlot];
//                    speed = graveRobber.getSpeedArray()[arraySlot];
//                    accMod = graveRobber.getAccModArray()[arraySlot];
//                    critMod = graveRobber.getCritModArray()[arraySlot];
//                    dmg = graveRobber.getDmgArray()[arraySlot];
//
//                    stunRes = 0.2 + .1 * resolveLvl;
//                    moveRes = 0.2 + .1 * resolveLvl;
//                    blightRes = 0.5 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.5 + .1 * resolveLvl;
                    break;
                case "Hellion":
                    myHero = new Hellion(this);
//                    maxHP = hellion.getMaxHPArray()[arraySlot];
//                    dodge = hellion.getDodgeArray()[arraySlot];
//                    speed = hellion.getSpeedArray()[arraySlot];
//                    accMod = hellion.getAccModArray()[arraySlot];
//                    critMod = hellion.getCritModArray()[arraySlot];
//                    dmg = hellion.getDmgArray()[arraySlot];
//
//                    stunRes = 0.4 + .1 * resolveLvl;
//                    moveRes = 0.4 + .1 * resolveLvl;
//                    blightRes = 0.4 + .1 * resolveLvl;
//                    bleedRes = 0.4 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.2 + .1 * resolveLvl;
                    break;
                case "Highwayman":
                    myHero = new Highwayman(this);
//                    maxHP = highwayman.getMaxHPArray()[arraySlot];
//                    dodge = highwayman.getDodgeArray()[arraySlot];
//                    speed = highwayman.getSpeedArray()[arraySlot];
//                    accMod = highwayman.getAccModArray()[arraySlot];
//                    critMod = highwayman.getCritModArray()[arraySlot];
//                    dmg = highwayman.getDmgArray()[arraySlot];
//
//                    stunRes = 0.3 + .1 * resolveLvl;
//                    moveRes = 0.3 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.4 + .1 * resolveLvl;
                    break;
                case "Hound Master":
                    myHero = new HoundMaster(this);
//                    maxHP = houndMaster.getMaxHPArray()[arraySlot];
//                    dodge = houndMaster.getDodgeArray()[arraySlot];
//                    speed = houndMaster.getSpeedArray()[arraySlot];
//                    accMod = houndMaster.getAccModArray()[arraySlot];
//                    critMod = houndMaster.getCritModArray()[arraySlot];
//                    dmg = houndMaster.getDmgArray()[arraySlot];
//
//                    stunRes = 0.4 + .1 * resolveLvl;
//                    moveRes = 0.4 + .1 * resolveLvl;
//                    blightRes = 0.4 + .1 * resolveLvl;
//                    bleedRes = 0.4 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.4 + .1 * resolveLvl;
                    break;
                case "Jester":
                    myHero = new Jester(this);
//                    maxHP = jester.getMaxHPArray()[arraySlot];
//                    dodge = jester.getDodgeArray()[arraySlot];
//                    speed = jester.getSpeedArray()[arraySlot];
//                    accMod = jester.getAccModArray()[arraySlot];
//                    critMod = jester.getCritModArray()[arraySlot];
//                    dmg = jester.getDmgArray()[arraySlot];
//
//                    stunRes = 0.2 + .1 * resolveLvl;
//                    moveRes = 0.2 + .1 * resolveLvl;
//                    blightRes = 0.4 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.2 + .1 * resolveLvl;
//                    debuffRes = 0.4 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.3 + .1 * resolveLvl;
                    break;
                case "Leper":
                    myHero = new Leper(this);
//                    maxHP = leper.getMaxHPArray()[arraySlot];
//                    dodge = leper.getDodgeArray()[arraySlot];
//                    speed = leper.getSpeedArray()[arraySlot];
//                    accMod = leper.getAccModArray()[arraySlot];
//                    critMod = leper.getCritModArray()[arraySlot];
//                    dmg = leper.getDmgArray()[arraySlot];
//
//                    stunRes = 0.6 + .1 * resolveLvl;
//                    moveRes = 0.6 + .1 * resolveLvl;
//                    blightRes = 0.4 + .1 * resolveLvl;
//                    bleedRes = 0.1 + .1 * resolveLvl;
//                    diseaseRes = 0.2 + .1 * resolveLvl;
//                    debuffRes = 0.4 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.1 + .1 * resolveLvl;
                    break;
                case "Man-at-Arms":
                    myHero = new ManAtArms(this);
//                    maxHP = manAtArms.getMaxHPArray()[arraySlot];
//                    dodge = manAtArms.getDodgeArray()[arraySlot];
//                    speed = manAtArms.getSpeedArray()[arraySlot];
//                    accMod = manAtArms.getAccModArray()[arraySlot];
//                    critMod = manAtArms.getCritModArray()[arraySlot];
//                    dmg = manAtArms.getDmgArray()[arraySlot];
//
//                    stunRes = 0.5 + .1 * resolveLvl;
//                    moveRes = 0.5 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.4 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.1 + .1 * resolveLvl;
                    break;
                case "Occultist":
                    myHero = new Occultist(this);
//                    maxHP = occultist.getMaxHPArray()[arraySlot];
//                    dodge = occultist.getDodgeArray()[arraySlot];
//                    speed = occultist.getSpeedArray()[arraySlot];
//                    accMod = occultist.getAccModArray()[arraySlot];
//                    critMod = occultist.getCritModArray()[arraySlot];
//                    dmg = occultist.getDmgArray()[arraySlot];
//
//                    stunRes = 0.2 + .1 * resolveLvl;
//                    moveRes = 0.2 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.4 + .1 * resolveLvl;
//                    diseaseRes = 0.4 + .1 * resolveLvl;
//                    debuffRes = 0.6 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.1 + .1 * resolveLvl;
                    break;
                case "Plague Doctor":
                    myHero = new PlagueDoctor(this);
//                    maxHP = plagueDoctor.getMaxHPArray()[arraySlot];
//                    dodge = plagueDoctor.getDodgeArray()[arraySlot];
//                    speed = plagueDoctor.getSpeedArray()[arraySlot];
//                    accMod = plagueDoctor.getAccModArray()[arraySlot];
//                    critMod = plagueDoctor.getCritModArray()[arraySlot];
//                    dmg = plagueDoctor.getDmgArray()[arraySlot];
//
//                    stunRes = 0.2 + .1 * resolveLvl;
//                    moveRes = 0.2 + .1 * resolveLvl;
//                    blightRes = 0.6 + .1 * resolveLvl;
//                    bleedRes = 0.2 + .1 * resolveLvl;
//                    diseaseRes = 0.5 + .1 * resolveLvl;
//                    debuffRes = 0.5 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.2 + .1 * resolveLvl;
                    break;
                case "Shieldbreaker":
                    myHero = new Shieldbreaker(this);
//                    maxHP = shieldbreaker.getMaxHPArray()[arraySlot];
//                    dodge = shieldbreaker.getDodgeArray()[arraySlot];
//                    speed = shieldbreaker.getSpeedArray()[arraySlot];
//                    accMod = shieldbreaker.getAccModArray()[arraySlot];
//                    critMod = shieldbreaker.getCritModArray()[arraySlot];
//                    dmg = shieldbreaker.getDmgArray()[arraySlot];
//
//                    stunRes = 0.5 + .1 * resolveLvl;
//                    moveRes = 0.5 + .1 * resolveLvl;
//                    blightRes = 0.2 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.2 + .1 * resolveLvl;
                    break;
                case "Vestal":
                    myHero = new Vestal(this);
//                    maxHP = vestal.getMaxHPArray()[arraySlot];
//                    dodge = vestal.getDodgeArray()[arraySlot];
//                    speed = vestal.getSpeedArray()[arraySlot];
//                    accMod = vestal.getAccModArray()[arraySlot];
//                    critMod = vestal.getCritModArray()[arraySlot];
//                    dmg = vestal.getDmgArray()[arraySlot];
//
//                    stunRes = 0.3 + .1 * resolveLvl;
//                    moveRes = 0.3 + .1 * resolveLvl;
//                    blightRes = 0.3 + .1 * resolveLvl;
//                    bleedRes = 0.3 + .1 * resolveLvl;
//                    diseaseRes = 0.3 + .1 * resolveLvl;
//                    debuffRes = 0.3 + .1 * resolveLvl;
//                    deathRes = 0.67;
//                    trapRes = 0.1 + .1 * resolveLvl;
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

            stunRes = myHero.getStunRes() + .1 * resolveLvl;
            moveRes = myHero.getMoveRes() + .1 * resolveLvl;
            blightRes = myHero.getBlightRes() + .1 * resolveLvl;
            bleedRes = myHero.getBleedRes() + .1 * resolveLvl;
            diseaseRes = myHero.getDiseaseRes() + .1 * resolveLvl;
            debuffRes = myHero.getDebuffRes() + .1 * resolveLvl;
            trapRes = myHero.getTrapRes() + .1 * resolveLvl;

            deathRes = myHero.getDeathRes();

            //this is still within the IF statement so it won't trigger if the class was not set properly
            curHP = maxHP;
            //the specific damage types only matter once I bring quirks into it which can modify them individually
            //until then they are exactly the same as each other and as base dmg
            meleeDmg = dmg;
            rangedDmg = dmg;
        }
    }

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

    public double getAcc() {
        return acc;
    }

    public double getAccMod() {
        return accMod;
    }

    public int getAttacksMade() {
        return attacksMade;
    }

    public double getCrit() {
        return crit;
    }

    public double getCritMod() {
        return critMod;
    }

    public int getCrits() {
        return crits;
    }

    public int getDamageDealtDOT() {
        return damageDealtDOT;
    }

    public int getDamageDealtDirect() {
        return damageDealtDirect;
    }

    public int getDamageTakenDOT() {
        return damageTakenDOT;
    }

    public int getDamageTakenDirect() {
        return damageTakenDirect;
    }

    public double getDeathRes() {
        return deathRes;
    }

    public double getDiseaseRes() {
        return diseaseRes;
    }

    public double getDmg() {
        return dmg * dmgMod;
    }

    public double getDmgMod() {
        return dmgMod;
    }

    public Hero getGuardian() {
        return guardian;
    }

    public int getHealingDone() {
        return healingDone;
    }

    public String getHeroClass() {
        return heroClass.get();
    }

    public String getHeroDesc() {
        return getHeroClass() + " " + getHeroName();
    }

    public String getHeroName() {
        return heroName.get();
    }

    public double getMeleeDmg() {
        return meleeDmg * dmgMod;
    }

    public int getMisses() {
        return misses;
    }

    public int getMove1Rank() {
        return move1Rank;
    }

    public int getMove2Rank() {
        return move2Rank;
    }

    public int getMove3Rank() {
        return move3Rank;
    }

    public int getMove4Rank() {
        return move4Rank;
    }

    public int getMove5Rank() {
        return move5Rank;
    }

    public int getMove6Rank() {
        return move6Rank;
    }

    public int getMove7Rank() {
        return move7Rank;
    }

    public ArrayList<String> getOtherQuirks() {
        return otherQuirks;
    }

    public double getRangedDmg() {
        return rangedDmg * dmgMod;
    }

    public int getResolveLvl() {
        return resolveLvl.get();
    }

    public ArrayList<String> getStatQuirks() {
        return statQuirks;
    }

    public int getStressLvl() {
        return stressLvl;
    }

    public double getStressMod() {
        return stressMod;
    }

    public ArrayList<String> getStressQuirks() {
        return stressQuirks;
    }

    public ArrayList<String> getTownQuirks() {
        return townQuirks;
    }

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

//        switch (heroClass.getValue()) {
//            case "Antiquarian":
//                antiquarian.selectAction(combat);
//                break;
//            case "Arbalest":
//                arbalest.selectAction(combat);
//                break;
//            case "Bounty Hunter":
//                bountyHunter.selectAction(combat);
//                break;
//            case "Crusader":
//                crusader.selectAction(combat);
//                break;
//            case "Flagellant":
//                flagellant.selectAction(combat);
//                break;
//            case "Grave Robber":
//                graveRobber.selectAction(combat);
//                break;
//            case "Hellion":
//                hellion.selectAction(combat);
//                break;
//            case "Highwayman":
//                highwayman.selectAction(combat);
//                break;
//            case "Hound Master":
//                houndMaster.selectAction(combat);
//                break;
//            case "Jester":
//                jester.selectAction(combat);
//                break;
//            case "Leper":
//                leper.selectAction(combat);
//                break;
//            case "Man-At-Arms":
//                manAtArms.selectAction(combat);
//                break;
//            case "Occultist":
//                occultist.selectAction(combat);
//                break;
//            case "Plague Doctor":
//                plagueDoctor.selectAction(combat);
//                break;
//            case "Shieldbreaker":
//                shieldbreaker.selectAction(combat);
//                break;
//            case "Vestal":
//                vestal.selectAction(combat);
//                break;
//            default:
//                System.out.println("Hero.selectAction() did not receive a valid class (Tara is poopoo)");
//        }
        myHero.selectAction(combat);
    }

    /**
     * This method is used to reset class abilities that are "X uses per
     * battle".
     *
     *
     */
    public void resetSpecials() {
//        switch (getHeroClass()) {
//            case "Man-At-Arms":
//                manAtArms.resetSpecials();
//                break;
//            case "Plague Doctor":
//                plagueDoctor.resetSpecials();
//                break;
//        }

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
