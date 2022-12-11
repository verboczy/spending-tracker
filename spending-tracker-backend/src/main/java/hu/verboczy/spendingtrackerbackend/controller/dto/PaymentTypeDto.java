package hu.verboczy.spendingtrackerbackend.controller.dto;

import lombok.Value;

@Value
public class PaymentTypeDto {
    long id;
    String type;
}
