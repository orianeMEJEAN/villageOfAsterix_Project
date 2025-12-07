package places;

import characters.ClanChief;
import characters.enums.PersonType;
import food.enums.Ingredient;
import places.enums.PlacesType;
import characters.Character;

import java.util.List;
import java.util.Set;

public abstract class Place {

    private String name;
    private double area;
    private PlacesType placeType;
    private ClanChief leader;
    private List<Character> characters;
    private List<Ingredient> foods;
    private Set<PersonType> whitelist;

    /**
     * Constructs a new Place with its basic properties.
     *
     * @param name       the name of the place
     * @param area       the total surface area of the place
     * @param placesType the type of place (village area, inn, forest, etc.)
     * @param clanChief  the leader or person in charge of this place
     * @param characters the list of characters currently present in the place
     */
    public Place(String name,
                 double area,
                 PlacesType placesType,
                 ClanChief clanChief,
                 List<Character> characters){

        this.name = name;
        this.area = area;
        this.placeType = placesType;
        this.leader = clanChief;
        this.characters = characters;

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
     * Returns the list of characters in this place.
     */
    public List<Character> getCharacters() {
        return characters;
    }

    /**
     * Returns the list of foods available in this place.
     */
    public List<Ingredient> getFoods() {
        return foods;
    }

    /**
     * Returns the name of this place.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of this place.
     */
    public PlacesType getPlaceType() {
        return placeType;
    }

    /**
     * Adds a character to the place.
     *
     * @param character the character to add
     */
    public void addCharacter(Character character) {
        characters.add(character);
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
}
