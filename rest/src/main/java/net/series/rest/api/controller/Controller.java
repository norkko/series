package net.series.rest.api.controller;

import net.series.rest.api.domain.Account;
import net.series.rest.api.domain.Episode;
import net.series.rest.api.domain.Series;
import net.series.rest.api.repository.AccountRepository;
import net.series.rest.api.repository.EpisodeRepository;
import net.series.rest.api.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    private static final Logger logger = LoggerFactory.getLogger(HttpController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody Account account) {
        accountRepository.save(account);
    }

    @RequestMapping(value = "/series/{id}", method = RequestMethod.POST)
    public void saveSeries(@RequestBody Series body, @PathVariable int id) {
        Account account = accountRepository.findById(id);
        Series series = new Series();
        series.setSeries(body.getSeries());
        series.setAccount(account);
        seriesRepository.save(series);
    }

    @RequestMapping(value = "/series/{id}", method = RequestMethod.GET)
    public List<Series> getSeries(@PathVariable int id) {
        return accountRepository.findById(id).getSeries();
    }


    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.POST)
    public void saveEpisode(@RequestBody Episode body, @PathVariable int id) {
        Account account = accountRepository.findById(id);
        Episode episode = new Episode();
        episode.setSeries(body.getSeries());
        episode.setEpisode(body.getEpisode());
        episode.setSeason(body.getSeason());
        episode.setAccount(account);
        episodeRepository.save(episode);
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.GET)
    public List<Episode> getEpisodes(@PathVariable int id) {
        return accountRepository.findById(id).getEpisodes();
    }
}
