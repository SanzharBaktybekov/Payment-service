package com.baktybekov.demo.service;

import com.baktybekov.demo.model.Payment;
import com.baktybekov.demo.persistence.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment pay(Payment payment) {
        paymentRepository.save(payment);
        logger.info("\nPayment saved: {}", payment);
        return payment;
    }

    public Optional<Payment> check(UUID uuid) {
        return paymentRepository.findById(uuid);
    }

    public Iterable<Payment> findAll() {
        logger.info("\nTotal payment records: " + paymentRepository.count());
        return paymentRepository.findAll();
    }
}