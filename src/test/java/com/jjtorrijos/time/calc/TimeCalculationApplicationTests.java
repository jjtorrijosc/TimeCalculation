package com.jjtorrijos.time.calc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TimeCalculationApplicationTests {

	@Autowired
	MockMvc mock;
	
	@Test
	void testNegativeTimeZones() throws Exception {
		mock.perform(post("/minutesBetween")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content("{\"time1\":\"Thu, 21 Dec 2000 16:11:07 -0100\",\"time2\":\"Thu, 21 Dec 2000 16:11:07 +0100\"}")
		).andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		   .andExpect(content().string("120"));
	}
	
	@Test
	void testZeroTimeZones() throws Exception {
		mock.perform(post("/minutesBetween")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content("{\"time1\":\"Thu, 21 Dec 2000 16:11:07 -0000\",\"time2\":\"Thu, 21 Dec 2000 16:11:07 +0000\"}")
		).andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		   .andExpect(content().string("0"));
	}
	
	@Test
	void testTime2GreaterThanTime1() throws Exception {
		mock.perform(post("/minutesBetween")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content("{\"time1\":\"Mon, 28 Jun 2021 00:11:07 +0100\",\"time2\":\"Mon, 28 Jun 2021 00:21:07 +0100\"}")
		).andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		   .andExpect(content().string("10"));
	}
	
	@Test
	void testRoundedDown() throws Exception {
		mock.perform(post("/minutesBetween")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content("{\"time1\":\"Mon, 28 Jun 2021 00:12:00 +0100\",\"time2\":\"Mon, 28 Jun 2021 00:10:01 +0100\"}")
		).andExpect(status().isOk())
		   .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		   .andExpect(content().string("1"));
	}
	
	@Test
	void testInvalidDateFormat() throws Exception {
		mock.perform(post("/minutesBetween")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content("{\"time1\":\"aaaa\",\"time2\":\"bbbb\"}")
		).andExpect(status().isBadRequest());
	}

}
