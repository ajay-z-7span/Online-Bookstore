package com.demo.onlinebookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int id;
    private String title;
    private String description;
    private String author;
    private Date publishedData;
    private long price;
    private int page;

}
