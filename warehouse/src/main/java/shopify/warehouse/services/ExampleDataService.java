package shopify.warehouse.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import shopify.warehouse.models.Item;
import shopify.warehouse.repositories.ItemRepository;


@Service
public class ExampleDataService {

    private final ItemRepository itemRepository;

    public ExampleDataService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void generateData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item(
                    RandomStringUtils.randomAlphabetic(5),
                    RandomStringUtils.randomAlphabetic(15),
                    (int) Math.round(Math.random() * 100));
            itemRepository.save(item);
        }
    }
}
