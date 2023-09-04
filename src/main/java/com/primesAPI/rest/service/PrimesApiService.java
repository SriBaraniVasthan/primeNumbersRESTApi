package com.primesAPI.rest.service;

import java.util.List;

/*
 * Defining the methods for prime number generation implementation by getting an input number for 3 algorithms.
 */
public interface PrimesApiService {

	public List<Integer> getPrimeNumbers(int inputNum);

	public List<Integer> getPrimesBySieveofEratosthenes(int inputNum);

	public List<Integer> getPrimesBySieveOfAtkin(int inputNum);
}
