package net.series.rest.api.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;

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
    @OneToMany(mappedBy = "account")
    private List<Series> series;

    @JsonManagedReference
    @OneToMany(mappedBy = "account")
    private List<Episode> episodes;

}
