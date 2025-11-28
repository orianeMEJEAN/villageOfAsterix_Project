/**
 * Represents different types of people, allowing the definition
 * of their default dietary preferences.
 *
 * Types include:
 *     gaul : typical lovers of boar and fish.
 *     roman : more refined tastes including honey and mead.
 *
 * @author Oriane
 * @version 1.0
 */
package enums;

import food.enums.Ingredient;

import java.util.EnumSet;
import java.util.Set;

public enum PersonType
{
    gaul,
    fantasy,
    roman;

    /**
     * Returns the standard foods associated with this type of person.
     * The returned sets are immutable, as they are produced via {@link EnumSet}.
     *
     * @return a set of ingredients corresponding to the default diet
     */
    public Set<Ingredient> defaultFoods()
    {
        switch (this)
        {
            case gaul:
                return EnumSet.of(
                        Ingredient.boar,
                        Ingredient.moderatelyFreshFish,
                        Ingredient.wine
                );

            case roman:
                return EnumSet.of(
                        Ingredient.boar,
                        Ingredient.honey,
                        Ingredient.wine,
                        Ingredient.mead
                );

            case fantasy:


            default:
                return null;
        }
    }
}