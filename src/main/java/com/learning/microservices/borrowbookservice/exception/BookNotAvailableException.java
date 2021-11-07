package com.learning.microservices.borrowbookservice.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(Long bookId) {
        super("Request book is not available for booking :" + bookId);
    }
}
