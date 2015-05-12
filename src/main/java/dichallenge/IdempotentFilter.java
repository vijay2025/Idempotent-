package dichallenge;


/**
 * Created by vkumar6 on 4/27/15.
 */

public interface IdempotentFilter {

    Payload getResource(String md5, String request);

}
