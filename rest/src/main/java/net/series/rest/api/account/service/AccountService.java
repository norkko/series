package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    /**
     * Stores an account
     * @param account account body
     */
    void save(Account account);

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
     * Stores an episode for an account
     * @param body episode body
     * @param id account id
     */
    void saveEpisode(Episode body, int id);

    /**
     * Fetches all episodes for a specific series
     * @param id account id
     * @param seriesId series id
     */
    List<Episode> getEpisodesForSpecificSeries(int id, int seriesId);
}
