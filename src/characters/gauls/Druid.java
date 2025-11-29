package characters.gauls;

import characters.Character;
import characters.Leader;
import characters.Worker;
import enums.GaulType;
import enums.Gender;

/**
 * Handles the creation of a Druid
 */
public class Druid extends Gaul implements Leader {

    /**
     * Creates a new Druid with the specified base attributes.
     *
     * @param name             character's name
     * @param gender           biological gender
     * @param height           height in centimeters
     * @param age              age in years
     * @param strength         physical strength
     * @param endurance        resistance to damage
     * @param health           current health points
     * @param hunger           current hunger level
     * @param belligerence     aggressivity level
     * @param magicPotionLevel current magic potion amount
     */
    public Druid(String name,
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
        super(name, gender, height, age, strength, endurance, health,
                hunger, belligerence, magicPotionLevel, GaulType.DRUID);
    }

    /**
     *  Makes the Druid lead (NOT IMPLEMENTED YET)
     *  TODO
     */
    public void lead(){
        System.out.println(getName() + " is leading...");
    }

    public void heal(Character character, int points){
        character.heal(points);
    }

    /**
     * Makes the Druid make a poition pot
     * TODO
     * Implémenter une classe marmite afin d'avoir une marmite avec x dose de potion magique.
     */
    public void makePotion()
    {
        System.out.println("Making Potion...");



        System.out.println("Potion Made !");
    }
}
