package dichallenge;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Created by vkumar6 on 4/27/15.
 */

// NOTE - similar to CASCacheUtil . or might exted NamedCache .
public class MessageStore extends  ConcurrentMapCacheManager {
    private static ConcurrentMapCacheManager concurrentMapCacheManager;
    MessageStore(String name){
        super(name);
    }

    @Bean
    public static CacheManager cacheManager() {
        concurrentMapCacheManager = new ConcurrentMapCacheManager("payloads");
        return concurrentMapCacheManager;
    }


}
