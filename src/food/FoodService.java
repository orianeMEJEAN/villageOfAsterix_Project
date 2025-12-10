/**
 * Provides services related to feeding different types of people.
 * This class allows you to:
 *    check whether an ingredient is allowed for a given type of person,
 *    assess whether a meal is considered healthy or problematic according to certain rules.
 *
 * @author Oriane
 * @version 2.0
 */
package food;

import characters.Character;
import characters.enums.PersonType;
import food.enums.HealthIssue;
import food.enums.Ingredient;
import food.HealthResult;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class FoodService
{
    private static final int DAMAGE_BAD_FISH = 10;
    private static final int DAMAGE_CONSECUTIVE_VEGETABLES = 5;
    private static final int SATIETY_PER_INGREDIENT_HEALTHY = 5;
    private static final int SATIETY_PER_INGREDIENT_UNHEALTHY = 2;


    /**
     * Returns a random ingredient
     * @return
     */
    public static Ingredient random() {
        Ingredient[] ingredients = Ingredient.values();
        Random rand = new Random();
        return ingredients[rand.nextInt(ingredients.length)];
    }

    /**
     * Checks whether an ingredient is allowed for a given PersonType.
     */
    public boolean isAllowedFor(PersonType person, Ingredient ingredient)
    {
        return person.defaultFoods().contains(ingredient);
    }

    /**
     * Evaluate a meal and return HealthResult with detected issues as enums.
     *
     * Rules applied:
     *  - notFreshFish (poisson pas frais) is always disallowed (bad for health)
     *  - two consecutive vegetables is bad for health
     */
    public HealthResult evaluateMeal(PersonType person, List<Ingredient> meal)
    {
        List<HealthIssue> issues = new ArrayList<>();

        if (meal == null || meal.isEmpty())
        {
            return new HealthResult(true, issues);
        }

        if (meal.contains(Ingredient.notFreshFish))
        {
            issues.add(HealthIssue.BAD_FISH);
        }

        int consecutiveVegetables = 0;
        for (Ingredient ing : meal)
        {
            if (ing.isVegetable())
            {
                consecutiveVegetables++;
                if (consecutiveVegetables >= 2)
                {
                    issues.add(HealthIssue.CONSECUTIVE_VEGETABLES);
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

    /**
     * Feed the character: evaluate the meal, apply damages if needed, and update satiety.
     *
     * @param character character to feed
     * @param meal      list of ingredients
     * @return evaluation result (HealthResult)
     */
    public HealthResult feed(Character character, List<Ingredient> meal)
    {
        if (character == null) throw new IllegalArgumentException("Le personnage ne peut pas être inexistant");

        HealthResult result = evaluateMeal(character.getPersonType(), meal);

        if (!result.isHealthy())
        {
            for (HealthIssue issue : result.getIssues())
            {
                switch (issue)
                {
                    case BAD_FISH ->
                            character.receiveDamage(DAMAGE_BAD_FISH);
                    case CONSECUTIVE_VEGETABLES ->
                            character.receiveDamage(DAMAGE_CONSECUTIVE_VEGETABLES);
                }
            }
            character.eat(Math.max(1, meal.size() * SATIETY_PER_INGREDIENT_UNHEALTHY));
        }
        else
        {
            character.eat(Math.max(1, meal.size() * SATIETY_PER_INGREDIENT_HEALTHY));
        }

        return result;
    }
}