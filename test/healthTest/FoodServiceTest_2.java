package healthTest;

import characters.Character;
import enums.Gender;
import enums.PersonType;
import food.FoodService;
import food.HealthResult;
import food.enums.Ingredient;
import magicPotion.MagicPotion;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests (number 2) for FoodService.
 *
 * These tests use a minimal implementation of Character (TestCharacter)
 * in order to instantiate a concrete character for testing.
 *
 * @author Oriane
 * @version 1.0
 */
public class FoodServiceTest_2
{
    /**
     * Small implementation of Character for testing purposes.
     * A public constructor is exposed in order to initialize controlled values.
     *
     * Note: Character's constructor signature used here matches:
     * (String name, Gender gender, int height, int age, int strength, int endurance,
     * int health, int hunger, int belligerence, MagicPotion magicPotion, PersonType personType)
     */
    static class TestCharacter extends Character
    {
        public TestCharacter(
                String name,
                Gender gender,
                int height,
                int age,
                int strength,
                int endurance,
                int health,
                int hunger,
                int belligerence,
                MagicPotion magicPotion,
                PersonType personType)
                {
                    super(name, gender, height,
                          age, strength, endurance, health,
                          hunger, belligerence, magicPotion, personType);
                }
    }

    /**
     * Checks that eating stale fish inflicts the expected damage.
     * BAD_FISH -> 10 HP damage.
     */
    @Test
    public void testBadFishDamagesHealth()
    {
        TestCharacter c = new TestCharacter(
                "Asterix",
                Gender.MALE,
                160,
                30,
                10,
                5,
                100,
                10,
                0,
                0,
                PersonType.gaul
        );
        FoodService service = new FoodService();

        List<Ingredient> meal = List.of(Ingredient.notFreshFish);

        HealthResult result = service.feed(c, meal);

        assertFalse(result.isHealthy(), "Le meal contenant du poisson pas frais doit être non-sain.");
        assertEquals(90, c.getHealth(), "La santé doit diminuer de 10 points après ingestion de poisson pas frais.");
    }

    /**
     * Checks that two consecutively consumed vegetables inflict the expected damage.
     * CONSECUTIVE_VEGETABLES -> 5 HP damage.
     *
     */
    @Test
    public void testConsecutiveVegetablesDamagesHealth()
    {
        TestCharacter c = new TestCharacter(
                "Obelix",
                Gender.MALE,
                185,
                35,
                15,
                8,
                100,
                10,
                0,
                0,
                PersonType.gaul
        );
        FoodService service = new FoodService();

        List<Ingredient> meal = List.of(Ingredient.carrots, Ingredient.strawberries);

        HealthResult result = service.feed(c, meal);

        assertFalse(result.isHealthy(), "Deux végétaux consécutifs doivent produire un HealthResult non-sain.");
        assertEquals(95, c.getHealth(), "La santé doit diminuer de 5 points après deux végétaux consécutifs.");
    }

    /**
     * Checks that a healthy meal does not remove health points.
     */
    @Test
    public void testHealthyMealDoesNotDamageHealth()
    {
        TestCharacter c = new TestCharacter(
                "Druid",
                Gender.MALE,
                170,
                60,
                5,
                3,
                100,
                50,
                0,
                0,
                PersonType.gaul
        );
        FoodService service = new FoodService();

        List<Ingredient> meal = List.of(Ingredient.boar, Ingredient.carrots);

        HealthResult result = service.feed(c, meal);

        assertTrue(result.isHealthy(), "Le meal donné doit être considéré comme sain.");
        assertEquals(100, c.getHealth(), "La santé ne doit pas diminuer pour un repas sain.");
    }
}