package shopify.warehouse.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopify.warehouse.models.Item;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemsControllerTest {

    @Autowired
    private ItemsController itemsController;

    private final Item item1 = new Item("Table", "Oak dining table", 10);
    private final Item item2 = new Item("Chair", "Leather reclining chair", 8);


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
        Item deletedItem = this.itemsController.deleteItem(this.item1.getId(), "Deleted");
        assertEquals(0, deletedItem.getAmount());
        assertTrue(deletedItem.isDeleted());
        assertEquals("Deleted", deletedItem.getDeletionComments());
        assertThrows(NoSuchElementException.class, () -> this.itemsController.getItem(deletedItem.getId()));
    }

    @Test
    void getDeletedItems() {
        Item deletedItem1 = this.itemsController.deleteItem(this.item1.getId(), "Deleted");
        Item deletedItem2 = this.itemsController.deleteItem(this.item2.getId(), "Deleted");
        List<Item> deletedItems = this.itemsController.getDeletedItems();
        assertTrue(deletedItem1.getId() == deletedItems.get(0).getId() || deletedItem1.getId() == deletedItems.get(1).getId());
        assertTrue(deletedItem2.getId() == deletedItems.get(0).getId() || deletedItem2.getId() == deletedItems.get(1).getId());
    }

    @Test
    void restoreItem() {
        Item deletedItem = this.itemsController.deleteItem(this.item1.getId(), "Deleted");
        Item restoredItem = this.itemsController.restoreItem(deletedItem.getId());
        assertFalse(restoredItem.isDeleted());
        assertNull(restoredItem.getDeletionComments());
        assertNotNull(this.itemsController.getItem(restoredItem.getId()));
    }
}