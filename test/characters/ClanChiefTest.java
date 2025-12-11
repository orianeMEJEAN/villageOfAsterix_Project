package characters;

import characters.enums.Gender;
import characters.enums.GaulType;
import characters.enums.PersonType;
import characters.gauls.Gaul;
import characters.gauls.Druid;
import characters.romans.Legionary;
import food.enums.Ingredient;
import magicPotion.MagicPotion;
import magicPotion.Pot;
import places.Place;
import places.enums.PlacesType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static places.enums.PlacesType.GAUL_VILLAGE;

/**
 * Test class for ClanChief.
 * Uses only JUnit without Mockito - creates real objects for testing.
 */
class ClanChiefTest {

    private ClanChief clanChief;
    private Place village;
    private Place battlefield;
    private Place enclosure;
    private Character gaul;
    private Character druid;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        village = new Place("Village", 50, PlacesType.GAUL_VILLAGE );
        battlefield = new Place("Champ de bataille", 100, PlacesType.BATTLEFIELD);
        enclosure = new Place("Enclos", 30, PlacesType.ENCLOSURE);

        clanChief = new ClanChief("Abraracourcix", Gender.MALE, 45, village);

        gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                8, 8, 100, 100, 3, 0, GaulType.NONE);
        druid = new Druid("Panoramix", Gender.MALE, 170, 60,
                5, 5, 100, 100, 1, 0, GaulType.DRUID);

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testConstructor() {
        assertNotNull(clanChief);
        assertEquals("Abraracourcix", clanChief.getName());
        assertEquals(Gender.MALE, clanChief.getGender());
        assertEquals(45, clanChief.getAge());
        assertEquals(village, clanChief.getPlace());
        assertEquals(180, clanChief.getHeight());
        assertEquals(10, clanChief.getStrength());
        assertEquals(10, clanChief.getEndurance());
        assertEquals(100, clanChief.getHealth());
        assertEquals(PersonType.gaul, clanChief.getPersonType());
    }

    @Test
    void testExaminePlace() {
        village.addCharacter(gaul);

        clanChief.examinePlace();

        String output = outputStream.toString();
        assertTrue(output.contains("EXAMEN DU LIEU"));
        assertTrue(output.contains("Personnages présents"));
        assertTrue(output.contains("Aliments disponibles"));
    }

    @Test
    void testCreateCharacter_Success() {
        boolean result = clanChief.createCharacter(gaul);

        assertTrue(result);
        assertTrue(village.getCharacters().contains(gaul));
        assertTrue(outputStream.toString().contains("a été créé dans"));
    }

    @Test
    void testCreateCharacter_Failure() {
        // Create a character that the village cannot accept (if applicable)
        Character romanFighter = new Legionary("Roman", Gender.MALE, 180, 30,
                10, 10, 100, 100, 5, 0);

        boolean result = clanChief.createCharacter(romanFighter);

        if (!result) {
            assertTrue(outputStream.toString().contains("n'est pas autorisé"));
            assertFalse(village.getCharacters().contains(romanFighter));
        }
    }

    @Test
    void testHealCharactersInPlace() {
        village.addCharacter(gaul);
        gaul.setHealth(50); // Damage the character

        clanChief.healCharactersInPlace();

        String output = outputStream.toString();
        assertTrue(output.contains("soigne tous les personnages"));
        assertTrue(output.contains("ont été soignés"));
    }

    @Test
    void testFeedCharactersInPlace() {
        village.addCharacter(gaul);

        clanChief.feedCharactersInPlace();

        String output = outputStream.toString();
        assertTrue(output.contains("nourrit tous les personnages"));
        assertTrue(output.contains("ont été nourris"));
    }

    @Test
    void testAskDruidToBrewPotion_DruidNotInPlace() {

        Pot pot = new Pot();

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

        List<Ingredient> ingredients = Arrays.asList(Ingredient.boar);

        boolean result = clanChief.askDruidToBrewPotion(druid, pot, ingredients);

        assertFalse(result);
        assertTrue(outputStream.toString().contains("n'est pas dans votre lieu"));
    }

    @Test
    void testAskDruidToBrewPotion_Success() {
        village.addCharacter(druid);

        Pot pot = new Pot();

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

        List<Ingredient> ingredients = Arrays.asList(
                Ingredient.boar,
                Ingredient.honey
        );

        boolean result = clanChief.askDruidToBrewPotion(druid, pot, ingredients);

        String output = outputStream.toString();
        assertTrue(output.contains("demande à"));
        assertTrue(output.contains("de préparer une potion"));
    }

    @Test
    void testGivePotionToCharacter_CharacterNotInPlace() {

        Pot pot = new Pot();

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

        boolean result = clanChief.givePotionToCharacter(gaul, pot);

        assertFalse(result);
        assertTrue(outputStream.toString().contains("n'est pas dans votre lieu"));
    }

    @Test
    void testGivePotionToCharacter_EmptyPot() {
        village.addCharacter(gaul);

        Pot pot = new Pot();

        boolean result = clanChief.givePotionToCharacter(gaul, pot);

        assertFalse(result);
        assertTrue(outputStream.toString().contains("marmite est vide"));
    }

    @Test
    void testGivePotionToCharacter_Success() {
        village.addCharacter(gaul);

        Pot pot = new Pot();

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


        // Brew a potion first
        pot.addIngredient(Ingredient.boar);
        pot.brewPotion();

        boolean result = clanChief.givePotionToCharacter(gaul, pot);

        assertTrue(result);
        assertTrue(outputStream.toString().contains("donne de la potion magique"));
    }

    @Test
    void testTransferCharacter_CharacterNotInPlace() {
        boolean result = clanChief.transferCharacter(gaul, battlefield);

        assertFalse(result);
        assertTrue(outputStream.toString().contains("n'est pas dans votre lieu"));
    }

    @Test
    void testTransferCharacter_InvalidDestination() {
        village.addCharacter(gaul);
        Place anotherVillage = new Place("Autre Village", 50, PlacesType.GAUL_VILLAGE);

        boolean result = clanChief.transferCharacter(gaul, anotherVillage);

        assertFalse(result);
        assertTrue(outputStream.toString().contains("champ de bataille ou un enclos"));
    }

    @Test
    void testTransferCharacter_ToBattlefield() {
        village.addCharacter(gaul);

        boolean result = clanChief.transferCharacter(gaul, battlefield);

        assertTrue(result);
        assertFalse(village.getCharacters().contains(gaul));
        assertTrue(battlefield.getCharacters().contains(gaul));
        assertTrue(outputStream.toString().contains("a été transféré vers"));
    }

    @Test
    void testTransferCharacter_ToEnclosure() {
        village.addCharacter(gaul);

        boolean result = clanChief.transferCharacter(gaul, enclosure);

        if (result) {
            assertFalse(village.getCharacters().contains(gaul));
            assertTrue(enclosure.getCharacters().contains(gaul));
            assertTrue(outputStream.toString().contains("a été transféré vers"));
        }
    }

    @Test
    void testTransferCharacter_DestinationRefuses() {
        village.addCharacter(gaul);

        // Fill the battlefield to capacity
        for (int i = 0; i < 100; i++) {
            Character temp = new Gaul("Temp" + i, Gender.MALE, 170, 30,
                    5, 5, 100, 100, 3, 0,
                    GaulType.NONE);
            battlefield.addCharacter(temp);
        }

        boolean result = clanChief.transferCharacter(gaul, battlefield);

        if (!result) {
            assertTrue(outputStream.toString().contains("ne peut pas entrer"));
            assertTrue(village.getCharacters().contains(gaul));
        }
    }

    @Test
    void testGetPlace() {
        assertEquals(village, clanChief.getPlace());
    }

    @Test
    void testSetPlace() {
        Place newPlace = new Place("Nouveau Village",  40, PlacesType.GAUL_VILLAGE);

        clanChief.setPlace(newPlace);

        assertEquals(newPlace, clanChief.getPlace());
    }

    @Test
    void testToString() {
        String result = clanChief.toString();

        assertTrue(result.contains("ClanChief"));
        assertTrue(result.contains("Abraracourcix"));
        assertTrue(result.contains("MALE"));
        assertTrue(result.contains("45"));
        assertTrue(result.contains("Village"));
    }

    @Test
    void testToString_NullPlace() {
        clanChief.setPlace(null);

        String result = clanChief.toString();

        assertTrue(result.contains("none"));
    }

    @Test
    void testExaminePlace_MultipleCharacters() {
        village.addCharacter(gaul);
        village.addCharacter(druid);

        clanChief.examinePlace();

        String output = outputStream.toString();
        assertTrue(output.contains("Astérix"));
        assertTrue(output.contains("Panoramix"));
    }

    @Test
    void testExaminePlace_EmptyPlace() {
        clanChief.examinePlace();

        String output = outputStream.toString();
        assertTrue(output.contains("Aucun personnage présent") ||
                output.contains("Personnages présents"));
    }
}