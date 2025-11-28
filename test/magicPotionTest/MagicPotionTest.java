package magicPotionTest;

import magicPotion.MagicPotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the MagicPotion class.
 *
 * Tests the correct behavior of the MagicPotion class.
 *
 * @author Lou
 * @version 1.0
 */
public class MagicPotionTest {

    /** Potion instance used for the tests */
    private MagicPotion potion;

    /**
     * Initializes a new instance of MagicPotion
     */
    @BeforeEach
    public void setUp() {
        potion = new MagicPotion();
    }

    // CONSTRUCTOR TESTS

    /**
     * Checks that the constructor initializes the potion with 10 doses.
     */
    @Test
    public void testPotionStratsWith10Doses() {
        assertEquals(10, potion.getDoses());
    }

    /**
     * Without adding ingredients,
     * the potion must not be nourishing.
     */
    @Test
    public void testPotionNotNourishing() {
        assertFalse(potion.isNourishing());
    }

    // OPTIONAL INGREDIENT TESTS

    /**
     * Checks that adding lobster makes the potion nourishing.
     */
    @Test
    public void testAddLobster() {
        potion.addLobster();
        assertTrue(potion.isNourishing());
    }

    /**
     * Checks that adding strawberries makes the potion nourishing.
     */
    @Test
    public void testAddStrawberries() {
        potion.addStrawberries();

        assertTrue(potion.isNourishing());
    }

    /**
     * Checks that replacing with beet juice makes the potion nourishing.
     */
    @Test
    public void testReplaceByBeetJuice() {
        potion.replaceByBeetJuice();

        assertTrue(potion.isNourishing());
    }

    /**
     * Checks that adding unicorn milk does not cause any error.
     */
    @Test
    public void testAddUnicornMilk() {
        assertDoesNotThrow(() -> potion.addUnicornMilk());
    }

    /**
     * Checks that adding Idefix's hair does not cause any error.
     */
    @Test
    public void testaddIdefixsHair() {
        assertDoesNotThrow(() -> potion.addIdefixsHair());
    }

    // DRINK ONE DOSE TESTS

    /**
     * Checks that drinking one dose decreases the stock by 1.
     */
    @Test
    public void testdrinkADose() {
        potion.drinkADose();

        assertEquals(9, potion.getDoses());
    }

    /**
     * Checks that drinkADose() returns true when doses are available.
     */
    @Test
    public void testDrinkADoseReturnsTrue() {
        boolean resultat = potion.drinkADose();

        assertTrue(resultat);
    }

    /**
     * Checks that drinkADose() returns false when the pot is empty.
     */
    @Test
    public void testDrinkADosereturnsFalse() {
        for (int i = 0; i < 10; i++) {
            potion.drinkADose();
        }

        boolean resultat = potion.drinkADose();
        assertFalse(resultat);
    }

    // DRINK ONE POT TESTS

    /**
     * Checks that drinking one pot uses 10 doses.
     */
    @Test
    public void testDrinkAPot() {
        potion.drinkAPot();
        assertEquals(0, potion.getDoses());
    }

    /**
     * Checks that drinkAPot() returns true when there are enough doses.
     */
    @Test
    public void testDrinkAPotReturnsTrue() {
        boolean resultat = potion.drinkAPot();
        assertTrue(resultat);
    }

    // DRINK TWO POTS TESTS

    /**
     * Checks that drinkTwoPots() returns false without enough doses.
     */
    @Test
    public void testDrinkTwoPotsReturnsFalse() {
        boolean resultat = potion.drinkTwoPots();
        assertFalse(resultat);
    }

    /**
     * Checks that drinkTwoPots() returns true when there are enough doses.
     */
    @Test
    public void testDrinkTwoPotsReturnsTrue() {
        potion.addPot();
        boolean resultat = potion.drinkTwoPots();
        assertTrue(resultat);
    }

    /**
     * Checks that drinking two pots consumes 20 doses.
     */
    @Test
    public void testDrinkTwoPots() {
        potion.addPot();
        potion.drinkTwoPots();
        assertEquals(0, potion.getDoses());
    }

    // FILL POT TESTS

    /**
     * Checks that fillPot() restores 10 doses.
     */
    @Test
    public void testFillPot() {
        potion.drinkAPot();
        potion.fillPot();

        assertEquals(10, potion.getDoses());
    }

    // PRINT RECIPE METHOD TESTS

    /**
     * Checks that printRecipe() does not cause any error.
     */
    @Test
    public void testPrintRecipe() {
        assertDoesNotThrow(() -> potion.printRecipe());
    }

    /**
     * Checks that printRecipe() works with added ingredients.
     */
    @Test
    public void testPrintRecipeWithAddedIngredients() {
        potion.addLobster();
        potion.addUnicornMilk();
        assertDoesNotThrow(() -> potion.printRecipe());
    }

    /**
     * Normal usage scenario.
     * Ensures that all features work together properly.
     */
    @Test
    public void normaleUseTest() {
        potion.addStrawberries();
        assertTrue(potion.isNourishing());

        potion.drinkADose();
        potion.drinkADose();
        assertEquals(8, potion.getDoses());

        potion.fillPot();
        assertEquals(10, potion.getDoses());

        potion.drinkAPot();
        assertEquals(0, potion.getDoses());
    }
}
