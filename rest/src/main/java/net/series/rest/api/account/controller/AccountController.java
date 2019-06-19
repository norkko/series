package net.series.rest.api.account.controller;

import net.series.rest.api.account.domain.Account;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AccountController {

    // todo error handling, validation, move some methods into episode/series controller/services

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody Account account) {
        accountService.save(account);
    }

    @RequestMapping(value = "/series", method = RequestMethod.POST)
    public void saveSeries(Authentication authentication, @RequestBody Series body) {
        accountService.saveSeries(body, accountService.findByUsername(authentication.getName()).getId());
    }

    @RequestMapping(value = "/series", method = RequestMethod.GET)
    public List<Series> getSeries(Authentication authentication) {
        return accountService.getSeries(accountService.findByUsername(authentication.getName()).getId());
    }

    @RequestMapping(value = "/episodes", method = RequestMethod.POST)
    public void saveEpisode(Authentication authentication, @RequestBody Episode body) {
        accountService.saveEpisode(body, accountService.findByUsername(authentication.getName()).getId());
    }

    @RequestMapping(value = "/episodes", method = RequestMethod.GET)
    public List<Episode> getEpisodes(Authentication authentication) {
        return accountService.getEpisodes(accountService.findByUsername(authentication.getName()).getId());
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.GET)
    public List<Episode> getEpisodesForSpecificSeries(Authentication authentication, @PathVariable int id) {
        return accountService.getEpisodesForSpecificSeries(accountService.findByUsername(authentication.getName()).getId(), id);
    }

}
