package characters;

import characters.romans.Legionary;
import enums.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LegionaryTests {

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
                null
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
                null
        );

        caesarSoldier.fight(brutusSoldier);

        assertTrue(brutusSoldier.getHealth() < 100);
    }

    @Test
    void testEatKeepsHungerAtMostInitial() {
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
                null
        );

        soldier.eat(3);

        assertTrue(soldier.getHunger() <= 8);
    }

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
                null
        );

        soldier.drinkPotion();

        assertEquals(2, soldier.getMagicPotion());
    }

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
                null
        );

        soldier.fight(soldier);

        assertTrue(soldier.isDead());
    }
}
