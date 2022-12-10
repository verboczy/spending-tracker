package hu.verboczy.spendingtrackerbackend.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;
}
