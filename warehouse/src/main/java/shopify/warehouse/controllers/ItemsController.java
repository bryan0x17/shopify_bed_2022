package shopify.warehouse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;
import shopify.warehouse.services.DeletionService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/inventory")
public class ItemsController {

    private final ItemRepository itemRepository;

    public ItemsController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String getItems(Model model) {
        model.addAttribute(itemRepository.findByDeletedFalse());
        return "listInventory";
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) throws NoSuchElementException {
        return itemRepository.findByIdAndDeletedFalse(id).orElseThrow();
    }

    @GetMapping("/deleted")
    public List<Item> getDeletedItems() {
        return this.itemRepository.findByDeletedTrue();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) throws NoSuchElementException {
        Item currentItem = itemRepository.findById(id).orElseThrow();
        currentItem.setName(item.getName());
        currentItem.setAmount(item.getAmount());
        currentItem.setDescription(item.getDescription());
        return itemRepository.save(currentItem);
    }

    @PutMapping("/deleted/{id}")
    public Item restoreItem(@PathVariable Long id) throws NoSuchElementException {
        DeletionService deletionService = new DeletionService(this.itemRepository);
        return deletionService.undoDelete(id);
    }

    @DeleteMapping("/{id}")
    public Item deleteItem(@PathVariable Long id, @RequestParam String deletionComments) {
        DeletionService deletionService = new DeletionService(this.itemRepository);
        return deletionService.deleteItem(id, deletionComments);
    }
}
