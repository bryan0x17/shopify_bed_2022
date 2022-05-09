package shopify.warehouse.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsControllerTest {

    @Autowired
    private ItemsController itemsController;

    private static final Item item1 = new Item("Table", "Oak dining table", 10);
    private static final Item item2 = new Item("Chair", "Leather reclining chair", 8);


    @BeforeEach
    void setUp() {
        itemsController.createItem(this.item1);
        itemsController.createItem(this.item2);
    }

    @Test
    void getItems() {
        List<Item> items = itemsController.getItems();
        assertEquals(items.get(0).getId(), this.item1.getId());
        assertEquals(items.get(1).getId(), this.item2.getId());
    }

    @Test
    void getItem() {
        assertEquals(this.itemsController.getItem(this.item1.getId()).getId(), this.item1.getId());
        assertEquals(this.itemsController.getItem(this.item2.getId()).getId(), this.item2.getId());
    }

    @Test
    void createItem() {
        assertNotNull(this.itemsController.getItem(this.item1.getId()));
        assertNotNull(this.itemsController.getItem(this.item2.getId()));
    }

    @Test
    void updateItem() {
        this.itemsController.updateItem(this.item1.getId(), new Item("Bookcase", this.item1.getDescription(), this.item1.getAmount()));
        assertEquals("Bookcase", this.itemsController.getItem(this.item1.getId()).getName());
        this.itemsController.updateItem(this.item2.getId(), new Item("Stool", this.item2.getDescription(), this.item2.getAmount()));
        assertEquals("Stool", this.itemsController.getItem(this.item2.getId()).getName());
    }

    @Test
    void deleteItem() {
    }
}