package net.series.rest.http;

import com.google.gson.Gson;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    public Response send(String url) {
        try {
            HttpResponse<JsonNode> json = Unirest.get(url).asJson();
            logger.info("" + json.getBody());

            Gson gson = new Gson();
            String body = json.getBody().toString();
            Response obj = gson.fromJson(body, Response.class);
            
            logger.info("" + obj);
            return obj;
        } catch (Exception e) {
            logger.error("Exception " + e);
        }
        return null;
    }

}
