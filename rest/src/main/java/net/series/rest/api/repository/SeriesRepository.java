package net.series.rest.api.repository;

import net.series.rest.api.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    Series save(Series Series);
}
