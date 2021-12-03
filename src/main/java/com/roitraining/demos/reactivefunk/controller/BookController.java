package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController implements BookInterface {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @GetMapping("sampleClass")
    public Book getSampleBookClassResponse() {
        return bookService.getRandomBook().block();
    }

    @Override
    @GetMapping("sample")
    public Mono<Book> getSampleBook() {
        return bookService.getRandomBook();
    }

    @Override
    @GetMapping("{id:\\d\\d?\\d?}")
    // 1,2,3 decimals are allowed, 4 decimals will result in NotFound 404
    public Mono<Book> getBook(@PathVariable("id") int id) {
        return bookService.getBookById(id)
                .log()
                .onErrorReturn(new Book());
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> addBook(@RequestBody Mono<Book> newBook) {
        return bookService.addBook(newBook);
    }

    @Override
    public Flux<Book> addBooks(Flux<Book> newBooks) {
        return bookService.addBooks(newBooks);
    }


}
