package com.baktybekov.demo.exception;

public class ReceiverOfPaymentNotFound extends RuntimeException {
    public ReceiverOfPaymentNotFound(String message) {
        super(message);
    }
}
