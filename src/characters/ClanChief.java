package characters;

import characters.enums.Gender;
import characters.enums.PersonType;
import food.enums.Ingredient;
import magicPotion.Pot;
import places.Place;
import places.enums.PlacesType;

import java.util.List;

/**
 * Represents a clan chief who leads a place and manages its inhabitants.
 * A clan chief can examine their place, create new characters,
 * heal and feed characters, manage magic potion distribution,
 * and transfer characters between locations.
 *
 * @author Lou
 * @version 1.0
 */
public class ClanChief extends Character {

    private Place place;

    /**
     * Creates a new clan chief.
     *
     * @param name      the chief's name
     * @param gender    the chief's gender
     * @param age       the chief's age
     * @param place     the place this chief leads
     */
    public ClanChief(String name, Gender gender, int age, Place place) {
        super(
                name,
                gender,
                180,              // height
                age,
                10,               // strength
                10,               // endurance
                100,              // health
                100,              // hunger
                5,                // belligerence
                0,                // magicPotionLevel
                PersonType.gaul   // default type
        );
        this.place = place;
    }

    /**
     * Displays information about the chief's place :
     * its characteristics, list of characters, and available food.
     */
    public void examinePlace() {
        System.out.println("\nEXAMEN DU LIEU");
        place.display();

        System.out.println("\nPersonnages présents");
        displayCharactersInPlace();

        System.out.println("\nAliments disponibles");
        displayFoodsInPlace();
    }

    /**
     * Creates a new character in this chief's place.
     *
     * @param character the character to create and add to the place
     * @return true if the character was successfully added, false otherwise
     */
    public boolean createCharacter(Character character) {
        if (place.canAccept(character)) {
            place.addCharacter(character);
            System.out.println(character.getName() + " a été créé dans " + place.getName());
            return true;
        } else {
            System.out.println("Ce type de personnage n'est pas autorisé dans ce lieu !");
            return false;
        }
    }

    /**
     * Heals all characters in the chief's place.
     * Delegates to the place's healing method.
     */
    public void healCharactersInPlace() {
        System.out.println(getName() + " soigne tous les personnages du lieu...");
        place.healCharacters();
        System.out.println("Tous les personnages ont été soignés !");
    }

    /**
     * Feeds all characters in the chief's place.
     */
    public void feedCharactersInPlace() {
        System.out.println(getName() + " nourrit tous les personnages du lieu");
        place.feedCharacters();
        System.out.println("Tous les personnages ont été nourris !");
    }

    /**
     * Asks a druid to brew a magic potion.
     * The druid must be present in the place and must be of type DRUID.
     *
     * @param druid      the druid who will brew the potion
     * @param pot        the pot to fill with potion
     * @param ingredients the ingredients to use
     * @return true if the potion was successfully brewed, false otherwise
     */
    public boolean askDruidToBrewPotion(Character druid, Pot pot, List<Ingredient> ingredients) {
        if (!isCharacterInPlace(druid)) {
            System.out.println("Ce druide n'est pas dans votre lieu !");
            return false;
        }

        System.out.println(getName() + " demande à " + druid.getName() + " de préparer une potion");

        for (Ingredient ingredient : ingredients) {
            if (!pot.addIngredient(ingredient)) {
                System.out.println("L'ingrédient " + ingredient + " ne peut pas être ajouté !");
            }
        }

        pot.brewPotion();

        if (pot.getMagicPotion() != null) {
            System.out.println("La potion magique a été créée avec succès !");
            pot.getMagicPotion().printRecipe();
            return true;
        }

        return false;
    }

    /**
     * Makes a character from this place drink a dose of magic potion.
     *
     * @param character the character who will drink
     * @param pot       the pot containing the potion
     * @return true if the character successfully drank, false otherwise
     */
    public boolean givePotionToCharacter(Character character, Pot pot) {
        if (!isCharacterInPlace(character)) {
            System.out.println(character.getName() + " n'est pas dans votre lieu !");
            return false;
        }

        if (pot.isEmpty()) {
            System.out.println("La marmite est vide !");
            return false;
        }

        System.out.println(getName() + " donne de la potion magique à " + character.getName());
        character.drinkPotion(pot);
        return true;
    }

    /**
     * Transfers a character from this place to a battlefield or enclosure.
     *
     * @param character       the character to transfer
     * @param destinationPlace the destination (must be BATTLEFIELD or ENCLOSURE)
     * @return true if transfer was successful, false otherwise
     */
    public boolean transferCharacter(Character character, Place destinationPlace) {
        if (!isCharacterInPlace(character)) {
            System.out.println(character.getName() + " n'est pas dans votre lieu !");
            return false;
        }

        PlacesType destType = destinationPlace.getPlaceType();
        if (destType != PlacesType.BATTLEFIELD && destType != PlacesType.ENCLOSURE) {
            System.out.println("On ne peut transférer que vers un champ de bataille ou un enclos !");
            return false;
        }

        if (!destinationPlace.canAccept(character)) {
            System.out.println(character.getName() + " ne peut pas entrer dans ce lieu !");
            return false;
        }

        place.removeCharacter(character);
        destinationPlace.addCharacter(character);
        System.out.println(character.getName() + " a été transféré vers " + destinationPlace.getName());
        return true;
    }

    /**
     * Checks if a character is present in this chief's place.
     *
     * @param character the character to check
     * @return true if the character is in the place, false otherwise
     */
    private boolean isCharacterInPlace(Character character) {
        return place.getCharacters().contains(character);
    }

    /**
     * Displays all characters currently in the place.
     */
    private void displayCharactersInPlace() {
        List<Character> characters = place.getCharacters();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage présent.");
        } else {
            for (Character character : characters) {
                System.out.println("- " + character.getName() +
                        " (" + character.getPersonType() +
                        ") - Santé: " + character.getHealth() +
                        "/" + character.getMaxHealth());
            }
        }
    }

    /**
     * Displays all food items available in the place.
     */
    private void displayFoodsInPlace() {
        List<Ingredient> foods = place.getFoods();
        if (foods == null || foods.isEmpty()) {
            System.out.println("Aucun aliment disponible.");
        } else {
            for (Ingredient food : foods) {
                System.out.println("- " + food.getDisplayName());
            }
        }
    }

    /**
     * Returns the place led by this chief.
     *
     * @return the place
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Sets the place led by this chief.
     *
     * @param place the new place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "ClanChief{" +
                "name='" + getName() + '\'' +
                ", gender=" + getGender() +
                ", age=" + getAge() +
                ", place=" + (place != null ? place.getName() : "none") +
                '}';
    }
}