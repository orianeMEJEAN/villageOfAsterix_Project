package characters.romans;

import enums.Gender;
import enums.PersonType;
import magicPotion.MagicPotion;

public class Roman extends characters.Character {

    // private static final double POTION_STRENGTH_MULTIPLIER = 2.0;

    /**
     * Creates a new character with all base attributes.
     *
     * @param name             character's name
     * @param gender           biological gender
     * @param height           height in centimeters
     * @param age              age in years
     * @param strength         physical strength
     * @param endurance        resistance to damage
     * @param health           current health points
     * @param hunger           current hunger level
     * @param belligerence     belligerence level
     * @param magicPotion current magic potion amount
     */
    public Roman(String name,
                 Gender gender,
                 int height,
                 int age,
                 int strength,
                 int endurance,
                 int health,
                 int hunger,
                 int belligerence,
                 MagicPotion magicPotion
    ) {
        super(name, gender, height, age, strength, endurance,
                health, hunger, belligerence, magicPotion,
                PersonType.roman);
    }


}
