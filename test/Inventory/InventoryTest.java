package Inventory;

import characters.inventory.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory<String> inventory;

    /**
     * Initializes a fresh empty inventory before each test.
     */
    @BeforeEach
    void setUp() {
        inventory = new Inventory<>();
    }

    /**
     * Ensures adding an item places it inside the inventory.
     */
    @Test
    void testAddItemSuccess() {
        inventory.addItem("Potion");

        assertEquals(1, inventory.getItems().size(),
                "L’inventaire devrait contenir un seul élément après l’ajout.");
        assertTrue(inventory.getItems().contains("Potion"),
                "L’élément ajouté devrait être présent dans l’inventaire.");
    }

    /**
     * Ensures an item can be removed when it exists.
     */
    @Test
    void testRemoveItemSuccess() {
        inventory.addItem("Potion");

        boolean result = inventory.removeItem("Potion");

        assertTrue(result, "La suppression devrait réussir pour un élément présent.");
        assertEquals(0, inventory.getItems().size(),
                "L’inventaire devrait être vide après la suppression.");
    }

    /**
     * Ensures removeItem returns false when the target item is not in the inventory.
     */
    @Test
    void testRemoveItemFailsIfNotPresent() {
        inventory.addItem("A");

        boolean result = inventory.removeItem("B");

        assertFalse(result, "La suppression doit échouer si l’élément n’existe pas.");
        assertEquals(1, inventory.getItems().size(),
                "L’inventaire ne doit pas être modifié.");
    }

    /**
     * Ensures multiple items can be added successfully.
     */
    @Test
    void testAddMultipleItems() {
        inventory.addItem("A");
        inventory.addItem("B");
        inventory.addItem("C");

        List<String> items = inventory.getItems();

        assertEquals(3, items.size(),
                "L’inventaire devrait contenir trois éléments.");
        assertTrue(items.containsAll(List.of("A", "B", "C")),
                "Tous les éléments ajoutés devraient être présents.");
    }

    /**
     * Ensures removing one occurrence only removes a single instance when duplicates exist.
     */
    @Test
    void testRemoveOnlyOneOccurrence() {
        inventory.addItem("Potion");
        inventory.addItem("Potion");

        boolean result = inventory.removeItem("Potion");

        assertTrue(result, "Une occurrence devrait être supprimée.");
        assertEquals(1, inventory.getItems().size(),
                "Il devrait rester une seule occurrence.");
    }

    /**
     * Ensures getItems does not return null and is accessible.
     */
    @Test
    void testGetItemsNotNull() {
        assertNotNull(inventory.getItems(),
                "La liste des items ne devrait jamais être null.");
    }

    /**
     * Ensures clearing the inventory removes everything.
     */
    @Test
    void testClearInventory() {
        inventory.addItem("A");
        inventory.addItem("B");

        inventory.clear();

        assertEquals(0, inventory.getItems().size(),
                "L’inventaire devrait être vide après l’appel à clear().");
    }
}
