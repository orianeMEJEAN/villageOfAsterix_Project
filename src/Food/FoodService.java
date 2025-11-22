/**
 * Fournit des services liés à l'alimentation des différents types de personnes.
 * Cette classe permet notamment :
 *     de vérifier si un ingrédient est autorisé pour un type de personne donné.
 *     d'évaluer si un repas est considéré comme sain ou problématique selon certaines règles.
 *
 * @author Oriane
 * @version 1.0
 */
package Food;

import Characters.PersonType;
import Food.Ingredient;
import Food.HealthResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FoodService
{
    /**
     * Vérifie si un ingrédient est autorisé pour un type de personne donné, en se basant sur ses aliments par défaut.
     *
     * @param person     le type de personne (gaulois, romain)
     * @param ingredient l'ingrédient à vérifier
     * @return true si l'ingrédient est autorisé, sinon false
     */
    public boolean isAllowedFor(PersonType person, Ingredient ingredient)
    {
        Set<Ingredient> allowed = person.defaultFoods();
        return allowed.contains(ingredient);
    }

    /**
     * Évalue si un repas est sain selon plusieurs règles :
     *     Le repas ne doit pas contenir de poisson pas frais.
     *     Deux ingrédients considérés comme végétaux ne doivent pas être consommés consécutivement.
     *
     * Si une ou plusieurs règles sont violées, on rajoute le problème à la liste issues
     *
     * @param person le type de personne pour lequel le repas est évalué
     * @param meal   la liste d'ingrédients constituant le repas
     * @return un objet {@link HealthResult} indiquant si le repas est sain,
     *         ainsi que les éventuels problèmes détectés
     */
    public HealthResult evaluateMeal(PersonType person, List<Ingredient> meal)
    {
        List<String> issues = new ArrayList<>();

        // Pas de poisson pas frais
        if (meal.contains(Ingredient.poissonPasFrais))
        {
            issues.add("Contient du poisson pas frais...ces mauvais pour la santé...");
        }

        // Pas deux végétaux consécutifs
        int consecutiveVegetals = 0;

        for (Ingredient ing : meal)
        {
            if (ing.isVegetable())
            {
                consecutiveVegetals++;

                if (consecutiveVegetals >= 2)
                {
                    issues.add("Deux végétaux consommés consécutivement...ces mauvais pour la santé...");
                    break;
                }
            }
            else
            {
                consecutiveVegetals = 0;
            }
        }

        boolean healthy = issues.isEmpty();
        return new HealthResult(healthy, issues);
    }
}