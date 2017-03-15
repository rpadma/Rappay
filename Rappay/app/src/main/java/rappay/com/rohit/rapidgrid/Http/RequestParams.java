package rappay.com.rohit.rapidgrid.Http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohit on 3/22/2016.
 */
public class RequestParams {

    private String uri;
    private Map<String, Object> params = new HashMap<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void setParam(String key, Object value){
        params.put(key, value);
    }
}
