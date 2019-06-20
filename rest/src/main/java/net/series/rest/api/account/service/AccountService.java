package net.series.rest.api.account.service;

import net.series.rest.api.account.domain.Account;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService extends UserDetailsService {

    void save(Account account);

    Account findById(int id);

    void saveSeries(Authentication authentication, Series body);

    void removeSeries(Authentication authentication, int seriesId);

    List<Series> getSeries(Authentication authentication);

    void saveSeason(Authentication authentication, Episode body);

    void saveEpisode(Authentication authentication, Episode body);

    void removeEpisode(Authentication authentication, int episodeId);

    List<Episode> getEpisodesForSpecificSeries(Authentication authentication, int seriesId);

    List<Episode> getEpisodes(Authentication authentication);

    Account findByUsername(String username);

}
