package com.primesAPI.rest.controller;

import java.util.HashMap;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.primesAPI.rest.service.PrimesApiService;
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

	@Autowired
	private PrimesApiService apiServiceObj;

	/*
	 * GET API 
	 */
	@GetMapping(path = "/{number}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> generatePrimesFunction(
			@NotNull(message = "Input should not be blank.") @PathVariable("number") @Min(value = 1) Integer number,
			@RequestParam(value = "algorithm", required = false, defaultValue = "primes") String algorithm) {
		switch (algorithm) {
		case "primes":
			resList = apiServiceObj.getPrimeNumbers(number);
			break;
		case "Eratosthenes":
			resList = apiServiceObj.getPrimesBySieveofEratosthenes(number);
			break;
		case "Atkin":
			resList = apiServiceObj.getPrimesBySieveOfAtkin(number);
			break;
		default:
			resList = apiServiceObj.getPrimeNumbers(number);
		}
		HashMap<String, Object> respObj = new HashMap<String, Object>();
		respObj.put("Initial", number);
		respObj.put("Primes", resList);

		return new ResponseEntity<Object>(respObj, HttpStatus.OK);
	}

}
