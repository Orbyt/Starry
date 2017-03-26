package orbyt.starry.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by calebchiesa on 3/24/17.
 */

public class ResponseWrapper implements Serializable {

    private List<Response> responses;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
