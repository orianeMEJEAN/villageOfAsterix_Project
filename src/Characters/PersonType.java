/**
 * Représente différents types de personnes, permettant de définir
 * leurs préférences alimentaires par défaut.
 *
 * Les types incluent :
 *     gaulois : amateurs typiques de sanglier et de poisson.
 *     romain : goûts plus raffinés incluant le miel et l'hydromel.
 *
 * @author Oriane
 * @version 1.0
 */
package Characters;

import Food.Ingredient;

import java.util.EnumSet;
import java.util.Set;

public enum PersonType
{
    gaulois,
    romain;

    /**
     * Retourne les aliments standards associés à ce type de personne.
     * Les ensembles retournés sont immuables, car produits via {@link EnumSet}.
     *
     * @return un ensemble d'ingrédients correspondant au régime par défaut
     */
    public Set<Ingredient> defaultFoods()
    {
        switch (this)
        {
            case gaulois:
                return EnumSet.of(Ingredient.sanglier,
                                  Ingredient.poissonPassablementFrais,
                                  Ingredient.vin);
            case romain:
                return EnumSet.of(Ingredient.sanglier,
                                  Ingredient.miel,
                                  Ingredient.vin,
                                  Ingredient.hydromel);
            default:
                return null;
        }
    }
}