package characters;

import characters.enums.PersonType;
import characters.fantasy.Lycanthrope;
import characters.enums.Gender;
import food.enums.Ingredient;
import magicPotion.Pot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LycanthropeTests {

    /**
     * Creates a valid brewed pot of magic potion,
     * containing all base ingredients so that drinkPotion(Pot) behaves correctly.
     */
    private Pot createBasicFullPot() {
        Pot pot = new Pot();

        Ingredient[] baseIngredients = {
                Ingredient.mistletoe,
                Ingredient.carrots,
                Ingredient.salt,
                Ingredient.freshFourLeafClover,
                Ingredient.moderatelyFreshFish,
                Ingredient.rockOil,
                Ingredient.honey,
                Ingredient.mead,
                Ingredient.secretIngredient
        };

        for (Ingredient ingredient : baseIngredients) {
            assertTrue(pot.addIngredient(ingredient),
                    "Ingredient " + ingredient + " devrait être accepté dans la marmite");
        }

        pot.brewPotion();
        return pot;
    }

    /**
     * Ensures that combat reduces the opponent's health.
     */
    @Test
    void testCombatReducesOpponentHealth() {
        Lycanthrope wolf1 = new Lycanthrope(
                "Lupus",
                Gender.MALE,
                190,
                28,
                16,
                7,
                100,
                5,
                6,
                0,
                PersonType.fantasy
        );

        Lycanthrope wolf2 = new Lycanthrope(
                "Fenrir",
                Gender.MALE,
                200,
                30,
                14,
                6,
                100,
                5,
                6,
                0,
                PersonType.fantasy
        );

        int healthBefore = wolf2.getHealth();

        wolf1.fight(wolf2);

        assertTrue(wolf2.getHealth() < healthBefore,
                "La santé de l’adversaire aurait dû diminuer après le combat");
    }

    /**
     * Ensures eating increases hunger without exceeding maxHunger.
     */
    @Test
    void testEatIncreasesHungerButNotBeyondMax() {
        Lycanthrope wolf = new Lycanthrope(
                "Lupus",
                Gender.MALE,
                190,
                28,
                16,
                7,
                100,
                9,
                6,
                0,
                PersonType.fantasy
        );

        wolf.setHunger(4);
        int maxHunger = wolf.getMaxHunger();

        wolf.eat(10);

        assertEquals(maxHunger, wolf.getHunger(),
                "La satiété ne doit pas dépasser la valeur maximale");
    }

    /**
     * Ensures drinking one potion dose increases magicPotionLevel by 1.
     */
    @Test
    void testDrinkPotionIncreasesMagicLevel() {
        Lycanthrope wolf = new Lycanthrope(
                "Lupus",
                Gender.MALE,
                190,
                28,
                16,
                7,
                100,
                5,
                6,
                0,
                PersonType.fantasy
        );

        Pot pot = createBasicFullPot();

        int before = wolf.getMagicPotionLevel();

        wolf.drinkPotion(pot);

        assertEquals(before + 1, wolf.getMagicPotionLevel(),
                "Boire une dose doit augmenter le niveau de potion magique");
    }

    /**
     * Ensures a lycanthrope dies when its health reaches 0 or less.
     */
    @Test
    void testDeathWhenHealthTooLow() {
        Lycanthrope wolf = new Lycanthrope(
                "Lupus",
                Gender.MALE,
                190,
                28,
                16,
                7,
                1,
                5,
                6,
                0,
                PersonType.fantasy
        );

        wolf.fight(wolf);

        assertTrue(wolf.isDead(), "Le personnage devrait être mort avec 0 PV");
        assertEquals(0, wolf.getHealth(),
                "La vie d’un personnage mort doit être ramenée à 0");
    }
}
