package net.series.rest.api.account.service;

import net.series.rest.api.account.Account;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    void save(Account account);
    void saveSeries(Series body, int id);
    List<Series> getSeries(int id);
    void saveEpisode(Episode body, int id);
    List<Episode> getEpisodesForSpecificSeries(int id, int seriesId);
}
