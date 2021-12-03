package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Shelve;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

//@Controller
//@RequestMapping(path = "shelve", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShelveController implements ShelveInterface{

    @PutMapping()
    public Mono<Void> createShelve() {
        throw new RuntimeException("notimplemented");
    }

    @Override
    public Mono<Shelve> getShelve(int shelveId) {
        return null;
    }

    @Override
    public Mono<Shelve> addBookToShelve(int bookId, int shelveId) {
        return null;
    }

}
