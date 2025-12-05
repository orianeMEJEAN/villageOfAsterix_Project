package characters.gauls;

import characters.Character;
import characters.Leader;
import characters.Worker;
import enums.GaulType;
import enums.Gender;
import food.enums.Ingredient;
import magicPotion.Pot;

/**
 * Handles the creation of a Druid
 */
public class Druid extends Gaul implements Leader {

    private Pot pot;

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
        this.pot = new Pot();
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
     * Attempts to cook a magic potion using the given ingredient.
     * The ingredient must be present in the druid's inventory and allowed
     * by the pot whitelist. If valid, the ingredient is added to the pot.
     * Once all ingredients are added, the pot attempts to brew the potion.
     *
     * @param ingredient the ingredient to use for brewing
     * @throws IllegalArgumentException if the ingredient is not in the inventory
     */
    public void makePotion(Ingredient ingredient)
    {
        if (!getInventory().contains(ingredient)) {
            throw new IllegalArgumentException("L'ingrédient " + ingredient + " n'est pas dans l'inventaire !");
        }

        if (!pot.canAccept(ingredient)) {
            System.out.println("La marmite refuse cet ingrédient : " + ingredient);
            return;
        }

        pot.addIngredient(ingredient);

        getInventory().removeItem(ingredient);

        pot.brewPotion();
    }

}
