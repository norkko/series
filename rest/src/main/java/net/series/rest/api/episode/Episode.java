package net.series.rest.api.episode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import net.series.rest.api.account.domain.Account;

import javax.persistence.*;

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
}

