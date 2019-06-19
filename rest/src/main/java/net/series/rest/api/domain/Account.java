package net.series.rest.api.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @JsonManagedReference
    @OneToMany(
            targetEntity = Series.class,
            mappedBy = "account")
    private List<Series> series;

    @JsonManagedReference
    @OneToMany(
            targetEntity = Episode.class,
            mappedBy = "account")
    private List<Episode> episodes;
}
