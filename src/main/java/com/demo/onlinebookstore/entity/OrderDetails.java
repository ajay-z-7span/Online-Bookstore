package com.demo.onlinebookstore.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue
    private int id;

    private List<Book> bookList;

    private LocalDate orderDate;

    private LocalDate expactedDate;

    private String msg;
}
