package places.theather;

import characters.Character;
import characters.ClanChief;
import characters.enums.Gender;
import characters.enums.PersonType;
import places.Place;
import places.enums.PlacesType;
import food.enums.Ingredient;
import food.FoodService;
import characters.ClanChief;
import characters.ClanChiefMenu;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TheatherOfWar
{
    private String name;
    private int maxPlaces;
    private List<Place> places;
    private List<ClanChief> chiefs;
    private Random random;

    public TheatherOfWar(String name,  int maxPlaces, List<Place> places, List<ClanChief> chiefs)
    {
        this.name = name;
        this.maxPlaces = maxPlaces;
        this.places = places;
        this.chiefs = chiefs;
    }

    public void showPlace()
    {
        System.out.println("== Liste des Lieux ==");
        for (Place place : places)
        {
            place.display();
        }
    }
    public void showNumberOfCharacters()
    {
        int total = 0;
        for (Place place : places)
        {
            total += place.getCharacters().size();
        }
        System.out.println("Le nombre total de personnage est de " + total);
    }

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
                    // TODO doit diminuer l'effet de la potion sur le personnage
                }
            }
        }
    }

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

    private void chiefsClanMoment()
    {
        for (ClanChief chief : chiefs)
        {
            System.out.println("Au tour du chef : " + chief.getName());
            ClanChiefMenu menu = new ClanChiefMenu(chief);
            menu.start();
        }
    }

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