package characters;

import characters.gauls.Gaul;
import enums.GaulType;
import enums.Gender;
import magicPotion.Pot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GaulTests {

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

        asterix.fight(obelix);

        assertTrue(obelix.getHealth() < 100);
    }

    @Test
    void testEatImprovesHunger() {
        Gaul asterix = new Gaul(
                "Asterix",
                Gender.MALE,
                180,
                35,
                10,
                8,
                100,
                10,
                5,
                0,
                GaulType.NONE
        );

        asterix.eat(5);

        assertTrue(asterix.getHunger() == 10);
    }

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

        asterix.drinkPotion();

        assertEquals(3, asterix.getMagicPotionLevel());
    }

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

        assertTrue(asterix.isDead());
    }
}
