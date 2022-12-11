package hu.verboczy.spendingtrackerbackend.service;

import hu.verboczy.spendingtrackerbackend.controller.dto.PaymentTypeDto;
import hu.verboczy.spendingtrackerbackend.dao.entity.PaymentType;
import hu.verboczy.spendingtrackerbackend.dao.repository.PaymentTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class PaymentTypeService {

    PaymentTypeRepository paymentTypeRepository;

    public List<String> getPaymentTypes() {
        return paymentTypeRepository.findAll().stream().map(PaymentType::getType).collect(Collectors.toList());
    }

    public void addPaymentType(String paymentType) {
        if (paymentTypeRepository.existsByType(paymentType)) {
            log.warn("Payment type [{}] already exists in the database, it won't be saved.", paymentType);
        } else {
            savePaymentType(paymentType);
        }
    }

    private void savePaymentType(String type) {
        log.trace("Saving payment type with name [{}]...", type);
        PaymentType paymentType = new PaymentType();
        paymentType.setType(type);
        PaymentType savedPaymentType = paymentTypeRepository.save(paymentType);
        log.debug("Payment type [{}] has been saved successfully. Generated id = [{}].", savedPaymentType.getType(), savedPaymentType.getId());
    }

    public void deletePaymentType(long id) {
        log.trace("Deleting payment type with id [{}]...", id);
        try {
            paymentTypeRepository.deleteById(id);
            log.debug("Payment type [{}] has been deleted successfully.", id);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.warn("Cannot delete payment type with id [{}], because the given id is not saved in the database.", id);
        }
    }

    public void updatePayment(PaymentTypeDto paymentTypeDto) {
        String newType = paymentTypeDto.getType();
        long id = paymentTypeDto.getId();

        log.trace("Updating payment type with id [{}] to [{}]", id, newType);

        validateUpdateRequest(paymentTypeDto);

        Optional<PaymentType> paymentTypeById = paymentTypeRepository.findById(id);
        if (paymentTypeById.isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no store with id [%d]", id));
        } else {
            PaymentType paymentType = paymentTypeById.get();
            String oldType = paymentType.getType();
            paymentType.setType(newType);
            paymentTypeRepository.save(paymentType);
            log.debug("Payment type [{}] has been renamed successfully from [{}] to [{}]", id, oldType, newType);
        }
    }

    private void validateUpdateRequest(PaymentTypeDto paymentTypeDto) {
        long id = paymentTypeDto.getId();
        if (!paymentTypeRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Won't update payment type, because there is no payment type with id [%d]", id));
        }
        if (paymentTypeRepository.existsByType(paymentTypeDto.getType())) {
            throw new IllegalArgumentException(String.format("Won't update payment type [%d], because a payment type already has type [%s].", id, paymentTypeDto.getType()));
        }
    }
}
