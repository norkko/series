package net.series.rest.api.series.service;

import net.series.rest.api.series.Series;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeriesService {

    /**
     * Gets account id using authorization header
     * Links appropriate account to series
     * Saves the series
     *
     * @param authentication
     * @param body
     */
    void saveSeries(Authentication authentication, Series body);

    /**
     * Gets account id using authorization header
     * Finds the account using found id
     * Finds the list of series of the account and removes where the seriesId match
     * Saves the account using injected accountService
     *
     * @param authentication
     * @param seriesId
     */
    void removeSeries(Authentication authentication, int seriesId);

    /**
     * Gets account id using authorization header
     * Finds the list of series using the account id
     *
     * @param authentication
     * @return all series
     */
    List<Series> getSeries(Authentication authentication);

}
