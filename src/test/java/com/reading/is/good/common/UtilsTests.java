package com.reading.is.good.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.reading.is.good.ReadingIsGoodApplication;
import com.reading.is.good.exception.DateParseException;

@SpringBootTest(classes = ReadingIsGoodApplication.class)
public class UtilsTests {


	@Test
	void testConvertStrToDateLongException() {
		Exception exception = assertThrows(DateParseException.class, () -> {
			Utils.convertStrToDateLong("25-25-2021 02:02");
		});

		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains("Date format is wrong. dd-MM-yyyy hh:mm:ss"));
	}
	
	@Test
	void testConvertStrToDateLongSuccess() {
		long actualDate = Utils.convertStrToDateLong("03-04-2021 21:00:22");

		assertEquals(1617472822000L, actualDate);
	}
	
	//It is changed based on the localization
	/*
	 * @Test void testConvertLongToMonthSuccess() { String actual =
	 * Utils.convertLongToMonth(1617472822000L);
	 * 
	 * assertEquals("Nis", actual); }
	 */
	
}
