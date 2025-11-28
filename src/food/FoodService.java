/**
 * Provides services related to feeding different types of people.
 * This class allows you to:
 *    check whether an ingredient is allowed for a given type of person,
 *    assess whether a meal is considered healthy or problematic according to certain rules.
 *
 * @author Oriane
 * @version 1.0
 */
package food;

import characters.PersonType;
import food.Ingredient;
import food.HealthResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodService
{
    /**
     * Checks whether an ingredient is allowed for a given type of person,
     * based on their default foods.
     *
     * @param person     the type of person (Gaul, Roman)
     * @param ingredient the ingredient to check
     * @return true if the ingredient is allowed, otherwise false
     */
    public boolean isAllowedFor(PersonType person, Ingredient ingredient)
    {
        Set<Ingredient> allowed = person.defaultFoods();
        return allowed.contains(ingredient);
    }

    /**
     * Evaluates whether a meal is healthy according to several rules:
     *     - The meal must not contain fish that is not fresh.
     *     - Two ingredients considered to be vegetables must not be consumed consecutively.
     *
     * If one or more rules are violated, the issue is added to the list.
     *
     * @param person the type of person for whom the meal is being evaluated
     * @param meal   the list of ingredients in the meal
     * @return a {@link HealthResult} object indicating whether the meal is healthy,
     *         as well as any detected issues
     */
    public HealthResult evaluateMeal(PersonType person, List<Ingredient> meal)
    {
        List<String> issues = new ArrayList<>();

        // No not-fresh fish
        if (meal.contains(Ingredient.notFreshFish))
        {
            issues.add("Contient du poisson pas frais...ces mauvais pour la santé...");
        }

        // No two consecutive vegetables
        int consecutiveVegetables = 0;

        for (Ingredient ing : meal)
        {
            if (ing.isVegetable())
            {
                consecutiveVegetables++;

                if (consecutiveVegetables >= 2)
                {
                    issues.add("Deux végétaux consommés consécutivement...ces mauvais pour la santé...");
                    break;
                }
            }
            else
            {
                consecutiveVegetables = 0;
            }
        }

        boolean healthy = issues.isEmpty();
        return new HealthResult(healthy, issues);
    }
}