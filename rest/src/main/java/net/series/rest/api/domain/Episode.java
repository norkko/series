package net.series.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int series;
    private int season;
    private int episode;

    @JsonBackReference
    @ManyToOne
    private Account account;

    Map<Series, Episode> seen;
}

