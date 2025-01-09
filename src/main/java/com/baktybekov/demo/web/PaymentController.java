package com.baktybekov.demo.web;

import com.baktybekov.demo.model.Payment;
import com.baktybekov.demo.model.PaymentDTO;
import com.baktybekov.demo.service.PaymentService;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping( "/pay")
public class PaymentController {
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Payment> pay(@RequestBody @Valid PaymentDTO paymentDTO) {
        Payment result = paymentService.pay(Payment.of(paymentDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();
        logger.info("\nCreated new payment record at path:{}", location);
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_XML_VALUE,
                path = "/{id}")
    public ResponseEntity<?> check(@PathVariable UUID id) {
        Optional<Payment> payment = paymentService.check(id);
        if(payment.isPresent()) {
            logger.info("\nCheck with identifier {}:{{}}", id, payment.get());
            return ResponseEntity.ok(payment.get());
        }
        logger.error("\nNo such check with identifier:{}", id);
        return ResponseEntity.badRequest().body("No such element");
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<PaymentListResult> findAll() {
        return ResponseEntity.ok().body(
                new PaymentListResult(paymentService.findAll())
        );
    }

    @JacksonXmlRootElement(localName = "Payment-list")
    public static class PaymentListResult {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Payment-item")
        private final Iterable<Payment> payment;
        public PaymentListResult(Iterable<Payment> payment) {
            this.payment = payment;
        }
    }
}