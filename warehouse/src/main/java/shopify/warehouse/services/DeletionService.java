package shopify.warehouse.services;

import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;

import java.util.NoSuchElementException;

public class DeletionService {

    private final ItemRepository itemRepository;

    public DeletionService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item deleteItem(Long id, String deletionComments) throws NoSuchElementException {
        Item deletedItem = this.itemRepository.findById(id).orElseThrow();
        deletedItem.setAmount(0);
        deletedItem.setDeleted(true);
        deletedItem.setDeletionComments(deletionComments);
        return this.itemRepository.save(deletedItem);
    }

    public Item undoDelete(Long id) throws NoSuchElementException {
        Item deletedItem = this.itemRepository.findById(id).orElseThrow();
        deletedItem.setDeleted(false);
        deletedItem.setDeletionComments(null);
        return this.itemRepository.save(deletedItem);
    }
}
