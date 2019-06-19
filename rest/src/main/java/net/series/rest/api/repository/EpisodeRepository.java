package net.series.rest.api.repository;

import net.series.rest.api.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    Episode save(Episode episode);
}
