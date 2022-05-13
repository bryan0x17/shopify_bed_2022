package shopify.warehouse.controllers;

import org.springframework.web.bind.annotation.*;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;
import shopify.warehouse.services.DeletionService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller class that handles all requests relating to inventory items (requests sent to /inventory)
 */
@RestController
@RequestMapping("/inventory")
public class ItemsController {

    private final ItemRepository itemRepository;

    public ItemsController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Handles GET requests on the /inventory endpoint. Retrieves a List of items not marked as deleted from the database
     * @return a list of items that have not been marked as deleted
     */
    @GetMapping
    public List<Item> getItems() {
        return itemRepository.findByDeletedFalse();
    }

    /**
     * Handles GET requests on the /inventory endpoint that contain an item ID in the path. Retrieves a single item from the repository using the ID in the path
     * @param id a long representing the ID of the item requested
     * @return an item with the ID matching the id parameter
     */
    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findByIdAndDeletedFalse(id).orElseThrow();
    }

    /**
     * Handles GET requests on the /inventory/deleted endpoint. Retrieves all inventory items that have been marked as deleted
     * @return a list of items that have been marked as deleted
     */
    @GetMapping("/deleted")
    public List<Item> getDeletedItems() {
        return this.itemRepository.findByDeletedTrue();
    }

    /**
     * Handles POST requests on the /inventory endpoint. Parses the request body for an item and persists it to the repository
     * @param item the new item that must be persisted to the database
     * @return the persisted item
     */
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    /**
     * Handles PUT requests on the /inventory endpoint that contain an item id in the path. Updates the item matching the ID and persists it to the repository
     * @param id the item ID of the item being updated
     * @param item the item whose attributes will be used to update repository
     * @return the updated item
     * @throws NoSuchElementException if there is no element in the repository matching the ID
     */
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) throws NoSuchElementException {
        Item currentItem = itemRepository.findById(id).orElseThrow();
        currentItem.setName(item.getName());
        currentItem.setAmount(item.getAmount());
        currentItem.setDescription(item.getDescription());
        return itemRepository.save(currentItem);
    }

    /**
     * Handles PUT requests to the /inventory/deleted endpoint that contain an item ID in the path.
     * Uses a deletion service to mark the item matching the ID as not deleted
     * @param id the ID of the item that must be marked as not deleted
     * @return the item that was marked as not deleted
     * @throws NoSuchElementException if there is no element in the repository matching the ID
     */
    @PutMapping("/deleted/{id}")
    public Item restoreItem(@PathVariable Long id) throws NoSuchElementException {
        DeletionService deletionService = new DeletionService(this.itemRepository);
        return deletionService.undoDelete(id);
    }

    /**
     * Handles DELETE requests to the /inventory endpoint that contain an item ID in the path.
     * Uses a deletion services to mark the item matching the ID as deleted
     * @param id the ID of the item to be marked as deleted
     * @param item the item containing the deletion comments that must be persisted to the database
     * @return the item marked as deleted
     */
    @DeleteMapping("/{id}")
    public Item deleteItem(@PathVariable Long id, @RequestBody Item item) {
        DeletionService deletionService = new DeletionService(this.itemRepository);
        return deletionService.deleteItem(id, item.getDeletionComments());
    }
}
