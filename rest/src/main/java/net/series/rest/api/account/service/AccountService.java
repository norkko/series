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

    /**
     * Stores an account
     * @param account account body
     */
    void save(Account account);

    /**
     * Fetches account by id
     * @param id account id
     */
    Account findById(int id);

    /**
     * Stores a series for an account
     * @param body series body
     * @param id account id
     */
    void saveSeries(Series body, int id);

    /**
     * Fetches all series for an account
     * @param id account id
     */
    List<Series> getSeries(int id);

    /**
     * Sets a season as watched
     * @param body episode body
     * @param id account id
     */
    void saveSeason(Episode body, int id);

    /**
     * Sets an episode as watched
     * @param body episode body
     * @param id account id
     */
    void saveEpisode(Episode body, int id);

    /**
     * Fetches all watched episodes of specific series
     * @param id account id
     * @param seriesId series id
     */
    List<Episode> getEpisodesForSpecificSeries(int id, int seriesId);

    /**
     * Fetches all watched episodes
     * @param id account id
     */
    List<Episode> getEpisodes(int id);

    /**
     * Fetches account by username field
     * @param username username field
     */
    Account findByUsername(String username);

}
