package com.learning.microservices.borrowbookservice.controller;


import com.learning.microservices.borrowbookservice.bean.BorrowBook;
import com.learning.microservices.borrowbookservice.bean.BorrowStatus;
import com.learning.microservices.borrowbookservice.exception.BookNotFoundException;
import com.learning.microservices.borrowbookservice.service.BorrowBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class BorrowBookController {
    private static final Logger logger = LoggerFactory.getLogger(BorrowBookController.class);
    @Autowired
    private BorrowBookService borrowBookService;


    @PostMapping("/borrowbook")
    public ResponseEntity<BorrowBook> addBorrowBookEntry(@RequestBody BorrowBook borrowBook) throws BookNotFoundException {
        logger.info("Borrowing a book :" + borrowBook);
        BorrowBook borrowedBook = new BorrowBook();

        borrowedBook = borrowBookService.borrowABook(borrowBook);

        return new ResponseEntity<>(borrowedBook, HttpStatus.CREATED);
    }
    @GetMapping("/borrowbook/{borrowId}")
    public ResponseEntity<BorrowBook> getBorrowBook(@PathVariable Long borrowId){
        return new ResponseEntity<>(borrowBookService.getBorrowBook(borrowId), HttpStatus.OK);
    }
    @GetMapping("/borrowbook")
    public ResponseEntity<List<BorrowBook>> getBorrowBooks(){
        return new ResponseEntity<>(borrowBookService.getBorrowBooks(), HttpStatus.OK);
    }

    @PostMapping("/borrowbook/update")
    public ResponseEntity<String> updateBooking(@RequestBody BorrowBook borrowBook){
        boolean updateStatus = borrowBookService.updateBooking(borrowBook);
        if(updateStatus)
            return new ResponseEntity<>("Updated successfully", HttpStatus.ACCEPTED);
        else
            return (ResponseEntity<String>) ResponseEntity.notFound();

    }

}
