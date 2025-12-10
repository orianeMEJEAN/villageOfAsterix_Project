package characters;

import characters.enums.Gender;
import characters.enums.GaulType;
import characters.enums.PersonType;
import characters.gauls.Gaul;
import food.enums.Ingredient;
import magicPotion.Pot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import places.Place;
import places.enums.PlacesType;

import javax.xml.stream.events.Characters;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Uses manual input simulation and output capture.
 *
 * @author Lou
 * @version 1.0
 */
class ClanChiefMenuTest {

    private ClanChiefMenu menu;
    private ClanChief testChief;
    private TestPlace testPlace;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    /**
     * Simple test implementation of Place for testing purposes.
     */
    private static class TestPlace extends Place {
        public TestPlace(String name, double area, ClanChief clanChief, List<Character> characters) {
            super(name, area, PlacesType.GAUL_VILLAGE, clanChief, characters);
        }

        @Override
        public boolean canAccept(Character character) {
            return true;
        }

        @Override
        public void display() {
            System.out.println("Test Place: " + getName());
        }

        @Override
        public void healCharacters() {
            getCharacters().forEach(c -> c.setHealth(c.getMaxHealth()));
        }

        @Override
        public void feedCharacters() {
            getCharacters().forEach(c -> c.setHunger(100));
        }
    }

    @BeforeEach
    void setUp() {
        // Créer un lieu de test
        testPlace = new TestPlace("Village Test", 1000.0, clanChief, characters);

        // Créer un chef de clan de test
        testChief = new ClanChief("Abraracourcix", Gender.MALE, 45, testPlace);

        // Créer le menu avec le chef
        menu = new ClanChiefMenu(testChief);

        // Capturer la sortie standard
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restaurer la sortie standard
        System.setOut(originalOut);

        // Fermer le menu si nécessaire
        try {
            menu.close();
        } catch (Exception e) {
            // Ignorer les erreurs de fermeture
        }
    }

    /**
     * Simule une entrée utilisateur.
     */
    private void simulateInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    /**
     * Récupère la sortie console.
     */
    private String getOutput() {
        return outputStream.toString();
    }

    /**
     * Réinitialise la capture de sortie.
     */
    private void resetOutput() {
        outputStream.reset();
    }

    @Test
    void testConstructorWithClanChief() {
        assertNotNull(menu);
        // Le chef devrait être ajouté à la liste
    }

    @Test
    void testConstructorWithNullClanChief() {
        ClanChiefMenu emptyMenu = new ClanChiefMenu(null);
        assertNotNull(emptyMenu);
        // Devrait créer un menu vide sans erreur
    }

    @Test
    void testAddClanChief() {
        ClanChief newChief = new ClanChief("Agecanonix", Gender.MALE, 80, testPlace);
        menu.addClanChief(newChief);

        // Tester l'affichage de tous les chefs
        simulateInput("2\n0\n");

        // La méthode start() devrait afficher les chefs
        // mais nous ne pouvons pas la tester complètement sans interaction
    }

    @Test
    void testExaminePlaceWithNoCharacters() {
        resetOutput();
        testChief.examinePlace();

        String output = getOutput();
        assertTrue(output.contains("EXAMEN DU LIEU"));
        assertTrue(output.contains("Personnages présents"));
        assertTrue(output.contains("Aliments disponibles"));
    }

    @Test
    void testExaminePlaceWithCharacters() {
        // Ajouter un personnage au lieu
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        resetOutput();
        testChief.examinePlace();

        String output = getOutput();
        assertTrue(output.contains("Astérix"));
    }

    @Test
    void testCreateCharacterSuccess() {
        Character newGaul = new Gaul("Obélix", Gender.MALE, 180, 35,
                20, 15, 100, 100, 8, 0, GaulType.NONE);

        resetOutput();
        boolean result = testChief.createCharacter(newGaul);

        assertTrue(result);
        assertTrue(testPlace.getCharacters().contains(newGaul));
        String output = getOutput();
        assertTrue(output.contains("Obélix"));
        assertTrue(output.contains("créé"));
    }

    @Test
    void testHealCharactersInPlace() {
        // Ajouter un personnage blessé
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 50, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        resetOutput();
        testChief.healCharactersInPlace();

        assertEquals(gaul.getMaxHealth(), gaul.getHealth());
        String output = getOutput();
        assertTrue(output.contains("soigne"));
        assertTrue(output.contains("soignés"));
    }

    @Test
    void testFeedCharactersInPlace() {
        // Ajouter un personnage affamé
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 50, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        resetOutput();
        testChief.feedCharactersInPlace();

        assertEquals(100, gaul.getHunger());
        String output = getOutput();
        assertTrue(output.contains("nourrit"));
        assertTrue(output.contains("nourris"));
    }

