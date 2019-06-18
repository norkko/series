package net.series.rest.http;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    public Response send(String url) {
        try {
            JsonNode body = Unirest.get(url).asJson().getBody();
            logger.info("" + body);
            return this.respond(body);
        } catch (UnirestException e) {
            logger.error("Exception " + e);
        }
        return null;
    }

    private Response respond(JsonNode json) {
        return new Response(json);
    }

}
