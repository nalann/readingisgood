package com.reading.is.good.getir.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.dto.DetailDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.exception.StockCannotUpdatedException;
import com.reading.is.good.pojo.Book;
import com.reading.is.good.service.BookService;

@SpringBootTest
public class BookServiceTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private BookService bookService;

	@BeforeEach
	void init() {
		mongoTemplate.dropCollection(Book.class);
	}

	@Test
	public void testSaveOrUpdateBook() throws Exception {
		BookDTO bookDTO = new BookDTO("Bir Idam Mahkumunun Son Gunu", "Victor Hugo", "İş Bankası", "classic", 6.0, 2);
		;
		bookService.saveOrUpdate(bookDTO);

		Book book = bookService.findByBookName(bookDTO.getBookName());

		assertEquals(2, book.getStock());
	}

	@Test
	public void testUpdateBookStockDecrease() throws Exception {
		BookDTO bookDTO = new BookDTO("Bir Idam Mahkumunun Son Gunu", "Victor Hugo", "İş Bankası", "classic", 6.0, 2);
		bookService.saveOrUpdate(bookDTO);

		OrderDTO orderDTO = createOrderDTO();

		bookService.updateBookStock(orderDTO, true);

		Book book = bookService.findByBookName(bookDTO.getBookName());

		assertEquals(1, book.getStock());

	}

	@Test
	public void testUpdateBookStockIncrease() throws Exception {
		BookDTO bookDTO = new BookDTO("Bir Idam Mahkumunun Son Gunu", "Victor Hugo", "İş Bankası", "classic", 6.0, 2);
		bookService.saveOrUpdate(bookDTO);

		OrderDTO orderDTO = createOrderDTO();

		bookService.updateBookStock(orderDTO, false);

		Book book = bookService.findByBookName(bookDTO.getBookName());

		assertEquals(3, book.getStock());

	}

	@Test
	public void testUpdateBookStockException() throws Exception {
		OrderDTO orderDTO = createOrderDTO();
		String expectedMessage = "Stock is not enough for: " + orderDTO.getDetail().get(0).getBookName();
		Exception exception = assertThrows(StockCannotUpdatedException.class, () -> {
			bookService.updateBookStock(orderDTO, false);
		});

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	private OrderDTO createOrderDTO() {
		OrderDTO orderDTO = new OrderDTO("test@test.com", new ArrayList<DetailDTO>(), "29-10-2021 14:02:05", "Istanbul",
				"+900002", "shopping");
		DetailDTO detailDTO = new DetailDTO(1, "Bir Idam Mahkumunun Son Gunu", "Victor Hugo", 6.0);
		orderDTO.getDetail().add(detailDTO);

		return orderDTO;
	}

	@AfterEach
	void tearDown() {
		mongoTemplate.dropCollection(Book.class);
	}
}
