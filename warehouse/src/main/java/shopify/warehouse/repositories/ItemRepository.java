package shopify.warehouse.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import shopify.warehouse.models.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
