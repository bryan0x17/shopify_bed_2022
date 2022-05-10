package shopify.warehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shopify.warehouse.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByIdAndDeletedFalse(@Param("id") Long id);
    List<Item> findByDeletedFalse();
    List<Item> findByDeletedTrue();
}