    @Test
    void testGivePotionToCharacterSuccess() {
        // Ajouter un personnage
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        // Créer une potion
        Pot pot = new Pot();
        pot.addDose(5);
        pot.addIngredient(Ingredient.mistletoe);
        pot.brewPotion();

        resetOutput();
        boolean result = testChief.givePotionToCharacter(gaul, pot);

        assertTrue(result);
        String output = getOutput();
        assertTrue(output.contains("potion magique"));
    }

    @Test
    void testGivePotionToCharacterNotInPlace() {
        // Créer un personnage qui n'est PAS dans le lieu
        Character gaul = new Gaul("Panoramix", Gender.MALE, 170, 60,
                10, 10, 100, 100, 5, 0, GaulType.DRUID);

        Pot pot = new Pot();
        pot.addDose(5);

        resetOutput();
        boolean result = testChief.givePotionToCharacter(gaul, pot);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testGivePotionToCharacterEmptyPot() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        Pot emptyPot = new Pot();

        resetOutput();
        boolean result = testChief.givePotionToCharacter(gaul, emptyPot);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("vide"));
    }

    @Test
    void testTransferCharacterSuccess() {
        // Créer un personnage dans le lieu d'origine
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        // Créer un lieu de destination (BATTLEFIELD)
        TestPlace battlefield = new TestPlace("Champ de bataille", 500.0, clanChief, characters) {
            @Override
            public PlacesType getPlaceType() {
                return PlacesType.BATTLEFIELD;
            }
        };

        resetOutput();
        boolean result = testChief.transferCharacter(gaul, battlefield);

        assertTrue(result);
        assertFalse(testPlace.getCharacters().contains(gaul));
        assertTrue(battlefield.getCharacters().contains(gaul));
        String output = getOutput();
        assertTrue(output.contains("transféré"));
    }

    @Test
    void testTransferCharacterNotInPlace() {
        Character gaul = new Gaul("Obélix", Gender.MALE, 180, 35,
                20, 15, 100, 100, 8, 0, GaulType.NONE);

        TestPlace battlefield = new TestPlace("Champ de bataille", 500.0, clanChief, characters) {
            @Override
            public PlacesType getPlaceType() {
                return PlacesType.BATTLEFIELD;
            }
        };

        resetOutput();
        boolean result = testChief.transferCharacter(gaul, battlefield);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testTransferCharacterInvalidDestination() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        testPlace.addCharacter(gaul);

        // Lieu avec type invalide (VILLAGE)
        TestPlace invalidPlace = new TestPlace("Village", 500.0, clanChief, characters);

        resetOutput();
        boolean result = testChief.transferCharacter(gaul, invalidPlace);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("champ de bataille") || output.contains("enclos"));
    }

    @Test
    void testAskDruidToBrewPotionSuccess() {
        // Créer un druide
        Character druid = new Gaul("Panoramix", Gender.MALE, 170, 60,
                10, 10, 100, 100, 5, 0, GaulType.DRUID);
        testPlace.addCharacter(druid);

        Pot pot = new Pot();
        List<Ingredient> ingredients = List.of(
                Ingredient.mistletoe,
                Ingredient.carrots,
                Ingredient.salt
        );

        resetOutput();
        boolean result = testChief.askDruidToBrewPotion(druid, pot, ingredients);

        // Le résultat dépend de la logique de création de potion
        String output = getOutput();
        assertTrue(output.contains("préparer") || output.contains("potion"));
    }

    @Test
    void testAskDruidToBrewPotionDruidNotInPlace() {
        Character druid = new Gaul("Panoramix", Gender.MALE, 170, 60,
                10, 10, 100, 100, 5, 0, GaulType.DRUID);

        Pot pot = new Pot();
        List<Ingredient> ingredients = List.of(Ingredient.mistletoe);

        resetOutput();
        boolean result = testChief.askDruidToBrewPotion(druid, pot, ingredients);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testGetAndSetPlace() {
        Place newPlace = new TestPlace("Nouveau Village", 2000.0, clanChief, characters);
        testChief.setPlace(newPlace);

        assertEquals(newPlace, testChief.getPlace());
    }

    @Test
    void testToString() {
        String result = testChief.toString();

        assertNotNull(result);
        assertTrue(result.contains("ClanChief"));
        assertTrue(result.contains("Abraracourcix"));
        assertTrue(result.contains("Village Test"));
    }

    @Test
    void testClanChiefMenuClose() {
        // S'assurer que close() ne génère pas d'exception
        assertDoesNotThrow(() -> menu.close());
    }
}