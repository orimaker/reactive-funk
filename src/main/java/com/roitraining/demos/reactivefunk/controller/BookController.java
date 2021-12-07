package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping(path = "book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController implements BookInterface {

    private final BookService bookService;

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


    @Override
    @GetMapping("randomMonoBookStream")
    public Flux<ServerSentEvent<Mono<Book>>> randomMonoBookStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(s -> ServerSentEvent.<Mono<Book>>builder()
                        .id("randomMonoBookStream")
                        .event(UUID.randomUUID().toString())
                        .data(this.bookService.getRandomBook())
                        .build());
    }

    @Override
    @GetMapping("bookStreamError")
    public Flux<ServerSentEvent<Book>> randomBookStream() {
        var infiniteRandomBookPublisher = Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> this.bookService.getRandomBook());
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(s -> s == 5 ? Mono.error(new IllegalArgumentException("error")) : Mono.just(s)
                )
                //.onErrorReturn(-1l)
                .onErrorStop()
                .zipWith(infiniteRandomBookPublisher,
                        (tick, book) -> ServerSentEvent.<Book>builder()
                                .id("randomBookStream + " + tick)
                                .event(UUID.randomUUID().toString())
                                .data(book)
                                .build()
                )
                .log();

    }

    @Override
    @GetMapping("bookStreamCompleted")
    public Flux<ServerSentEvent<Book>> randomBookStreamCompleted() {
        var infiniteRandomBookPublisher = Flux.interval(Duration.ofSeconds(1))
                .flatMap(tick -> this.bookService.getRandomBook());
        return Flux.interval(Duration.ofSeconds(2))
                .take(5)
                .zipWith(infiniteRandomBookPublisher,
                        (tick, book) -> ServerSentEvent.<Book>builder()
                                .id("randomBookStream + " + tick)
                                .event(UUID.randomUUID().toString())
                                .data(book)
                                .build()
                )
                .log();

    }


  /*
  * @Override
    @GetMapping("randomBookStream")
    public Flux<ServerSentEvent<Book>> randomBookStream() {
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(s -> this.bookService.getRandomBook())
                .map(book -> ServerSentEvent.<Book>builder()
                        .id("randomBookStream + ")
                        .event(UUID.randomUUID().toString())
                        .data(book)
                        .build());
    }*/


}
