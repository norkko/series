package net.series.rest.http;

import lombok.Data;

import java.util.List;

/**
 * GSON Object
 */
@Data
public class Response {
    public int id;
    public int number_of_seasons;
    public String overview;

    public List<Result> results;
    public List<Season> seasons;
    public List<Episode> episodes;

    @Data
    class Result {
        int id;
        String name;
        String overview;
        String first_air_date;
        String original_name;
    }

    @Data
    class Season {
        int season_number;
        int episode_count;
        String name;
        String overview;
    }

    @Data
    class Episode {
        int season_number;
        int episode_number;
        String name;
        String overview;
    }
}
