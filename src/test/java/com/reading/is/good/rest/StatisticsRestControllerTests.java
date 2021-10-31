package com.reading.is.good.rest;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.bson.Document;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.reading.is.good.pojo.Detail;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.service.StatisticsService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class StatisticsRestControllerTests {
	
	@MockBean
	private StatisticsService statisticsService;
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;
	
	private ArrayList<Document> documents;
	
	private List<Order> orders;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		Detail detail = new Detail(2, "Harry Potter and the Chamber of Secrets", "J.K. Rowling", 25.0);

		ArrayList<Detail> details = new ArrayList<Detail>();
		details.add(detail);
		
		orders = List.of(new Order("hectorgmail", details, 1620043802000L, "Istanbul", "7852", "shopping"),
				new Order("hectorgmail", details, 1620043802000L, "Istanbul", "7852", "shopping"));
		
		Gson gson = new Gson();
		
		Document document1 = Document.parse(gson.toJson(orders.get(0)));
		Document document2 = Document.parse(gson.toJson(orders.get(1)));
		
		documents = new ArrayList<Document>();
		
		documents.add(document1);
		documents.add(document2);
	}
	
	@Test
	void testOrderRestGetAllOrderByCustomer() throws Exception {
		Mockito.when(statisticsService.getStatistics("hectorgmail")).thenReturn(documents);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/statistics/all?email=hectorgmail")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String statisticsJson = result.getResponse().getContentAsString();

		Assertions.assertThat(statisticsJson).isNotEmpty();
		Assertions.assertThat(statisticsJson).isNotEqualToIgnoringCase(objectMapper.writeValueAsString(orders));
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}
}
