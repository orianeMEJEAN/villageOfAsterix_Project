/**
 * Unit tests (number 2) for FoodService.
 *
 * These tests use a minimal implementation of Character (TestCharacter)
 * in order to instantiate a concrete character for testing.
 *
 * @author Oriane
 * @version 2.0
 */

package FoodTest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import food.enums.Ingredient;
import food.FoodService;
import food.HealthResult;
import food.enums.HealthIssue;
import enums.PersonType;

/**
 * Suite of unit tests for the FoodService class.
 * These tests verify the behavior of meal evaluation according to different character types.
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
        assertTrue(res.isHealthy(), "Le gaulois devrait être en bonne santé en mangeant un sanglier, poisson passablement frais ou du vin");
    }

    /**
     * Verifies that not-fresh fish leads to poor health,
     * regardless of who consumes it.
     *
     * For the gaul.
     */
    @Test
    void not_fresh_fish_is_unhealthy_gaul()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.notFreshFish);
        HealthResult res = service.evaluateMeal(PersonType.gaul, meal);

        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(issue -> issue == HealthIssue.BAD_FISH),"Le résultat doit contenir l'issue BAD_FISH.");
    }

    /**
     * Verifies that two (or more) plant-based ingredients eaten consecutively
     * are considered an unhealthy meal.
     *
     * For the gaul.
     */
    @Test
    void two_vegetables_in_a_row_is_unhealthy_gaul()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.carrots, Ingredient.strawberries);
        HealthResult res = service.evaluateMeal(PersonType.gaul, meal);

        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(issue -> issue == HealthIssue.CONSECUTIVE_VEGETABLES),
                "Le résultat doit contenir l'issue CONSECUTIVE_VEGETABLES lorsque deux végétaux consécutifs sont présents.");
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

    /**
     * Verifies that not-fresh fish leads to poor health,
     * regardless of who consumes it.
     *
     * For the roman.
     */
    @Test
    void not_fresh_fish_is_unhealthy_roman()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.notFreshFish);
        HealthResult res = service.evaluateMeal(PersonType.roman, meal);

        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(issue -> issue == HealthIssue.BAD_FISH),"Le résultat doit contenir l'issue BAD_FISH.");
    }

    /**
     * Verifies that two (or more) plant-based ingredients eaten consecutively
     * are considered an unhealthy meal.
     *
     * For the roman.
     */
    @Test
    void two_vegetables_in_a_row_is_unhealthy_roman()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.beetrootJuice, Ingredient.freshFourLeafClover);
        HealthResult res = service.evaluateMeal(PersonType.roman, meal);

        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(issue -> issue == HealthIssue.CONSECUTIVE_VEGETABLES),
                "Le résultat doit contenir l'issue CONSECUTIVE_VEGETABLES lorsque deux végétaux consécutifs sont présents.");
    }
}