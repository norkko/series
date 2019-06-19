package net.series.rest.api.account.controller;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody Account account) {
        accountService.save(account);
    }

    @RequestMapping(value = "/series/{id}", method = RequestMethod.POST)
    public void saveSeries(@RequestBody Series body, @PathVariable int id) {
        accountService.saveSeries(body, id);
    }

    @RequestMapping(value = "/series/{id}", method = RequestMethod.GET)
    public List<Series> getSeries(@PathVariable int id) {
        return accountService.getSeries(id);
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.POST)
    public void saveEpisode(@RequestBody Episode body, @PathVariable int id) {
        accountService.saveEpisode(body, id);
    }

    @RequestMapping(value = "/episodes/{id}/{seriesId}", method = RequestMethod.GET)
    public List<Episode> getEpisodesForSpecificSeries(@PathVariable int id, @PathVariable int seriesId) {
        return accountService.getEpisodesForSpecificSeries(id, seriesId);
    }

}
