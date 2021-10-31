package com.reading.is.good.rest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reading.is.good.dto.DetailDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Detail;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.service.BookService;
import com.reading.is.good.service.OrderService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class OrderRestServiceTests {

	@MockBean
	private OrderService orderService;
	
	@MockBean
	private BookService bookService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private Order order;

	private List<Order> orders;

	private Page<Order> pageOrder;

	private List<Order> emptyOrder = null;

	private Optional<Order> optionalOrder;

	private OrderDTO orderDTO;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		Detail detail = new Detail(2, "Harry Potter and the Chamber of Secrets", "J.K. Rowling", 25.0);
		Detail detail2 = new Detail(1, "Bir Idam Mahkumunun Son Gunu", "Victor Hugo", 6.0);
		Detail detail3 = new Detail(3, "Seker Portakali", "Jose Mauro De Vasconcelos", 30.0);

		ArrayList<Detail> details = new ArrayList<Detail>();
		details.add(detail);

		ArrayList<Detail> details2 = new ArrayList<Detail>();
		details2.add(detail2);
		details2.add(detail3);

		DetailDTO detailDTO = new DetailDTO(2, "Simyaci", "Paulo Coelho", 6.0);
		ArrayList<DetailDTO> detailDTOs = new ArrayList<DetailDTO>();
		detailDTOs.add(detailDTO);

		order = new Order("hector@gmail.com", details, 1620043802000L, "Istanbul", "7852", "shopping");

		orderDTO = new OrderDTO("hector@gmail.com", detailDTOs, "03-05-2021 15:10:02", "Istanbul", "7852", "shopping");

		orders = List.of(new Order("hector@gmail.com", details, 1620043802000L, "Istanbul", "7852", "shopping"),
				new Order("hector@gmail.com", details, 1620043802000L, "Istanbul", "7852", "shopping"));

		pageOrder = new PageImpl<Order>(orders);

		optionalOrder = Optional.ofNullable(order);
	}

	@Test
	void testOrderRestGetAllOrderByCustomer() throws Exception {
		Pageable pageable = PageRequest.of(1, 1);
		Mockito.when(orderService.findByEmail(order.getEmail(), pageable)).thenReturn(pageOrder);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/orders/get/all/orders/by/customer?email=hector@gmail.com&page=1&size=1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String orderJson = result.getResponse().getContentAsString();

		Assertions.assertThat(orderJson).isNotEmpty();
		Assertions.assertThat(orderJson).isNotEqualToIgnoringCase(objectMapper.writeValueAsString(orders.get(1)));
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	/*
	 * @Test void testOrderRestGetOrderByDateInterval() throws Exception {
	 * Mockito.when(orderService.findByDateInterval(1617472822000L,
	 * 1633285822000L)).thenReturn(orders);
	 * 
	 * MvcResult result = mockMvc.perform(MockMvcRequestBuilders
	 * .get("/orders/get/date/interval?startDate=03-04-2021 21:00:22&endDate=03-10-2021 21:30:22"
	 * ) .accept(MediaType.APPLICATION_JSON)).andReturn();
	 * 
	 * String orderJson = result.getResponse().getContentAsString();
	 * 
	 * Assertions.assertThat(orderJson).isNotEmpty();
	 * Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200); }
	 */

	@Test
	void testOrderRestGetOrderByDateIntervalNoContent() throws Exception {
		Mockito.when(orderService.findByDateInterval(1620043802000L, 1620043802000L)).thenReturn(emptyOrder);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get("/orders/get/date/interval?startDate=03-04-2021 21:00:22&endDate=03-10-2021 21:30:22")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		String orderJson = result.getResponse().getContentAsString();

		Assertions.assertThat(orderJson).isEqualTo("");
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(204);
	}

	@Test
	void testOrderRestGetById() throws Exception {
		Mockito.when(orderService.findById("615ddb12bda9d143e04036a7")).thenReturn(optionalOrder);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/get/by/id?id=615ddb12bda9d143e04036a7")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		String orderJson = result.getResponse().getContentAsString();

		Assertions.assertThat(orderJson).isNotEmpty();
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}
	@Test
	void testOrderRestGetByIdNoContent() throws Exception {
		Mockito.when(orderService.findById("123123")).thenReturn(optionalOrder);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/get/by/id?id=615ddb12bda9d143e04036a7")
				.accept(MediaType.APPLICATION_JSON)).andReturn();

		String orderJson = result.getResponse().getContentAsString();

		Assertions.assertThat(orderJson).isEmpty();
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(204);
	}


	@Test
	void testOrderRestSaveSuccess() throws Exception {
		Mockito.when(orderService.save(orderDTO)).thenReturn(order);

		mockMvc.perform(MockMvcRequestBuilders.post("/orders/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderDTO).getBytes(StandardCharsets.UTF_8))
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated());

	}
	
}
