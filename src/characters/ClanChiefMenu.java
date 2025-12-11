package characters;

import characters.enums.GaulType;
import characters.enums.Gender;
import characters.enums.PersonType;
import food.enums.Ingredient;
import magicPotion.Pot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import characters.fantasy.Lycanthrope;
import characters.gauls.Druid;
import characters.gauls.Gaul;
import characters.romans.General;
import characters.romans.Legionary;
import characters.romans.Prefect;
import characters.romans.Roman;

import places.Place;
import places.enums.PlacesType;

/**
 * Menu to manage clan chiefs through a menu interface.
 * Allows users to control multiple clan chiefs and perform various actions
 * such as examining places, creating characters, managing potions, etc.
 *
 * @author Lou
 * @subauthor: Oriane
 * @version 1.0
 */
public class ClanChiefMenu {

    private List<ClanChief> clanChiefs = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public ClanChiefMenu(ClanChief clanChief)
    {
        if (clanChief != null) {
            clanChiefs.add(clanChief);
        }
    }


    /**
     * Creates a new controller with an empty list of clan chiefs.
     */
    public void ClanChiefController() {
        this.clanChiefs = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Adds a clan chief to the controller.
     *
     * @param chief the clan chief to add
     */
    public void addClanChief(ClanChief chief) {
        clanChiefs.add(chief);
    }

    /**
     * Starts the main menu loop.
     */
    public void start() {
        while (true) {
            displayMainMenu();
            int choice = readInt("Votre choix: ");

            if (choice == 0) {
                System.out.println("Au revoir !");
                break;
            }

            handleMainMenuChoice(choice);
        }
    }

    /**
     * Displays the main menu.
     */
    private void displayMainMenu() {
        System.out.println("== GESTION DES CHEFS DE CLAN == ");
        System.out.println("1. Sélectionner un chef de clan");
        System.out.println("2. Afficher tous les chefs de clan");
        System.out.println("0. Quitter");
    }

    /**
     * Handles main menu choices.
     *
     * @param choice the user's choice
     */
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1 -> selectClanChief();
            case 2 -> displayAllClanChiefs();
            default -> System.out.println("Choix invalide !");
        }
    }

    /**
     * Allows the user to select a clan chief and manage them.
     */
    private void selectClanChief() {
        if (clanChiefs.isEmpty()) {
            System.out.println("Aucun chef de clan disponible !");
            return;
        }

        System.out.println("\nSÉLECTION DU CHEF DE CLAN");
        for (int i = 0; i < clanChiefs.size(); i++) {
            System.out.println((i + 1) + ". " + clanChiefs.get(i).getName());
        }

        int choice = readInt("Choisissez un chef (1-" + clanChiefs.size() + "): ");

        if (choice < 1 || choice > clanChiefs.size()) {
            System.out.println("Choix invalide !");
            return;
        }

        ClanChief selectedChief = clanChiefs.get(choice - 1);
        manageClanChief(selectedChief);
    }

    /**
     * Displays all clan chiefs.
     */
    private void displayAllClanChiefs() {
        if (clanChiefs.isEmpty()) {
            System.out.println("Aucun chef de clan disponible !");
            return;
        }

        System.out.println("\nLISTE DES CHEFS DE CLAN");
        for (ClanChief chief : clanChiefs) {
            System.out.println(chief);
        }
    }

    /**
     * Manages a specific clan chief through a submenu.
     *
     * @param chief the clan chief to manage
     */
    private void manageClanChief(ClanChief chief) {
        while (true) {
            displayChiefMenu(chief);
            int choice = readInt("Votre choix: ");

            if (choice == 0) {
                break;
            }

            handleChiefMenuChoice(chief, choice);
        }
    }

    /**
     * Displays the menu for managing a specific clan chief.
     *
     * @param chief the clan chief being managed
     */
    private void displayChiefMenu(ClanChief chief) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   CHEF: " + String.format("%-30s", chief.getName()) + " ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Examiner le lieu");
        System.out.println("2. Créer un nouveau personnage");
        System.out.println("3. Soigner les personnages");
        System.out.println("4. Nourrir les personnages");
        System.out.println("5. Demander à un druide de faire une potion");
        System.out.println("6. Faire boire de la potion à un personnage");
        System.out.println("7. Transférer un personnage");
        System.out.println("0. Retour");
    }

    /**
     * Handles choices from the clan chief management menu.
     *
     * @param chief  the clan chief being managed
     * @param choice the user's choice
     */
    private void handleChiefMenuChoice(ClanChief chief, int choice) {
        switch (choice) {
            case 1 -> chief.examinePlace();
            case 2 -> createCharacterMenu(chief);
            case 3 -> chief.healCharactersInPlace();
            case 4 -> chief.feedCharactersInPlace();
            case 5 -> brewPotionMenu(chief);
            case 6 -> givePotionMenu(chief);
            case 7 -> transferCharacterMenu(chief);
            default -> System.out.println("Choix invalide !");
        }
    }

    /**
     * Menu for creating a new character.
     *
     * @param chief the clan chief creating the character
     */
    private void createCharacterMenu(ClanChief chief) {
        System.out.println("\nCRÉATION D'UN PERSONNAGE");

        System.out.print("Nom: ");
        String name = scanner.nextLine();

        System.out.println("Genre (1: MALE, 2: FEMALE): ");
        int genderChoice = readInt("");
        Gender gender = (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;

        int height = readInt("Taille (cm): ");
        int age = readInt("Âge: ");
        int strength = readInt("Force: ");
        int endurance = readInt("Endurance: ");
        int health = readInt("Santé: ");
        int hunger = readInt("Faim: ");
        int belligerence = readInt("Belligérance: ");

        System.out.println("Type de personnage:");
        System.out.println("1. Gaulois");
        System.out.println("2. Romain");
        System.out.println("3. Combattant romain");
        System.out.println("4. Créature fantastique");
        int typeChoice = readInt("Choix: ");

        PersonType type = switch (typeChoice) {
            case 1 -> PersonType.gaul;
            case 2 -> PersonType.roman;
            case 3 -> PersonType.roman_fighter;
            case 4 -> PersonType.fantasy;
            default -> PersonType.gaul;
        };

        Character newCharacter = createCharacterInstance(name, gender, height, age,
                strength, endurance, health,
                hunger, belligerence, type);

        if (newCharacter != null) {
            chief.createCharacter(newCharacter);
        }
    }

    /**
     * Creates a character instance. This is a placeholder method.
     * You'll need to implement this based on your actual Character subclasses.
     */
    private Character createCharacterInstance(String name, Gender gender, int height,
                                              int age, int strength, int endurance,
                                              int health, int hunger, int belligerence,
                                              PersonType type) {
        Character newCharacter = null;

        switch (type) {
            case gaul:
                System.out.println("\nType de Gaulois:");
                System.out.println("1. Druide");
                System.out.println("2. Gaulois normal");
                int gaulChoice = readInt("Choix: ");

                if (gaulChoice == 1) {
                    newCharacter = new Druid(name, gender, height, age, strength,
                            endurance, health, hunger, belligerence, 0, GaulType.DRUID);
                } else {
                    newCharacter = new Gaul(name, gender, height, age, strength,
                            endurance, health, hunger, belligerence, 0, GaulType.DRUID);
                }
                break;

            case roman:
            case roman_fighter:
                // Choisir le sous-type de Romain
                System.out.println("\nType de Romain:");
                System.out.println("1. Général");
                System.out.println("2. Légionnaire");
                System.out.println("3. Préfet");
                System.out.println("4. Romain normal");
                int romanChoice = readInt("Choix: ");

                switch (romanChoice) {
                    case 1:
                        newCharacter = new General(name, gender, height, age, strength,
                                endurance, health, hunger, belligerence, 0);
                        break;
                    case 2:
                        newCharacter = new Legionary(name, gender, height, age, strength,
                                endurance, health, hunger, belligerence, 0);
                        break;
                    case 3:
                        newCharacter = new Prefect(name, gender, height, age, strength,
                                endurance, health, hunger, belligerence, 0);
                        break;
                    default:
                        newCharacter = new Roman(name, gender, height, age, strength,
                                endurance, health, hunger, belligerence, 0, PersonType.roman);
                        break;
                }
                break;

            case fantasy:
                // Choisir le sous-type de créature fantastique
                System.out.println("\nType de créature fantastique:");
                System.out.println("1. Créature fantastique");
                System.out.println("2. Lycanthrope");
                int fantasyChoice = readInt("Choix: ");

                if (fantasyChoice == 2) {
                    newCharacter = new Lycanthrope(name, gender, height, age, strength,
                            endurance, health, hunger, belligerence, 0, type);
                }
                break;

            default:
                System.out.println("Type de personnage non reconnu !");
                break;
        }

        return newCharacter;
    }

    /**
     * Menu for brewing a magic potion.
     *
     * @param chief the clan chief requesting the potion
     */
    private void brewPotionMenu(ClanChief chief) {
        System.out.println("\nFABRICATION DE POTION MAGIQUE");

        System.out.println("Sélectionnez le druide:");

        Pot pot = new Pot();
        List<Ingredient> ingredients = selectIngredients();


        System.out.println("Fonctionnalité à compléter avec la liste des personnages du lieu.");
    }

    /**
     * Menu for selecting ingredients.
     *
     * @return list of selected ingredients
     */
    private List<Ingredient> selectIngredients() {
        List<Ingredient> selected = new ArrayList<>();

        System.out.println("\nSÉLECTION DES INGRÉDIENTS");
        System.out.println("Entrez les ingrédients (0 pour terminer):");

        Ingredient[] allIngredients = Ingredient.values();
        for (int i = 0; i < allIngredients.length; i++) {
            System.out.println((i + 1) + ". " + allIngredients[i].getDisplayName());
        }

        while (true) {
            int choice = readInt("Choix: ");
            if (choice == 0) break;

            if (choice > 0 && choice <= allIngredients.length) {
                selected.add(allIngredients[choice - 1]);
                System.out.println("Ajouté: " + allIngredients[choice - 1].getDisplayName());
            }
        }

        return selected;
    }

    /**
     * Menu for giving potion to a character.
     *
     * @param chief the clan chief giving the potion
     */
    private void givePotionMenu(ClanChief chief) {
        System.out.println("\nDISTRIBUTION DE POTION");
        List<Character> characters = chief.getPlace().getCharacters();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage dans le lieu !");
            return;
        }

        System.out.println("\nPersonnages disponibles :");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.println((i + 1) + ". " + c.getName() +
                    " - Santé: " + c.getHealth() + "/" + c.getMaxHealth() +
                    " - Niveau potion: " + c.getMagicPotionLevel());
        }
        int charChoice = readInt("Choisissez un personnage (1-" + characters.size() + "): ");
        if (charChoice < 1 || charChoice > characters.size()) {
            System.out.println("Choix invalide !");
            return;
        }
        Character selectedChar = characters.get(charChoice - 1);

        System.out.println("\nCombien de doses la marmite contient-elle ?");
        int doses = readInt("Nombre de doses: ");

        if (doses <= 0) {
            System.out.println("Nombre de doses invalide !");
            return;
        }

        Pot pot = new Pot();
        pot.addDose(doses);

        System.out.println("\nLa marmite contient-elle une potion magique ? (1: Oui, 2: Non)");
        int hasPotion = readInt("Choix: ");

        if (hasPotion == 1) {
            List<Ingredient> baseIngredients = List.of(
                    Ingredient.mistletoe,
                    Ingredient.carrots,
                    Ingredient.salt,
                    Ingredient.freshFourLeafClover,
                    Ingredient.moderatelyFreshFish,
                    Ingredient.rockOil,
                    Ingredient.honey,
                    Ingredient.mead,
                    Ingredient.secretIngredient
            );

            for (Ingredient ingredient : baseIngredients) {
                pot.addIngredient(ingredient);
            }
            pot.brewPotion();
        }
        chief.givePotionToCharacter(selectedChar, pot);
    }

    /**
     * Menu for transferring a character.
     *
     * @param chief the clan chief transferring the character
     */
    private void transferCharacterMenu(ClanChief chief) {
        System.out.println("\nTRANSFERT DE PERSONNAGE");System.out.println("\nTRANSFERT DE PERSONNAGE");

       List<Character> characters = chief.getPlace().getCharacters();
        if (characters.isEmpty()) {
            System.out.println("Aucun personnage dans le lieu !");
            return;
        }

        System.out.println("\nPersonnages disponibles :");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.println((i + 1) + ". " + c.getName() +
                    " (" + c.getPersonType() + ")" +
                    " - Santé: " + c.getHealth() + "/" + c.getMaxHealth());
        }

        int charChoice = readInt("Choisissez un personnage (1-" + characters.size() + "): ");
        if (charChoice < 1 || charChoice > characters.size()) {
            System.out.println("Choix invalide !");
            return;
        }
        Character selectedChar = characters.get(charChoice - 1);

        System.out.println("\nOù voulez-vous transférer " + selectedChar.getName() + " ?");
        System.out.println("1. Champ de bataille (BATTLEFIELD)");
        System.out.println("2. Enclos (ENCLOSURE)");
        int destChoice = readInt("Choix: ");

        PlacesType destinationType;
        if (destChoice == 1) {
            destinationType = PlacesType.BATTLEFIELD;
        } else if (destChoice == 2) {
            destinationType = PlacesType.ENCLOSURE;
        } else {
            System.out.println("Choix invalide !");
            return;
        }

        System.out.println("\nVoulez-vous :");
        System.out.println("1. Créer un nouveau lieu");
        System.out.println("2. Utiliser un lieu existant (si disponible)");
        int createChoice = readInt("Choix: ");

        Place destination = null;

        if (createChoice == 1) {
            System.out.print("Nom du lieu: ");
            String placeName = scanner.nextLine();

            double area = readInt("Surface (m²): ");

            destination = createPlace(placeName, area, destinationType);

            if (destination == null) {
                System.out.println("Impossible de créer le lieu !");
                return;
            }

            System.out.println("Lieu '" + placeName + "' créé avec succès !");
        } else {
            System.out.println("Fonctionnalité non implémentée.");
            System.out.println("Veuillez créer un nouveau lieu.");
            return;
        }

        chief.transferCharacter(selectedChar, destination);
    }

    private Place createPlace(String name, double area, PlacesType type) {
        switch (type) {
            case BATTLEFIELD:
               System.out.println("Création d'un champ de bataille...");
                return null;

            case ENCLOSURE:
                System.out.println("Création d'un enclos...");
                return null;

            default:
                System.out.println("Type de lieu non supporté pour le transfert !");
                return null;
        }
    }

    /**
     * Reads an integer from user input.
     *
     * @param prompt the prompt to display
     * @return the integer entered
     */
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide !");
            }
        }
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}