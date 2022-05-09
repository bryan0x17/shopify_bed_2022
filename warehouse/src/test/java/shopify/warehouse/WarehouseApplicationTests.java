package shopify.warehouse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopify.warehouse.controllers.ItemsController;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WarehouseApplicationTests {

	@Autowired
	private ItemsController itemsController;

	@Test
	void contextLoads() {
		assertThat(itemsController).isNotNull();

	}

}
