package com.demo.onlinebookstore.service;

import com.demo.onlinebookstore.entity.Book;
import com.demo.onlinebookstore.entity.OrderDetails;
import com.demo.onlinebookstore.exceptionhandler.ResourceNotFoundExceptions;
import com.demo.onlinebookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {
    Book addBookInfo(Book book) throws Exception;

    Book removeBookInfo(int bookId) throws ResourceNotFoundExceptions;

    List<Book> getAllBookDetails(String displayType) throws ResourceNotFoundExceptions;

    Book getBookDetailsById(int bookId) throws ResourceNotFoundExceptions;

    Book cartOperations(int bookId,String operationType) throws ResourceNotFoundExceptions;
    List<Book> sortDetailsByPrice(int sortFlag) throws Exception;

    List<Book> getBookDetailsByPriceRange(long lowRange, long highRange) throws ResourceNotFoundExceptions;

    OrderDetails placeOrder() throws ResourceNotFoundExceptions;

    List<OrderDetails> getAllOrderDetails() throws ResourceNotFoundExceptions;
}
