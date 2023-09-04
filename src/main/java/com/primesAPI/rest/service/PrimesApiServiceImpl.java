package com.primesAPI.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/*
 * Overriding Service implementation of prime number generations using multiple algorithms, returning a list of prime numbers.
 * Spring boot caching enabled using @Cacheable annotation.
 */
@Service
public class PrimesApiServiceImpl implements PrimesApiService {

	/*
	 * Sieve of Eratosthenes implementation as default
	 */
	@Override
	@Cacheable(key = "#inputNum", value = "primes")
	public List<Integer> getPrimeNumbers(int inputNum) {
		if (inputNum < 2) {
			return Collections.emptyList();
		}

		return IntStream.rangeClosed(2, inputNum).filter(n -> isPrimeNumber(n)).boxed().collect(Collectors.toList());
	}

	static boolean isPrimeNumber(int num) {
		if (num > 2)
			return (num % 2) != 0 && IntStream.rangeClosed(3, (int) Math.sqrt(num)).filter(n -> n % 2 != 0)
					.noneMatch(n -> (num % n == 0));
		else
			return num == 2;

	}

	/*
	 * Sieve of Atkin implementation
	 */
	@Override
	@Cacheable(key = "#inputNum", value = "primes")
	public List<Integer> getPrimesBySieveOfAtkin(int inputNum) {
		
		List<Integer> primeList = new ArrayList<Integer>();
		// 2 and 3 are known to be prime
		if (inputNum > 2)
			primeList.add(2);

		if (inputNum > 3)
			primeList.add(3);

		// Initializing the sieve of Atkin array with boolean false values
		boolean sieve[] = new boolean[inputNum + 1];

		for (int i = 0; i <= inputNum; i++)
			sieve[i] = false;

		for (int x = 1; x * x <= inputNum; x++) {
			for (int y = 1; y * y <= inputNum; y++) {

				// Sieve of Atkin calculation
				int n = (4 * x * x) + (y * y);
				if (n <= inputNum && (n % 12 == 1 || n % 12 == 5))

					sieve[n] ^= true;

				n = (3 * x * x) + (y * y);
				if (n <= inputNum && n % 12 == 7)
					sieve[n] ^= true;

				n = (3 * x * x) - (y * y);
				if (x > y && n <= inputNum && n % 12 == 11)
					sieve[n] ^= true;
			}
		}

		// all multiples of squares are Marked as non-prime numbers
		for (int r = 5; r * r <= inputNum; r++) {
			if (sieve[r]) {
				for (int i = r * r; i <= inputNum; i += r * r)
					sieve[i] = false;
			}
		}

		// Adding prime numbers to the list
		for (int a = 5; a <= inputNum; a++) {

			if (sieve[a]) {
				primeList.add(a);
			}
		}
		return primeList;
	}

	/*
	 * Sieve of Eratosthenes implementation 2
	 */
	@Override
	@Cacheable(key = "#inputNum", value = "primes")
	public List<Integer> getPrimesBySieveofEratosthenes(int inputNum) {
		int num = inputNum, i;
		List<Integer> primeList = new ArrayList<Integer>();
		boolean[] a = new boolean[num + 1];
		Arrays.fill(a, true);

		// Setting the Boolean to false 0 and 1 are not prime
		a[0] = false;
		a[1] = false;
		for (i = 2; i <= Math.sqrt(num); i++)
			// Checking if the number is prime
			if (a[i])
				for (int j = i * i; j <= num; j += i) {
					a[j] = false;
				}
		for (i = 0; i <= num; i++) {

			// Adding prime numbers to the list
			if (a[i]) {
				primeList.add(i);
			}
		}
		return primeList;
	}

}
