package net.series.rest.api.episode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import net.series.rest.api.account.Account;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(1)
    private int series;

    @Min(1)
    private int season;

    @Min(1)
    private int episode;

    @JsonBackReference
    @ManyToOne
    private Account account;
}

