package net.series.rest.api.episode.controller;

import net.series.rest.api.episode.Episode;
import net.series.rest.api.episode.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @RequestMapping(
            value = "/episodes",
            method = RequestMethod.GET)
    public List<Episode> getEpisodes(
            Authentication authentication) {
        return episodeService.getEpisodes(authentication);
    }

    @RequestMapping(
            value = "/episodes",
            method = RequestMethod.POST)
    public void saveEpisode (
            Authentication authentication,
            @Valid @RequestBody Episode body) {
        episodeService.saveEpisode(authentication, body);
    }

    @RequestMapping(
            value = "/episodes/season",
            method = RequestMethod.POST)
    public void saveEpisodesOfSeason(
            Authentication authentication,
            @RequestBody List<Episode> body) {
        episodeService.saveEpisodesOfSeason(authentication, body);
    }

    @RequestMapping(
            value = "/episodes/{id}",
            method = RequestMethod.DELETE)
    public void removeEpisode(
            Authentication authentication,
            @PathVariable int id) {
        episodeService.removeEpisode(authentication, id);
    }

    @RequestMapping(
            value = "/episodes/{id}",
            method = RequestMethod.GET)
    public List<Episode> getEpisodesForSpecificSeries(
            Authentication authentication,
            @PathVariable int id) {
        return episodeService.getEpisodesForSpecificSeries(authentication, id);
    }
}
