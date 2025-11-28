package characters.gauls;

import characters.Character;
import characters.Worker;
import enums.GaulType;
import enums.Gender;

/**
 * Represents a Gaul character, a cultural subgroup of characters
 * known for their unique battle cry and their particular affinity
 * with magic potions.
 *
 * This class mainly provides shared Gaul-specific
 * behaviors, while delegating all common character attributes
 * and actions to the {@link characters.Character} base class.
 */
public class Gaul extends characters.Character  implements Worker {

    // private static final double POTION_STRENGTH_MULTIPLIER = 2.5;
    private GaulType type;

    /**
     * Creates a new Gaul with the specified base attributes.
     *
     * @param name              character's name
     * @param gender            biological gender
     * @param height            height in centimeters
     * @param age               age in years
     * @param strength          physical strength
     * @param endurance         resistance to damage
     * @param health            current health points
     * @param hunger            current hunger level
     * @param belligerence      aggressivity level
     * @param magicPotionLevel  current magic potion amount
     * @param type              Gaul's type use None as a default parameter
     */
    public Gaul(String name,
                    Gender gender,
                    int height,
                    int age,
                    int strength,
                    int endurance,
                    int health,
                    int hunger,
                    int belligerence,
                    int magicPotionLevel,
                    GaulType type
    ) {
        super(name, gender, height, age, strength, endurance,
                health, hunger, belligerence, magicPotionLevel);
        this.type = type;
    }

    /**
     * Returns gaul's type
     *
     * @return
     */
    public GaulType getType() {
        return type;
    }

    /**
     * Modify gaul's type
     *
     * @param type
     */
    public void setType(GaulType type) {
        this.type = type;
    }

    /**
     *  Makes the Gaul Work (NOT IMPLEMENTED YET)
     *  TODO
     */
    public void work(){
        System.out.println("Working...");
    }

    /**
     * Returns a string representation of this character.
     * The output contains all attributes with their current values.
     *
     * @return a formatted string describing the character
     */
    @Override
    public String toString() {
        return "Character {" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", height=" + getHeight() +
                ", age=" + getAge() +
                ", strength=" + getStrength() +
                ", endurance=" + getEndurance() +
                ", health=" + getHealth() +
                ", maxHealth=" + getMaxHealth() +
                ", hunger=" + getHunger() +
                ", maxHunger=" + getMaxHunger() +
                ", belligerence=" + getBelligerence() +
                ", magicPotionLevel=" + getBelligerence() +
                ", type=" + getType() +
                '}';
    }

}
