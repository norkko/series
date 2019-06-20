package net.series.rest.api.series.controller;

import net.series.rest.api.series.Series;
import net.series.rest.api.series.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @RequestMapping(
            value = "/series/save",
            method = RequestMethod.POST)
    public void saveSeries(
            Authentication authentication,
            @RequestBody Series body) {
        seriesService.saveSeries(authentication, body);
    }

    @RequestMapping(
            value = "/series/{id}",
            method = RequestMethod.DELETE)
    public void removeSeries(
            Authentication authentication,
            @PathVariable int id) {
        seriesService.removeSeries(authentication, id);
    }

    @RequestMapping(
            value = "/series",
            method = RequestMethod.POST)
    public List<Series> getSeries(
            Authentication authentication) {
        return seriesService.getSeries(authentication);
    }
}
