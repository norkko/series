package net.series.rest.api.account;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.series.rest.api.episode.Episode;
import net.series.rest.api.series.Series;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "account",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Series> series;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "account",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Episode> episodes;

}
