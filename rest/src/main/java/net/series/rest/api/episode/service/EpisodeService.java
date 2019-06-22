package net.series.rest.api.episode.service;

import net.series.rest.api.episode.Episode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EpisodeService {

    /**
     *
     * @param authentication
     */
    List<Episode> getEpisodes(Authentication authentication);

    /**
     *
     * @param authentication
     * @param body
     */
    void saveEpisode(Authentication authentication, Episode body);

    /**
     *
     * @param authentication
     * @param episodeId
     */
    void removeEpisode(Authentication authentication, int episodeId);

    /**
     *
     * @param authentication
     * @param seriesId
     */
    List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId);
}
