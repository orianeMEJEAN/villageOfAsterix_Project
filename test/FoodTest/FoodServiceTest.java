package FoodTest;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import Food.Ingredient;
import Food.FoodService;
import Food.HealthResult;
import Characters.PersonType;

/**
 * Suite de tests unitaires pour la classe FoodService.
 * Ces tests vérifient le comportement de l'évaluation d'un repas selon différents types de personnages.
 * Ils garantissent que le moteur de règles alimentaires fonctionne conformément aux règles définies.
 *
 * @author Oriane
 * @version 1.0
 */
public class FoodServiceTest
{
    private final FoodService service = new FoodService();

    /**
     * Vérifie qu’un Gaulois mangeant un repas composé de sanglier, de poisson passablement frais et de vin est
     * considéré en bonne santé.
     * Le test confirme que le HealthResult est marqué comme sain.
     */
    @Test
    void gaulois_mange_sanglier_poisson_passable_vin_est_healthy()
    {
        List<Ingredient> meal = Arrays.asList(
                Ingredient.sanglier,
                Ingredient.poissonPassablementFrais,
                Ingredient.vin
        );
        HealthResult res = service.evaluateMeal(PersonType.gaulois, meal);
        assertTrue(res.isHealthy(), "Le gaulois devrait être en bonne santé en mangeant sanglier, " +
                "                             poisson passablement frais, vin");
    }

    /**
     * Vérifie qu’un poisson « pas frais » entraîne une mauvaise santé, peu importe le consommateur.
     * Le test s'assure que :
     *      le repas est évalué comme malsain.
     *      le résultat contient bien une issue mentionnant le poisson pas frais.
     */
    @Test
    void poisson_pas_frais_est_mauvais_pour_la_sante()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.poissonPasFrais);
        HealthResult res = service.evaluateMeal(PersonType.gaulois, meal);
        assertFalse(res.isHealthy());
        assertTrue(res.getIssues().stream().anyMatch(s -> s.contains("poisson pas frais")));
    }

    /**
     * Vérifie que deux végétaux (ou plus) consommés consécutivement constituent un repas considéré comme malsain.
     * Le test vérifie que :
     *     le repas est marqué comme non sain.
     *     une cause est bien signalée mentionnant les végétaux.
     */
    @Test
    void deux_vegetaux_consecutifs_est_mauvais()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.carottes, Ingredient.fraises);
        HealthResult res = service.evaluateMeal(PersonType.gaulois, meal);
        assertFalse(res.isHealthy());

        assertTrue(res.getIssues().stream().anyMatch(s -> s.contains("végétaux") || s.contains("végétal")));
    }

    /**
     * Vérifie qu’un Romain consommant sanglier, miel, vin et hydromel est autorisé à manger tous ces ingrédients
     * sans que cela ne porte atteinte à sa santé.
     */
    @Test
    void romain_mange_sanglier_miel_vin_hydromel_est_autorise_et_healthy()
    {
        List<Ingredient> meal = Arrays.asList(Ingredient.sanglier, Ingredient.miel, Ingredient.vin, Ingredient.hydromel);
        HealthResult res = service.evaluateMeal(PersonType.romain, meal);

        assertTrue(res.isHealthy(), "Le romain devrait être en bonne santé avec ce repas.");
    }
}