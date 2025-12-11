package place;

import characters.ClanChief;
import characters.Character;
import characters.enums.Gender;
import characters.enums.GaulType;
import characters.enums.PersonType;
import characters.gauls.Gaul;
import characters.romans.Roman;
import characters.fantasy.Lycanthrope;
import places.Place;
import food.enums.Ingredient;
import org.junit.jupiter.api.Test;
import places.enums.PlacesType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {

    @Test
    void testConstructorWithFoodsAndCharacters() {
        Set<Ingredient> foods = new HashSet<>();
        foods.add(Ingredient.boar);
        foods.add(Ingredient.honey);

        List<Character> characters = new ArrayList<>();
        characters.add(new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE));

        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE, foods, characters);

        assertEquals("Village", place.getName());
        assertEquals(100.0, place.getArea());
        assertEquals(PlacesType.GAUL_VILLAGE, place.getPlaceType());

        assertEquals(foods, place.getFoods());
        assertEquals(characters, place.getCharacters());
        assertNotNull(place.getWhitelist());
        assertFalse(place.getWhitelist().isEmpty());
    }

    @Test
    void testConstructorWithFoodsAndCharacters_NullCollections() {
        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE, null, null);

        assertEquals("Village", place.getName());
        assertEquals(100.0, place.getArea());
        assertEquals(PlacesType.GAUL_VILLAGE, place.getPlaceType());

        assertNotNull(place.getFoods());
        assertTrue(place.getFoods().isEmpty());

        assertNotNull(place.getCharacters());
        assertTrue(place.getCharacters().isEmpty());

        assertNotNull(place.getWhitelist());
    }

    @Test
    void testConstructorWithCharactersOnly() {
        List<Character> characters = new ArrayList<>();
        characters.add(new Gaul("Obélix", Gender.MALE, 180, 30,
                15, 8, 100, 100, 6, 0, GaulType.NONE));

        Place place = new Place("Village", 120.0, PlacesType.GAUL_VILLAGE, characters);

        assertEquals("Village", place.getName());
        assertEquals(120.0, place.getArea());
        assertEquals(PlacesType.GAUL_VILLAGE, place.getPlaceType());

        assertEquals(characters, place.getCharacters());
        assertNotNull(place.getFoods());
        assertTrue(place.getFoods().isEmpty());
    }

    @Test
    void testConstructorEmptyPlace() {
        Place place = new Place("Champ", 300.0, PlacesType.BATTLEFIELD);

        assertEquals("Champ", place.getName());
        assertEquals(300.0, place.getArea());
        assertEquals(PlacesType.BATTLEFIELD, place.getPlaceType());

        assertNotNull(place.getCharacters());
        assertTrue(place.getCharacters().isEmpty());

        assertNotNull(place.getFoods());
        assertTrue(place.getFoods().isEmpty());

        assertNotNull(place.getWhitelist());
    }

    @Test
    void testConstructorWithFoodsOnly() {
        Set<Ingredient> foods = new HashSet<>();
        foods.add(Ingredient.boar);
        foods.add(Ingredient.honey);

        Place place = new Place("Réserve", 80.0, PlacesType.ENCLOSURE, foods);

        assertEquals("Réserve", place.getName());
        assertEquals(80.0, place.getArea());
        assertEquals(PlacesType.ENCLOSURE, place.getPlaceType());

        assertEquals(foods, place.getFoods());
        assertNotNull(place.getCharacters());
        assertTrue(place.getCharacters().isEmpty());
    }

    @Test
    void testWhitelistForGaulVillage() {
        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE);

        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        Character roman = new Roman("Caius", Gender.MALE, 170, 40,
                8, 8, 100, 100, 3, 0, PersonType.roman);

        Character lycan = new Lycanthrope("Loup", Gender.MALE, 190, 28,
                12, 10, 100, 100, 7, 0, PersonType.fantasy);

        assertTrue(place.canAccept(gaul), "Gaul should be accepted in GAUL_VILLAGE");
        assertTrue(place.canAccept(lycan), "Fantasy should be accepted in GAUL_VILLAGE");
        assertFalse(place.canAccept(roman), "Roman should NOT be accepted in GAUL_VILLAGE");
    }

    @Test
    void testWhitelistForBattlefieldAcceptsAll() {
        Place battlefield = new Place("Champ de bataille", 500.0, PlacesType.BATTLEFIELD);

        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        Character roman = new Roman("Caius", Gender.MALE, 170, 40,
                8, 8, 100, 100, 3, 0, PersonType.roman);

        Character lycan = new Lycanthrope("Loup", Gender.MALE, 190, 28,
                12, 10, 100, 100, 7, 0, PersonType.fantasy);

        assertTrue(battlefield.canAccept(gaul));
        assertTrue(battlefield.canAccept(roman));
        assertTrue(battlefield.canAccept(lycan));
    }

    @Test
    void testAddCharacterRespectsWhitelist() {
        Place village = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE);

        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        Character roman = new Roman("Caius", Gender.MALE, 170, 40,
                8, 8, 100, 100, 3, 0, PersonType.roman);

        village.addCharacter(gaul);
        village.addCharacter(roman); // should be ignored

        assertTrue(village.getCharacters().contains(gaul));
        assertFalse(village.getCharacters().contains(roman));
    }

    @Test
    void testAddCharacterAllAddsAllGivenCharacters() {
        Place place = new Place("Lieu", 50.0, PlacesType.BATTLEFIELD);

        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        Character roman = new Roman("Caius", Gender.MALE, 170, 40,
                8, 8, 100, 100, 3, 0, PersonType.roman);

        List<Character> list = new ArrayList<>();
        list.add(gaul);
        list.add(roman);

        place.addCharacterAll(list);

        assertEquals(2, place.getCharacters().size());
        assertTrue(place.getCharacters().contains(gaul));
        assertTrue(place.getCharacters().contains(roman));
    }

    @Test
    void testRemoveCharacter() {
        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE);

        Character gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        place.addCharacter(gaul);
        assertTrue(place.getCharacters().contains(gaul));

        place.removeCharacter(gaul);
        assertFalse(place.getCharacters().contains(gaul));
    }

    @Test
    void testHealCharacters() {
        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE);

        Gaul gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE);

        gaul.setHealth(80);
        place.addCharacter(gaul);

        place.healCharacters();

        assertEquals(85, gaul.getHealth());
    }

    @Test
    void testFeedCharacters() {
        Place place = new Place("Village", 100.0, PlacesType.GAUL_VILLAGE);

        Gaul gaul = new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 50, 5, 0, GaulType.NONE);

        gaul.setHunger(49);
        place.addCharacter(gaul);

        place.feedCharacters();
        assertEquals(50, gaul.getHunger());
    }

    @Test
    void testSettersAndGetters() {
        Place place = new Place("Ancien nom", 10.0, PlacesType.GAUL_VILLAGE);

        place.setName("Nouveau nom");
        place.setArea(42.0);
        place.setPlaceType(PlacesType.ROMAN_CITY);

        ClanChief chief = new ClanChief("Abraracourcix", Gender.MALE, 45, place);
        place.setLeader(chief);

        Set<Ingredient> foods = new HashSet<>();
        foods.add(Ingredient.boar);
        place.setFoods(foods);

        List<Character> chars = new ArrayList<>();
        chars.add(new Gaul("Astérix", Gender.MALE, 165, 35,
                10, 10, 100, 100, 5, 0, GaulType.NONE));
        place.setCharacters(chars);

        assertEquals("Nouveau nom", place.getName());
        assertEquals(42.0, place.getArea());
        assertEquals(PlacesType.ROMAN_CITY, place.getPlaceType());
        assertEquals(chief, place.getLeader());
        assertEquals(foods, place.getFoods());
        assertEquals(chars, place.getCharacters());
    }
}
