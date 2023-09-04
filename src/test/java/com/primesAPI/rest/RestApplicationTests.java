package com.primesAPI.rest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.primesAPI.rest.service.PrimesApiService;
import com.primesAPI.rest.service.PrimesApiServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Component
class RestApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * The service should not be actively running when executing these tests as Spring boot starts up the test service using the above DEFINED_PORT
	 * The platform for this test is made configurable in the application.properties.
	 */
	@Value("${application_host_url}")
	private String baseUrl;
	ResponseEntity<int[]> entity;
	int[] expected;

	/**
	 * Primary simple unit test for first 8 prime numbers.
	 */
	@Test
	public void testPrimeGeneration() {
		System.out.println("baseURL::" + baseUrl);
		entity = restTemplate.getForEntity(baseUrl + "/primes/19", int[].class);
		expected = new int[] { 2, 3, 5, 7, 11, 13, 17, 19 };
		checkBody(entity, expected);
	}

	@Test
	public void testPrimeNegativeInteger() {
		entity = restTemplate.getForEntity(baseUrl + "/primes/-7919", int[].class);
		expected = new int[] {};
		checkBody(entity, expected);
	}

	/**
	 * Primary simple unit test for first 1000 prime numbers.
	 */
	@Test
	public void testPrimePositiveInteger() {
		entity = restTemplate.getForEntity(baseUrl + "/primes/7919", int[].class);
		expected = RestApplicationTestsUtil.FIRST_1000_PRIMES;
		checkBody(entity, expected);
	}

	/*
	 * @Test public void testStrings() {
	 * 
	 * // Test PrimeNumberUtilTest.KNOWN_PRIMES entity =
	 * restTemplate.getForEntity(baseUrl + "/primes/abc", int[].class);
	 * assertEquals(HttpStatus., entity.getStatusCode()); }
	 */
	/*
	 * @Test public void firstParameterExceptionTest() { entity =
	 * restTemplate.getForEntity(baseUrl + "/primes/abc", int[].class); expected =;
	 * checkBody(entity, expected); }
	 */
	
	private void checkBody(ResponseEntity<int[]> entity, int[] expected) {
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		int[] actual = entity.getBody();
		actual = Arrays.copyOfRange(actual, 1, actual.length);
		assertEquals(expected.length, actual.length);
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Test the API resource using combinations of numbers and 3 different algorithms as query params.
	 */
	@Test
	public void testPrimeNumberAlgorithms() {

		entity = restTemplate.getForEntity(baseUrl + "/primes/123?algorithm=primes", int[].class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/312?algorithm=Eratosthenes", int[].class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/231?algorithm=Atkin", int[].class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		entity = restTemplate.getForEntity(baseUrl + "/primes/132?algorithm=", int[].class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

	}

	PrimesApiService apiService = new PrimesApiServiceImpl();

	@Test
	public void testPrimeGenerator() {

		final List<Integer> primes = apiService.getPrimeNumbers(7919);
		assertEquals("'primes' and 'KNOWN_PRIMES' should be equal in size",
				RestApplicationTestsUtil.FIRST_1000_PRIMES.length, primes.size());

		for (int i = 0; i < RestApplicationTestsUtil.FIRST_1000_PRIMES.length; i++) {
			assertEquals("'primes.get(i)' should equal 'KNOWN_PRIMES[i]'",
					RestApplicationTestsUtil.FIRST_1000_PRIMES[i], primes.get(i).intValue());
		}
	}

	/*
	 * The list should be empty as there is no prime with the given number
	 */
	@Test
	public void testNoPrimeEmpty() {

		final List<Integer> primes = apiService.getPrimeNumbers(1);
		assertTrue(primes.isEmpty());

	}

}
