package MagicPotionTest;

import MagicPotion.MagicPotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests unitaires pour la classe potionMagique.
 *
 * Test le bon fonctionnement de la classe potionMagique
 *
 * @author Lou
 * @version 1.0
 */
public class MagicPotionTest {

    /** Instance de potion utilisée pour les tests */
    private MagicPotion potion;

    /**
     * Initialise une nouvelle instance de potionMagique
     */
    @BeforeEach
    public void setUp() {
        potion = new MagicPotion();
    }

    // TESTS DU CONSTRUCTEUR

    /**
     * Vérifie que le constructeur initialise la potion avec 10 doses.
     */
    @Test
    public void testPotionStratsWith10Doses() {
        assertEquals(10, potion.getDoses());
    }

    /**
     * Sans ajout d'ingrédients,
     * la potion ne doit pas être nourrissante.
     */
    @Test
    public void testPotionNotNourishing() {
        assertFalse(potion.isNourishing());
    }

    // TESTS DES INGRÉDIENTS OPTIONNELS

    /**
     * Vérifie que l'ajout de homard rend la potion nourrissante.
     */
    @Test
    public void testAddLobster() {
        potion.addLobster();
        assertTrue(potion.isNourishing());
    }

    /**
     * Vérifie que l'ajout de fraises rend la potion nourrissante.
     */
    @Test
    public void testAddStrawberries() {
        potion.addStrawberries();

        assertTrue(potion.isNourishing());
    }

    /**
     * Vérifie que le remplacement par du jus de betterave rend la potion nourrissante.
     */
    @Test
    public void testReplaceByBeetJuice() {
        potion.replaceByBeetJuice();

        assertTrue(potion.isNourishing());
    }

    /**
     * Vérifie que l'ajout de lait de licorne ne provoque pas d'erreur.
     */
    @Test
    public void testAddUnicornMilk() {
        assertDoesNotThrow(() -> potion.addUnicornMilk());
    }

    /**
     * Vérifie que l'ajout de poils d'Idéfix ne provoque pas d'erreur.
     */
    @Test
    public void testaddIdefixsHair() {
        assertDoesNotThrow(() -> potion.addIdefixsHair());
    }

    // TESTS DE BOIRE UNE DOSE

    /**
     * Vérifie que boire une dose diminue le stock de 1.
     */
    @Test
    public void testdrinkADose() {
        potion.drinkADose();

        assertEquals(9, potion.getDoses());
    }

    /**
     * Vérifie que boireUneDose() retourne true quand des doses sont disponibles.
     */
    @Test
    public void testDrinkADoseReturnsTrue() {
        boolean resultat = potion.drinkADose();

        assertTrue(resultat);
    }

    /**
     * Vérifie que boireUneDose() retourne false quand la marmite est vide.
     */
    @Test
    public void testDrinkADosereturnsFalse() {
        for (int i = 0; i < 10; i++) {
            potion.drinkADose();
        }

        boolean resultat = potion.drinkADose();
        assertFalse(resultat);
    }

    // TESTS DE BOIRE UNE MARMITE

    /**
     * Vérifie que boire une marmite utilise 10 doses.
     */
    @Test
    public void testDrinkAPot() {
        potion.drinkAPot();
        assertEquals(0, potion.getDoses());
    }

    /**
     * Vérifie que boireUneMarmite() retourne true avec assez de doses.
     */
    @Test
    public void testDrinkAPotReturnsTrue() {
        boolean resultat = potion.drinkAPot();
        assertTrue(resultat);
    }

    // TESTS DE BOIRE DEUX MARMITES

    /**
     * Vérifie que boireDeuxMarmites() retourne false sans assez de doses.
     */
    @Test
    public void testDrinkTwoPotsReturnsFalse() {
        boolean resultat = potion.drinkTwoPots();
        assertFalse(resultat);
    }

    /**
     * Vérifie que boireDeuxMarmites() retourne true avec assez de doses.
     */
    @Test
    public void testDrinkTwoPotsReturnsTrue() {
        potion.addPot();
        boolean resultat = potion.drinkTwoPots();
        assertTrue(resultat);
    }

    /**
     * Vérifie que boire deux marmites consomme 20 doses.
     */
    @Test
    public void testDrinkTwoPots() {
        potion.addPot();
        potion.drinkTwoPots();
        assertEquals(0, potion.getDoses());
    }

    // TESTS DE REMPLIR LA MARMITE

    /**
     * Vérifie que remplirMarmite() remet 10 doses
     */
    @Test
    public void testFillPot() {
        potion.drinkAPot();
        potion.fillPot();

        assertEquals(10, potion.getDoses());
    }

    // TESTS DE LA MÉTHODE AFFICHER RECETTE

    /**
     * Vérifie que afficherRecette() ne provoque pas d'erreur.
     */
    @Test
    public void testPrintRecipe() {
        assertDoesNotThrow(() -> potion.printRecipe());
    }

    /**
     * Vérifie que afficherRecette() fonctionne avec des ingrédients ajoutés.
     */
    @Test
    public void testPrintRecipeWithAddedIngredients() {
        potion.addLobster();
        potion.addUnicornMilk();
        assertDoesNotThrow(() -> potion.printRecipe());
    }

    /**
     * Scénario d'utilisation normale.
     * Permet de vérifier que toutes les fonctionnalités fonctionnent ensemble.
     */
    @Test
    public void normaleUseTest() {
        potion.addStrawberries();
        assertTrue(potion.isNourishing());

        potion.drinkADose();
        potion.drinkADose();
        assertEquals(8, potion.getDoses());

        potion.fillPot();
        assertEquals(10, potion.getDoses());

        potion.drinkAPot();
        assertEquals(0, potion.getDoses());
    }
}