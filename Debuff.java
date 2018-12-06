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
public class Debuff {

    private int duration;
    private String name;
    private int dmg;
    private Hero user;
    private Hero target;
    private int rank;

    /**
     * This constructor is intended to be used for DOTs: bleeds and blight
     * effects.
     *
     * @param type     bleed or blight.
     * @param duration The duration in rounds.
     * @param dmg      The damage dealt per round.
     *
     */
    public Debuff(String type, int duration, int dmg) {
        if (type.equals("Bleed") || type.equals("Blight")) {
            this.duration = duration;
            this.name = type;
            this.dmg = dmg;
        } else {
            System.out.println("DOT debuff constructor was called (on a Hero) with a bad string");
        }

    }

    public Debuff(String type, int duration, int dmg, Hero user) {
        if (type.equals("Bleed") || type.equals("Blight")) {
            this.duration = duration;
            this.name = type;
            this.dmg = dmg;
            this.user = user;
        } else {
            System.out.println("DOT debuff constructor was called (on an Enemy) with a bad string");
        }
    }

    /**
     * This constructor is intended for adding non-DOT status effects (stat
     * modifiers generally).
     *
     * @param name     the actual name of the debuff as it appears in game
     * @param duration The duration in rounds.
     * @param target   The Hero the Debuff will be applied to.
     */
    public Debuff(String name, int duration, Hero target) {
        //my thought initially was that this constructor would just be called directly
        //(ie without being wrapped in a call to add it to the debuff array) but
        //I'm no longer sold on that.
        this.name = name;
        this.duration = duration;

        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, true);
        }
    }

    /**
     * This constructor is for adding status effects to friendlies.
     *
     * @param name
     * @param duration
     * @param target
     * @param user
     */
    public Debuff(String name, int duration, Hero target, Hero user) {
        this.name = name;
        this.duration = duration;
        this.user = user;

        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, true);
        }
    }

    public Debuff(String name, int duration, Enemy target, Hero attacker) {
        this.user = attacker;
        this.name = name;
        this.duration = duration;

        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, true);
        }
    }

    public Debuff(String name, int duration, Enemy target) {
        this.name = name;
        this.duration = duration;

        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, true);
        }
    }

    public void removeDebuff(Hero target) {
        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, false);
        }
        target.getDebuffs().remove(this);
    }

    public void removeDebuff(Enemy target) {
        if (!name.equals("Blight") && !name.equals("Bleed") && !name.equals("Marked") && !name.equals("Stun")) {
            setModifiers(name, target, false);
        }
        target.getDebuffs().remove(this);
    }

    /**
     * This is the method that actually applies or removes *harmful* status
     * effects to *Heroes*.
     *
     * @param status This should be true if the debuff is being *applied* and
     *               false if it's being *removed*.
     */
    private void setModifiers(String name, Hero target, boolean status) {
        switch (name) {
            //cases should be organized alphabetically for ease of reading/finding specific debuffs
            case "Accusation":
                if (status) {
                    target.setStressMod(.1);
                } else {
                    target.setStressMod(-.1);
                }
                break;
            case "Adrenaline Rush":
                rank = target.getMove6Rank() - 1;
                if (status) {
                    target.setAccMod(.05 + .01 * rank);
                    target.setDmgMod(.20 + .02 * rank);
                } else {
                    target.setAccMod((.05 + .01 * rank) * -1);
                    target.setDmgMod((.20 + .02 * rank) * -1);
                }
                break;
            case "Blanket Fire":
                if (status) {
                    target.setDodge(-.2);
                } else {
                    target.setDodge(.2);
                }
                break;
            case "Brine":
                if (status) {
                    target.setDodge(-.2);
                    target.setSpeed(- 3);
                } else {
                    target.setDodge(.2);
                    target.setSpeed(3);
                }
                break;
            case "Death's Door":
                if (status) {
                    target.setAccMod(-.1);
                    target.setDmgMod(-.25);
                    target.setSpeed(-5);
                    target.setStressMod(.33);
                } else {
                    target.setAccMod(.1);
                    target.setDmgMod(.25);
                    target.setSpeed(5);
                    target.setStressMod(-.33);
                }
                break;
            case "Death's Door Recovery":
                if (status) {
                    target.setAccMod(-.02);
                    target.setDmgMod(-.05);
                    target.setSpeed(-1);
                    target.setStressMod(.1);
                } else {
                    target.setAccMod(.02);
                    target.setDmgMod(.05);
                    target.setSpeed(1);
                    target.setStressMod(-.1);
                }
                break;
            case "Drum of Debilitation":
                if (status) {
                    target.setDodge(-.1);
                } else {
                    target.setDodge(.1);
                }
                break;
            case "Emboldening Vapours":
                rank = user.getMove6Rank() - 1;
                if (status) {
                    target.setDmgMod(.2 + .0125 * rank);
                } else {
                    target.setDmgMod((.2 + .0125 * rank) * -1);
                }
                break;
            case "Purge":
                if (status) {
                    target.setAccMod(.05);
                } else {
                    target.setAccMod(-.05);
                }
                break;
            case "Rend for the Old Gods":
                if (status) {
                    target.setStressMod(.1);
                } else {
                    target.setStressMod(-.1);
                }
                break;
            case "Slice and Dice":
                if (status) {
                    target.setBleedRes(-.15);
                } else {
                    target.setBleedRes(.15);
                }
                break;
            case "Thrown Dagger":
                rank = user.getMove5Rank() - 1;
                if (status) {
                    target.setAccMod(.05 + .0125 * rank);
                } else {
                    target.setAccMod((.05 + .0125 * rank) * -1);
                }
                break;
            case "Tracking Shot":
                rank = target.getMove5Rank() - 1;
                if (status) {
                    target.setAccMod((.06 + .01 * rank));
                    target.setCritMod((.05 + .013 * rank));
                    target.setDmgMod((.12 + .02 * rank));
                } else {
                    target.setAccMod((.06 + .01 * rank) * -1);
                    target.setCritMod((.05 + .013 * rank) * -1);
                    target.setDmgMod((.12 + .02 * rank) * -1);
                }
                break;
            default:
                System.out.println(this.toString() + ": Debuff " + name + " (affecting Heroes) not found!");
        }
    }

    /**
     * This is the method that actually applies or removes status effects to
     * *enemies*.
     *
     * @param status This should be true if the debuff is being *applied* and
     *               false if it's being *removed*.
     */
    private void setModifiers(String name, Enemy target, boolean status) {
        switch (name) {
            //cases should be organized alphabetically for ease of reading/finding specific debuffs
            case "Barnacle Barrier":
                if (status) {
                    target.setProt(.25);
                } else {
                    target.setProt(-.25);
                }
                break;
            case "Guard":
                if (!status) {
                    //this exists to remove the guardian status when the relevant buff expires
                    //it doesn't do anything when initially applied (that's handled in the spell method)
                    target.setGuardian(null);
                }
                break;
            case "Harmless Poke":
                if (status) {
                    target.setSpeed(2);
                } else {
                    target.setSpeed(-2);
                }
                break;
            case "Illumination":
                rank = user.getMove6Rank() - 1;
                if (status) {
                    target.setDodge((.2 + .025 * rank) * -1);
                } else {
                    target.setDodge((.2 + .025 * rank));
                }
                break;
            case "Noxious Blast":
                if (status) {
                    target.setAccMod(-.05);
                } else {
                    target.setAccMod(.05);
                }
                break;
            case "Open Vein":
                rank = user.getMove7Rank() - 1;
                if (status) {
                    target.setBleedRes((.2 + .033 * rank) * -1);
                    target.setSpeed((int) ((1 + .5 * rank) * - 1));
                } else {
                    target.setBleedRes((.2 + .033 * rank));
                    target.setSpeed((int) ((1 + .5 * rank)));
                }
                break;
            case "Point Blank Shot Debuff":
                if (status) {
                    target.setSpeed(-2);
                } else {
                    target.setSpeed(2);
                }
                break;
            case "Poison Dart":
                rank = user.getMove6Rank() - 1;
                if (status) {
                    target.setBlightRes((.2 + .033 * rank) * -1);
                } else {
                    target.setBlightRes((.2 + .033 * rank));
                }
                break;
            case "Sniper's Mark Debuff":
                if (status) {
                    target.setDodge(-.2); //this modifier should be stronger based on the skill rank but I'm not getting that rn
                } else {
                    target.setDodge(.2);
                }
                break;
            case "Target Whistle":
                if (status) {
                    target.setProt((.2 + .025 * user.getMove3Rank()) * -1);
                } else {
                    target.setProt(.2 + .025 * user.getMove3Rank());
                }
                break;
            case "Weakening Curse":
                rank = user.getMove3Rank() - 1;
                if (status) {
                    target.setDmgMod((.1 + .025 * rank));
                    target.setProt((.1 + .025 * rank));
                } else {
                    target.setDmgMod((.1 + .025 * rank) * -1);
                    target.setProt((.1 + .025 * rank) * -1);
                }
                break;
            default:
                System.out.println(this.toString() + ": Debuff " + name + " (affecting Enemies) not found!");
        }
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDuration(int duration) {
        this.duration += duration;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public Hero getAttacker() {
        return user;
    }

}
