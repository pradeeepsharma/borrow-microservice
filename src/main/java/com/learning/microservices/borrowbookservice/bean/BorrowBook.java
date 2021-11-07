package com.learning.microservices.borrowbookservice.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BorrowBook {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long bookId;
   // @JsonDeserialize
    private Date bookingDate;
    private double chargePerDay;
    private int noOfDays;
    private BorrowStatus borrowStatus;
}
