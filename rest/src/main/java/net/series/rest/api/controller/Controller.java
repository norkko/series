package net.series.rest.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController(value = "/api")
public class Controller {

    @RequestMapping(value = "/series/query", method = RequestMethod.POST)
    public void searchWithQuery(
            @PathVariable String query) {
        // search for series with search query.

        // decide on how to handle the search query
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
        // search for specific series season
    }

}
