package net.series.rest.http.controller;

import net.series.rest.api.series.Series;
import net.series.rest.http.Request;
import net.series.rest.http.Response;
import net.series.rest.http.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
            @RequestParam(value = "query") String query) {
        logger.info("" + query);
        logger.info("GET Query");
        return request.send(new Url(query).toString());
    }

    @RequestMapping(
            value = "/series/{id}",
            method = RequestMethod.GET)
    public Response searchSpecificSeries(
            @PathVariable int id) {
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
        for (Future<Response> x : futureObjects) {
            series.add(x.get());
        }

        return series;
    }

    private class performRequest implements Callable<Response> {
        private int seriesId;

        private performRequest(int seriesId) {
            this.seriesId = seriesId;
        }

        @Override
        public Response call() {
            return request.send(new Url(seriesId).toString());
        }
    }

    @RequestMapping(
            value = "/series/{id}/{season}",
            method = RequestMethod.GET)
    public Response searchSpecificSeriesSeason(
            @PathVariable int id,
            @PathVariable int season) {
        logger.info("GET season");
        return request.send(new Url(id, season).toString());
    }

    @RequestMapping(
            value = "/series/{id}/{season}/{episode}",
            method = RequestMethod.GET)
    public Response searchSpecificSeriesEpisode(
            @PathVariable int id,
            @PathVariable int season,
            @PathVariable int episode) {
        logger.info("GET episode");
        return request.send(new Url(id, season, episode).toString());
    }

}
