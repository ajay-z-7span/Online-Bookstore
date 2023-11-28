package com.demo.onlinebookstore.service.impl;

import com.demo.onlinebookstore.entity.Book;
import com.demo.onlinebookstore.entity.OrderDetails;
import com.demo.onlinebookstore.exceptionhandler.ResourceNotFoundExceptions;
import com.demo.onlinebookstore.repository.BookRepository;
import com.demo.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    public static BookRepository bookDao = null;

    @Override
    public Book addBookInfo(Book book) throws Exception {
        Optional<Book> existBook =  bookDao.bookList.stream().filter(book1 -> book1.getId()== book.getId()).findFirst();
       if(existBook.isPresent()) throw new Exception("Book with this id already present");
        bookDao = (bookList, bookData) -> {
            bookList.add(bookData);
            return bookData;
        };
        return bookDao.doOperation(bookDao.bookList, book);
    }

    @Override
    public Book removeBookInfo(int bookId) throws ResourceNotFoundExceptions {
        Book bookExits = bookDao.bookList.stream().filter(book -> book.getId() == bookId).findFirst().orElseThrow(() -> new ResourceNotFoundExceptions("Resource Not Found", HttpStatus.NOT_FOUND));
        bookDao = (bookList, bookData) -> {
            bookList.remove(bookData);
            return bookData;
        };
        return bookDao.doOperation(bookDao.bookList, bookExits);
    }

    @Override
    public List<Book> getAllBookDetails(String displayType) throws ResourceNotFoundExceptions {
        if (bookDao.bookList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        return bookDao.displayBookDetails(displayType);
    }

    @Override
    public Book getBookDetailsById(int bookId) throws ResourceNotFoundExceptions {
        if (bookDao.bookList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        return bookDao.displayBookById(bookId);
    }

    @Override
    public Book cartOperations(int bookId, String operationType) throws ResourceNotFoundExceptions {
        Book bookExits = bookDao.bookList.stream().filter(book -> book.getId() == bookId).findFirst().orElseThrow(() -> new ResourceNotFoundExceptions("Resource Not Found", HttpStatus.NOT_FOUND));
        bookDao = (cartList, bookData) -> {
            if (operationType.equals("Add")) {
                cartList.add(bookData);
            } else {
                cartList.remove(bookData);
            }
            return bookData;
        };
        return bookDao.doOperation(bookDao.cartList, bookExits);
    }

    @Override
    public List<Book> sortDetailsByPrice(int sortFlag) throws Exception {

        if (sortFlag < 0 || sortFlag > 1)
            throw new Exception("Please give O for low to high Or 1 for high to low value.");
        if (bookDao.bookList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        List<Book> sortedBookList = null;
        if (sortFlag == 0) {
            sortedBookList = bookDao.bookList.stream().sorted((b1, b2) -> Integer.compare((int) b1.getPrice(), (int) b2.getPrice())).collect(Collectors.toList());

        } else {
            sortedBookList = bookDao.bookList.stream().sorted((b1, b2) -> Integer.compare((int) b2.getPrice(), (int) b1.getPrice())).collect(Collectors.toList());

        }
        return sortedBookList;
    }

    @Override
    public List<Book> getBookDetailsByPriceRange(long lowRange, long highRange) throws ResourceNotFoundExceptions {
        if (bookDao.bookList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        return bookDao.bookList.stream().filter(book -> book.getPrice()>=lowRange&&book.getPrice()<=highRange).collect(Collectors.toList());
    }

    @Override
    public OrderDetails placeOrder() throws ResourceNotFoundExceptions {
        if (bookDao.cartList.isEmpty()) throw new ResourceNotFoundExceptions("No Record Found", HttpStatus.NOT_FOUND);
        OrderDetails orderDetails = new OrderDetails();
        List<Book> getBook = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bookDao.cartList)){
            for (Book b : bookDao.cartList) {
                getBook.add(b);

            }
        }
        bookDao.cartList.clear();
        orderDetails.setBookList(getBook);
        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        orderDetails.setOrderDate(date);
        orderDetails.setExpactedDate(date.plus(Period.ofDays(10)));
        orderDetails.setMsg("You order has been placed and delivered within "+ ChronoUnit.DAYS.between(date,date.plus(Period.ofDays(10)))+" days.");
        bookDao.orderList.add(orderDetails);
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() throws ResourceNotFoundExceptions{
        if(CollectionUtils.isEmpty(bookDao.orderList)) throw new ResourceNotFoundExceptions("No Record Found",HttpStatus.NOT_FOUND);
        return bookDao.orderList;
    }
}
