package characters;

import characters.romans.Legionary;
import enums.Gender;
import food.enums.Ingredient;
import magicPotion.Pot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LegionaryTests {

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
                    "L'Ingredient " + ingredient + " devrait être accepté dans la marmite");
        }

        pot.brewPotion();
        return pot;
    }

    /**
     * Ensures that combat reduces the opponent's health value.
     */
    @Test
    void testCombatReducesOpponentHealth() {
        Legionary caesarSoldier = new Legionary(
                "Marcus",
                Gender.MALE,
                175,
                30,
                12,
                6,
                100,
                5,
                4,
                0
        );

        Legionary brutusSoldier = new Legionary(
                "Brutus",
                Gender.MALE,
                180,
                32,
                11,
                5,
                100,
                5,
                4,
                0
        );

        int healthBefore = brutusSoldier.getHealth();

        caesarSoldier.fight(brutusSoldier);

        assertTrue(brutusSoldier.getHealth() < healthBefore,
                "La vie de l'ennemie devrait être plus basse qu'avant");
    }

    /**
     * Ensures eating raises hunger but cannot exceed maxHunger.
     */
    @Test
    void testEatIncreasesHungerButNotBeyondMax() {
        Legionary soldier = new Legionary(
                "Marcus",
                Gender.MALE,
                175,
                30,
                10,
                6,
                100,
                8,
                4,
                0
        );

        soldier.setHunger(5);
        int maxHunger = soldier.getMaxHunger();

        soldier.eat(10);

        assertEquals(maxHunger, soldier.getHunger(),
                "La quantité de nourriture qu'il contient ne devrait pas être plus grande que ce qu'il peut manger");
    }

    /**
     * Ensures drinking one potion dose increases magicPotionLevel by 1.
     */
    @Test
    void testDrinkPotionIncreasesMagicLevel() {
        Legionary soldier = new Legionary(
                "Marcus",
                Gender.MALE,
                175,
                30,
                10,
                6,
                100,
                5,
                4,
                0
        );

        Pot pot = createBasicFullPot();

        int before = soldier.getMagicPotionLevel();

        soldier.drinkPotion(pot);

        assertEquals(before + 1, soldier.getMagicPotionLevel(),
                "Boire une dose de potion magique devrait lui faire monter son niveau de potion magique de 1");
    }

    /**
     * Ensures a soldier dies when his health reaches 0 or below.
     */
    @Test
    void testDeathWhenHealthTooLow() {
        Legionary soldier = new Legionary(
                "Marcus",
                Gender.MALE,
                175,
                30,
                10,
                6,
                1,
                5,
                4,
                0
        );

        soldier.fight(soldier);

        assertTrue(soldier.isDead(), "Le soldat devrait être mort si sa vie est égale ou inférieure à 0");
        assertEquals(0, soldier.getHealth(),
                "La vie des morts devrait être à 0");
    }
}
