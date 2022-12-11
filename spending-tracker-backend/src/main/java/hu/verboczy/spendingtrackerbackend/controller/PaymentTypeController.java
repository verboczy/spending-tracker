package hu.verboczy.spendingtrackerbackend.controller;

import hu.verboczy.spendingtrackerbackend.controller.dto.PaymentTypeDto;
import hu.verboczy.spendingtrackerbackend.service.PaymentTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/paymentType")
public class PaymentTypeController {

    PaymentTypeService paymentTypeService;

    @PostMapping
    public void addPaymentType(@RequestBody String paymentType) {
        log.info("Request: addPaymentType(name=[{}])", paymentType);
        paymentTypeService.addPaymentType(paymentType);
    }

    @PutMapping
    public void updatePaymentType(@RequestBody PaymentTypeDto paymentType) {
        log.info("Request: updatePaymentType(id=[{}], newName=[{}])", paymentType.getId(), paymentType.getType());
        try {
            paymentTypeService.updatePayment(paymentType);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.warn(illegalArgumentException.getMessage());
        }
    }

    @GetMapping("/list")
    public List<String> getPaymentTypes() {
        log.info("Request: getPaymentTypes()");
        List<String> paymentTypes = paymentTypeService.getPaymentTypes();
        log.info("Response: getPaymentTypes() = [{}]", paymentTypes);
        return paymentTypes;
    }

    @DeleteMapping("/delete")
    public void deletePaymentType(@RequestParam long id) {
        log.info("Request: deletePaymentType(id=[{}])", id);
        paymentTypeService.deletePaymentType(id);
    }
}
