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
import characters.romans.Roman;
import characters.romans.General;
import characters.romans.Legionary;
import characters.romans.Prefect;

// MagicPotion
import magicPotion.MagicPotion;
import magicPotion.Pot;
// Food
import food.FoodService;
import food.HealthResult;
import food.enums.HealthIssue;
import food.enums.Ingredient;

// Place
import places.Place;
import places.enums.PlacesType;
import places.theather.TheaterOfWar;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=================Before Starting==================");
        System.out.println();
        System.out.println("Choisis la version de la simulation à lancer : ");
        System.out.println("1. Story 1 (simplified, playful version)");
        System.out.println("2. Story 2 (detailed version with village and theater of war)");

        int choice = 0;
        while (choice != 1 && choice != 2)
        {
            System.out.print("Ton choix : ");
            String input = scanner.nextLine();

            try
            {
                choice = Integer.parseInt(input.trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Merci d'entrer 1 ou 2.");
                continue;
            }

            if (choice != 1 && choice != 2)
            {
                System.out.println("Choix invalide, merci de choisir 1 ou 2.");
            }
        }

        switch (choice)
        {
            case 1 -> story1.main(new String[]{});
            case 2 -> story2.main(new String[]{});
        }

        scanner.close();
    }
}