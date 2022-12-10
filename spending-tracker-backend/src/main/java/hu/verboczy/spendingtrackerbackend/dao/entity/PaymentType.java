package hu.verboczy.spendingtrackerbackend.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment_type")
public class PaymentType {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String type;
}
