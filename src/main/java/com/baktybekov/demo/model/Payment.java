package com.baktybekov.demo.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JacksonXmlRootElement(localName = "Payment")
public class Payment {
    @Id
    private UUID id;
    private String source;
    private String destination;
    private BigDecimal amount;
    private LocalDateTime operationDate;

    public static Payment of(PaymentDTO paymentDTO) {
        var payment =  new Payment();
        payment.setId(UUID.randomUUID());
        payment.setSource(paymentDTO.from());
        payment.setDestination(paymentDTO.to());
        payment.setAmount(paymentDTO.amount());
        payment.setOperationDate(paymentDTO.operationDate());
        return payment;
    }

    @Override
    public String toString() {
        return String.format(
                "\nAmount:%s." +
                "\nReceiver:%s." +
                "\nSender:%s." +
                "\nDate:%s." +
                "\nReceipt number:%s",
        getAmount(), getDestination(), getSource(), getOperationDate(), getId()) ;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}