package com.learning.microservices.borrowbookservice.repository;

import com.learning.microservices.borrowbookservice.bean.Book;
import com.learning.microservices.borrowbookservice.config.ApplicationConfig;
import com.learning.microservices.borrowbookservice.exception.BookNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BookRepository {
    private Logger logger = LoggerFactory.getLogger(BookRepository.class);
    @Autowired
    ApplicationConfig applicationConfig;

    public Book getBookDetails(Long bookId) {
        ResponseEntity<Book> bookResponse = WebClient.create()
                .get().uri(applicationConfig.getBookServiceApi() + "/" + bookId)
                .retrieve()
                .toEntity(Book.class)
                .doOnError(error->Mono.error(new BookNotFoundException(error.getMessage())))
                .block();
        if(bookResponse.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new BookNotFoundException("Book not found :"+bookId);
        Book bookBody = bookResponse.getBody();
        System.out.println("User from Service :" + bookBody);
        return bookBody;
    }

    public void updateBookDetails(Book book) {
        Book updatedBook = WebClient.create().post().uri(applicationConfig.getBookServiceApi()).body(Mono.just(book), Book.class).retrieve().bodyToMono(Book.class).block();

        /*return ServerResponse.ok()
                .body(sayHello(request)
                        .onErrorResume(e -> Mono.error(new NameRequiredException(HttpStatus.BAD_REQUEST,"username is required", e))), String.class);
*/
    }
}
