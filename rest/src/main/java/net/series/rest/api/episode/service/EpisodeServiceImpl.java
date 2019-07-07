package net.series.rest.api.episode.service;

import net.series.rest.api.account.Account;
import net.series.rest.api.account.service.AccountService;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.episode.repository.EpisodeRepository;
import net.series.rest.api.exception.type.BadRequestException;
import net.series.rest.api.series.Series;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Episode> getEpisodes(Authentication authentication) {
        int id = getId(authentication);

        logger.info("Episodes fetched");
        return accountService.findById(id).getEpisodes();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void saveEpisode(Authentication authentication, Episode episode)  {
        int id = getId(authentication);

        List<Episode> list = episodeRepository.findAll();
        if (list.stream().anyMatch(item ->
                item.getId() == id &&
                item.getEpisode() == episode.getEpisode() &&
                item.getSeason() == episode.getSeason() &&
                item.getSeries() == episode.getSeries())) {
            throw new BadRequestException("Episode already added");
        }

        episode.setAccount(accountService.findById(id));
        episodeRepository.save(episode);

        logger.info("Episode saved");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEpisode(Authentication authentication, Episode episode) {
        int id = getId(authentication);

        Account account = accountService.findById(id);
        account.getEpisodes().removeIf(e ->
                e.getSeries() == episode.getSeries() &&
                e.getSeason() == episode.getSeason() &&
                e.getEpisode() == episode.getEpisode());
        accountService.save(account);
        logger.info("Episode removed");
    }

    /**
     * {@inheritDoc}
     */
    public List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId) {
        int id = getId(authentication);

        List<Episode> episodes = accountService.findById(id).getEpisodes();
        episodes.removeIf(item -> item.getSeries() != seriesId);

        logger.info("Episodes fetched from series" + seriesId);
        return episodes;
    }

    /**
     * Helper method for finding account ids,
     * using an authorization header on requests
     *
     * @param authentication
     * @return found account id
     */
    private int getId(Authentication authentication) {
        return accountService.findByUsername(authentication.getName()).getId();
    }

}
