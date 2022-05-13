package shopify.warehouse.services;

import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;

import java.util.NoSuchElementException;

/**
 * Service class that provides the functionality of marking items and deleted or not deleted
 */
public class DeletionService {

    private final ItemRepository itemRepository;

    public DeletionService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Marks an item as deleted in the repository
     * @param id the ID of the item that must be marked as deleted
     * @param deletionComments the deletion comments that must be added to the item
     * @return the deleted item
     * @throws NoSuchElementException if there is no element in the repository matching the given ID
     */
    public Item deleteItem(Long id, String deletionComments) throws NoSuchElementException {
        Item deletedItem = this.itemRepository.findById(id).orElseThrow();
        deletedItem.setAmount(0);
        deletedItem.setDeleted(true);
        deletedItem.setDeletionComments(deletionComments);
        return this.itemRepository.save(deletedItem);
    }

    /**
     * Restores an item by marking it as not deleted
     * @param id the item ID of the item that must be restored
     * @return the restored item
     * @throws NoSuchElementException if there is no element in the repository matching the given ID
     */
    public Item undoDelete(Long id) throws NoSuchElementException {
        Item deletedItem = this.itemRepository.findById(id).orElseThrow();
        deletedItem.setDeleted(false);
        deletedItem.setDeletionComments(null);
        return this.itemRepository.save(deletedItem);
    }
}
