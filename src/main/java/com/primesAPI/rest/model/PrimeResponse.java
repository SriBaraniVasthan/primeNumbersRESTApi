package com.primesAPI.rest.model;

import java.util.List;


public class PrimeResponse {

	private int initial;

	public int getInitial() {
		return initial;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	private List<Integer> primes;

	public List<Integer> getPrimes() {
		return primes;
	}

	public void setPrimes(List<Integer> primes) {
		this.primes = primes;
	}

}
