package net.series.rest.api.episode.service.impl;

import net.series.rest.api.account.Account;
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

    @Override
    public List<Episode> getEpisodes(Authentication authentication) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("fetching episodes for %s", id));
        return accountService.findById(id).getEpisodes();
    }

    // only allow saved series to be saved by episode
    public void saveEpisode(Authentication authentication, Episode episode)  {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("saving episode %s/%s for %s ", episode.getSeason(), episode.getEpisode(), id));
        episode.setAccount(accountService.findById(id));
        episodeRepository.save(episode);
    }

    @Override
    public void removeEpisode(Authentication authentication, int episodeId) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        Account account = accountService.findById(id);
        account.getEpisodes().removeIf(episode -> episode.getId() == episodeId);
        accountService.save(account);
    }

    public List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId) {
        int id = accountService.findByUsername(authentication.getName()).getId();
        logger.info(String.format("fetching episodes from %s for %s", seriesId, id));
        List<Episode> episodes = accountService.findById(id).getEpisodes();
        episodes.removeIf(item -> item.getSeries() != seriesId);
        return episodes;
    }

}
