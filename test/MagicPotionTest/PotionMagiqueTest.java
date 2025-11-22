package MagicPotionTest;

import MagicPotion.PotionMagique;
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
public class PotionMagiqueTest {

    /** Instance de potion utilisée pour les tests */
    private PotionMagique potion;

    /**
     * Initialise une nouvelle instance de potionMagique
     */
    @BeforeEach
    public void setUp() {
        potion = new PotionMagique();
    }

    // TESTS DU CONSTRUCTEUR

    /**
     * Vérifie que le constructeur initialise la potion avec 10 doses.
     */
    @Test
    public void testPotionDemarreAvec10Doses() {
        assertEquals(10, potion.getNombreDoses());
    }

    /**
     * Sans ajout d'ingrédients,
     * la potion ne doit pas être nourrissante.
     */
    @Test
    public void testPotionPasNourrisante() {
        assertFalse(potion.estNourrissante());
    }

    // TESTS DES INGRÉDIENTS OPTIONNELS

    /**
     * Vérifie que l'ajout de homard rend la potion nourrissante.
     */
    @Test
    public void testAjouterHomard() {
        potion.ajouterHomard();
        assertTrue(potion.estNourrissante());
    }

    /**
     * Vérifie que l'ajout de fraises rend la potion nourrissante.
     */
    @Test
    public void testAjouterFraises() {
        potion.ajouterFraises();

        assertTrue(potion.estNourrissante());
    }

    /**
     * Vérifie que le remplacement par du jus de betterave rend la potion nourrissante.
     */
    @Test
    public void testRemplacerParJusBetterave() {
        potion.remplacerParJusBetterave();

        assertTrue(potion.estNourrissante());
    }

    /**
     * Vérifie que l'ajout de lait de licorne ne provoque pas d'erreur.
     */
    @Test
    public void testAjouterLaitLicorne() {
        assertDoesNotThrow(() -> potion.ajouterLaitLicorne());
    }

    /**
     * Vérifie que l'ajout de poils d'Idéfix ne provoque pas d'erreur.
     */
    @Test
    public void testAjouterPoilsIdefix() {
        assertDoesNotThrow(() -> potion.ajouterPoilsIdefix());
    }

    // TESTS DE BOIRE UNE DOSE

    /**
     * Vérifie que boire une dose diminue le stock de 1.
     */
    @Test
    public void testBoireUneDose() {
        potion.boireUneDose();

        assertEquals(9, potion.getNombreDoses());
    }

    /**
     * Vérifie que boireUneDose() retourne true quand des doses sont disponibles.
     */
    @Test
    public void testBoireUneDoseRetourneTrue() {
        boolean resultat = potion.boireUneDose();

        assertTrue(resultat);
    }

    /**
     * Vérifie que boireUneDose() retourne false quand la marmite est vide.
     */
    @Test
    public void testBoireUneDoseRetourneFalse() {
        for (int i = 0; i < 10; i++) {
            potion.boireUneDose();
        }

        boolean resultat = potion.boireUneDose();
        assertFalse(resultat);
    }

    // TESTS DE BOIRE UNE MARMITE

    /**
     * Vérifie que boire une marmite utilise 10 doses.
     */
    @Test
    public void testBoireUneMarmite() {
        potion.boireUneMarmite();
        assertEquals(0, potion.getNombreDoses());
    }

    /**
     * Vérifie que boireUneMarmite() retourne true avec assez de doses.
     */
    @Test
    public void testBoireUneMarmiteRetourneTrue() {
        boolean resultat = potion.boireUneMarmite();
        assertTrue(resultat);
    }

    // TESTS DE BOIRE DEUX MARMITES

    /**
     * Vérifie que boireDeuxMarmites() retourne false sans assez de doses.
     */
    @Test
    public void testBoireDeuxMarmitesRetourneFalse() {
        boolean resultat = potion.boireDeuxMarmites();
        assertFalse(resultat);
    }

    /**
     * Vérifie que boireDeuxMarmites() retourne true avec assez de doses.
     */
    @Test
    public void testBoireDeuxMarmitesRetourneTrue() {
        potion.ajouterMarmite();
        boolean resultat = potion.boireDeuxMarmites();
        assertTrue(resultat);
    }

    /**
     * Vérifie que boire deux marmites consomme 20 doses.
     */
    @Test
    public void testBoireDeuxMarmites() {
        potion.ajouterMarmite();
        potion.boireDeuxMarmites();
        assertEquals(0, potion.getNombreDoses());
    }

    // TESTS DE REMPLIR LA MARMITE

    /**
     * Vérifie que remplirMarmite() remet 10 doses
     */
    @Test
    public void testRemplirMarmite() {
        potion.boireUneMarmite();
        potion.remplirMarmite();

        assertEquals(10, potion.getNombreDoses());
    }

    // TESTS DE LA MÉTHODE AFFICHER RECETTE

    /**
     * Vérifie que afficherRecette() ne provoque pas d'erreur.
     */
    @Test
    public void testAfficherRecette() {
        assertDoesNotThrow(() -> potion.afficherRecette());
    }

    /**
     * Vérifie que afficherRecette() fonctionne avec des ingrédients ajoutés.
     */
    @Test
    public void testAfficherRecetteAvecIngredients() {
        potion.ajouterHomard();
        potion.ajouterLaitLicorne();
        assertDoesNotThrow(() -> potion.afficherRecette());
    }

    /**
     * Scénario d'utilisation normale.
     * Permet de vérifier que toutes les fonctionnalités fonctionnent ensemble.
     */
    @Test
    public void testUtilisationNormale() {
        potion.ajouterFraises();
        assertTrue(potion.estNourrissante());

        potion.boireUneDose();
        potion.boireUneDose();
        assertEquals(8, potion.getNombreDoses());

        potion.remplirMarmite();
        assertEquals(10, potion.getNombreDoses());

        potion.boireUneMarmite();
        assertEquals(0, potion.getNombreDoses());
    }
}