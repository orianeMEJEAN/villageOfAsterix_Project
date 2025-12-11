package characters;

import characters.enums.Gender;
import characters.enums.GaulType;
import characters.enums.PersonType;
import characters.gauls.Gaul;
import characters.romans.General;
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
import java.util.ArrayList;
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
    private ClanChief chief;
    private Place place;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    List<Character> characters = new ArrayList<Character>();

    @BeforeEach
    void setUp() {
        Character asterix = new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE);
        Character obelix = new Gaul("Obélix",  Gender.MALE, 190, 23, 100, 50, 90, 0, 0, 100, GaulType.NONE);
        Character caesar = new General("César", Gender.MALE, 160, 35, 60, 70, 100, 10, 0, 0);

        characters.add(asterix);
        characters.add(obelix);

        place = new Place("Village Gaul", 1000.0, PlacesType.GAUL_VILLAGE,characters);
        chief = new ClanChief("Abraracourcix", Gender.MALE, 45, place);
        menu = new ClanChiefMenu(chief);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);

        try {
            menu.close();
        } catch (Exception e) {
        }
    }

    /**
     * Simulates a user entry
     */
    private void simulateInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    /**
     * Retrieves the console output.
     */
    private String getOutput() {
        return outputStream.toString();
    }

    /**
     * Resets the output capture.
     */
    private void resetOutput() {
        outputStream.reset();
    }

    @Test
    void testConstructorWithClanChief() {
        assertNotNull(menu);
    }

    @Test
    void testConstructorWithNullClanChief() {
        ClanChiefMenu emptyMenu = new ClanChiefMenu(null);
        assertNotNull(emptyMenu);
    }

    @Test
    void testAddClanChief() {
        ClanChief newChief = new ClanChief("Agecanonix", Gender.MALE, 80, place);
        menu.addClanChief(newChief);

        simulateInput("2\n0\n");

    }

    @Test
    void testExaminePlaceWithNoCharacters() {
        resetOutput();
        chief.examinePlace();

        String output = getOutput();
        assertTrue(output.contains("EXAMEN DU LIEU"));
        assertTrue(output.contains("Personnages présents"));
        assertTrue(output.contains("Aliments disponibles"));
    }

    @Test
    void testExaminePlaceWithCharacters() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        resetOutput();
        chief.examinePlace();

        String output = getOutput();
        assertTrue(output.contains("Astérix"));
    }

    @Test
    void testCreateCharacterSuccess() {
        Character newGaul = new Gaul("Obélix", Gender.MALE, 180, 35,
                20, 15, 100, 100, 8, 0, GaulType.NONE);

        resetOutput();
        boolean result = chief.createCharacter(newGaul);

        assertTrue(result);
        assertTrue(place.getCharacters().contains(newGaul));
        String output = getOutput();
        assertTrue(output.contains("Obélix"));
        assertTrue(output.contains("créé"));
    }

    @Test
    void testHealCharactersInPlace() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 50, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        resetOutput();
        chief.healCharactersInPlace();

        assertEquals(gaul.getMaxHealth(), gaul.getHealth());
        String output = getOutput();
        assertTrue(output.contains("soigne"));
        assertTrue(output.contains("soignés"));
    }

    @Test
    void testFeedCharactersInPlace() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 50, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        gaul.setHunger(49);

        resetOutput();
        chief.feedCharactersInPlace();

        assertEquals(50, gaul.getHunger());
        String output = getOutput();
        assertTrue(output.contains("nourrit"));
        assertTrue(output.contains("nourris"));
    }

    @Test
    void testGivePotionToCharacterSuccess() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        Pot pot = new Pot();
        pot.addDose(5);

        pot.addIngredient(Ingredient.mistletoe);
        pot.addIngredient(Ingredient.carrots);
        pot.addIngredient(Ingredient.salt);
        pot.addIngredient(Ingredient.freshFourLeafClover);
        pot.addIngredient(Ingredient.moderatelyFreshFish);
        pot.addIngredient(Ingredient.rockOil);
        pot.addIngredient(Ingredient.honey);
        pot.addIngredient(Ingredient.mead);
        pot.addIngredient(Ingredient.secretIngredient);

        pot.brewPotion();

        resetOutput();
        boolean result = chief.givePotionToCharacter(gaul, pot);

        assertTrue(result);
        String output = getOutput();
        assertTrue(output.contains("potion magique"));
    }

    @Test
    void testGivePotionToCharacterNotInPlace() {
        Character gaul = new Gaul("Panoramix", Gender.MALE, 170, 60,
                10, 10, 100, 100, 5, 0, GaulType.DRUID);

        Pot pot = new Pot();
        pot.addDose(5);

        resetOutput();
        boolean result = chief.givePotionToCharacter(gaul, pot);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testGivePotionToCharacterEmptyPot() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        Pot emptyPot = new Pot();

        resetOutput();
        boolean result = chief.givePotionToCharacter(gaul, emptyPot);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("vide"));
    }

    @Test
    void testTransferCharacterSuccess() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        Place battlefield = new Place("Champ de bataille", 500.0, PlacesType.BATTLEFIELD, new ArrayList<>());

        resetOutput();
        boolean result = chief.transferCharacter(gaul, battlefield);

        assertTrue(result);
        assertFalse(place.getCharacters().contains(gaul));
        assertTrue(battlefield.getCharacters().contains(gaul));
        String output = getOutput();
        assertTrue(output.contains("transféré"));
    }

    @Test
    void testTransferCharacterNotInPlace() {
        Character gaul = new Gaul("Obélix", Gender.MALE, 180, 35,
                20, 15, 100, 100, 8, 0, GaulType.NONE);

        Place battlefield = new Place("Champ de bataille", 500.0, PlacesType.BATTLEFIELD, characters);

        resetOutput();
        boolean result = chief.transferCharacter(gaul, battlefield);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testTransferCharacterInvalidDestination() {
        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                15, 10, 100, 100, 7, 0, GaulType.NONE);
        place.addCharacter(gaul);

        Place invalidPlace = new Place("Village", 500.0, PlacesType.GAUL_VILLAGE, characters);

        resetOutput();
        boolean result = chief.transferCharacter(gaul, invalidPlace);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("champ de bataille") || output.contains("enclos"));
    }

    @Test
    void testAskDruidToBrewPotionSuccess() {
        Character druid = new Gaul("Panoramix", Gender.MALE, 170, 60,
                10, 10, 100, 100, 5, 0, GaulType.DRUID);
        place.addCharacter(druid);

        Pot pot = new Pot();
        List<Ingredient> ingredients = List.of(
                Ingredient.mistletoe,
                Ingredient.carrots,
                Ingredient.salt
        );

        resetOutput();
        boolean result = chief.askDruidToBrewPotion(druid, pot, ingredients);

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
        boolean result = chief.askDruidToBrewPotion(druid, pot, ingredients);

        assertFalse(result);
        String output = getOutput();
        assertTrue(output.contains("n'est pas dans votre lieu"));
    }

    @Test
    void testGetAndSetPlace() {
        Place newPlace = new Place("Nouveau Village", 2000.0, PlacesType.GAUL_VILLAGE, characters);
        chief.setPlace(newPlace);

        assertEquals(newPlace, chief.getPlace());
    }

    @Test
    void testToString() {
        String result = chief.toString();

        assertNotNull(result);
        assertTrue(result.contains("ClanChief"));
        assertTrue(result.contains("Abraracourcix"));
        assertTrue(result.contains("Village Gaul"));
    }

    @Test
    void testClanChiefMenuClose() {
        assertDoesNotThrow(() -> menu.close());
    }
}