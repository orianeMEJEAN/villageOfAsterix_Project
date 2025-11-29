package magicPotion;

import food.enums.Ingredient;

import java.util.List;

/**
 * Represents the magic potion of Asterix and Obelix.
 *
 * This class models the effects of a brewed magic potion based on
 * the list of ingredients used to create it. It validates that all
 * required base ingredients are present and limits the number of
 * special ingredients that grant additional powers.
 *
 * The potion can be nourishing (lobster, strawberries, beetroot juice)
 * or can grant special powers depending on the presence of specific
 * ingredients such as two-headed unicorn milk or Idéfix's hair.
 *
 * @author Lou
 * @subauthor Oriane &amp; Maxence
 * @version 4.0
 */
public class MagicPotion {
    /** List of optional ingredients
     * that makes the potion nourishing or grants powers
     */
    private boolean withLobster;
    private boolean withStrawberries;
    private boolean withBeetJuice;

    private boolean withUnicornMilk;
    private boolean withIdefixsHair;

    private final int specialIngredientsThreshold = 1;

    /**
     * Builds a magic potion from the given list of ingredients.
     *
     * The constructor:
     *     checks that all mandatory base ingredients are present,
     *     checks that the number of special ingredients does not exceed
     *         {@link #specialIngredientsThreshold},
     *     initializes internal flags according to the provided ingredients.
     *
     * @param ingredients the list of ingredients used to brew the potion
     * @throws IllegalArgumentException if some base ingredients are missing
     *                                  or if too many special ingredients are present
     */
    public MagicPotion(List<Ingredient> ingredients){
        if (!checkIngredients(ingredients)){
            throw new IllegalArgumentException("Ingrédients de base manquants pour créer la potion !");
        }

        this.withLobster = ingredients.contains(Ingredient.lobster);
        this.withStrawberries = ingredients.contains(Ingredient.strawberries);
        this.withBeetJuice = ingredients.contains(Ingredient.beetrootJuice);

        this.withUnicornMilk = ingredients.contains(Ingredient.twoHeadedUnicornMilk);
        this.withIdefixsHair = ingredients.contains(Ingredient.idefixHair);
    }

    /**
     * Validates the list of ingredients used to brew the potion.
     *
     * This method checks:
     *
     *     that all base ingredients are present,
     *     that the number of special ingredients does not exceed
     *         {@link #specialIngredientsThreshold}.
     *
     *
     * @param ingredients the list of ingredients to validate
     * @return {@code true} if the ingredients are valid, {@code false} otherwise
     */
    private boolean checkIngredients(List<Ingredient> ingredients) {

        Ingredient[] baseIngredients = {
                Ingredient.mistletoe,
                Ingredient.carrots,
                Ingredient.salt,
                Ingredient.freshFourLeafClover,
                Ingredient.moderatelyFreshFish,
                Ingredient.rockOil,
                Ingredient.honey,
                Ingredient.mead,
                Ingredient.secretIngredient
        };

        Ingredient[] specialIngredients = {
                Ingredient.twoHeadedUnicornMilk,
                Ingredient.idefixHair
        };

        boolean valid = true;

        for (Ingredient ingredient : baseIngredients) {
            if (!ingredients.contains(ingredient)) {
                System.out.println("Ingrédient manquant : " + ingredient.getDisplayName());
                valid = false;
            }
        }

        int specialCount = 0;

        for (Ingredient ingredient : specialIngredients) {
            if (ingredients.contains(ingredient)) {
                specialCount++;
            }
        }

        if (specialCount > specialIngredientsThreshold) {
            System.out.println("Trop d'ingrédients spéciaux ! (" +
                    specialCount + "/" + specialIngredientsThreshold + ")");
            valid = false;
        }

        return valid;
    }

    /**
     * Checks whether the potion is nourishing.
     *
     * @return true if the potion is nourishing, false otherwise
     */
    public boolean isNourishing() {
        return withLobster || withStrawberries || withBeetJuice;
    }

    /**
     * Displays the full recipe of the potion.
     *
     * Shows the ingredients
     * and the currently available number of doses.
     */
    public void printRecipe() {
        System.out.println("\nRECETTE DE LA POTION MAGIQUE");
        System.out.println("Ingrédients de base :");
        System.out.println("  - Gui");
        System.out.println("  - Carottes");
        System.out.println("  - Sel");
        System.out.println("  - Trèfle à quatre feuilles");
        System.out.println("  - Poisson passablement frais");
        System.out.println("  - Huile de roche");
        System.out.println("  - Miel");
        System.out.println("  - Hydromel");
        System.out.println("  - Ingredient secret");

        if (withLobster || withStrawberries || withBeetJuice ||
                withUnicornMilk || withIdefixsHair) {
            System.out.println("\nIngrédients spéciaux ajoutés :");
            if (withLobster) System.out.println("  - Homard");
            if (withStrawberries) System.out.println("  - Fraises");
            if (withBeetJuice) System.out.println("  - Jus de betterave");
            if (withUnicornMilk) System.out.println("  - Lait de licorne à deux têtes");
            if (withIdefixsHair) System.out.println("  - Poils d'Idéfix");
        }
    }
}