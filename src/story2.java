import characters.ClanChief;
import characters.ClanChiefMenu;
import characters.Character;
import characters.enums.GaulType;
import characters.enums.Gender;
import characters.enums.PersonType;
import characters.gauls.Druid;
import characters.gauls.Gaul;
import food.enums.Ingredient;
import magicPotion.Pot;
import places.Place;
import places.enums.PlacesType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Entry point for the turn-based clan chief simulation.
 * <p>
 * The simulation creates a Gaul clan chief in his village, a global theatre of war
 * and runs a turn-by-turn loop where the player can manage inhabitants, send
 * warriors to battle and observe immigration of war refugees.
 * Every {@value #AGING_INTERVAL_TURNS} turns, all characters grow one year older.
 * The druid can be sent to a special ingredient place and asked to brew different
 * types of magic potions chosen by the player.
 */
public class story2 {

    private static final int AGING_INTERVAL_TURNS = 10;
    private static final int INITIAL_GAULS = 60;
    private static final Random RANDOM = new Random();

    private static Druid druid;
    private static Place ingredientsPlace;
    private static Pot villagePot = new Pot();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printWelcome();

        Place homeVillage = createHomeVillage();
        ClanChief chief = createClanChief(scanner, homeVillage);
        homeVillage.setLeader(chief);

        populateVillageWithGauls(homeVillage);

        Place theatreOfWar = createTheatreOfWar();
        ingredientsPlace = createIngredientsPlace();

        ClanChiefMenu chiefMenu = new ClanChiefMenu(chief);

        int turn = 1;
        boolean running = true;

        while (running) {
            displayTurnHeader(turn, chief, homeVillage, theatreOfWar);

            printMainMenu();
            int choice = readInt(scanner, "Ton choix : ");

            switch (choice) {
                case 1 -> chiefMenu.start();
                case 2 -> displayInhabitantsSummary(homeVillage);
                case 3 -> sendInhabitantToTheatreOfWar(scanner, chief, theatreOfWar);
                case 4 -> displayTheatreOfWarSummary(theatreOfWar);
                case 5 -> manageDruidAndPotions(scanner, chief);
                case 6 -> {
                    applyEndOfTurn(turn, chief, homeVillage, theatreOfWar);
                    turn++;
                }
                case 0 -> {
                    running = false;
                    System.out.println("Fin de la simulation. À bientôt.");
                }
                default -> System.out.println("Choix invalide, essaie encore.");
            }

            if (chief.isDead()) {
                System.out.println();
                System.out.println("Ton chef de clan est mort. La simulation s'arrête.");
                running = false;
            }
        }

        scanner.close();
    }

    /**
     * Prints the initial banner of the simulation.
     */
    private static void printWelcome() {
        System.out.println("====================================");
        System.out.println("      SIMULATION - CHEF DE CLAN");
        System.out.println("====================================");
        System.out.println();
    }

    /**
     * Creates the clan chief. The user chooses the name, the gender and the age.
     * The starting place is always the Gaul village.
     *
     * @param scanner     input source
     * @param homeVillage starting village
     * @return the created clan chief
     */
    private static ClanChief createClanChief(Scanner scanner, Place homeVillage) {
        System.out.println("Création du chef de clan");
        System.out.println("------------------------");

        System.out.print("Entre le nom de ton chef de clan : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Chef anonyme";
        }

        System.out.println();
        System.out.println("Choisis le genre :");
        System.out.println("1. Masculin");
        System.out.println("2. Féminin");
        int genderChoice = readInt(scanner, "Ton choix : ");
        Gender gender = (genderChoice == 2) ? Gender.FEMALE : Gender.MALE;

        int age = readInt(scanner, "\nÂge du chef (en années) : ");

        ClanChief chief = new ClanChief(name, gender, age, homeVillage);

        System.out.println();
        System.out.println("Chef de clan créé : " + chief.getName() +
                " (" + chief.getAge() + " ans) dans " + homeVillage.getName());

        return chief;
    }

    /**
     * Creates the starting Gaul village. The clan chief always begins here.
     *
     * @return the created village
     */
    private static Place createHomeVillage() {
        String name = "Village des irréductibles";
        double area = 1000.0;
        return new Place(name, area, PlacesType.GAUL_VILLAGE);
    }

    /**
     * Creates the special ingredient place that contains all possible ingredients.
     *
     * @return the ingredient place
     */
    private static Place createIngredientsPlace() {
        EnumSet<Ingredient> all = EnumSet.allOf(Ingredient.class);
        Place place = new Place("Forêt sacrée du druide", 2000.0, PlacesType.BATTLEFIELD, all);
        System.out.println("Un lieu spécial a été créé pour le druide : " + place.getName());
        return place;
    }

    /**
     * Populates the village with {@link #INITIAL_GAULS} inhabitants including exactly one druid.
     *
     * @param village the clan chief village
     */
    private static void populateVillageWithGauls(Place village) {
        druid = new Druid(
                "Panoramix",
                Gender.MALE,
                170,
                60,
                8,
                7,
                100,
                60,
                5,
                0,
                GaulType.DRUID
        );
        village.addCharacter(druid);

        int remaining = INITIAL_GAULS - 1;
        List<Character> others = new ArrayList<>();

        for (int i = 0; i < remaining; i++) {
            String name = "Gaulois " + (i + 1);
            Gender gender = RANDOM.nextBoolean() ? Gender.MALE : Gender.FEMALE;
            int height = 160 + RANDOM.nextInt(31);
            int age = 18 + RANDOM.nextInt(30);
            int strength = 5 + RANDOM.nextInt(6);
            int endurance = 5 + RANDOM.nextInt(6);
            int health = 100;
            int hunger = 50;
            int belligerence = 3 + RANDOM.nextInt(4);
            int magicPotionLevel = 0;

            Gaul gaul = new Gaul(
                    name,
                    gender,
                    height,
                    age,
                    strength,
                    endurance,
                    health,
                    hunger,
                    belligerence,
                    magicPotionLevel,
                    GaulType.MERCHANT
            );
            others.add(gaul);
        }

        village.addCharacterAll(others);

        System.out.println();
        System.out.println("Ton village commence avec " + village.getCharacters().size() +
                " habitants gaulois, dont un druide.");
    }

    /**
     * Creates the global battlefield used as theatre of war.
     *
     * @return the battlefield place
     */
    private static Place createTheatreOfWar() {
        double area = 5000.0;
        return new Place("Théâtre de guerre", area, PlacesType.BATTLEFIELD);
    }

    /**
     * Displays the header for a given turn with a quick summary.
     *
     * @param turn         current turn number
     * @param chief        clan chief
     * @param village      clan chief village
     * @param theatreOfWar global battlefield
     */
    private static void displayTurnHeader(int turn, ClanChief chief, Place village, Place theatreOfWar) {
        System.out.println();
        System.out.println("====================================");
        System.out.println("              TOUR " + turn);
        System.out.println("====================================");

        int inhabitants = village.getCharacters().size();

        System.out.println("Chef : " + chief.getName() + " (" + chief.getAge() + " ans)");
        System.out.println("Lieu : " + village.getName() + " - Habitants : " + inhabitants);

        int gauls = 0;
        int romans = 0;

        for (Character c : theatreOfWar.getCharacters()) {
            PersonType type = c.getPersonType();
            if (type == PersonType.gaul || type == PersonType.fantasy) {
                gauls++;
            } else if (type == PersonType.roman || type == PersonType.roman_fighter) {
                romans++;
            }
        }

        System.out.println("Théâtre de guerre : " + gauls + " Gaulois/Fantastiques vs " + romans + " Romains.");
    }

    /**
     * Prints the main player menu.
     */
    private static void printMainMenu() {
        System.out.println();
        System.out.println("Que veux-tu faire ?");
        System.out.println("1. Gérer le chef et les habitants (menu détaillé)");
        System.out.println("2. Voir les habitants du village");
        System.out.println("3. Envoyer un habitant au théâtre de guerre");
        System.out.println("4. Voir l'état du théâtre de guerre");
        System.out.println("5. Gérer le druide et les potions");
        System.out.println("6. Passer le tour (guerre, immigration, vieillissement éventuel)");
        System.out.println("0. Quitter la simulation");
    }

    /**
     * Displays a summary of the inhabitants currently living in the village.
     *
     * @param village clan chief village
     */
    private static void displayInhabitantsSummary(Place village) {
        List<Character> characters = village.getCharacters();

        System.out.println();
        System.out.println("Habitants de " + village.getName() + " :");

        if (characters.isEmpty()) {
            System.out.println("Aucun habitant pour le moment.");
            return;
        }

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.println((i + 1) + ". " + c.getName()
                    + " - Type : " + c.getPersonType()
                    + " - Âge : " + c.getAge()
                    + " - Santé : " + c.getHealth() + "/" + c.getMaxHealth());
        }
    }

    /**
     * Displays a summary of the current state of the theatre of war.
     *
     * @param theatreOfWar global battlefield
     */
    private static void displayTheatreOfWarSummary(Place theatreOfWar) {
        List<Character> characters = theatreOfWar.getCharacters();

        System.out.println();
        System.out.println("=== THÉÂTRE DE GUERRE ===");
        if (characters.isEmpty()) {
            System.out.println("Aucun combattant pour l'instant.");
            return;
        }

        int gauls = 0;
        int romans = 0;

        for (Character c : characters) {
            PersonType type = c.getPersonType();
            if (type == PersonType.gaul || type == PersonType.fantasy) {
                gauls++;
            } else if (type == PersonType.roman || type == PersonType.roman_fighter) {
                romans++;
            }
        }

        System.out.println("Total combattants : " + characters.size());
        System.out.println(" - Côté Gaulois/Fantastiques : " + gauls);
        System.out.println(" - Côté Romains : " + romans);

        System.out.println();
        System.out.println("Détail des combattants :");
        for (Character c : characters) {
            System.out.println(" * " + c.getName()
                    + " - Type : " + c.getPersonType()
                    + " - Santé : " + c.getHealth() + "/" + c.getMaxHealth());
        }
    }

    /**
     * Sends one inhabitant from the village to the theatre of war.
     *
     * @param scanner      input source
     * @param chief        clan chief
     * @param theatreOfWar global battlefield
     */
    private static void sendInhabitantToTheatreOfWar(Scanner scanner,
                                                     ClanChief chief,
                                                     Place theatreOfWar) {
        Place village = chief.getPlace();
        List<Character> characters = village.getCharacters();

        if (characters.isEmpty()) {
            System.out.println();
            System.out.println("Aucun habitant à envoyer au théâtre de guerre.");
            return;
        }

        System.out.println();
        System.out.println("Quel habitant envoyer au théâtre de guerre ?");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.println((i + 1) + ". " + c.getName()
                    + " - Type : " + c.getPersonType()
                    + " - Santé : " + c.getHealth() + "/" + c.getMaxHealth());
        }

        int choice = readInt(scanner, "Ton choix (0 pour annuler) : ");

        if (choice == 0) {
            System.out.println("Transfert annulé.");
            return;
        }

        if (choice < 1 || choice > characters.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        Character selected = characters.get(choice - 1);
        boolean success = chief.transferCharacter(selected, theatreOfWar);

        if (success) {
            System.out.println(selected.getName() + " rejoint le théâtre de guerre !");
        }
    }

    /**
     * Manages druid-related actions and potion creation.
     *
     * @param scanner input source
     * @param chief   clan chief
     */
    private static void manageDruidAndPotions(Scanner scanner, ClanChief chief) {
        if (druid == null) {
            System.out.println("Aucun druide n'est disponible dans cette partie.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("=== GESTION DU DRUIDE ET DES POTIONS ===");
            System.out.println("1. Envoyer le druide dans le lieu des ingrédients");
            System.out.println("2. Rappeler le druide au village");
            System.out.println("3. Préparer une potion magique");
            System.out.println("4. Voir l'état de la marmite");
            System.out.println("0. Retour");
            int choice = readInt(scanner, "Ton choix : ");

            switch (choice) {
                case 1 -> sendDruidToIngredientsPlace(chief);
                case 2 -> bringDruidBackToVillage(chief);
                case 3 -> prepareMagicPotion(scanner, chief);
                case 4 -> showPotStatus();
                case 0 -> back = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Sends the druid from the village to the ingredient place.
     *
     * @param chief clan chief
     */
    private static void sendDruidToIngredientsPlace(ClanChief chief) {
        Place village = chief.getPlace();

        if (village.getCharacters().contains(druid)) {
            boolean success = chief.transferCharacter(druid, ingredientsPlace);
            if (success) {
                System.out.println("Le druide part dans la forêt sacrée récupérer tous les ingrédients.");
            }
        } else if (ingredientsPlace.getCharacters().contains(druid)) {
            System.out.println("Le druide est déjà dans le lieu des ingrédients.");
        } else {
            System.out.println("Le druide est introuvable.");
        }
    }

    /**
     * Brings the druid back from the ingredient place to the village.
     *
     * @param chief clan chief
     */
    private static void bringDruidBackToVillage(ClanChief chief) {
        Place village = chief.getPlace();

        if (ingredientsPlace.getCharacters().contains(druid)) {
            ingredientsPlace.removeCharacter(druid);
            village.addCharacter(druid);
            System.out.println("Le druide revient au village avec tout ce qu'il lui faut pour préparer une potion.");
        } else if (village.getCharacters().contains(druid)) {
            System.out.println("Le druide est déjà au village.");
        } else {
            System.out.println("Le druide est introuvable.");
        }
    }

    /**
     * Asks the druid to brew a magic potion of a type chosen by the player.
     *
     * @param scanner input source
     * @param chief   clan chief
     */
    private static void prepareMagicPotion(Scanner scanner, ClanChief chief) {
        Place village = chief.getPlace();

        if (!village.getCharacters().contains(druid)) {
            System.out.println("Le druide n'est pas dans le village. Rappelle-le d'abord.");
            return;
        }

        System.out.println();
        System.out.println("CRÉATION DE POTION MAGIQUE");
        System.out.println("Quel type de potion veux-tu ?");
        System.out.println("1. Potion classique");
        System.out.println("2. Potion nourrissante");
        System.out.println("3. Potion spéciale au lait de licorne");
        System.out.println("4. Potion spéciale aux poils d'Idéfix");

        int typeChoice = readInt(scanner, "Ton choix : ");

        List<Ingredient> ingredients = buildIngredientsForPotionType(typeChoice);
        if (ingredients == null) {
            System.out.println("Type de potion inconnu, la préparation est annulée.");
            return;
        }

        villagePot.flipPot();

        boolean ok = chief.askDruidToBrewPotion(druid, villagePot, ingredients);
        if (!ok) {
            System.out.println("La potion n'a pas pu être préparée.");
        }
    }

    /**
     * Builds the ingredient list for a given potion type choice.
     *
     * @param typeChoice player choice
     * @return ingredient list or {@code null} if the choice is invalid
     */
    private static List<Ingredient> buildIngredientsForPotionType(int typeChoice) {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(Ingredient.mistletoe);
        ingredients.add(Ingredient.carrots);
        ingredients.add(Ingredient.salt);
        ingredients.add(Ingredient.freshFourLeafClover);
        ingredients.add(Ingredient.moderatelyFreshFish);
        ingredients.add(Ingredient.rockOil);
        ingredients.add(Ingredient.honey);
        ingredients.add(Ingredient.mead);
        ingredients.add(Ingredient.secretIngredient);

        switch (typeChoice) {
            case 1:
                break;
            case 2:
                ingredients.add(Ingredient.strawberries);
                ingredients.add(Ingredient.beetrootJuice);
                break;
            case 3:
                ingredients.add(Ingredient.twoHeadedUnicornMilk);
                break;
            case 4:
                ingredients.add(Ingredient.idefixHair);
                break;
            default:
                return null;
        }

        return ingredients;
    }

    /**
     * Displays a short status of the shared pot.
     */
    private static void showPotStatus() {
        System.out.println();
        System.out.println("ÉTAT DE LA MARMITE");
        if (villagePot.getMagicPotion() == null || villagePot.isEmpty()) {
            System.out.println("La marmite est vide pour le moment.");
        } else {
            System.out.println("La marmite contient une potion magique prête à être distribuée.");
        }
    }

    /**
     * Applies end-of-turn logic: potion evaporation, aging, battle resolution and immigration.
     *
     * @param turn         current turn
     * @param chief        clan chief
     * @param village      clan chief village
     * @param theatreOfWar global battlefield
     */
    private static void applyEndOfTurn(int turn,
                                       ClanChief chief,
                                       Place village,
                                       Place theatreOfWar) {
        System.out.println();
        System.out.println("--- Fin du tour " + turn + " ---");

        chief.potionEffectEvaporation();
        applyPotionEvaporationToPlace(village);
        applyPotionEvaporationToPlace(theatreOfWar);

        if (turn % AGING_INTERVAL_TURNS == 0) {
            ageCharacters(chief, village, theatreOfWar);
        }

        simulateTheatreOfWar(theatreOfWar);

        handleImmigration(village, theatreOfWar);
    }

    /**
     * Applies potion effect evaporation to all characters present in a place.
     *
     * @param place target place
     */
    private static void applyPotionEvaporationToPlace(Place place) {
        for (Character c : place.getCharacters()) {
            c.potionEffectEvaporation();
        }
    }

    /**
     * Ages the chief and all characters on the map by one year.
     *
     * @param chief        clan chief
     * @param village      clan chief village
     * @param theatreOfWar global battlefield
     */
    private static void ageCharacters(ClanChief chief, Place village, Place theatreOfWar) {
        System.out.println();
        System.out.println("Une année s'est écoulée : tout le monde vieillit d'un an.");

        chief.setAge(chief.getAge() + 1);

        for (Character c : village.getCharacters()) {
            c.setAge(c.getAge() + 1);
        }

        for (Character c : theatreOfWar.getCharacters()) {
            c.setAge(c.getAge() + 1);
        }
    }

    /**
     * Simulates a simple battle on the theatre of war where one Gaul/Fantasy
     * character fights one Roman chosen at random.
     *
     * @param theatreOfWar global battlefield
     */
    private static void simulateTheatreOfWar(Place theatreOfWar) {
        List<Character> snapshot = new ArrayList<>(theatreOfWar.getCharacters());

        if (snapshot.size() < 2) {
            System.out.println("Pas assez de combattants pour une bataille ce tour-ci.");
            return;
        }

        List<Character> gauls = new ArrayList<>();
        List<Character> romans = new ArrayList<>();

        for (Character c : snapshot) {
            PersonType type = c.getPersonType();
            if (type == PersonType.gaul || type == PersonType.fantasy) {
                gauls.add(c);
            } else if (type == PersonType.roman || type == PersonType.roman_fighter) {
                romans.add(c);
            }
        }

        if (gauls.isEmpty() || romans.isEmpty()) {
            System.out.println("Le théâtre de guerre est calme : un seul camp est présent.");
            return;
        }

        Character gaul = gauls.get(RANDOM.nextInt(gauls.size()));
        Character roman = romans.get(RANDOM.nextInt(romans.size()));

        System.out.println();
        System.out.println("Sur le théâtre de guerre, " + gaul.getName() + " affronte " + roman.getName() + " !");

        gaul.fight(roman);
        if (roman.isDead()) {
            theatreOfWar.removeCharacter(roman);
            System.out.println(roman.getName() + " est tombé au combat.");
            return;
        }

        roman.fight(gaul);
        if (gaul.isDead()) {
            theatreOfWar.removeCharacter(gaul);
            System.out.println(gaul.getName() + " est tombé au combat.");
        }
    }

    /**
     * Handles immigration: each turn, some war refugees can arrive and settle in the Gaul village.
     * Refugees are chosen among surviving Gaul or fantasy characters from the theatre of war.
     *
     * @param village      clan chief village
     * @param theatreOfWar global battlefield
     */
    private static void handleImmigration(Place village, Place theatreOfWar) {
        List<Character> warChars = new ArrayList<>(theatreOfWar.getCharacters());
        List<Character> candidates = new ArrayList<>();

        for (Character c : warChars) {
            PersonType type = c.getPersonType();
            if (type == PersonType.gaul || type == PersonType.fantasy) {
                candidates.add(c);
            }
        }

        if (candidates.isEmpty()) {
            System.out.println("Aucun réfugié n'arrive dans le village ce tour-ci.");
            return;
        }

        int maxArrivals = Math.min(3, candidates.size());
        int refugeesCount = RANDOM.nextInt(maxArrivals + 1);

        if (refugeesCount == 0) {
            System.out.println("Les combats font rage, mais aucun réfugié ne parvient jusqu'au village ce tour-ci.");
            return;
        }

        for (int i = 0; i < refugeesCount; i++) {
            int index = RANDOM.nextInt(candidates.size());
            Character refugee = candidates.remove(index);
            theatreOfWar.removeCharacter(refugee);
            village.addCharacter(refugee);
        }

        System.out.println(refugeesCount + " réfugié(s) de la bataille se sont installés dans le village.");
    }

    /**
     * Reads an integer from the standard input, looping until a valid value is entered.
     *
     * @param scanner input source
     * @param prompt  text displayed to the user
     * @return the parsed integer
     */
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Merci d'entrer un nombre entier valide.");
            }
        }
    }
}
