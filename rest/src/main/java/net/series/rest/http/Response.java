package net.series.rest.http;

import com.mashape.unirest.http.JsonNode;
import lombok.Data;

@Data
public class Response {
    private JsonNode body;

    Response(JsonNode body) {
        this.body = body;
    }

}
