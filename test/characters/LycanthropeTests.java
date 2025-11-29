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
                null
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
                null
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
                null
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
                null
        );

        wolf.drinkPotion();

        assertEquals(1, wolf.getMagicPotion());
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
                null
        );

        wolf.fight(wolf);

        assertTrue(wolf.isDead());
    }
}
