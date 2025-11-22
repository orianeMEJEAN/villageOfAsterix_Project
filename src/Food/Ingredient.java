/**
 * Représente un ingrédient utilisable.
 * Chaque ingrédient possède :
 *     un nom d'affichage lisible,
 *     une catégorie (viande, poisson, plante, etc.),
 *     un indicateur optionnel précisant s'il peut être compté comme légume.
 *
 * @author Oriane
 * @version 1.0
 */
package Food;

import java.util.Set;

public enum Ingredient
{
    sanglier("sanglier", Category.meat, false),
    poissonPassablementFrais("poisson passablement frais", Category.fish, false),
    poissonPasFrais("poisson pas frais", Category.fish, false),
    gui("gui", Category.plant, true),
    homard("homard", Category.meat, false),
    fraises("fraises", Category.fruit, true),
    carottes("carottes", Category.vegetable, true),
    sel("sel", Category.condiment, false),
    trefle4Frais("trèfle à quatre feuilles frais", Category.plant, true),
    trefle4PasFrais("trèfle à quatre feuilles pas frais", Category.plant, true),
    huileDeRoche("huile de roche", Category.other, false),
    jusDeBettrave("jus de betterave", Category.drink, true),
    miel("miel", Category.sweet, false),
    vin("vin", Category.drink, false),
    hydromel("hydromel", Category.drink, false),
    laitLicorne2Têtes("lait de licorne à deux têtes", Category.drink, false),
    poilsIdefix("poils d'Idéfix", Category.other, false),
    ingredientSecret("ingrédient secret", Category.other, false);

    /**
     * Énumère toutes les catégories possibles auxquelles un ingrédient peut appartenir.
     * Ces catégories permettent de classifier les aliments.
     */
    public enum Category
    {
        meat,
        fish,
        plant,
        vegetable,
        fruit,
        drink,
        condiment,
        sweet,
        other
    }

    private final String displayName;
    private final Category category;
    private final boolean isVegetable;

    /**
     * Construit un ingrédient.
     *
     * @param displayName nom d'affichage de l'ingrédient
     * @param category catégorie de l'ingrédient
     * @param isVegetable indique si cet ingrédient peut être considéré comme un légume
     */
    Ingredient(String displayName, Category category, boolean isVegetable)
    {
        this.displayName = displayName;
        this.category = category;
        this.isVegetable = isVegetable;
    }

    /**
     * Retourne le nom d'affichage de l'ingrédient.
     *
     * @return le nom sous forme lisible
     */
    public String getDisplayName() { return displayName; }

    /**
     * Retourne la catégorie à laquelle appartient l'ingrédient.
     *
     * @return la catégorie de l'ingrédient
     */
    public Category getCategory() { return category; }

    /**
     * Indique si cet ingrédient peut être compté comme un légume.
     *
     * @return true si l'ingrédient peut être considéré comme un légume
     * @return false dans le cas contraire
     */
    public boolean isVegetable() { return isVegetable; }
}