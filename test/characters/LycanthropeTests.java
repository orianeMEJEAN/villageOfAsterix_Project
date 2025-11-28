package characters;

import characters.fantasy.Lycanthrope;
import enums.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LycanthropeTests {

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
                0
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
                0
        );

        wolf1.fight(wolf2);

        assertTrue(wolf2.getHealth() < 100);
    }

    @Test
    void testEatKeepsHungerAtMostInitial() {
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
                0
        );

        wolf.eat(4);

        assertTrue(wolf.getHunger() <= 9);
    }

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
                0
        );

        wolf.drinkPotion(1);

        assertEquals(1, wolf.getMagicPotionLevel());
    }

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
                0
        );

        wolf.fight(wolf);

        assertTrue(wolf.isDead());
    }
}
