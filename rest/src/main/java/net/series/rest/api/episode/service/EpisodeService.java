package net.series.rest.api.episode.service;

import net.series.rest.api.episode.Episode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EpisodeService {
    List<Episode> getEpisodes(Authentication authentication);
    void saveEpisode(Authentication authentication, Episode body);
    void saveEpisodesOfSeason(Authentication authentication, List<Episode> body);
    void removeEpisode(Authentication authentication, int episodeId);
    List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId);
}
