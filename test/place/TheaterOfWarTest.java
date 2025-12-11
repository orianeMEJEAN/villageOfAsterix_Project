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

/**
 * Tests JUnit for TheaterOfWar
 *
 * @author Oriane
 * @version 1.0
 */
class TheaterOfWarTest
{
    private TheaterOfWar theaterOfWar;
    private List<Place> places;
    private List<ClanChief> chiefs;
    private Place village;
    private Place empire;
    private Place battlefield;
    private List<Character> charactersG;
    private List<Character> charactersR;
    private List<Character> charactersGR;

    @BeforeEach
    void setUp()
    {
        places = new ArrayList<>();

        Character asterix = new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE);
        Character obelix = new Gaul("Obélix",  Gender.MALE, 190, 23, 100, 50, 90, 0, 0, 100, GaulType.NONE);
        Character panorama = new Druid("Panoramix", Gender.MALE, 120, 41, 30, 20, 30, 40, 0, 0, GaulType.NONE);
        Character caesar = new General("César", Gender.MALE, 160, 35, 60, 70, 100, 10, 0, 0);
        Character centurion = new General("Centurion", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0);

        // Characters list Gaul
        charactersG = new ArrayList<>();
        charactersG.add(asterix);
        charactersG.add(obelix);
        charactersG.add(panorama);

        // Characters list Roman
        charactersR = new ArrayList<>();
        charactersR.add(caesar);
        charactersR.add(centurion);

        // Characters list Roman & Gaul
        charactersGR = new ArrayList<>();
        charactersGR.addAll(charactersG);
        charactersGR.addAll(charactersR);

        village = new Place("Village Gaulois", 50, PlacesType.GAUL_VILLAGE, new ArrayList<>());
        battlefield = new Place("Champ de bataille", 100, PlacesType.BATTLEFIELD, new ArrayList<>());
        empire = new Place("Empire Romain", 100, PlacesType.ROMAN_CITY, new ArrayList<>());

        places.add(village);
        places.add(battlefield);
        places.add(empire);

        chiefs = new ArrayList<>();
        ClanChief chief1 = new ClanChief("Abraracourcix", Gender.MALE, 34, village);
        chiefs.add(chief1);

        theaterOfWar = new TheaterOfWar("Guerre des Gaules", 3, places, chiefs);
    }

    @Test
    void testShowPlace()
    {
        String output = theaterOfWar.showPlace();

        assertTrue(output.contains("Liste des Lieux"));
        assertTrue(output.contains("Village Gaulois"));
        assertTrue(output.contains("Champ de bataille"));
    }

    @Test
    void testShowNumberCharacters()
    {
        village.addCharacterAll(charactersG);
        empire.addCharacterAll(charactersR);

        String output = theaterOfWar.showNumberOfCharacters();
        assertEquals("Le nombre total de personnage est de 5", output);
    }

    @Test
    void testShowNumberOfCharactersNoCharacters()
    {
        String output = theaterOfWar.showNumberOfCharacters();
        assertEquals("Le nombre total de personnage est de 0", output);
    }

    @Test
    void testShowCharactersOfAllPlaces()
    {
        village.addCharacterAll(charactersG);
        empire.addCharacterAll(charactersR);
        battlefield.addCharacterAll(charactersGR);

        String output = theaterOfWar.showCharactersAllPlaces();


        Character asterix = new Gaul("Astérix", Gender.MALE, 150, 21, 10, 100, 100, 0, 0, 0, GaulType.NONE);
        Character obelix = new Gaul("Obélix",  Gender.MALE, 190, 23, 100, 50, 90, 0, 0, 100, GaulType.NONE);
        Character panorama = new Druid("Panoramix", Gender.MALE, 120, 41, 30, 20, 30, 40, 0, 0, GaulType.NONE);

        assertTrue(output.contains("> Lieu : Village Gaulois"));
        assertTrue(output.contains(">> Personnage : Astérix"));
        assertTrue(output.contains(">> Personnage : Obélix"));
        assertTrue(output.contains(">> Personnage : Panoramix"));

        assertTrue(output.contains("> Lieu : Empire Romain"));
        assertTrue(output.contains(">> Personnage : César"));
        assertTrue(output.contains(">> Personnage : Centurion"));

        assertTrue(output.contains("> Lieu : Champ de bataille"));
        assertTrue(output.contains(">> Personnage : Astérix"));
        assertTrue(output.contains(">> Personnage : Obélix"));
        assertTrue(output.contains(">> Personnage : Panoramix"));
        assertTrue(output.contains(">> Personnage : César"));
        assertTrue(output.contains(">> Personnage : Centurion"));
    }

    @Test
    void testShowCharactersAllPlacesButEmpty()
    {
        String output = theaterOfWar.showCharactersAllPlaces();

        assertTrue(output.contains("> Lieu : Village Gaulois"));
        assertTrue(output.contains("> Lieu : Empire Romain"));
        assertTrue(output.contains("> Lieu : Champ de bataille"));
    }

    @Test
    void testTheaterOfWarCreation()
    {
        assertNotNull(theaterOfWar);
        assertEquals(3, places.size());
        assertEquals(1, chiefs.size());
    }

    @Test
    void testPlacesIntegrity()
    {
        assertNotNull(places);
        assertEquals("Village Gaulois", places.get(0).getName());
        assertEquals("Champ de bataille", places.get(1).getName());

        assertEquals(PlacesType.GAUL_VILLAGE, places.get(0).getPlaceType());
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
    void testShowNumberCharactersByPlaces()
    {
        village.addCharacterAll(charactersG);
        empire.addCharacterAll(charactersR);
        battlefield.addCharacterAll(charactersGR);

        assertEquals(3, village.getCharacters().size(), "Le village devrait avoir 3 characters");
        assertEquals(2, empire.getCharacters().size(), "L'empire devrait avoir 2 characters");
        assertEquals(5, battlefield.getCharacters().size(), "Le battlefield devrait avoir 5 characters");

        String output = theaterOfWar.showNumberOfCharacters();
        assertEquals("Le nombre total de personnage est de 10", output);
    }
}