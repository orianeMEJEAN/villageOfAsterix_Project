package characters.romans;

import characters.Leader;
import enums.Gender;
import enums.PersonType;

/**
 * Handles the creation of a roman prefect
 */
public class Prefect extends Roman implements Leader {
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
    public Prefect(String name,
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
                PersonType.roman);
    }

    /**
     * Makes the prefect lead.
     *
     * TODO
     * Une vrai méthode de leader
     */
    @Override
    public void lead() {
        System.out.println(getName() + " is leading...");
    }
}
