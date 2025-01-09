package com.baktybekov.demo.persistence;

import com.baktybekov.demo.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PaymentRepository extends CrudRepository<Payment, UUID> { }