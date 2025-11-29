/**
 * Represents a pot containing doses of a magic potion.
 * A pot can be consumed dose by dose or entirely,
 * and can be refilled or augmented with additional doses.
 *
 * @author Oriane & Maxence & Lou
 * @version 1.4
 */
package magicPotion;

import java.util.ArrayList;
import food.enums.Ingredient;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Pot
{
    private int nbDoses;
    public static final int dosesPerPot = 10;
    private MagicPotion magicPotion;
    private final List<Ingredient> ingredients = new ArrayList<>();

    private final Set<Ingredient.Category> whitelist;

    /**
     * Creates an empty pot with a default whitelist
     * of allowed ingredient categories
     */
    public Pot()
    {
        this(EnumSet.of(
                Ingredient.Category.meat,
                Ingredient.Category.fish,
                Ingredient.Category.plant,
                Ingredient.Category.vegetable,
                Ingredient.Category.fruit,
                Ingredient.Category.drink,
                Ingredient.Category.condiment,
                Ingredient.Category.sweet,
                Ingredient.Category.other
        ));
    }

    /**
     * Creates an empty pot with a custom whitelist of allowed categories.
     *
     * @param allowedCategories the set of allowed ingredient categories
     */
    public Pot(Set<Ingredient.Category> allowedCategories)
    {
        this.whitelist = EnumSet.copyOf(allowedCategories);
        this.nbDoses = 0;
        this.magicPotion = null;
    }

    /**
     * Checks whether the given ingredient can be added to the pot
     * according to the whitelist
     *
     * @param ingredient the ingredient to check
     * @return true if the ingredient is allowed, false otherwite
     */
    public boolean canAccept(Ingredient ingredient){
        if (ingredient == null){
            return false;
        }
        return whitelist.contains(ingredient.getCategory());
    }

    /**
     * Checks whether the given ingredient can be added to the pot
     * and then adds it if it can
     *
     * @param ingredient the ingredient to check
     * @return true if the ingredient is allowed, false otherwite
     */
    public boolean addIngredient(Ingredient ingredient){
        if (!canAccept(ingredient)){
            return false;
        }
        ingredients.add(ingredient);
        return true;
    }

    /**
     * Checks whether the potion can be brewed or not and makes it if possible
     *
     * @throws IllegalArgumentException if potion creation fails.
     */
    public void brewPotion(){
        try {
            this.magicPotion = new MagicPotion(ingredients);
            this.nbDoses = dosesPerPot;
        } catch (IllegalArgumentException e) {
            System.out.println("La potion n'a pu être crée " + e.getMessage());
            flipPot();
        }
    }

    /**
     * Resets the ingredients in the pot and empties the pot itself at the same time.
     */
    public void flipPot(){
        ingredients.clear();
        magicPotion = null;
        nbDoses = 0;
    }

    /**
     * Drinks a single dose from the pot.
     * Decreases the number of doses by one and prints the remaining amount.
     *
     * @return true if the dose was consumed, false if the pot is empty
     */
    public boolean drinkADose()
    {
        if (nbDoses <= 0) {
            System.out.println("La marmite est vide !");
            return false;
        }
        this.nbDoses--;

        System.out.println("Il reste " + nbDoses + " à la marmite.");
        return true;
    }

    /**
     * Drinks an entire pot worth of potion.
     * This consumes {@link #dosesPerPot} doses at once.
     *
     * @return true if a full pot could be consumed, false if not enough doses are available
     */
    public boolean drinkWhole()
    {
        if (nbDoses < dosesPerPot) {
            System.out.println("Doses insuffisante !");
            return false;
        }
        nbDoses -= dosesPerPot;
        return true;
    }

    /**
     * Adds a given number of doses to the pot.
     *
     * @param dose the amount of doses to add
     */
    public void addDose(int dose)
    {
        this.nbDoses += dose;
    }

    /**
     * Refills the pot back to the default capacity.
     */
    public void refill()
    {
        this.nbDoses = dosesPerPot;
    }

    /**
     * Returns the current number of remaining doses.
     *
     * @return the number of doses
     */
    public int getnbDoses()
    {
        return this.nbDoses;
    }

    /**
     * Returns the number of doses contained in one full pot.
     *
     * @return the current number of doses
     */
    public int getDosesPerPot(){
        return this.dosesPerPot;
    }

    /**
     * Checks whether the pot contains at least the required number of doses.
     *
     * @param required the minimum number of doses needed
     * @return true if the pot has at least the given amount, false otherwise
     */
    public boolean hasAtLeast(int required)
    {
        return nbDoses >= required;
    }

    /**
     * Checks whether the pot is empty.
     *
     * @return true if the number of doses is 0 or less, false otherwise
     */
    public boolean isEmpty()
    {
        return nbDoses <= 0;
    }

    /**
     * Returns the magic potion
     *
     * @return
     */
    public MagicPotion getMagicPotion(){
        return this.magicPotion;
    }
}