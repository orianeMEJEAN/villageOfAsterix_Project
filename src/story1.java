import java.util.*;
// Characters
import characters.Character;
import characters.ClanChief;
import characters.ClanChiefMenu;
import characters.gauls.Druid;
import characters.gauls.Gaul;
import characters.fantasy.Lycanthrope;
import characters.fantasy.FantasyCreature;
import characters.enums.GaulType;
import characters.enums.PersonType;
import characters.enums.Gender;
import characters.inventory.Inventory;
import characters.romans.Legionary;
import characters.romans.Prefect;

// MagicPotion
import magicPotion.Pot;
// Food
import food.enums.Ingredient;

// Place
import places.Place;
import places.enums.PlacesType;

public class story1
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the world of Asterix & Obelix !");

        ClanChief playerChief = createPlayer(scanner);
        Place startingPlace = playerChief.getPlace();

        Place battlefield = new Place("Battlefield", 100, PlacesType.BATTLEFIELD);
        Place village = playerChief.getPlace().getPlaceType() == PlacesType.GAUL_VILLAGE ? startingPlace : new Place("Gaulish Village", 50, PlacesType.GAUL_VILLAGE);
        Place romanCity = playerChief.getPlace().getPlaceType() == PlacesType.ROMAN_CITY ? startingPlace : new Place("Roman Empire", 50, PlacesType.ROMAN_CITY);

        List<Place> places = Arrays.asList(village, battlefield, romanCity);

        Druid panoramix = new Druid("Panoramix", Gender.MALE, 120, 41, 30, 20, 30, 40, 0, 0, GaulType.NONE);
        panoramix.setInventory(new Inventory());

        panoramix.getInventory().addItem(Ingredient.mistletoe);
        panoramix.getInventory().addItem(Ingredient.carrots);
        panoramix.getInventory().addItem(Ingredient.salt);
        panoramix.getInventory().addItem(Ingredient.freshFourLeafClover);
        panoramix.getInventory().addItem(Ingredient.moderatelyFreshFish);
        panoramix.getInventory().addItem(Ingredient.rockOil);
        panoramix.getInventory().addItem(Ingredient.honey);
        panoramix.getInventory().addItem(Ingredient.mead);
        panoramix.getInventory().addItem(Ingredient.secretIngredient);
        panoramix.getInventory().addItem(Ingredient.lobster);
        panoramix.getInventory().addItem(Ingredient.strawberries);
        panoramix.getInventory().addItem(Ingredient.beetrootJuice);
        panoramix.getInventory().addItem(Ingredient.twoHeadedUnicornMilk);
        panoramix.getInventory().addItem(Ingredient.idefixHair);


        Gaul asterix = new Gaul("Asterix", Gender.MALE, 90, 20, 15, 10, 25, 30, 0, 0, GaulType.NONE);
        Gaul obelix = new Gaul("Obelix", Gender.MALE, 170, 25, 20, 15, 30, 35, 0, 0, GaulType.NONE);
        village.addCharacter(panoramix);
        village.addCharacter(asterix);
        village.addCharacter(obelix);

        Legionary legionary1 = new Legionary("Legionary Marcus", Gender.MALE, 160, 15, 10, 5, 20, 25, 0, 0);
        Prefect prefect1 = new Prefect("Prefect Julius", Gender.MALE, 170, 25, 20, 15, 30, 35, 0, 0);
        romanCity.addCharacter(legionary1);
        romanCity.addCharacter(prefect1);

        Lycanthrope werewolf = new Lycanthrope("Fenrir", Gender.MALE, 100, 30, 25, 20, 35, 40, 0, 0, PersonType.fantasy);
        FantasyCreature dragon = new FantasyCreature("Dragon", Gender.MALE, 800, 40, 35, 30, 50, 60, 0, 0, PersonType.fantasy);
        battlefield.addCharacter(werewolf);
        battlefield.addCharacter(dragon);


        boolean inGame = true;

        while (inGame)
        {
            System.out.println("\n------------Game Menu-------------------------");
            System.out.println("1. See current location");
            System.out.println("2. See characters present");
            System.out.println("3. Move to another location");
            System.out.println("4. Interact with the Druid to make a potion");
            System.out.println("5. Clan Chief action menu");
            System.out.println("6. Exit game");
            System.out.print("Select an option : ");

            String choice = scanner.nextLine().trim();

            switch (choice)
            {
                case "1":
                    System.out.println("You are currently at : " + playerChief.getPlace().getName());
                    break;
                case "2":
                    System.out.println("Characters present : ");
                    playerChief.getPlace().getCharacters()
                            .forEach(c -> System.out.println(" - " + c.getName()));
                    break;
                case "3":
                    System.out.println("Choose a location to move to :");
                    for (int i = 0; i < places.size(); i++)
                    {
                        System.out.println((i + 1) + ". " + places.get(i).getName());
                    }
                    try
                    {
                        int moveChoice = Integer.parseInt(scanner.nextLine()) - 1;
                        if (moveChoice >= 0 && moveChoice < places.size())
                        {
                            playerChief.setPlace(places.get(moveChoice));
                            System.out.println("You moved to : " + playerChief.getPlace().getName());
                        }
                        else
                        {
                            System.out.println("Invalid choice.");
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a valid number.");
                    }
                    break;
                case "4":
                    Optional<Character> druidOpt = playerChief.getPlace().getCharacters().stream().filter(c -> c instanceof Druid).findFirst();

                    if (druidOpt.isPresent())
                    {
                        Druid druid = (Druid) druidOpt.get();
                        System.out.println("You are interacting with " + druid.getName() + " to create a potion !");

                        Pot pot = new Pot();

                        boolean addingIngredients = true;
                        while (addingIngredients)
                        {
                            System.out.println("\nSelect an action :");
                            System.out.println("1. Add an ingredient to the potion");
                            System.out.println("2. See druid's inventory");
                            System.out.println("0. Finish potion");

                            System.out.print("Your choice : ");
                            int actionChoice;

                            try
                            {
                                actionChoice = Integer.parseInt(scanner.nextLine().trim());
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Please enter a valid number !");
                                continue;
                            }

                            switch (actionChoice)
                            {
                                case 0:
                                    addingIngredients = false;
                                    break;
                                case 1:
                                    Ingredient[] allIngredients = Ingredient.values();

                                    for (int i = 0; i < allIngredients.length; i++)
                                    {
                                        System.out.println((i + 1) + ". " + allIngredients[i].getDisplayName());
                                    }

                                    System.out.print("Select ingredient : ");
                                    int ingChoice;

                                    try
                                    {
                                        ingChoice = Integer.parseInt(scanner.nextLine().trim());
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        System.out.println("Please enter a valid number !");
                                        continue;
                                    }

                                    if (ingChoice > 0 && ingChoice <= allIngredients.length)
                                    {
                                        Ingredient chosen = allIngredients[ingChoice - 1];
                                        pot.addIngredient(chosen);
                                        System.out.println(chosen.getDisplayName() + " has been added to the potion !");
                                    }
                                    else
                                    {
                                        System.out.println("Invalid choice!");
                                    }
                                    break;
                                case 2:
                                    System.out.println("\nDruid's Inventory:");
                                    if (druid.getInventory() != null && !druid.getInventory().isEmpty())
                                    {
                                        druid.getInventory().getItems().forEach(ing ->
                                                System.out.println(" - " + ing.getClass())
                                        );
                                    }
                                    else
                                    {
                                        System.out.println("The inventory is empty!");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                            }
                        }

                        try
                        {
                            Ingredient ingredient = null;
                            druid.makePotion(ingredient);
                            System.out.println("The potion is ready!");
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Error while making the potion : " + e.getMessage());
                            break;
                        }

                        List<Character> charactersHere = playerChief.getPlace().getCharacters();

                        if (!charactersHere.isEmpty())
                        {
                            System.out.println("\nDo you want to give the potion to a character? \n1 for Yes and 2 for No : ");
                            int giveChoice = Integer.parseInt(scanner.nextLine().trim());

                            if (giveChoice == 1)
                            {
                                System.out.println("Available characters :");

                                for (int i = 0; i < charactersHere.size(); i++)
                                {
                                    System.out.println((i + 1) + ". " + charactersHere.get(i).getName());
                                }

                                System.out.print("Choose a character : ");
                                int charChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;

                                if (charChoice >= 0 && charChoice < charactersHere.size())
                                {
                                    Character target = charactersHere.get(charChoice);
                                    druid.givePotion(target, pot);
                                    System.out.println("The potion has been given to " + target.getName() + " !");
                                }
                                else
                                {
                                    System.out.println("Invalid choice.");
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("There is no druid here to make a potion.");
                    }
                    break;
                case "5":
                    System.out.println("\nOpening Clan Chief Menu...");
                    ClanChiefMenu chiefMenu = new ClanChiefMenu(playerChief);
                    chiefMenu.start();
                    break;
                case "6":
                    System.out.println("Exiting the game. Goodbye !");
                    inGame = false;
                    break;
            }
        }
        scanner.close();
    }

    private static ClanChief createPlayer(Scanner scanner)
    {
        System.out.println("\nWelcome to the world of Asterix & Obelix !");
        System.out.println("\n------------Creating your avatar-------------");
        System.out.println("/!\\ If you restart the game, you will lose your avatar.");

        System.out.print("\nChoose your gender,\nM for male or F for female : ");
        String genderInput = scanner.nextLine().trim().toUpperCase();
        Gender gender = genderInput.equals("M") ? Gender.MALE : Gender.FEMALE;

        System.out.print("\nDo you want to be a Gaul or a Roman?\nG for Gaul and R for Roman : ");
        String sideInput = scanner.nextLine().trim().toUpperCase();

        System.out.print("\nEnter your name : ");
        String playerName = scanner.nextLine().trim();

        Place startingPlace;
        ClanChief playerChief;

        if (sideInput.equals("G"))
        {
            startingPlace = new Place("Gaulish Village", 50, PlacesType.GAUL_VILLAGE);
        }
        else
        {
            startingPlace = new Place("Roman Empire", 50, PlacesType.ROMAN_CITY);
        }

        playerChief = new ClanChief(playerName, gender, 30, startingPlace);
        startingPlace.addCharacter(playerChief);

        System.out.println("\n------------Summary of your information-------------");
        System.out.println("Name : " + playerChief.getName());
        System.out.println("Gender : " + playerChief.getGender());
        System.out.println("Location : " + startingPlace.getName());

        return playerChief;
    }
}