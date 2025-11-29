package magicPotionTest;

import magicPotion.Pot;
import magicPotion.MagicPotion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the Pot class.
 * Tests the correct behavior of the Pot class.
 *
 * @version 2.0
 */
public class PotTests
{
    private MagicPotion magicPotion;

    @Test
    public void emptyAndNoMagicPotion()
    {
        Pot pot = new Pot();

        assertEquals(0, pot.getnbDoses(), "Par défaut le pot doit contenir 0 doses.");
        assertNull(pot.getMagicPotion(), "Par défaut il ne doit pas y avoir de MagicPotion (null).");
    }

    @Test
    public void testConstructorWithCustomDosesAndPotionPositiveValue()
    {
        Pot pot = new Pot();
        pot.addDose(5);

        assertEquals(5, pot.getnbDoses());
        assertSame(magicPotion, pot.getMagicPotion());
    }

    @Test
    public void testConstructorWithCustomDosesAndPotionNegativeValueBecomesZero()
    {
        Pot pot = new Pot();
        pot.addDose(-3);

        assertEquals(0, pot.getnbDoses());
        assertSame(magicPotion, pot.getMagicPotion());
    }

    @Test
    public void testConstructorWithPotionDefaultDosesPerPot()
    {
        Pot pot = new Pot();

        assertEquals(Pot.dosesPerPot, pot.getnbDoses());
        assertSame(magicPotion, pot.getMagicPotion());
    }

    @Test
    public void drinkADoseWhenNotEmpty()
    {
        Pot pot = new Pot();
        pot.addDose(3);
        boolean result = pot.drinkADose();

        assertTrue(result);
        assertEquals(2, pot.getnbDoses());
    }

    @Test
    public void drinkADoseWhenEmpty()
    {
        Pot pot = new Pot();
        boolean result = pot.drinkADose();

        assertFalse(result);
        assertEquals(0, pot.getnbDoses());
    }

    @Test
    public void drinkWholeWhenEnoughDoses()
    {
        Pot pot = new Pot();
        pot.addDose(Pot.dosesPerPot + 4);
        boolean result = pot.drinkWhole();

        assertTrue(result);
        assertEquals(4, pot.getnbDoses());
    }

    @Test
    public void drinkWholeWhenNotEnoughDoses()
    {
        Pot pot = new Pot();
        pot.addDose(Pot.dosesPerPot - 1);
        boolean result = pot.drinkWhole();

        assertFalse(result);
        assertEquals(Pot.dosesPerPot - 1, pot.getnbDoses(), "Aucune dose ne doit être consommée si insuffisant.");
    }

    @Test
    public void refill_SetsToFullCapacity()
    {
        Pot pot = new Pot();

        pot.refill();

        assertEquals(Pot.dosesPerPot, pot.getnbDoses());
    }

    @Test
    public void testHasAtLeastTrue()
    {
        Pot pot = new Pot();
        pot.addDose(10);

        assertTrue(pot.hasAtLeast(5));
        assertTrue(pot.hasAtLeast(10));
        assertFalse(pot.hasAtLeast(11));
    }

    @Test
    public void testHasAtLeastFalse()
    {
        Pot pot = new Pot();
        pot.addDose(3);

        assertFalse(pot.hasAtLeast(4));
        assertFalse(pot.hasAtLeast(10));
    }

    @Test
    public void testIsEmptyTrueWhenZero()
    {
        Pot pot = new Pot();
        pot.addDose(0);

        assertTrue(pot.isEmpty());
    }

    @Test
    public void testIsEmptyFalseWhenPositive()
    {
        Pot pot = new Pot();
        pot.addDose(5);

        assertFalse(pot.isEmpty());
    }

    @Test
    public void getDosesPerPot()
    {
        Pot pot = new Pot();
        assertEquals(Pot.dosesPerPot, pot.getDosesPerPot());
    }

    @Test
    public void canAcceptAndAddIngredient()
    {
        Pot pot = new Pot();
        assertFalse(pot.canAccept(null), "canAccept(null) doit retourner false.");
        assertFalse(pot.addIngredient(null), "addIngredient(null) doit retourner false et ne pas lever d'exception.");
    }

    @Test
    public void brewPotionWithEmptyIngredients()
    {
        Pot pot = new Pot();

        pot.addDose(5);
        assertEquals(5, pot.getnbDoses());

        pot.brewPotion();
        assertEquals(0, pot.getnbDoses(), "Après un échec de brewPotion(), le pot doit être vidé (flipPot).");
        assertNull(pot.getMagicPotion(), "Après un échec de brewPotion(), pas de MagicPotion (null).");
    }
}