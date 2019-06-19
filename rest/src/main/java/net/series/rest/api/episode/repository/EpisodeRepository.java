package net.series.rest.api.episode.repository;

import net.series.rest.api.episode.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {

    /**
     * Sets an episode as watched
     * @param episode episode JSON body
     */
    Episode save(Episode episode);
}
