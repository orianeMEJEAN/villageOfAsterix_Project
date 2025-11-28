package FoodTest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import food.enums.Ingredient;
import food.FoodService;
import food.HealthResult;
import enums.PersonType;

/**
 * Suite of unit tests for the FoodService class.
 * These tests verify the behavior of meal evaluation according to different character types.
 * They ensure that the dietary rules engine works according to the defined rules.
 *
 * @author Oriane
 * @version 1.0
 */
public class FoodServiceTest
{
    private final FoodService service = new FoodService();

    /**
     * Verifies that a Gaul eating a meal consisting of wild boar,
     * moderately fresh fish, and wine is considered to be in good health.
     */
    @Test
    void gaul_eats_boar_moderately_fresh_fish_wine_is_healthy()
    {
        List<Ingredient> meal = Arrays.asList(
                Ingredient.boar,
                Ingredient.moderatelyFreshFish,
                Ingredient.wine
        );
        HealthResult res = service.evaluateMeal(PersonType.gaul, meal);
        assertTrue(res.isHealthy(), "Le gaulois devrait être en bonne santé en mangeant sanglier, poisson passablement frais, vin");
    }

    /**
     * Verifies that not-fresh fish leads to poor health,
     * regardless of who consumes it.
     */
    @Test
    void not_fresh_fish_is_unhealthy()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.notFreshFish);
        HealthResult res = service.evaluateMeal(PersonType.gaul, meal);

        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(s -> s.contains("poisson pas frais")));
    }

    /**
     * Verifies that two (or more) plant-based ingredients eaten consecutively
     * are considered an unhealthy meal.
     */
    @Test
    void two_vegetables_in_a_row_is_unhealthy()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.carrots, Ingredient.strawberries);
        HealthResult res = service.evaluateMeal(PersonType.gaul, meal);

        assertFalse(res.isHealthy());

        assertTrue(res.getIssues().stream().anyMatch(s -> s.contains("végétaux") || s.contains("végétal")));
    }

    /**
     * Verifies that a Roman eating wild boar, honey, wine, and mead
     * is considered healthy.
     */
    @Test
    void roman_eats_boar_honey_wine_mead_is_healthy()
    {
        List<Ingredient> meal = Arrays.asList(
                Ingredient.boar,
                Ingredient.honey,
                Ingredient.wine,
                Ingredient.mead
        );
        HealthResult res = service.evaluateMeal(PersonType.roman, meal);

        assertTrue(res.isHealthy(), "Le romain devrait être en bonne santé avec ce repas.");
    }
}