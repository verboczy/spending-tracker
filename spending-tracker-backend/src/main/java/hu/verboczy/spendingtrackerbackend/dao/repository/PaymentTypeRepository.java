package hu.verboczy.spendingtrackerbackend.dao.repository;

import hu.verboczy.spendingtrackerbackend.dao.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
