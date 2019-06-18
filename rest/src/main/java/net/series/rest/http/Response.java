package net.series.rest.http;

import lombok.Data;

/**
 * GSON Object
 */
@Data
public class Response {
    public int episode_number;
    public int season_number;
    public String overview;
    public int id;
}
