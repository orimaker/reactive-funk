package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Book;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookInterface {

    Book getSampleBookClassResponse();

    Mono<Book> getSampleBook();

    Mono<Book> getBook(int bookId);

    Mono<Book> addBook(Mono<Book> newBook);

    Flux<Book> addBooks(Flux<Book> newBooks);

    Flux<ServerSentEvent<Book>> randomBookStream();

    Flux<ServerSentEvent<Mono<Book>>> randomMonoBookStream();
}
