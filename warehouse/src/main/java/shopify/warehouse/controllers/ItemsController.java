package shopify.warehouse.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;
import shopify.warehouse.services.DeletionService;
import shopify.warehouse.services.ExampleDataService;

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

    @GetMapping("/new")
    public String getNewItemPage(Model model) {
        return "addNewItem";
    }

    @GetMapping("/edit/{id}")
    public String getItem(@PathVariable Long id, Model model) throws NoSuchElementException {
        model.addAttribute(itemRepository.findByIdAndDeletedFalse(id).orElseThrow());
        return "editItem";
    }

    @GetMapping("/deleted")
    public List<Item> getDeletedItems() {
        return this.itemRepository.findByDeletedTrue();
    }

    @GetMapping("/generate")
    public String generateExampleData() {
        ExampleDataService exs = new ExampleDataService(itemRepository);
        exs.generateData();
        return "redirect:/inventory";
    }

    @PostMapping(value = "/new")
    public String createItem(Item item) {
        itemRepository.save(item);
        return "redirect:/inventory";
    }

    @PutMapping("edit/{id}")
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
