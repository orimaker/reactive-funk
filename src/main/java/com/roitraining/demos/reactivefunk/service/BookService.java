package com.roitraining.demos.reactivefunk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roitraining.demos.reactivefunk.domain.Book;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private Map<Integer, Book> bookStore;

    public BookService() {
        bookStore = new HashMap<>();
        String booksJSON = """
                [
                {"id":1,"title":"Step Up 2 the Streets","author":"Yelena Chasle","isbn":1},
                {"id":2,"title":"Idiots and Angels","author":"Desirae Taaffe","isbn":2},
                {"id":3,"title":"Boogeyman","author":"Fran Splevins","isbn":3},
                {"id":4,"title":"Albuquerque","author":"Tabatha Panons","isbn":4},
                {"id":5,"title":"Road to Morocco","author":"Abbi Eley","isbn":5}
                ]""";

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Book> books = mapper.readValue(booksJSON,
                    mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
            bookStore = books.stream().collect(Collectors.toMap(Book::getId, book -> book));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public Mono<Book> getBookById(int id) {
        return Mono.justOrEmpty(bookStore.get(id))
                .switchIfEmpty(Mono.error(new IllegalArgumentException()));

//        // TODO instead of these
//        return Mono.just(id)
//                .map(bookId -> bookStore.get(bookId))
//                .flatMap(book -> {
//                    if (book == null) return Mono.error(new IllegalArgumentException("Invalid book id."));
//                    return Mono.just(book);
//                });
        //.cast .handle
//                .map(book -> {
//                    if (book == null) throw new IllegalArgumentException("Invalid book id.");
//                    return book;
//                });
    }

    public Mono<Book> getRandomBook() {
        int randomIndex = new Random().nextInt(1, bookStore.size());
        return this.getBookById(randomIndex);
    }

    public Mono<Book> addBook(Mono<Book> newBook) {
        return newBook.map(book -> {
            book.setId(this.getNewBookIndex());
            this.bookStore.put(book.getId(), book);
            return book;
        });
    }

    private int getNewBookIndex() {
        return bookStore.keySet().stream()
                .max(Comparator.naturalOrder())
                .orElse(0)
                + 1;
    }

    public Flux<Book> addBooks(Flux<Book> newBooks) {
        return null;
    }
}
