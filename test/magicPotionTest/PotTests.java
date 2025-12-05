package magicPotionTest;

import food.enums.Ingredient;
import magicPotion.MagicPotion;
import magicPotion.Pot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the Pot class.
 * Tests doses management, ingredient handling, and brewing behavior.
 *
 * @version 3.0
 */
public class PotTests {

    /**
     * Ensures a new pot is empty and has no magic potion.
     */
    @Test
    void testDefaultPotIsEmptyAndHasNoMagicPotion() {
        Pot pot = new Pot();

        assertEquals(0, pot.getnbDoses(),
                "Par défaut le pot doit contenir 0 dose.");
        assertNull(pot.getMagicPotion(),
                "Par défaut il ne doit pas y avoir de potion magique (null).");
        assertTrue(pot.isEmpty(),
                "Un pot nouvellement créé doit être considéré comme vide.");
    }

    /**
     * Ensures adding a positive number of doses increases the count.
     */
    @Test
    void testAddPositiveDosesIncreasesCount() {
        Pot pot = new Pot();

        pot.addDose(5);

        assertEquals(5, pot.getnbDoses(),
                "Ajouter 5 doses à un pot vide doit donner 5 doses.");
    }

    /**
     * Ensures adding a negative number of doses does not create a negative dose count.
     */
    @Test
    void testAddNegativeDosesDoesNotGoBelowZero() {
        Pot pot = new Pot();

        pot.addDose(-3);

        assertEquals(0, pot.getnbDoses(),
                "Le nombre de doses ne doit jamais devenir négatif.");
    }

    /**
     * Ensures drinkADose consumes exactly one dose when the pot is not empty.
     */
    @Test
    void testDrinkADoseWhenNotEmpty() {
        Pot pot = new Pot();
        pot.addDose(3);

        boolean result = pot.drinkADose();

        assertTrue(result,
                "Boire une dose doit réussir si le pot contient au moins une dose.");
        assertEquals(2, pot.getnbDoses(),
                "Boire une dose doit décrémenter le nombre de doses de 1.");
    }

    /**
     * Ensures drinkADose fails and does not change dose count when the pot is empty.
     */
    @Test
    void testDrinkADoseWhenEmpty() {
        Pot pot = new Pot();

        boolean result = pot.drinkADose();

        assertFalse(result,
                "Boire une dose doit échouer si le pot est vide.");
        assertEquals(0, pot.getnbDoses(),
                "Le nombre de doses doit rester à 0 lorsque le pot est vide.");
    }

    /**
     * Ensures drinkWhole consumes dosesPerPot doses when there are enough doses.
     */
    @Test
    void testDrinkWholeWhenEnoughDoses() {
        Pot pot = new Pot();
        pot.addDose(Pot.dosesPerPot + 4);

        boolean result = pot.drinkWhole();

        assertTrue(result,
                "Boire tout le pot doit réussir si le nombre de doses est suffisant.");
        assertEquals(4, pot.getnbDoses(),
                "Après avoir bu tout le pot, seules les doses excédentaires doivent rester.");
    }

    /**
     * Ensures drinkWhole fails and does not change dose count when there are not enough doses.
     */
    @Test
    void testDrinkWholeWhenNotEnoughDoses() {
        Pot pot = new Pot();
        pot.addDose(Pot.dosesPerPot - 1);

        boolean result = pot.drinkWhole();

        assertFalse(result,
                "Boire tout le pot doit échouer si le nombre de doses est insuffisant.");
        assertEquals(Pot.dosesPerPot - 1, pot.getnbDoses(),
                "Aucune dose ne doit être consommée si le pot n’a pas assez de doses.");
    }

    /**
     * Ensures refill sets the pot to its full capacity (dosesPerPot).
     */
    @Test
    void testRefillSetsToFullCapacity() {
        Pot pot = new Pot();
        pot.addDose(3);

        pot.refill();

        assertEquals(Pot.dosesPerPot, pot.getnbDoses(),
                "La méthode refill() doit remplir le pot à sa capacité maximale.");
    }

