package net.series.rest.api.episode.service;

import net.series.rest.api.episode.Episode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EpisodeService {

    /**
     * Gets account id using authorization header
     * Fetches episodes list of an account using found id
     *
     * @param authentication
     * @return found episodes
     */
    List<Episode> getEpisodes(Authentication authentication);

    /**
     * Gets account id using authorization header
     * Links appropriate account to episode
     * Saves the episode
     *
     * @param authentication
     * @param body
     */
    void saveEpisode(Authentication authentication, Episode body);

    /**
     * Gets account id using authorization header
     * Finds the account using found id
     * Fetches the list of episodes of the account and removes where the episodeId match
     * Saves the account using injected accountService
     *
     * @param authentication
     * @param episode object body
     */
    void removeEpisode(Authentication authentication, Episode episode);

    /**
     * Gets account id using authorization header
     * Creates a list of episodes and filters out episodes
     * where the series id doesn't match
     *
     * @param authentication
     * @param seriesId
     * @return episodes found having appropriate series id
     */
    List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId);
}
