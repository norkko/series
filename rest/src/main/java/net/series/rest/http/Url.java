package net.series.rest.http;

import lombok.Data;

@Data
public class Url {
    private final String BASE_URL = System.getenv("API_URL");
    private final String API_KEY = System.getenv("API_KEY");

    private String operation; // make to enum
    private Integer id;
    private Integer season;
    private Integer episode;
    private String searchQuery;

    Url(String operation, String searchQuery) {
        this.operation = operation;
        this.searchQuery = searchQuery;
    }

    Url(String operation, Integer id) {
        this.operation = operation;
        this.id = id;
    }

    Url(String operation, Integer id, Integer season) {
        this.operation = operation;
        this.id = id;
        this.season = season;
    }

    Url(String operation, Integer id, Integer season, Integer episode) {
        this.operation = operation;
        this.id = id;
        this.season = season;
        this.episode = episode;
    }

    @Override
    public String toString() {
        return this.searchQuery != null ?
                String.format("%s %s", this.BASE_URL, this.searchQuery) : this.episode != null ?
                String.format("%s %s %s", this.BASE_URL,this.season, this.episode) : this.season != null ?
                String.format("%s %s", this.BASE_URL, this.season) : 
                String.format("%s %s", this.BASE_URL, this.id);
    }
}
