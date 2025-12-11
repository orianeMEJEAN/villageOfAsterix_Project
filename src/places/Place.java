package places;

import characters.ClanChief;
import characters.enums.PersonType;
import food.enums.Ingredient;
import places.enums.PlacesType;
import characters.Character;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

public class Place {

    private String name;
    private double area;
    private PlacesType placeType;
    private ClanChief leader;
    private List<Character> characters = new ArrayList<>();
    private Set<Ingredient> foods = new HashSet<>();
    private Set<PersonType> whitelist;

    /**
     * Constructs a Place with foods and characters.
     *
     * @param name
     * @param area
     * @param placesType
     * @param foods
     * @param characters
     */
    public Place(String name,
                 double area,
                 PlacesType placesType,
                 Set<Ingredient> foods,
                 List<Character> characters) {

        this.name = name;
        this.area = area;
        this.placeType = placesType;
        this.foods = (foods != null) ? foods : new HashSet<>();
        this.characters = (characters != null) ? characters : new ArrayList<>();

        this.whitelist = buildWhitelist(placesType);
    }

    /**
     * Constructs a new Place with characters and no food.
     *
     * @param name
     * @param area
     * @param placesType
     * @param characters
     */
    public Place(String name,
                 double area,
                 PlacesType placesType,
                 List<Character> characters) {

        this.name = name;
        this.area = area;
        this.placeType = placesType;
        this.characters = (characters != null) ? characters : new ArrayList<>();

        this.whitelist = buildWhitelist(placesType);
    }

    /**
     * Constructs a new empty Place (no foods, no characters).
     *
     * @param name
     * @param area
     * @param placesType
     */
    public Place(String name,
                 double area,
                 PlacesType placesType) {

        this.name = name;
        this.area = area;
        this.placeType = placesType;

        this.whitelist = buildWhitelist(placesType);
    }

    /**
     * Constructs a Place with foods only.
     *
     * @param name
     * @param area
     * @param placesType
     * @param foods
     */
    public Place(String name,
                 double area,
                 PlacesType placesType,
                 Set<Ingredient> foods) {

        this.name = name;
        this.area = area;
        this.placeType = placesType;
        this.foods = (foods != null) ? foods : new HashSet<>();
        this.whitelist = buildWhitelist(placesType);
    }

    /**
     * Create a characters whitelist who depends on the place's type
     * @param type
     * @return Set<PersonType>
     */
    private Set<PersonType> buildWhitelist(PlacesType type) {
        return switch (type) {
            case GAUL_VILLAGE -> Set.of(
                    PersonType.gaul,
                    PersonType.fantasy
            );
            case ROMAN_CAMP -> Set.of(
                    PersonType.roman_fighter,
                    PersonType.fantasy
            );
            case ROMAN_CITY -> Set.of(
                    PersonType.roman,
                    PersonType.fantasy
            );
            case GALLO_ROMAN_TOWN -> Set.of(
                    PersonType.gaul,
                    PersonType.roman
            );
            case ENCLOSURE -> Set.of(
                    PersonType.fantasy
            );
            case BATTLEFIELD -> Set.of(
                    PersonType.values()
            );
        };
    }


    /**
     * Displays a textual representation of the place,
     * including its name, area, type, leader, and current characters.
     */
    public void display() {
        System.out.println("Place{" +
                "name='" + name + '\'' +
                ", area=" + area +
                ", placeType=" + placeType +
                ", leader=" + leader +
                ", characters=" + characters +
                ", whitelist=" + whitelist +
                '}'
        );
    }

    /**
     * Adds a character to the place.
     *
     * @param character the character to add
     */
    public void addCharacter(Character character) {
        if (canAccept(character)) {
            characters.add(character);
        }
    }

    /**
     * Adds a list of characters to a place     *
     * @param chars the list of characters to add to a place
     */
    public void addCharacterAll(List<Character> chars)
    {
        this.characters.addAll(chars);
    }


    /**
     * Removes a character from the place.
     *
     * @param character the character to remove
     */
    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    /**
     * Determines whether the given character is allowed to enter the place
     * based on the internal whitelist of permitted character types.
     *
     * @param character the character attempting to enter
     * @return true if the character's type is allowed, false otherwise
     */
    public boolean canAccept(Character character) {
        return whitelist.contains(character.getPersonType());
    }

    /**
     * Heals all characters currently inside the place.
     * Each character receives a fixed amount of healing (+5 health).
     */
    public void healCharacters() {
        for (Character character : characters) {
            character.heal(5);
        }
    }

    /**
     * Feeds all characters currently inside the place.
     * Each character receives a fixed amount of nourishment (+5 hunger).
     */
    public void feedCharacters() {
        for (Character character : characters) {
            character.eat(5);
        }
    }

    /**
     * Returns the name of the place.
     *
     * @return the place name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the place.
     *
     * @param name the new name of the place
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the total surface area of the place.
     *
     * @return the area in square meters
     */
    public double getArea() {
        return area;
    }

    /**
     * Sets the surface area of the place.
     *
     * @param area the new area in square meters
     */
    public void setArea(double area) {
        this.area = area;
    }

    /**
     * Returns the type of this place (village, camp, battlefield, etc.).
     *
     * @return the place type
     */
    public PlacesType getPlaceType() {
        return placeType;
    }

    /**
     * Sets the type of this place.
     *
     * @param placeType the new place type
     */
    public void setPlaceType(PlacesType placeType) {
        this.placeType = placeType;
    }

    /**
     * Returns the leader (clan chief) managing this place.
     *
     * @return the leader, or null if none
     */
    public ClanChief getLeader() {
        return leader;
    }

    /**
     * Assigns a leader (clan chief) to this place.
     * Note: battlefields should not have leaders.
     *
     * @param leader the leader to assign
     */
    public void setLeader(ClanChief leader) {
        this.leader = leader;
    }

    /**
     * Returns the list of characters currently present in the place.
     *
     * @return the list of characters
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Sets the list of characters present in this place.
     *
     * @param characters the new list of characters
     */
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    /**
     * Returns the list of available food items in this place.
     *
     * @return the list of ingredients, or null if none
     */
    public Set<Ingredient> getFoods() {
        return foods;
    }

    /**
     * Sets the food items available in this place.
     *
     * @param foods the list of ingredients to set
     */
    public void setFoods(Set<Ingredient> foods) {
        this.foods = foods;
    }

    /**
     * Returns the whitelist of allowed character types for this place.
     *
     * @return a set of allowed PersonType values
     */
    public Set<PersonType> getWhitelist() {
        return whitelist;
    }
}
