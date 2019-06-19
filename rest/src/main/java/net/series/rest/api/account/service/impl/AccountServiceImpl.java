package net.series.rest.api.account.service.impl;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.repository.AccountRepository;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.episode.repository.EpisodeRepository;
import net.series.rest.api.series.Series;
import net.series.rest.api.series.repository.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    public void saveSeries(Series body, int id) {
        logger.info(String.format("saving series %s for %s", body.getSeries(), id));
        Account account = accountRepository.findById(id);
        Series series = new Series();
        series.setSeries(body.getSeries());
        series.setAccount(account);
        seriesRepository.save(series);
    }

    public List<Series> getSeries(int id) {
        logger.info(String.format("fetching series for %s", id));
        return accountRepository.findById(id).getSeries();
    }

    public void saveEpisode(Episode body, int id) {
        logger.info(String.format("saving episode %s/%s for %s ", body.getSeason(), body.getEpisode(), id));
        Account account = accountRepository.findById(id);
        Episode episode = new Episode();
        episode.setSeries(body.getSeries());
        episode.setEpisode(body.getEpisode());
        episode.setSeason(body.getSeason());
        episode.setAccount(account);
        episodeRepository.save(episode);
    }

    public List<Episode> getEpisodesForSpecificSeries(int id, int seriesId) {
        logger.info(String.format("fetching episodes from %s for %s", seriesId, id));
        List<Episode> episodes = accountRepository.findById(id).getEpisodes();
        episodes.removeIf(item -> item.getSeries() != seriesId);
        return episodes;
    }

}
