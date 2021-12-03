package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Shelve;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

public interface ShelveInterface {
    
    Mono<Void> createShelve();

    Mono<Shelve> getShelve(int shelveId);

    Mono<Shelve> addBookToShelve(int bookId, int shelveId);
}
