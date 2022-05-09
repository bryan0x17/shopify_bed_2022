package shopify.warehouse.controllers;

import org.springframework.web.bind.annotation.*;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/inventory")
public class ItemsController {

    private final ItemRepository itemRepository;

    public ItemsController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) throws NoSuchElementException {
        return itemRepository.findById(id).orElseThrow();
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

    @DeleteMapping("/{id}")
    public boolean deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return !itemRepository.findById(id).isPresent();
    }
}
