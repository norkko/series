package net.series.rest.api.series.repository;

import net.series.rest.api.series.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {

    Series save(Series series);

}
