package net.series.rest.api.series;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import net.series.rest.api.account.Account;

import javax.persistence.*;

@Data
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int series;

    @JsonBackReference
    @ManyToOne
    private Account account;
}
