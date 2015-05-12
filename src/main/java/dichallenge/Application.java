package dichallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * Created by vkumar6 on 4/27/15.
 */

@SpringBootApplication
@EnableCaching
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    MessageStore meessageStore;
    public enum HTTP_METHOD_TYPE {
        POST,PUT,DELETE,HEAD,OPTIONS
    }

    private static String PATH  = HTTP_METHOD_TYPE.POST+"cas-web/v2/fis/04715/fiCustomers?operation=createlocation";
    private static String PATH1 = HTTP_METHOD_TYPE.PUT+"cas-web/v3/fis/04715/fiCustomers?operation=createlocation";
    private static String REQUEST ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns9:FICustomer \n"+
            "    <ns9:fiId>04715</ns9:fiId>\n" +
            "    <ns9:cisNumber>123</ns9:cisNumber>\n" +
            "    <ns9:hostCredential>\n" +
            "        <ns2:password>1234</ns2:password>\n" +
            "    </ns9:hostCredential>\n" +
            "    <ns9:ssn>testsaasn5</ns9:ssn>\n" +
            "</ns9:FICustomer>";
    private static String REQUEST1 ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ns9:FICustomer \n"+
            "    <ns9:fiId>04715</ns9:fiId>\n" +
            "    <ns9:cisNumber>1234</ns9:cisNumber>\n" +
            "    <ns9:hostCredential>\n" +
            "        <ns2:password>1234</ns2:password>\n" +
            "    </ns9:hostCredential>\n" +
            "    <ns9:ssn>testsaasn5</ns9:ssn>\n" +
            "</ns9:FICustomer>";;

    @Component
    static class Runner implements CommandLineRunner {
        @Autowired
        private IdempotentFilter idempotentFilter;

        @Override
        public void run(String... args) throws Exception {


            String key = PATH + REQUEST;
            // Only Non Idempotent call will be made via idempotentFilter .
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST);
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST);

            key = PATH1 + REQUEST;
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST);

            key = PATH + REQUEST;
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST);

            key = PATH + REQUEST1;
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);

            key = PATH1 + REQUEST1;
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);

            key = PATH + REQUEST1;
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);
            idempotentFilter.getResource(MD5Sum.digest(key.toString()), REQUEST1);

            // Inharently Idempotent call may go via normal FSGClient calls .

            // Object response = fsgRestClient.getFiCustomerFromHostViaCBS(ficust, FSG_CBS_DIIS_RESOURCE_URL, diId, new NameValuePair("CUSTOMER_ID", hostLoginId), new NameValuePair("TYPE", "MEMNUMBER"));
        }
    }

    @Bean
    public CacheManager cacheManager() {
        meessageStore = new MessageStore("payloads");
        return meessageStore;
    }

    /*
    When we would want to implement this solution in our CAS environment , we would have to add new cache in  CASCacheUtil ( so that we could use Coherence Cache  ) .
    And we could get and put our payloads into that cache .
    something like
    public static final String FI_PAYLOAD_CACHE_NAME = "5M-CAS-Payload-Cache";

    The difference here is that i have used CacheManager interface . So similar solution we will build using NamedCache interface in CAS for example .

    in CAS com.tangosol.net.NamedCache is been used . In our case key is md5sum of the payload uri and the message type .

    final NamedCache cache = CacheFactory.getCache(cacheName);
    cache.put(key, payload);

	CacheFactory.getCache(cacheName).get(key);

     */


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
