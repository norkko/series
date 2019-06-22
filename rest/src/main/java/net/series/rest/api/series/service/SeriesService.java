package net.series.rest.api.series.service;

import net.series.rest.api.series.Series;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeriesService {

    /**
     *
     * @param authentication
     * @param body
     */
    Series saveSeries(Authentication authentication, Series body);

    /**
     *
     * @param authentication
     * @param seriesId
     */
    void removeSeries(Authentication authentication, int seriesId);

    /**
     *
     * @param authentication
     */
    List<Series> getSeries(Authentication authentication);

}
