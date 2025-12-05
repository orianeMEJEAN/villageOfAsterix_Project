package magicPotionTest;

import food.enums.Ingredient;
import magicPotion.MagicPotion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MagicPotionTest {

    /**
     * Creates the minimal list of valid base ingredients.
     */
    private List<Ingredient> createBaseIngredients() {
        return new ArrayList<>(Arrays.asList(
                Ingredient.mistletoe,
                Ingredient.carrots,
                Ingredient.salt,
                Ingredient.freshFourLeafClover,
                Ingredient.moderatelyFreshFish,
                Ingredient.rockOil,
                Ingredient.honey,
                Ingredient.mead,
                Ingredient.secretIngredient
        ));
    }

    /**
     * Potion with only base ingredients: should build without throwing.
     */
    @Test
    void testValidPotionWithOnlyBaseIngredients() {
        List<Ingredient> ingredients = createBaseIngredients();

        MagicPotion potion = new MagicPotion(ingredients);

        assertNotNull(potion);
        assertFalse(potion.isNourishing(), "La potion ne devrait pas être nourrissante sans homard/fraises/jus de betterave");
        assertFalse(potion.isWithLobster());
        assertFalse(potion.isWithStrawberries());
        assertFalse(potion.isWithBeetJuice());
        assertFalse(potion.isWithUnicornMilk());
        assertFalse(potion.isWithIdefixsHair());
    }

    /**
     * Missing a base ingredient: constructor should throw IllegalArgumentException.
     */
    @Test
    void testMissingBaseIngredientThrowsException() {
        List<Ingredient> ingredients = createBaseIngredients();
        // Remove a base ingredient to invalidate the potion
        ingredients.remove(Ingredient.salt);

        assertThrows(IllegalArgumentException.class,
                () -> new MagicPotion(ingredients),
                "La création d'une potion sans tous les ingrédients de base devrait lever une IllegalArgumentException");
    }

    /**
     * Too many special ingredients (2 instead of 1 max): should throw IllegalArgumentException.
     */
    @Test
    void testTooManySpecialIngredientsThrowsException() {
        List<Ingredient> ingredients = createBaseIngredients();
        ingredients.add(Ingredient.twoHeadedUnicornMilk);
        ingredients.add(Ingredient.idefixHair);

        assertThrows(IllegalArgumentException.class,
                () -> new MagicPotion(ingredients),
                "La potion ne doit pas accepter plus de specialIngredientsThreshold ingrédients spéciaux");
    }

    /**
     * Potion is nourishing if at least one nourishing ingredient is present.
     */
    @Test
    void testPotionIsNourishingWithLobster() {
        List<Ingredient> ingredients = createBaseIngredients();
        ingredients.add(Ingredient.lobster);

        MagicPotion potion = new MagicPotion(ingredients);

        assertTrue(potion.isNourishing(), "La présence de homard doit rendre la potion nourrissante");
        assertTrue(potion.isWithLobster());
        assertFalse(potion.isWithStrawberries());
        assertFalse(potion.isWithBeetJuice());
    }

    @Test
    void testPotionIsNourishingWithStrawberriesAndBeetJuice() {
        List<Ingredient> ingredients = createBaseIngredients();
        ingredients.add(Ingredient.strawberries);
        ingredients.add(Ingredient.beetrootJuice);

        MagicPotion potion = new MagicPotion(ingredients);

        assertTrue(potion.isNourishing(), "La présence de fraises ou de jus de betterave doit rendre la potion nourrissante");
        assertFalse(potion.isWithLobster());
        assertTrue(potion.isWithStrawberries());
        assertTrue(potion.isWithBeetJuice());
    }

    /**
     * Verifies that special ingredient flags are correctly initialized.
     */
    @Test
    void testSpecialIngredientsFlagsInitialization() {
        List<Ingredient> ingredients = createBaseIngredients();
        ingredients.add(Ingredient.twoHeadedUnicornMilk);
        // Only one special ingredient, below threshold -> no exception
        MagicPotion potion = new MagicPotion(ingredients);

        assertTrue(potion.isWithUnicornMilk());
        assertFalse(potion.isWithIdefixsHair());
    }

    /**
     * Verifies that setters correctly modify flags.
     */
    @Test
    void testSettersModifyFlags() {
        List<Ingredient> ingredients = createBaseIngredients();
        MagicPotion potion = new MagicPotion(ingredients);

        assertFalse(potion.isWithLobster());
        potion.setWithLobster(true);
        assertTrue(potion.isWithLobster());

        assertFalse(potion.isWithStrawberries());
        potion.setWithStrawberries(true);
        assertTrue(potion.isWithStrawberries());

        assertFalse(potion.isWithBeetJuice());
        potion.setWithBeetJuice(true);
        assertTrue(potion.isWithBeetJuice());

        assertFalse(potion.isWithUnicornMilk());
        potion.setWithUnicornMilk(true);
        assertTrue(potion.isWithUnicornMilk());

        assertFalse(potion.isWithIdefixsHair());
        potion.setWithIdefixsHair(true);
        assertTrue(potion.isWithIdefixsHair());
    }

    /**
     * Verifies that the special ingredient threshold matches the internal constant.
     */
    @Test
    void testSpecialIngredientsThresholdValue() {
        List<Ingredient> ingredients = createBaseIngredients();
        MagicPotion potion = new MagicPotion(ingredients);

        assertEquals(1, potion.getSpecialIngredientsThreshold(),
                "Le seuil d'ingrédients spéciaux devrait être de 1");
    }
}
