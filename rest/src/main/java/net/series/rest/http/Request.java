package net.series.rest.http;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    public Response send(String url) throws UnirestException {
        logger.info("sending GET");
        HttpResponse<JsonNode> json = Unirest.get(url).asJson();
        String body = json.getBody().toString();
        return new Gson().fromJson(body, Response.class);
    }

}
