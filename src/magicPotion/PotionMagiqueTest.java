package magicPotion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PotionMagiqueTest {

    private potionMagique potion;

    @BeforeEach
    public void setUp() {
        potion = new potionMagique();
    }

    // TESTS DU CONSTRUCTEUR

    @Test
    public void testConstructeur_PotionDemarreAvec10Doses() {
        // On vérifie que la nouvelle potion a bien 10 doses
        assertEquals(10, potion.getNombreDoses());
    }

    @Test
    public void testConstructeur_PotionPasNourrisanteAuDepart() {
        // Au départ la potion ne devrais pas etre nourrissante
        assertFalse(potion.estNourrissante());
    }

    // TESTS DES INGRÉDIENTS OPTIONNELS

    @Test
    public void testAjouterHomard_RendPotionNourrissante() {
        // On ajoute du homard
        potion.ajouterHomard();
        // On vérifie que la potion est nourrissante
        assertTrue(potion.estNourrissante());
    }

    @Test
    public void testAjouterFraises_RendPotionNourrissante() {
        // On ajoute des fraises
        potion.ajouterFraises();

        // On vérifie que la potion est nourrissante
        assertTrue(potion.estNourrissante());
    }

    @Test
    public void testRemplacerParJusBetterave_RendPotionNourrissante() {
        // On remplace par du jus de betterave
        potion.remplacerParJusBetterave();

        // On vérifie que la potion est nourrissante
        assertTrue(potion.estNourrissante());
    }

    @Test
    public void testAjouterLaitLicorne_NePasCasser() {
        // On vérifie juste que l'ajout marche
        assertDoesNotThrow(() -> potion.ajouterLaitLicorne());
    }

    @Test
    public void testAjouterPoilsIdefix_NePasCasser() {
        // On vérifie juste que l'ajout marche
        assertDoesNotThrow(() -> potion.ajouterPoilsIdefix());
    }

    // TESTS DE BOIRE UNE DOSE

    @Test
    public void testBoireUneDose_DiminueLesDoesDeUn() {
        // On boit une dose
        potion.boireUneDose();

        // On vérifie qu'il reste 9 doses
        assertEquals(9, potion.getNombreDoses());
    }

    @Test
    public void testBoireUneDose_RetourneTrue_QuandDoesDisponibles() {
        // boireUneDose() devrait retourner true
        boolean resultat = potion.boireUneDose();

        assertTrue(resultat);
    }

    @Test
    public void testBoireUneDose_RetourneFalse_QuandMarmiteVide() {
        // On boit toutes les doses
        //boucle pour boire 10 doses
        for (int i = 0; i < 10; i++) {
            potion.boireUneDose();
        }

        // On essaie de boire une 11ème dose
        boolean resultat = potion.boireUneDose();
        //on verifie que l'ont ne peut pas
        assertFalse(resultat);
    }

    // TESTS DE BOIRE UNE MARMITE

    @Test
    public void testBoireUneMarmite_ConsommeDixDoses() {
        // On boit une marmite entière
        potion.boireUneMarmite();
        // On vérifie qu'il reste 0 doses
        assertEquals(0, potion.getNombreDoses());
    }

    @Test
    public void testBoireUneMarmite_RetourneTrue_QuandAssezDeDoses() {
        // boireUneMarmite() devrait retourner true si ça marche
        boolean resultat = potion.boireUneMarmite();
        assertTrue(resultat);
    }

    // TESTS DE BOIRE DEUX MARMITES

    @Test
    public void testBoireDeuxMarmites_RetourneFalse_QuandPasAssezDeDoses() {
        // On a seulement 10 doses, mais il en faut 20
        boolean resultat = potion.boireDeuxMarmites();
        assertFalse(resultat);
    }

    @Test
    public void testBoireDeuxMarmites_RetourneTrue_AvecAssezDeDoses() {
        potion.ajouterMarmite(); // Maintenant on a 20 doses
        boolean resultat = potion.boireDeuxMarmites();
        assertTrue(resultat);
    }

    @Test
    public void testBoireDeuxMarmites_ConsommeVingtDoses() {
        // On remplit pour avoir 20 doses
        potion.ajouterMarmite();
        // On boit deux marmites
        potion.boireDeuxMarmites();
        // On vérifie qu'il reste 0 doses
        assertEquals(0, potion.getNombreDoses());
    }

    // TESTS DE REMPLIR LA MARMITE

    @Test
    public void testRemplirMarmite_QuandMarmiteVide() {
        // On vide complètement la marmite
        potion.boireUneMarmite();
        // On remplit
        potion.remplirMarmite();

        // On vérifie qu'on a bien 10 doses
        assertEquals(10, potion.getNombreDoses());
    }

    // TESTS DE LA MÉTHODE AFFICHER RECETTE

    @Test
    public void testAfficherRecette_NePasCasser() {
        // On vérifie juste que l'affichage marche
        assertDoesNotThrow(() -> potion.afficherRecette());
    }

    @Test
    public void testAfficherRecette_AvecIngredients() {
        // On ajoute des ingrédients
        potion.ajouterHomard();
        potion.ajouterLaitLicorne();
        // On vérifie que l'affichage marche
        assertDoesNotThrow(() -> potion.afficherRecette());
    }


    @Test
    public void testScenarioComplet_UtilisationNormale() {
        // Scénario : Créer une potion, ajouter des ingrédients,
        // boire quelques doses, remplir, et re-boire

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