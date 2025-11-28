package magicPotion;

import food.enums.Ingredient;
import characters.inventory.Inventory;

import java.util.Set;

/**
 * Represents the magic potion of Asterix and Obelix.
 *
 * This class allows creating a potion and adding various ingredients
 * that grant "powers", consuming it by doses or by whole pots,
 * and managing its stock.
 * One pot contains 10 doses by default.
 *
 * @author Lou
 * @version 2.0
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

    /** Number of doses available in the pot
     */
    private int doseNumber;

    /** Number of doses available in the pot
     */
    private static final int DOSES_PER_POT = 10;

    /**
     * Magic potion constructor.
     *
     * Creates a new potion with 10 doses if all base ingredients are available
     */
    public MagicPotion(Set<Ingredient> availableIngredients) {
        if (!checkMainIngredients(inventory)) {
            throw new IllegalArgumentException("Ingrédients de base manquants pour créer la potion !");
        }
        this.doseNumber = DOSES_PER_POT;
        System.out.println("Potion magique créée avec succès !");
    }

    /**
     * Verify if the base ingredients are avaible
     *
     * @param inventory
     * @return Boolean
     */
    private boolean checkMainIngredients(Inventory<Ingredient> inventory) {
        System.out.println("\nVÉRIFICATION DES INGRÉDIENTS");

        // List of main ingredients
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

        boolean allPresent = true;

        // Check main Ingredients
        for (Ingredient ingredient : baseIngredients) {
            if (!hasIngredient(inventory, ingredient)) {
                System.out.println("Ingrédient manquant : " + ingredient.getDisplayName());
                allPresent = false;
            }
        }

        if (allPresent) {
            System.out.println("Tous les ingrédients de base sont présents !");
        }
        return allPresent;
    }

    /**
     * Checks if a specific ingredient is present in the inventory.
     *
     * @param inventory the inventory to search in
     * @param ingredient the ingredient to look for
     * @return true if the ingredient is found, false otherwise
     */
    private boolean hasIngredient(Inventory<Ingredient> inventory, Ingredient ingredient) {
        return inventory.getItems().contains(ingredient);
    }

    /**
     * Adds lobster to the potion,
     * makes the potion nourishing
     */
    public void addLobster() {
        this.withLobster = true;
        System.out.println("Homard ajouté - Potion plus nourrissante !");
    }

    /**
     * Adds strawberries to the potion.
     * Makes the potion nourishing
     */
    public void addStrawberries() {
        this.withStrawberries = true;
        System.out.println("Fraises ajoutées - Potion plus nourrissante !");
    }

    /**
     * Replaces rock oil with beetroot juice,
     * makes the potion nourishing
     */
    public void replaceByBeetJuice() {
        this.withBeetJuice = true;
        System.out.println("Huile de roche remplacée par du jus de betterave - Potion plus nourrissante !");
    }

    /**
     * Adds two-headed unicorn milk to the potion.
     * Grants duplication power
     */
    public void addUnicornMilk() {
        this.withUnicornMilk = true;
        System.out.println("Lait de licorne à deux têtes ajouté - Pouvoir de dédoublement !");
    }

    /**
     * Adds Idéfix's fur to the potion.
     * Grants metamorphosis power
     */
    public void addIdefixsHair() {
        this.withIdefixsHair = true;
        System.out.println("Poils d'Idéfix ajoutés - Pouvoir de métamorphosis !");
    }

    /**
     * Allows drinking a single dose of potion.
     * Decreases the number of doses by 1 and displays
     * the obtained powers
     *
     * @return true if a dose could be consumed, false if the pot is empty
     */
    public boolean drinkADose() {
        if (doseNumber <= 0) {
            System.out.println("La marmite est vide !");
            return false;
        }

        // drink one dose
        doseNumber--;

        // Display obtained effects
        System.out.println("\n une dose bu!");
        System.out.println("Effets temporaires obtenus :");
        System.out.println("  - Force surhumaine");
        System.out.println("  - Invincibilité");

        // Special effects from ingredients
        if (withUnicornMilk) {
            System.out.println("  - Dédoublement");
        }
        if (withIdefixsHair) {
            System.out.println("  - Métamorphose");
        }

        System.out.println("Doses restantes : " + doseNumber + "\n");
        return true;
    }

    /**
     * Allows drinking an entire pot of potion
     * and displays the obtained powers
     *
     * @return true if a pot could be consumed, false if there are not enough doses
     */
    public boolean drinkAPot() {
        if (doseNumber < DOSES_PER_POT) {
            System.out.println("Doses insuffisante !");
            return false;
        }

        // drink the whole pot
        doseNumber -= DOSES_PER_POT;

        // Display permanent effects
        System.out.println("\n marmite bu !");
        System.out.println("Effets permanents obtenus :");
        System.out.println("  - Force");
        System.out.println("  - Invincibilité");

        if (withUnicornMilk) {
            System.out.println("  - Dédoublement");
        }
        if (withIdefixsHair) {
            System.out.println("  - Métamorphose");
        }

        System.out.println();
        return true;
    }

    /**
     * Allows drinking two entire pots of potion.
     * Requires at least 20 doses available
     *
     * @return true if two pots could be consumed, false if there are not enough doses
     */
    public boolean drinkTwoPots() {
        if (doseNumber < DOSES_PER_POT * 2) {
            System.out.println("Pas assez de doses !");
            return false;
        }

        doseNumber -= DOSES_PER_POT * 2;

        System.out.println("\n deux marmites bu !");
        System.out.println("Transformation en statue de granit !");
        System.out.println();
        return true;
    }

    /**
     * Completely refills the pot.
     * Resets the number of doses to 10
     */
    public void fillPot() {
        doseNumber = DOSES_PER_POT;
        System.out.println("Marmite remplie ! " + DOSES_PER_POT + " doses disponibles.\n");
    }

    /**
     * Adds one pot of potion.
     * Increases the number of available doses by 10.
     * To prepare the consumption of two pots.
     */
    public void addPot() {
        if (checkBaseIngredients(inventory)) == true {
        doseNumber += DOSES_PER_POT;
        System.out.println("Marmite ajoutée ! " + DOSES_PER_POT + " doses supplémentaires disponibles.\n");
        }
    }

    /**
     * Returns the number of currently available doses.
     *
     * @return the number of doses in the pot
     */
    public int getDoses() {
        return doseNumber;
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

        System.out.println("\n Doses disponibles : " + doseNumber);
    }
}