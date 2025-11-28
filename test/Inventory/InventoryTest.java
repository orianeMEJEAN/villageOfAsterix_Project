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
        inventory = new Inventory<>(3);
    }

    @Test
    void testAddItemSuccess() {
        boolean result = inventory.addItem("Potion");
        assertTrue(result);
        assertEquals(1, inventory.getItems().size());
    }

    @Test
    void testAddItemFailsWhenFull() {
        inventory.addItem("A");
        inventory.addItem("B");
        inventory.addItem("C");

        assertTrue(inventory.isFull());

        boolean result = inventory.addItem("D");
        assertFalse(result);
        assertEquals(3, inventory.getItems().size());
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

    @Test
    void testIsFull() {
        assertFalse(inventory.isFull());

        inventory.addItem("A");
        inventory.addItem("B");
        inventory.addItem("C");

        assertTrue(inventory.isFull());
    }

    @Test
    void testGetCapacity() {
        assertEquals(3, inventory.getCapacity());
    }

    @Test
    void testSetCapacity() {
        inventory.setCapacity(10);
        assertEquals(10, inventory.getCapacity());

        inventory.addItem("A");
        inventory.addItem("B");
        inventory.addItem("C");

        assertEquals(3, inventory.getItems().size());
    }
}
