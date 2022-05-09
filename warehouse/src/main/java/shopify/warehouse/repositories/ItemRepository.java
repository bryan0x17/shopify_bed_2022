package shopify.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shopify.warehouse.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
