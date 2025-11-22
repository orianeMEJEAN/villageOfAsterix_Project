/**
 * Représente le résultat d'une évaluation de santé
 * Un résultat de santé est composé de :
 *     un indicateur global précisant si l'élément est considéré comme sain.
 *     une liste immuable de problèmes.
 *
 * Les informations fournies lors de la construction ne peuvent plus être modifiées par la suite.
 *
 * @author Oriane
 * @version 1.0
 */
package Food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthResult
{
    private final boolean healthy;
    private final List<String> issues;

    /**
     * Crée un nouveau résultat de santé.
     *
     * @param healthy : true si l'élément est considéré comme sain,
     *                  false dans le cas contraire.
     * @param issues : une liste des problèmes détectés
     */
    public HealthResult(boolean healthy, List<String> issues)
    {
        this.healthy = healthy;
        this.issues = new ArrayList<>(issues);
    }

    /**
     * Indique si l'élément évalué est considéré comme sain.
     *
     * @return true si l'élément est sain, sinon false
     */
    public boolean isHealthy() { return healthy; }

    /**
     * Retourne la liste des problèmes détectés lors de l'évaluation.
     *
     * @return une vue non modifiable de la liste des problèmes
     */
    public List<String> getIssues() { return Collections.unmodifiableList(issues); }

    /**
     * Fournit une représentation textuelle du résultat de santé.
     *
     * @return une chaîne décrivant l'état de santé et les éventuels problèmes
     */
    public String toString()
    {
        return "HealthResult{healthy =" + healthy + ", issues =" + issues + "}";
    }
}