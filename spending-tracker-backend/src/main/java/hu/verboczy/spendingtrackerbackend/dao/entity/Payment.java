package hu.verboczy.spendingtrackerbackend.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "spending_type_id", nullable = false)
    private SpendingType spendingType;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", nullable = false)
    private PaymentType paymentType;

}
