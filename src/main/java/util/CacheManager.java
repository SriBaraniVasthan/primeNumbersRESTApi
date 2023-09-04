package util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 
 * Implemented in memory caching using spring boot
 *
 */
@Configuration
@EnableCaching
public class CacheManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Bean
	public SimpleCacheManager cacheManager() {

		SimpleCacheManager cacheManager = new SimpleCacheManager();
		try {
			cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("inputNum"), new ConcurrentMapCache("primes")));
			LOGGER.info("Cached the response for the input");
		} catch (Exception ex) {
			LOGGER.error("An Exception has been caught" + ex);
		}
		return cacheManager;
	}
}
