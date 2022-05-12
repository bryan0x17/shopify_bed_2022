package shopify.warehouse.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopify.warehouse.controllers.ItemsController;
import shopify.warehouse.models.Item;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeletionServiceTest {

    @Autowired
    private ItemsController itemsController;
    private final Item item1 = new Item("Table", "Oak dining table", 10);
    private final Item item2 = new Item("Chair", "Leather reclining chair", 8);

    @BeforeEach
    void setUp() {
        this.itemsController.createItem(item1);
        this.itemsController.createItem(item2);
    }

    @Test
    void deleteItem() {
        Item item = new Item("Deleted");
        Item deletedItem = this.itemsController.deleteItem(item1.getId(), item);
        assertTrue(deletedItem.isDeleted());
        assertEquals("Deleted", deletedItem.getDeletionComments());
        assertEquals(0, deletedItem.getAmount());
    }

    @Test
    void undoDelete() {
        Item item = new Item("Deleted");
        Item deletedItem = this.itemsController.deleteItem(item2.getId(), item);
        assertTrue(deletedItem.isDeleted());
        assertEquals("Deleted", deletedItem.getDeletionComments());
        assertEquals(0, deletedItem.getAmount());
        deletedItem = this.itemsController.restoreItem(item2.getId());
        assertFalse(deletedItem.isDeleted());
        assertNull(deletedItem.getDeletionComments());
    }
}