package characters;

import characters.gauls.Gaul;
import enums.GaulType;
import enums.Gender;
import food.enums.Ingredient;
import magicPotion.Pot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GaulTests {

    /**
     * Creates a "valid" and full pot of magic potion,
     * with all required base ingredients and no special optional ones.
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
                    "L’ingrédient " + ingredient + " n’a pas pu être ajouté à la marmite.");
        }

        pot.brewPotion();
        return pot;
    }

    /**
     * Ensures that combat reduces the opponent's health.
     */
    @Test
    void testCombatReducesOpponentHealth() {
        Gaul asterix = new Gaul(
                "Asterix",
                Gender.MALE,
                180,
                35,
                15,
                5,
                100,
                5,
                3,
                0,
                GaulType.NONE
        );

        Gaul obelix = new Gaul(
                "Obelix",
                Gender.MALE,
                200,
                40,
                18,
                7,
                100,
                5,
                3,
                0,
                GaulType.NONE
        );

        int healthBefore = obelix.getHealth();

        asterix.fight(obelix);

        assertTrue(obelix.getHealth() < healthBefore,
                "La santé de l’adversaire aurait dû diminuer après le combat.");
    }

    /**
     * Ensures eating increases hunger but never exceeds maxHunger.
     */
    @Test
    void testEatIncreasesHungerButNotBeyondMax() {
        Gaul asterix = new Gaul(
                "Asterix",
                Gender.MALE,
                180,
                35,
                10,
                8,
                100,
                10,
                10,
                0,
                GaulType.NONE
        );

        asterix.setHunger(5);
        int maxHunger = asterix.getMaxHunger();

        asterix.eat(3);

        assertEquals(8, asterix.getHunger(),
                "Manger doit augmenter le niveau de satiété (hunger).");
        assertTrue(asterix.getHunger() <= maxHunger,
                "Le niveau de satiété ne doit jamais dépasser maxHunger.");
    }

    /**
     * Ensures drinking a potion increases magicPotionLevel by 1.
     */
    @Test
    void testDrinkPotionIncreasesMagicLevel() {
        Gaul asterix = new Gaul(
                "Asterix",
                Gender.MALE,
                180,
                35,
                10,
                8,
                100,
                5,
                5,
                0,
                GaulType.NONE
        );

        Pot pot = createBasicFullPot();

        int before = asterix.getMagicPotionLevel();

        asterix.drinkPotion(pot);

        assertEquals(before + 1, asterix.getMagicPotionLevel(),
                "Boire une dose de potion doit augmenter magicPotionLevel de 1.");
    }

    /**
     * Ensures that a character dies when its health falls to 0 or below.
     */
    @Test
    void testDeathWhenHealthTooLow() {
        Gaul asterix = new Gaul(
                "Asterix",
                Gender.MALE,
                180,
                35,
                10,
                8,
                1,
                5,
                5,
                0,
                GaulType.NONE
        );

        asterix.fight(asterix);

        assertTrue(asterix.isDead(),
                "Le personnage devrait être mort lorsque sa vie tombe à 0 ou moins.");
        assertEquals(0, asterix.getHealth(),
                "La santé d’un personnage mort doit être 0.");
    }
}
