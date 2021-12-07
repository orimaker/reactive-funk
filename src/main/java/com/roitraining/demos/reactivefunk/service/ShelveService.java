package com.roitraining.demos.reactivefunk.service;

import com.roitraining.demos.reactivefunk.domain.Book;
import com.roitraining.demos.reactivefunk.domain.Shelve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShelveService {

    @Autowired
    BookService bookService;

    Map<Integer, Shelve> shelveStore;

    public ShelveService() {
        this.shelveStore = new HashMap<>();

    }

    @PostConstruct
    void init() {
        Shelve sampleShelve = new Shelve();
        sampleShelve.setName("my first shelve");
        sampleShelve.setShelveId(1);
        shelveStore.put(1, sampleShelve);

        Flux.range(1, 4)
                .flatMap(counter -> bookService.getRandomBook())
                .subscribe(sampleShelve.getBooks()::add);
    }

    public Flux<Shelve> getAllShelves() {
        return Flux.fromIterable(shelveStore.values());
    }

    public Mono<Shelve> addBookToShelve(Integer shelveId, Mono<Book> bookToAdd) {
        return this.findById(shelveId)
                .zipWith(bookToAdd, (shelve, book) -> {
                    shelve.getBooks().add(book);
                    return shelve;
                })
                .flatMap(Mono::just);
    }

    public Mono<Shelve> findById(Integer shelveId) {
        return Mono.just(shelveStore.get(shelveId));
    }
}
