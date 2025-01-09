package com.baktybekov.demo.service;

import com.baktybekov.demo.exception.ReceiverOfPaymentNotFound;
import com.baktybekov.demo.model.Payment;
import com.baktybekov.demo.persistence.PaymentRepository;
import com.baktybekov.demo.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public Payment pay(Payment payment) {
        String paymentDestination = payment.getDestination();
        if(userRepository.findUserByUsername(paymentDestination).isEmpty()) {
            throw new ReceiverOfPaymentNotFound("Not found user with name:" + paymentDestination);
        }
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