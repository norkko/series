package net.series.rest.http.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import net.series.rest.api.series.Series;
import net.series.rest.http.Request;
import net.series.rest.http.Response;
import net.series.rest.http.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(value = "/api")
public class HttpController {

    @Autowired
    private Request request;

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    @RequestMapping(
            value = "/search",
            method = RequestMethod.GET)
    public Response searchWithQuery(
            @RequestParam(value = "query") String query) throws UnirestException, UnsupportedEncodingException {
        logger.info("Decoded string: ", query);
        String encodedString = java.net.URLEncoder.encode(query, "UTF-8").replace("+", "%20");
        logger.info("Encoded string: ", encodedString);
        logger.info("GET Query");
        return request.send(new Url(encodedString).toString());
    }

    @RequestMapping(
            value = "/series/{id}",
            method = RequestMethod.GET)
    public Response searchSpecificSeries(
            @PathVariable int id) throws UnirestException {
        logger.info("GET series");
        return request.send(new Url(id).toString());
    }

    /**
     * Takes a list of series and for each series fetches
     * information and returns a list of all series
     *
     */
    @RequestMapping(
            value = "/series",
            method = RequestMethod.POST)
    public List<Response> fetchSeveralSeries(
            @RequestBody List<Series> list) throws InterruptedException, ExecutionException {
        List<Callable<Response>> callableList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            callableList.add(new performRequest(list.get(i).getSeries()));
        }

        final ExecutorService service = Executors.newFixedThreadPool(list.size());
        List<Future<Response>> futureObjects = service.invokeAll(callableList);
        List<Response> series = new ArrayList<>();
        for (Future<Response> obj : futureObjects) {
            series.add(obj.get());
        }

        return series;
    }

    private class performRequest implements Callable<Response> {
        private int seriesId;

        private performRequest(int seriesId) {
            this.seriesId = seriesId;
        }

        @Override
        public Response call() throws UnirestException {
            return request.send(new Url(seriesId).toString());
        }
    }

    @RequestMapping(
            value = "/series/{id}/{season}",
            method = RequestMethod.GET)
    public Response searchSpecificSeriesSeason(
            @PathVariable int id,
            @PathVariable int season) throws UnirestException {
        logger.info("GET season");
        return request.send(new Url(id, season).toString());
    }

    @RequestMapping(
            value = "/series/{id}/{season}/{episode}",
            method = RequestMethod.GET)
    public Response searchSpecificSeriesEpisode (
            @PathVariable int id,
            @PathVariable int season,
            @PathVariable int episode) throws UnirestException {
        logger.info("GET episode");
        return request.send(new Url(id, season, episode).toString());
    }

    @RequestMapping(
            value = "/series/popular",
            method = RequestMethod.GET)
    public Response fetchPopularSeries () throws UnirestException {
        logger.info("GET popular series");
        return request.send(new Url().toString());
    }

}
