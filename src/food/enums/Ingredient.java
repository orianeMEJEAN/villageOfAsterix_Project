/**
 * Represents an available ingredient.
 * Each ingredient has:
 *     a readable display name,
 *     a category (meat, fish, plant, etc.),
 *     an optional indicator specifying whether it can be counted as a vegetable.
 *
 * @author Oriane
 * @version 1.0
 */
package food.enums;

public enum Ingredient
{
    boar("sanglier", Category.meat, false),
    moderatelyFreshFish("poisson passablement frais", Category.fish, false),
    notFreshFish("poisson pas frais", Category.fish, false),
    mistletoe("gui", Category.plant, true),
    lobster("homard", Category.meat, false),
    strawberries("fraises", Category.fruit, true),
    carrots("carottes", Category.vegetable, true),
    salt("sel", Category.condiment, false),
    freshFourLeafClover("trèfle à quatre feuilles frais", Category.plant, true),
    notFreshFourLeafClover("trèfle à quatre feuilles pas frais", Category.plant, true),
    rockOil("huile de roche", Category.other, false),
    beetrootJuice("jus de betterave", Category.drink, true),
    honey("miel", Category.sweet, false),
    wine("vin", Category.drink, false),
    mead("hydromel", Category.drink, false),
    twoHeadedUnicornMilk("lait de licorne à deux têtes", Category.drink, false),
    idefixHair("poils d'Idéfix", Category.other, false),
    secretIngredient("ingrédient secret", Category.other, false);

    /**
     * Lists all possible categories an ingredient may belong to.
     * These categories help classify food items.
     */
    public enum Category
    {
        meat,
        fish,
        plant,
        vegetable,
        fruit,
        drink,
        condiment,
        sweet,
        other
    }

    private final String displayName;
    private final Category category;
    private final boolean isVegetable;

    /**
     * Builds an ingredient.
     *
     * @param displayName readable display name of the ingredient
     * @param category category of the ingredient
     * @param isVegetable indicates whether this ingredient can be considered a vegetable
     */
    Ingredient(String displayName, Category category, boolean isVegetable)
    {
        this.displayName = displayName;
        this.category = category;
        this.isVegetable = isVegetable;
    }

    /**
     * Returns the display name of the ingredient.
     *
     * @return a readable name
     */
    public String getDisplayName() { return displayName; }

    /**
     * Returns the category of the ingredient.
     *
     * @return the ingredient’s category
     */
    public Category getCategory() { return category; }

    /**
     * Indicates whether this ingredient can be counted as a vegetable.
     *
     * @return true if the ingredient can be considered a vegetable
     * @return false otherwise
     */
    public boolean isVegetable() { return isVegetable; }
}