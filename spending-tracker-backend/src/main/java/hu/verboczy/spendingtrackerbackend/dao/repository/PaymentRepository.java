package hu.verboczy.spendingtrackerbackend.dao.repository;

import hu.verboczy.spendingtrackerbackend.dao.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
