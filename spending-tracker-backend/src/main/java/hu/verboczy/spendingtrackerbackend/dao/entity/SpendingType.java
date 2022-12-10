package hu.verboczy.spendingtrackerbackend.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "spending_type")
public class SpendingType {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String type;
}
