package com.learning.microservices.borrowbookservice.repository;

import com.learning.microservices.borrowbookservice.bean.Book;
import com.learning.microservices.borrowbookservice.bean.User;
import com.learning.microservices.borrowbookservice.config.ApplicationConfig;
import com.learning.microservices.borrowbookservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserRepository {
    @Autowired
    ApplicationConfig applicationConfig;

    public User getUserDetails(Long userId) {
        ResponseEntity<User> userResponse = WebClient.create().get().uri(applicationConfig.getUserServiceApi() + "/" + userId).retrieve().toEntity(User.class).block();
        if(userResponse.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            throw new UserNotFoundException(userId);
        }
        User user = userResponse.getBody();
        System.out.println("User from Service :" + user);
        return user;
    }
}
