package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
	    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
	    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"}, 
	    config = @SqlConfig(encoding = "utf-8"))


class FetchJeepTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	  
	@LocalServerPort
	private int serverPort;
	
	@Test
	void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
		JeepModel model = JeepModel.WRANGLER;
		String trim = "Sport";
		//String uri = String.format("String.format(\"http://localhost:%d/jeeps?model=%s&trim=%s\", serverPort, model, trim");
		String uri = String.format("http://localhost:%d/jeeps?model=%s&trim=%s", serverPort, model, trim);
		//String uri = String.format("5s?model=%s&trim=%s", getBaseUri(), model, trim);
		ResponseEntity<List<Jeep>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		List<Jeep> actual = response.getBody();			
		List<Jeep> expected = buildExpected();
		//System.out.println(expected);
		
		actual.forEach(jeep -> jeep.setModelPK(null));
		
		assertThat(actual).isEqualTo(expected);
		
	
		
	}
	
	
	
	private List<Jeep> buildExpected() {
		// TODO Auto-generated method stub
		List<Jeep> list = new LinkedList<>();
		Jeep.builder();
		// @formatter:off
		//list.add(Jeep.builder()
		list.add(Jeep.builder()
				.modelId(JeepModel.WRANGLER)
				.trimLevel("Sport")
				.numDoors(2)
				.wheelSize(17)
				.basePrice(new BigDecimal("28475.00"))
				.build());
		
		list.add(Jeep.builder()
				.modelId(JeepModel.WRANGLER)
				.trimLevel("Sport")
				.numDoors(4)
				.wheelSize(17)
				.basePrice(new BigDecimal("31975.00"))
				.build());
		
		
		// @formatter:on
		Collections.sort(list);
		return list;
	}

		

}
