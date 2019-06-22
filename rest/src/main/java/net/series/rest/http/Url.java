package net.series.rest.http;

import lombok.Data;

@Data
public class Url {
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("API_KEY");

    private Integer id;
    private Integer season;
    private Integer episode;
    private String searchQuery;

    public Url(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Url(Integer id) {
        this.id = id;
    }

    public Url(Integer id, Integer season) {
        this.id = id;
        this.season = season;
    }

    public Url(Integer id, Integer season, Integer episode) {
        this.id = id;
        this.season = season;
        this.episode = episode;
    }

    @Override
    public String toString() {
        return this.searchQuery != null ?
                String.format("%s/search/tv?api_key=%s&language=en-US&query=%s", BASE_URL, API_KEY, this.searchQuery) : this.episode != null ?
                String.format("%s/tv/%s/season/%s/episode/%s?api_key=%s&language=en-US", BASE_URL, this.id, this.season, this.episode, API_KEY) : this.season != null ?
                String.format("%s/tv/%s/season/%s?api_key=%s&language=en-US", BASE_URL, this.id, this.season, API_KEY) :
                String.format("%s/tv/%s?api_key=%s&language=en-US", BASE_URL, this.id, API_KEY);
    }

}
