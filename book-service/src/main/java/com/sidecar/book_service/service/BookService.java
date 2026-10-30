package com.sidecar.book_service.service;

import com.sidecar.book_service.dto.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class BookService {
    private final List<Book> books = new CopyOnWriteArrayList<>();
    public Book addBook(Book book){
        books.add(book);
        return book;
    }

    public List<Book> getBooks(){
        return List.copyOf(books);
    }
}
