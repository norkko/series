package net.series.rest.http.controller;

import net.series.rest.http.Request;
import net.series.rest.http.Response;
import net.series.rest.http.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class HttpController {

    // todo, authentication on all but search?

    @Autowired
    private Request request;

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    @RequestMapping(
            value = "/search/{query}",
            method = RequestMethod.GET)
    public Response searchWithQuery(
            @PathVariable String query) { // dont use PathVariable for query.
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
