package rappay.com.rohit.rapidgrid.Http;

/**
 * Created by Rohit on 3/22/2016.
 */
public class HttpResponse {
    private int statusCode;
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
