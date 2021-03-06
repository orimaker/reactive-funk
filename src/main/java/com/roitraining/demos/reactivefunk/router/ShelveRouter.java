package com.roitraining.demos.reactivefunk.router;

import com.roitraining.demos.reactivefunk.handler.ShelveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ShelveRouter {

    @Bean
    public RouterFunction<ServerResponse> buildRoute(ShelveHandler shelveHandler) {
        return RouterFunctions.route()
                .path("shelve",
                        p -> p.nest(accept(MediaType.APPLICATION_JSON),
                                b -> b.GET("", shelveHandler::getRandomShelve)
                                        .GET("/all", shelveHandler::getAllShelves)
                                        .GET("/{shelveId:[0-9]+}", shelveHandler::findById)
                                        .GET("/{shelveId:[0-9]+}/books", shelveHandler::getShelveBooks)
                                        .PUT("/{shelveId:[0-9]+}/books", shelveHandler::addBookToShelve)
                                        .GET("/{name:[a-z]+}", shelveHandler::findByName)
                                        .PUT(shelveHandler::createShelve))
                ).build();
    }
}
