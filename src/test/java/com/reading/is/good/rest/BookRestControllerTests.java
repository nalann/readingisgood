package com.reading.is.good.rest;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.pojo.Book;
import com.reading.is.good.service.BookService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class BookRestControllerTests {

	@MockBean
	private BookService bookService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private List<BookDTO> books;

	private BookDTO bookDTO;

	private Book book;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		books = List.of(new BookDTO("Bir Idam Mahkumunun Son Gunu", "Victor Hugo", "pk", "classic", 6.0),
				new BookDTO("Seker Portakali", "Jose Mauro De Vasconcelos", "Can", "classic", 18.85),
				new BookDTO("1984", "George Orwell", "Can", "classic", 13.65));
		bookDTO = new BookDTO("Simyaci", "Paulo Coelho", "Can", "classic", 19.50);

		book = new Book("Simyaci", "Paulo Coelho", "Can", "classic", 19.50);
	}

	@Test
	void testBookRestSave() throws Exception {
		Mockito.when(bookService.saveOrUpdate(Mockito.any(BookDTO.class))).thenReturn(book);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/books/save").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(bookDTO).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String bookJson = result.getResponse().getContentAsString();
		Assertions.assertThat(bookJson).isNotEmpty();
		Assertions.assertThat(bookJson).isNotEqualToIgnoringCase(objectMapper.writeValueAsString(bookDTO));
	}

}
