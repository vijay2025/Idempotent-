package dichallenge;

import org.omg.DynamicAny.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


/**
 * Created by vkumar6 on 4/27/15.
 */
@Component
public class PayloadRepository implements IdempotentFilter {
    private static final Logger log = LoggerFactory.getLogger(Application.class);


    @Override
    @Cacheable("payloads")
    public Payload getResource(String md5, String request) {
        String se = simulateSlowService();
        return new Payload(md5, se);
    }

    // Make a actual webservice call . i am putting some delay to this .
    // Via FSGRestClient etc .
    private String simulateSlowService() {
        log.info(".... Making REST requests ");
        try {
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return  "Suggess\n";
        /*
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns9:FICustomer \n" +
                "    xmlns:ns2=\"http://schema.intuit.com/fs/common/v2\" \n" +
                "    xmlns:ns3=\"http://schema.intuit.com/domain/banking/preference/V2\" \n" +
                "    xmlns:ns4=\"http://schema.intuit.com/domain/banking/notification/v2\" \n" +
                "    xmlns:ns5=\"http://schema.intuit.com/domain/banking/challengeQuestionInfo/v2\" \n" +
                "    xmlns:ns6=\"http://schema.intuit.com/domain/banking/account/v2\" \n" +
                "    xmlns:ns7=\"http://schema.intuit.com/domain/banking/accountTransaction/v2\" \n" +
                "    xmlns:ns8=\"http://schema.intuit.com/domain/banking/businessbanking/v1\" \n" +
                "    xmlns:ns9=\"http://schema.intuit.com/domain/banking/fiCustomer/v2\" \n" +
                "    xmlns:ns10=\"http://schema.intuit.com/domain/banking/Appcode/v2\" \n" +
                "    xmlns:ns11=\"http://schema.intuit.com/domain/banking/Segment/v2\" \n" +
                "    xmlns:ns12=\"http://schema.intuit.com/domain/banking/formattedAccountNumber/v2\" \n" +
                "    xmlns:ns13=\"http://schema.intuit.com/domain/banking/fundingAccount/v2\" \n" +
                "    xmlns:ns14=\"http://schema.intuit.com/domain/banking/dbViewFICustomer/v1\">\n" +
                "    <ns9:id type=\"GUID\">93cc6a6e6d224015aab4060cb7043253</ns9:id>\n" +
                "    <ns9:fiId>04715</ns9:fiId>\n" +
                "    <ns9:cisNumber>1234</ns9:cisNumber>\n" +
                "    <ns9:hostCredential>\n" +
                "        <ns2:password>1234</ns2:password>\n" +
                "    </ns9:hostCredential>\n" +
                "    <ns9:person>\n" +
                "        <ns2:contactInfo>\n" +
                "            <ns2:emailAddress></ns2:emailAddress>\n" +
                "        </ns2:contactInfo>\n" +
                "    </ns9:person>\n" +
                "    <ns9:companyName>The coffee shop</ns9:companyName>\n" +
                "    <ns9:ssn>testsaasn5</ns9:ssn>\n" +
                "</ns9:FICustomer>";
                */
    }

}
