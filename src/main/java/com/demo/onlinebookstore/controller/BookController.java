package com.demo.onlinebookstore.controller;

import com.demo.onlinebookstore.entity.Book;
import com.demo.onlinebookstore.exceptionhandler.ResourceNotFoundExceptions;
import com.demo.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    public BookService bookService;

    @PostMapping("/addBooks")
    public ResponseEntity<Object> addBookInfo(@RequestBody Book book) throws Exception {
        Book bookInfo = bookService.addBookInfo(book);

        return new ResponseEntity<>(bookInfo, HttpStatus.CREATED);
    }

    @PostMapping("/removeBook/{id}")
    public ResponseEntity<Object> removeBookInfo(@PathVariable("id") int bookId) throws ResourceNotFoundExceptions {
        Book bookInfo = bookService.removeBookInfo(bookId);
        return new ResponseEntity<>(bookInfo, HttpStatus.CREATED);
    }

    @GetMapping("/getAllBookDetails")
    public ResponseEntity<Object> getAllBookDetails() throws ResourceNotFoundExceptions {
        List<Book> listOfBook = bookService.getAllBookDetails("Book");
        return new ResponseEntity<>(listOfBook, HttpStatus.OK);
    }

    @GetMapping("/getBookDetailsById/{id}")
    public ResponseEntity<Object> getBookDetailsById(@PathVariable("id") int bookId) throws ResourceNotFoundExceptions {
        Book bookDetails = bookService.getBookDetailsById(bookId);
        return new ResponseEntity<>(bookDetails, HttpStatus.OK);
    }

    @PostMapping("/addBookToCartList/{id}")
    public ResponseEntity<Object> addBookToCartListById(@PathVariable("id") int bookId) throws ResourceNotFoundExceptions {
        Book bookDetails = bookService.cartOperations(bookId, "Add");
        return new ResponseEntity<>(bookDetails, HttpStatus.OK);
    }

    @PostMapping("/removeBookFromCartList/{id}")
    public ResponseEntity<Object> removeBookFromCartList(@PathVariable("id") int bookId) throws ResourceNotFoundExceptions {
        Book bookDetails = bookService.cartOperations(bookId, "Remove");
        return new ResponseEntity<>(bookDetails, HttpStatus.OK);
    }

    @GetMapping("/getAllCartDetails")
    public ResponseEntity<Object> getAllCartDetails() throws ResourceNotFoundExceptions {
        List<Book> listOfBook = bookService.getAllBookDetails("Cart");
        return new ResponseEntity<>(listOfBook, HttpStatus.OK);
    }

    @GetMapping("/sortDetailsByPrice/{sortFlag}")
    public ResponseEntity<Object> sortDetailsByPrice(@PathVariable("sortFlag") int sortFlag) throws Exception {
        return new ResponseEntity<>(bookService.sortDetailsByPrice(sortFlag), HttpStatus.OK);
    }

    @GetMapping("/getBookDetailsByPriceRange/{lowRange}/to/{highRange}")
    public ResponseEntity<Object> getBookDetailsByPriceRange(@PathVariable("lowRange") long lowRange, @PathVariable("highRange") long highRange) throws ResourceNotFoundExceptions {
        List<Book> filterList = bookService.getBookDetailsByPriceRange(lowRange, highRange);
        if (filterList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(filterList, HttpStatus.OK);
    }

    @GetMapping("/placeOrder")
    public ResponseEntity<Object> placeOrder() throws ResourceNotFoundExceptions {
        return new ResponseEntity<>(bookService.placeOrder(), HttpStatus.OK);
    }

    @GetMapping("/getAllOrderDetails")
    public ResponseEntity<Object> getAllOrderDetails() throws ResourceNotFoundExceptions {
        return new ResponseEntity<>(bookService.getAllOrderDetails(), HttpStatus.OK);
    }

    @GetMapping("/check")
    public String checkLog(){
        return "API Checking..";
    }
}
