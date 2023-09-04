package util;

import java.util.Arrays;

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

    @Bean
    public SimpleCacheManager cacheManager() {
    	SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
          new ConcurrentMapCache("inputNum"), 
          new ConcurrentMapCache("primes")));
        return cacheManager; 
    }
}
