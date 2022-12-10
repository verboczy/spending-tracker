package hu.verboczy.spendingtrackerbackend.dao.repository;

import hu.verboczy.spendingtrackerbackend.dao.entity.SpendingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingTypeRepository extends JpaRepository<SpendingType, Long> {
}
