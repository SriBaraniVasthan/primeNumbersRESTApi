package com.primesAPI.rest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.primesAPI.rest.model.PrimeResponse;
import com.primesAPI.rest.service.PrimesApiService;
import com.primesAPI.rest.service.PrimesApiServiceImpl;

import util.GlobalExceptionHandler;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Component
class RestApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	/**
	 * The service should not be actively running when executing these tests as
	 * Spring boot starts up the test service using the above DEFINED_PORT The
	 * platform for this test is made configurable in the application.properties.
	 */
	@Value("${application_host_url}")
	private String baseUrl;
	ResponseEntity<PrimeResponse> entity;
	PrimeResponse expected;

	/**
	 * Primary simple unit test for first 8 prime numbers.
	 */
	@Test
	public void testPrimeGeneration() {
		entity = restTemplate.getForEntity(baseUrl + "/primes/19", PrimeResponse.class);
		expected = new PrimeResponse();
		expected.setPrimes(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19));

		checkBody(entity, expected);
	}

	@Test
	public void testPrimeNegativeInteger() {
		try {
			entity = restTemplate.getForEntity(baseUrl + "/primes/-7919", PrimeResponse.class);
		} catch (HttpClientErrorException e) {
			assertEquals(e.getStatusCode().value(), HttpStatus.BAD_REQUEST.value());
		}

	}

	/**
	 * Primary simple unit test for first 1000 prime numbers.
	 */
	/*
	 * @Test public void testPrimePositiveInteger() {
	 * LOGGER.info("Testing Positive Integer"); entity =
	 * restTemplate.getForEntity(baseUrl + "/primes/7919", PrimeResponse.class);
	 * expected = RestApplicationTestsUtil.FIRST_1000_PRIMES; checkBody(entity,
	 * expected); }
	 */

	// @Test
	public void testStrings() {
		LOGGER.info("Testing String input");
		restTemplate.getForEntity(baseUrl + "/primes/abc", int[].class);
		LOGGER.info("testStrings", entity.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
	}

	private void checkBody(ResponseEntity<PrimeResponse> entity, PrimeResponse expected) {
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		List<Integer> actual = entity.getBody().getPrimes();
		//actual = Arrays.copyOfRange(actual, 1, actual.length);
		assertEquals(expected.getPrimes().size(), actual.size());
		assertTrue(expected.getPrimes().containsAll(actual) && actual.containsAll(expected.getPrimes()));
	}
	
	/**
	 * Test the API resource using combinations of numbers and 3 different
	 * algorithms as query params.
	 */
	@Test
	public void testPrimeNumberAlgorithms() {
		LOGGER.info("Testing Algorithms as parameters");
		entity = restTemplate.getForEntity(baseUrl + "/primes/123?algorithm=primes", PrimeResponse.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/312?algorithm=Eratosthenes", PrimeResponse.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/231?algorithm=Atkin", PrimeResponse.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/132?algorithm=", PrimeResponse.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

	}

	PrimesApiService apiService = new PrimesApiServiceImpl();

	/*
	 * @Test public void testPrimeGenerator() { LOGGER.
	 * info("Validating array size of expected and actual generated prime numbers");
	 * final List<Integer> primes = apiService.getPrimeNumbers(7919);
	 * assertEquals("'primes' and 'KNOWN_PRIMES' should be equal in size",
	 * RestApplicationTestsUtil.FIRST_1000_PRIMES.length, primes.size());
	 * 
	 * for (int i = 0; i < RestApplicationTestsUtil.FIRST_1000_PRIMES.length; i++) {
	 * assertEquals("'primes.get(i)' should equal 'KNOWN_PRIMES[i]'",
	 * RestApplicationTestsUtil.FIRST_1000_PRIMES[i], primes.get(i).intValue()); } }
	 */
	/*
	 * The list should be empty as there is no prime with the given number
	 */
	@Test
	public void testNoPrimeEmpty() {

		final List<Integer> primes = apiService.getPrimeNumbers(1);
		assertTrue(primes.isEmpty());

	}

}
