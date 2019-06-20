package net.series.rest.api.series.service;

import net.series.rest.api.series.Series;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeriesService {
    void saveSeries(Authentication authentication, Series body);
    void removeSeries(Authentication authentication, int seriesId);
    List<Series> getSeries(Authentication authentication);
}
