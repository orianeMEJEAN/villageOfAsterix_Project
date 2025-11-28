package characters.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an inventory that holds items up to a specified capacity.
 *
 * @param <T> the type of items stored in the inventory
 */
public class Inventory<T> {
    private int capacity;
    private List<T> items = new ArrayList<>();

    /**
     * Creates an inventory with the given maximum capacity.
     *
     * @param capacity the maximum number of items the inventory can hold
     */
    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds an item to the inventory if there is space available.
     *
     * @param item the item to be added
     * @return {@code true} if the item was added, {@code false} if the inventory is full
     */
    public boolean addItem(T item) {
        if (isFull()) {
            return false;
        }
        items.add(item);
        return true;
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
     * Checks if the inventory has reached its maximum capacity.
     *
     * @return {@code true} if the inventory is full, {@code false} otherwise
     */
    public boolean isFull() {
        return items.size() >= capacity;
    }

    /**
     * Returns the maximum capacity of the inventory.
     *
     * @return the inventory capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets a new capacity for the inventory. Items exceeding the new capacity
     * are not automatically removed.
     *
     * @param capacity the new capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
}
