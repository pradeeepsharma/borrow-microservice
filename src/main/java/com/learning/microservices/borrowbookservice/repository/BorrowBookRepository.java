package com.learning.microservices.borrowbookservice.repository;

import com.learning.microservices.borrowbookservice.bean.BorrowBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowBookRepository extends CrudRepository<BorrowBook, Long> {
}
