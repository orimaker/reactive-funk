package com.roitraining.demos.reactivefunk.handler;

import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.domain.Shelve;
import com.roitraining.demos.reactivefunk.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ShelveHandler {

    private BookService bookService;

    public ShelveHandler(BookService bookService) {
        this.bookService = bookService;
    }

    public Mono<ServerResponse> getAllShelves(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just("shelve1", "shelve2"), String.class);

    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(new Shelve()), Shelve.class);
    }

    public Mono<ServerResponse> findByName(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(new Shelve()), Shelve.class);
    }

    public Mono<ServerResponse> createShelve(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(new Shelve()), Shelve.class);
    }

    public Mono<ServerResponse> addBookToShelve(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(new Shelve()), Shelve.class);
    }

    public Mono<ServerResponse> getShelveBooks(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.bookService.getRandomBook().concatWith(this.bookService.getRandomBook()), Book.class);
    }

    public Mono<ServerResponse> getRandomShelve(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new Shelve()), Shelve.class);
    }
}
