package Inventory;

import characters.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory<String> inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory<>();
    }

    @Test
    void testAddItemSuccess() {
        inventory.addItem("Potion");
        assertEquals(1, inventory.getItems().size());
    }

    @Test
    void testRemoveItemSuccess() {
        inventory.addItem("Potion");

        boolean result = inventory.removeItem("Potion");

        assertTrue(result);
        assertEquals(0, inventory.getItems().size());
    }

    @Test
    void testRemoveItemFailsIfNotPresent() {
        inventory.addItem("A");

        boolean result = inventory.removeItem("B");

        assertFalse(result);
        assertEquals(1, inventory.getItems().size());
    }
}
