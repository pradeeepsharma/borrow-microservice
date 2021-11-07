package com.learning.microservices.borrowbookservice.service;

import com.learning.microservices.borrowbookservice.bean.Book;
import com.learning.microservices.borrowbookservice.bean.BorrowBook;
import com.learning.microservices.borrowbookservice.bean.BorrowStatus;
import com.learning.microservices.borrowbookservice.bean.User;
import com.learning.microservices.borrowbookservice.config.ApplicationConfig;
import com.learning.microservices.borrowbookservice.exception.BookNotAvailableException;
import com.learning.microservices.borrowbookservice.exception.BookNotFoundException;
import com.learning.microservices.borrowbookservice.exception.BookingNotFoundException;
import com.learning.microservices.borrowbookservice.repository.BookRepository;
import com.learning.microservices.borrowbookservice.repository.BorrowBookRepository;
import com.learning.microservices.borrowbookservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BorrowBookService {

    @Autowired
    BorrowBookRepository borrowBookRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    public BorrowBook borrowABook(BorrowBook borrowingBook) {
        BorrowBook bookBorrowed = new BorrowBook();
        Book bookDetails = bookRepository.getBookDetails(borrowingBook.getBookId());
        if (bookDetails.getAvailableBooks() <= 0)
            throw new BookNotAvailableException(borrowingBook.getBookId());
        User userDetails = userRepository.getUserDetails(borrowingBook.getUserId());

            updateBookAvailability(bookDetails);
            bookRepository.updateBookDetails(bookDetails);
            createBorrowingBookDetails(borrowingBook, bookDetails, userDetails);
            bookBorrowed = borrowBookRepository.save(borrowingBook);

        return bookBorrowed;
    }


    public BorrowBook getBorrowBook(Long borrowBookId)  {

        return borrowBookRepository.findById(borrowBookId).orElseThrow(BookingNotFoundException::new);
    }

    public List<BorrowBook> getBorrowBooks() {
        List<BorrowBook> borrowBooks = new ArrayList<>();
        borrowBookRepository.findAll().forEach(borrowBooks::add);
        return borrowBooks;
    }

    public boolean updateBooking(BorrowBook borrowBook) {
        BorrowBook updatedBooking = borrowBookRepository.save(borrowBook);
        boolean updateSuccess = false;
        if(updatedBooking.getBookId()!=null)
            updateSuccess = true;
        return updateSuccess;
    }


    private boolean updateBookAvailability(Book book) throws BookNotFoundException {
        boolean updated = true;

        int count = book.getAvailableBooks() - 1;
        book.setAvailableBooks(count);
        if (count == 0)
            book.setStatus("Not Available");

        return updated;
    }

    private void createBorrowingBookDetails(BorrowBook borrowingBook, Book bookDetails, User userDetails) {
        borrowingBook.setBookId(bookDetails.getId());
        borrowingBook.setUserId(userDetails.getUserid());
        borrowingBook.setBookingDate(new Date());
        borrowingBook.setBorrowStatus(BorrowStatus.BORROWED);
    }
}
