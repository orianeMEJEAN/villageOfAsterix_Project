package characters.romans;

import characters.Leader;
import characters.enums.Gender;
import characters.enums.PersonType;

/**
 * Handles the creation of a roman General
 */
public class General extends Roman implements Leader {
    /**
     * Creates a new character with all base attributes.
     *
     * @param name              character's name
     * @param gender            biological gender
     * @param height            height in centimeters
     * @param age               age in years
     * @param strength          physical strength
     * @param endurance         resistance to damage
     * @param health            current health points
     * @param hunger            current hunger level
     * @param belligerence      belligerence level
     * @param magicPotionLevel  current magic potion amount
     */
    public General(String name,
                   Gender gender,
                   int height,
                   int age,
                   int strength,
                   int endurance,
                   int health,
                   int hunger,
                   int belligerence,
                   int magicPotionLevel
    ) {
        super(name, gender, height, age, strength, endurance,
                health,hunger, belligerence, magicPotionLevel,
                PersonType.roman_fighter);
    }

    /**
     * Makes the General lead.
     *
     */
    @Override
    public void lead() {
        System.out.println(getName() + " is leading...");
    }
}
