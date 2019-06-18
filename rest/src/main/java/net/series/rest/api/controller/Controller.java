package net.series.rest.api.controller;

import net.series.rest.http.Request;
import org.springframework.web.bind.annotation.*;


@RestController(value = "/api")
public class Controller {

    private static final String BASE_URL = System.getenv("API_URL");

    @RequestMapping(value = "/series/query", method = RequestMethod.POST)
    public void searchWithQuery(
            @PathVariable String query) {
        // search for series with search query.
        Request request = new Request();

    }

    @RequestMapping(value = "/series/id", method = RequestMethod.POST)
    public void searchSpecificSeries(
            @PathVariable int id) {
        // search for specific series with series id.

    }

    @RequestMapping(value = "/series/id/season", method = RequestMethod.POST)
    public void searchSpecificSeriesSeason(
            @PathVariable int id,
            @PathVariable int season) {
        // search for specific series season
    }

    @RequestMapping(value = "/series/id/season/episode", method = RequestMethod.POST)
    public void searchSpecificSeriesEpisode(
            @PathVariable int id,
            @PathVariable int season,
            @PathVariable int episode) {
    }

}