    /**
     * Ensures hasAtLeast returns true when the pot has at least the requested number of doses.
     */
    @Test
    void testHasAtLeastTrue() {
        Pot pot = new Pot();
        pot.addDose(10);

        assertTrue(pot.hasAtLeast(5),
                "hasAtLeast(5) doit être vrai si le pot contient 10 doses.");
        assertTrue(pot.hasAtLeast(10),
                "hasAtLeast(10) doit être vrai si le pot contient exactement 10 doses.");
        assertFalse(pot.hasAtLeast(11),
                "hasAtLeast(11) doit être faux si le pot ne contient que 10 doses.");
    }

    /**
     * Ensures hasAtLeast returns false when there are not enough doses.
     */
    @Test
    void testHasAtLeastFalse() {
        Pot pot = new Pot();
        pot.addDose(3);

        assertFalse(pot.hasAtLeast(4),
                "hasAtLeast(4) doit être faux si le pot ne contient que 3 doses.");
        assertFalse(pot.hasAtLeast(10),
                "hasAtLeast(10) doit être faux si le pot ne contient que 3 doses.");
    }

    /**
     * Ensures isEmpty is true when dose count is zero.
     */
    @Test
    void testIsEmptyTrueWhenZero() {
        Pot pot = new Pot();
        pot.addDose(0);

        assertTrue(pot.isEmpty(),
                "Un pot avec 0 dose doit être considéré comme vide.");
    }

    /**
     * Ensures isEmpty is false when there is at least one dose.
     */
    @Test
    void testIsEmptyFalseWhenPositive() {
        Pot pot = new Pot();
        pot.addDose(5);

        assertFalse(pot.isEmpty(),
                "Un pot avec un nombre de doses positif ne doit pas être considéré comme vide.");
    }

    /**
     * Ensures getDosesPerPot returns the static configuration value.
     */
    @Test
    void testGetDosesPerPot() {
        Pot pot = new Pot();

        assertEquals(Pot.dosesPerPot, pot.getDosesPerPot(),
                "getDosesPerPot() doit retourner la constante dosesPerPot.");
    }

    /**
     * Ensures null ingredients are rejected by canAccept and addIngredient.
     */
    @Test
    void testCanAcceptAndAddIngredientWithNull() {
        Pot pot = new Pot();

        assertFalse(pot.canAccept(null),
                "canAccept(null) doit retourner false.");
        assertFalse(pot.addIngredient(null),
                "addIngredient(null) doit retourner false et ne pas lever d’exception.");
    }

    /**
     * Ensures brewPotion with no ingredients fails and empties the pot.
     */
    @Test
    void testBrewPotionWithEmptyIngredientsFlipsPot() {
        Pot pot = new Pot();

        pot.addDose(5);
        assertEquals(5, pot.getnbDoses(),
                "Le pot devrait contenir 5 doses avant la tentative de préparation.");

        pot.brewPotion();

        assertEquals(0, pot.getnbDoses(),
                "Après un échec de brewPotion(), le pot doit être vidé.");
        assertNull(pot.getMagicPotion(),
                "Après un échec de brewPotion(), il ne doit pas y avoir de potion magique (null).");
    }

    /**
     * Ensures brewPotion with valid ingredients creates a MagicPotion and fills the pot.
     */
    @Test
    void testBrewPotionWithValidIngredientsCreatesMagicPotion() {
        Pot pot = new Pot();

        pot.canAccept(Ingredient.mistletoe);
        pot.addIngredient(Ingredient.mistletoe);
        pot.addIngredient(Ingredient.carrots);
        pot.addIngredient(Ingredient.salt);
        pot.addIngredient(Ingredient.freshFourLeafClover);
        pot.addIngredient(Ingredient.moderatelyFreshFish);
        pot.addIngredient(Ingredient.rockOil);
        pot.addIngredient(Ingredient.honey);
        pot.addIngredient(Ingredient.mead);
        pot.addIngredient(Ingredient.secretIngredient);

        pot.brewPotion();

        MagicPotion magicPotion = pot.getMagicPotion();

        assertNotNull(magicPotion,
                "Une potion magique valide doit être créée avec tous les ingrédients de base.");
        assertEquals(Pot.dosesPerPot, pot.getnbDoses(),
                "Après une préparation réussie, le pot doit être rempli à dosesPerPot.");
        assertFalse(pot.isEmpty(),
                "Après une préparation réussie, le pot ne doit pas être vide.");
    }
}
