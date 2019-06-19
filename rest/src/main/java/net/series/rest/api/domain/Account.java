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
    int id;

    String username;
    String password;

    @JsonManagedReference
    @OneToMany(
            targetEntity = Series.class,
            mappedBy = "account")
    List<Series> series;

}
