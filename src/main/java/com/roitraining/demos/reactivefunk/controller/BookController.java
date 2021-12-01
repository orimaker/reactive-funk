package com.roitraining.demos.reactivefunk.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping(path = "book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("sampleClass")
    public Book getSampleBookClassResponse() {
        return bookService.getRandomBook().block();
    }

    @GetMapping("sample")
    public Mono<Book> getSampleBook() {
        return bookService.getRandomBook();
    }

    @GetMapping("{id:\\d\\d?}")
    public Mono<Book> getBook(@PathVariable("id") int id) {
        return bookService.getBookById(id)
                .log()
                .onErrorReturn(new Book());
    }


}
