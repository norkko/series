package net.series.rest.api.episode.service.impl;

import net.series.rest.api.account.domain.Account;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.episode.repository.EpisodeRepository;
import net.series.rest.api.episode.service.EpisodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EpisodeServiceImpl implements EpisodeService {

    private static final Logger logger = LoggerFactory.getLogger(EpisodeServiceImpl.class);

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private AccountService accountService;

    // dont allow dupes
    // only allow saved series to be saved by episode
    public void saveEpisode(Authentication authentication, Episode body) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("saving episode %s/%s for %s ", body.getSeason(), body.getEpisode(), id));

        Episode episode = new Episode();
        episode.setSeries(body.getSeries());
        episode.setEpisode(body.getEpisode());
        episode.setSeason(body.getSeason());
        episode.setAccount(accountService.findById(id));
        episodeRepository.save(episode);
    }

    @Override
    public void saveEpisodesOfSeason(Authentication authentication, List<Episode> body) {
        // todo
    }

    @Override
    public void removeEpisode(Authentication authentication, int episodeId) {
        // todo
    }

    public List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("fetching episodes from %s for %s", seriesId, id));
        List<Episode> episodes = accountService.findById(id).getEpisodes();
        episodes.removeIf(item -> item.getSeries() != seriesId);
        return episodes;
    }

    @Override
    public List<Episode> getEpisodes(Authentication authentication) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("fetching episodes for %s", id));
        return accountService.findById(id).getEpisodes();
    }
}
