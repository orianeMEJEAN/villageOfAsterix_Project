package places.theather;

import characters.Character;
import characters.gauls.Gaul;
import characters.gauls.Druid;
import characters.ClanChief;
import characters.enums.GaulType;
import characters.enums.Gender;
import characters.enums.PersonType;
import characters.romans.General;
import places.Place;
import places.enums.PlacesType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static places.enums.PlacesType.GAUL_VILLAGE;

/**
 * Tests JUnit for TheaterOfWar
 *
 * @author Oriane
 * @version 1.0
 */
class TheatherOfWarTest
{
    private TheaterOfWar theatherOfWar;
    private List<Place> places;
    private List<ClanChief> chiefs;
    private Place village;
    private Place battlefield;

    @BeforeEach
    void setUp()
    {
        places = new ArrayList<>();

        village = new Place("Village Gaulois", 50, GAUL_VILLAGE, clanChief, characters);
        battlefield = new Place("Champ de bataille", 100, PlacesType.BATTLEFIELD, clanChief, characters);

        places.add(village);
        places.add(battlefield);

        chiefs = new ArrayList<>();
        ClanChief chief1 = new ClanChief("Abraracourcix", Gender.MALE, 34,PlacesType.GAUL_VILLAGE);
        chiefs.add(chief1);

        theatherOfWar = new TheaterOfWar("Guerre des Gaules", 3, places, chiefs);
    }

    @Test
    void testShowPlace()
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showPlace();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("LISTE DES LIEUX"));
        assertTrue(output.contains("Village Gaulois"));
        assertTrue(output.contains("Champ de bataille"));
        assertTrue(output.contains("Forêt"));
    }

    @Test
    void testShowNumberOfCharacters_NoCharacters()
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showNumberOfCharacters();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Le nombre total de personnage est de 0"));
    }

    @Test
    void testShowNumberOfCharacters_WithCharacters()
    {
        Character asterix = new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE);
        Character obelix = new Gaul("Obélix",  Gender.MALE, 190, 23, 100, 50, 90, 0, 0, 100, GaulType.NONE);
        Character caesar = new General("César", Gender.MALE, 160, 35, 60, 70, 100, 10, 0, 0);

        village.getCharacters().add(asterix);
        village.getCharacters().add(obelix);
        battlefield.getCharacters().add(caesar);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showNumberOfCharacters();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Le nombre total de personnage est de 3"));
    }

    @Test
    void testShowCharactersAllPlaces()
    {
        Character asterix = new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE);
        Character caesar = new General("César", Gender.MALE, 160, 35, 60, 70, 100, 10, 0, 0);

        village.getCharacters().add(asterix);
        battlefield.getCharacters().add(caesar);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showCharactersAllPlaces();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("> Lieu : Village Gaulois"));
        assertTrue(output.contains(">> Personnage : Astérix"));
        assertTrue(output.contains("> Lieu : Champ de bataille"));
        assertTrue(output.contains(">> Personnage : César"));
    }

    @Test
    void testShowCharactersAllPlaces_EmptyPlaces()
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showCharactersAllPlaces();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("> Lieu : Village Gaulois"));
        assertTrue(output.contains("> Lieu : Champ de bataille"));
        assertTrue(output.contains("> Lieu : Forêt"));
    }

    @Test
    void testTheatherOfWarCreation()
    {
        assertNotNull(theatherOfWar);
        assertEquals(3, places.size());
        assertEquals(1, chiefs.size());
    }

    @Test
    void testPlacesIntegrity()
    {
        assertNotNull(places);
        assertEquals("Village Gaulois", places.get(0).getName());
        assertEquals("Champ de bataille", places.get(1).getName());

        assertEquals(GAUL_VILLAGE, places.get(0).getPlaceType());
        assertEquals(PlacesType.BATTLEFIELD, places.get(1).getPlaceType());
    }

    @Test
    void testChiefsIntegrity()
    {
        assertNotNull(chiefs);
        assertEquals(1, chiefs.size());
        assertEquals("Abraracourcix", chiefs.get(0).getName());
        assertEquals(PersonType.gaul, chiefs.get(0).getPersonType());
    }

    @Test
    void testShowNumberCharactersPlaces()
    {
        village.getCharacters().add(new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE));
        village.getCharacters().add(new Gaul("Obélix",  Gender.MALE, 190, 23, 100, 50, 90, 0, 0, 100, GaulType.NONE));
        village.getCharacters().add(new Druid("Panoramix", Gender.MALE, 120, 41, 30, 20, 30, 40, 0, 0, GaulType.NONE ));

        battlefield.getCharacters().add(new General("César",  Gender.MALE, 160, 35, 60, 70, 100, 10, 0, 0));
        battlefield.getCharacters().add(new General("Centurion",  Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        theatherOfWar.showNumberOfCharacters();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Le nombre total de personnage est de 6"));
    }
}