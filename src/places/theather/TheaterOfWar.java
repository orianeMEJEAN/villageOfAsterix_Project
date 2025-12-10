package places.theather;

import characters.Character;
import characters.ClanChief;
import characters.enums.PersonType;
import places.Place;
import places.enums.PlacesType;
import food.enums.Ingredient;
import food.FoodService;
import characters.ClanChiefMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a theater of war where battles occur between different factions*
 * This class manages simulation including battles, food management, clan chief actions...etc
 *
 * @author Oriane
 * @version 2.0
 */
public class TheaterOfWar
{
    private String name;
    private int maxPlaces;
    private List<Place> places;
    private List<ClanChief> chiefs;
    private Random random;

    /**
     * Constructor for the theater of war.
     *
     * @param name The name of the theater
     * @param maxPlaces The maximum number of places allowed
     * @param places The list of places in the theater
     * @param chiefs The list of clan chiefs
     */
    public TheaterOfWar(String name, int maxPlaces, List<Place> places, List<ClanChief> chiefs)
    {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = places;
        this.chiefs = chiefs;
        this.random = new Random();
    }

    /**
     * Displays the list of all places in the theater
     */
    public void showPlace()
    {
        System.out.println("== Liste des Lieux ==");
        for (Place place : places)
        {
            place.display();
        }
    }

    /**
     * Calculates and displays the total number of characters present in all places
     */
    public void showNumberOfCharacters()
    {
        int total = 0;
        for (Place place : places)
        {
            total += place.getCharacters().size();
        }
        System.out.println("Le nombre total de personnage est de " + total);
    }

    /**
     * Displays all characters present in each place of the theater
     * For each place, lists the name and type of each character
     */
    public void showCharactersAllPlaces()
    {
        for (Place place : places)
        {
            System.out.println("> Lieu : " + place.getName());
            for (Character character : place.getCharacters())
            {
                System.out.println(">> Personnage : " + character.getName()
                + " (" + character.getPersonType() + ")");
            }
        }
    }

    /**
     * Manages battles on battlefields.
     *   Identifies all Gallic and Roman characters present
     *   Romans randomly attack Romans (deals 10 damage)
     *   Gauls randomly attack Gauls (deals 10 damage)
     *   Characters with 0 or fewer health points are removed
     */
    private void fight()
    {
        for (Place place : places)
        {
            if (place.getPlaceType() != PlacesType.BATTLEFIELD) continue;

            List<Character> characters = place.getCharacters();
            if (characters.isEmpty()) continue;

            List<Character> gaul = new ArrayList<>();
            List<Character> roman = new ArrayList<>();

            for (Character character : characters)
            {
                if (character.getPersonType() == PersonType.gaul)
                {
                    gaul.add(character);
                }
                if (character.getPersonType() == PersonType.roman)
                {
                    roman.add(character);
                }
            }

            for (Character character : roman)
            {
                if (!gaul.isEmpty())
                {
                    Character wanted = roman.get(random.nextInt(roman.size()));
                    wanted.receiveDamage(10);
                }
            }

            for (Character character : gaul)
            {
                if (!roman.isEmpty())
                {
                    Character wanted = gaul.get(random.nextInt(gaul.size()));
                    wanted.receiveDamage(10);
                }
            }

            characters.removeIf(character -> character.getHealth() <= 0);
        }
    }

    /**
     * Changes the state of characters randomly during each simulation cycle
     *   35% chance that a character's hunger increases by 5
     *   20% chance that a character's potion effect evaporates
     * Applies to all characters in all places.
     */
    private void changeStateCharacters()
    {
        for (Place place : places)
        {
            for (Character character : place.getCharacters())
            {
                if (random.nextDouble() < 0.35)
                {
                    character.setHunger(5);
                }

                if (random.nextDouble() < 0.20)
                {
                    character.potionEffectEvaporation();
                }
            }
        }
    }

    /**
     * Randomly spawns food ingredients in places except of the battlefields
     * For each non-battlefield place, there is a 50% chance that a random
     * ingredient will appear
     * Displays a message when food appears
     */
    private void spawnFood()
    {
        for (Place place : places)
        {
            if (place.getPlaceType() == PlacesType.BATTLEFIELD) continue;

            if (random.nextDouble() < 0.5)
            {
                Ingredient ingredient = FoodService.random();
                place.getFoods().add(ingredient);

                System.out.println("Un aliment " + ingredient.getDisplayName()
                        + " est apparu dans " + place.getName());
            }
        }
    }

    /**
     * Ages food ingredients by changing their state from fresh to not fresh
     * Displays a message when an ingredient becomes not fresh
     */
    private void deadFood()
    {
        for (Place place : places)
        {
            for (Ingredient ingredient : place.getFoods())
            {
                if (ingredient.getState() == Ingredient.State.fresh)
                {
                    ingredient.setState(Ingredient.State.notFresh);
                    System.out.println("L'ingrédient " + ingredient.getDisplayName()
                            + " est devenu pas frais dans " + place.getName());
                }
            }
        }
    }

    /**
     * Gives each clan chief their turn to perform actions
     * Creates a menu for each chief and starts their action phase
     */
    private void chiefsClanMoment()
    {
        for (ClanChief chief : chiefs)
        {
            System.out.println("Au tour du chef : " + chief.getName());
            ClanChiefMenu menu = new ClanChiefMenu(chief);
            menu.start();
        }
    }

    /**
     * Starts the main simulation loop of the theater of war
     * The simulation runs indefinitely and executes the following actions in order:
     *   Fights on battlefields
     *   Changes character states (hunger, potion effects)
     *   Spawns new food
     *   Ages existing food
     *   Gives clan chiefs their turn
     * Each cycle is separated by a 1.5 second pause
     */
    public void startSimulation()
    {
        System.out.println("== Début de la simulation : " + name + " ==");

        while (true)
        {
            fight();
            changeStateCharacters();

            spawnFood();
            deadFood();

            chiefsClanMoment();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Problème :(");
                break;
            }
        }
    }
}