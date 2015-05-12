package dichallenge;

/**
 * Created by vkumar6 on 4/27/15.
 */
public class Payload {

    private String md5;
    private String payload;

    public Payload(String md5, String payload) {
        this.md5 = md5;
        this.payload = payload;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Payload{" + "md5='" + md5 + '\'' + ", payload='" + payload + '\'' + '}';
    }
}
