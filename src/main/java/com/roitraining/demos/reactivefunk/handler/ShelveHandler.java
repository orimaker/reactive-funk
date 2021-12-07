package com.roitraining.demos.reactivefunk.handler;

import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.domain.Shelve;
import com.roitraining.demos.reactivefunk.service.BookService;
import com.roitraining.demos.reactivefunk.service.ShelveService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class ShelveHandler {

    @Autowired
    private ShelveService shelveService;

    @Autowired
    private BookService bookService;

    public Mono<ServerResponse> getAllShelves(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shelveService.getAllShelves(), Shelve.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        Integer shelveId = Integer.valueOf(serverRequest.pathVariable("shelveId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shelveService.findById(shelveId), Shelve.class);
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
        Mono<Book> bookToAdd = serverRequest.bodyToMono(Book.class);
        Integer shelveId = Integer.valueOf(serverRequest.pathVariable("shelveId"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shelveService.addBookToShelve(shelveId, bookToAdd), Shelve.class);
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
