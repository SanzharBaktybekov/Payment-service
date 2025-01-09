package com.baktybekov.demo.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDTO(
        @NotNull(message = "Sender of payment should be provided!")
        String from,
        @NotNull(message = "Receiver of payment should be provided!")
        String to,
        @DecimalMin(value = "0", inclusive = false, message = "Amount should be more than zero!")
        @NotNull(message = "Amount should be provided!")
        BigDecimal amount,
        @NotNull(message = "Operation date should be provided!")
        @PastOrPresent(message = "Operation date can not be in future!")
        LocalDateTime operationDate) {
}