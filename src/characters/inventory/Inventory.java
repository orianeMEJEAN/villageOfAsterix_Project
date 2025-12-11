package characters.inventory;

import food.enums.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory that holds items up to a specified capacity.
 *
 * @param <T> the type of items stored in the inventory
 */
public class Inventory<T> {
    private List<T> items = new ArrayList<>();

    /**
     * Creates an inventory.
     */
    public Inventory() {}

    /**
     * Adds an item to the inventory if there is space available.
     *
     * @param item the item to be added
     * @return {@code true} if the item was added, {@code false} if the inventory is full
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Removes the specified item from the inventory if it exists.
     *
     * @param item the item to be removed
     * @return {@code true} if the item was found and removed, {@code false} otherwise
     */
    public boolean removeItem(T item) {
        return items.remove(item);
    }


    /**
     * Returns the items list
     *
     * @return List<items>
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Set the items list
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * Checks if the specified ingredient is present in the inventory.
     *
     * @param ingredient the ingredient to search for
     * @return {@code true} if the ingredient is found in the inventory, {@code false} otherwise
     */
    public boolean contains(Ingredient ingredient) {
        for (T item : items) {
            if (item.equals(ingredient)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clear all the Inventory's content
     */
    public void clear() {
        items.clear();
    }

    public boolean isEmpty()
    {
        return items.isEmpty();
    }
}
