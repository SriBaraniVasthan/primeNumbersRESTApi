package com.primesAPI.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.primesAPI.rest.model.PrimeNumber;
import com.primesAPI.rest.model.PrimeResponse;
import com.primesAPI.rest.service.PrimesApiService;
import jakarta.validation.Valid;
import util.GlobalExceptionHandler;


/*
 * Controller for the REST API
 */
@RestController
@RequestMapping("/primes")
@Validated
public class PrimesApiController {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	private List<Integer> resList;
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);	
	
	PrimeResponse response;

	@Autowired
	private PrimesApiService apiServiceObj;

	/*
	 * GET API 
	 */
	@GetMapping(path = "/{number}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PrimeResponse> generatePrimesFunction(
			 @Valid PrimeNumber number,
			@RequestParam(value = "algorithm", required = false, defaultValue = "primes") String algorithm) {
		try {
			switch (algorithm) {
			case "primes":
				resList = apiServiceObj.getPrimeNumbers(number.getNumber());
				LOGGER.info("primes algorithm");
				break;
			case "Eratosthenes":
				resList = apiServiceObj.getPrimesBySieveofEratosthenes(number.getNumber());
				LOGGER.info("Eratosthenes algorithm");
				break;
			case "Atkin":
				resList = apiServiceObj.getPrimesBySieveOfAtkin(number.getNumber());
				LOGGER.info("Atkin algorithm");
				break;
			default:
				resList = apiServiceObj.getPrimeNumbers(number.getNumber());
				LOGGER.info("Default algorithm");
			}
		} catch (Exception ex) {
			LOGGER.error("An Exception has been caught" + ex);
		}
		response=new PrimeResponse();
		response.setPrimes(resList);
		response.setInitial(number.getNumber());
		
		return new ResponseEntity<PrimeResponse>(response, HttpStatus.OK);
	}

}
