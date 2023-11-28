package com.demo.onlinebookstore.repository;

import com.demo.onlinebookstore.entity.Book;
import com.demo.onlinebookstore.entity.OrderDetails;
import com.demo.onlinebookstore.exceptionhandler.ResourceNotFoundExceptions;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;


@FunctionalInterface
public interface BookRepository {

    List<Book> bookList = new ArrayList<>();

    List<Book> cartList = new ArrayList<>();

    List<OrderDetails> orderList = new ArrayList<>();


    Book doOperation(List<Book> bookOfList,Book bookData);

    default List<Book> displayBookDetails(String displayType) throws ResourceNotFoundExceptions{
        if(displayType.equals("Book")){
            if(bookList.isEmpty()){
                throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
            }
            return bookList;

        }else {
            if(cartList.isEmpty()){
                throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
            }
            return cartList;
        }
    }

    default Book displayBookById(int id) throws ResourceNotFoundExceptions{
        return bookList.stream().filter(book -> book.getId()==id).findFirst().orElseThrow(()-> new ResourceNotFoundExceptions("No Resource Found",HttpStatus.NOT_FOUND));
    }
}
