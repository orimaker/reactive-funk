package com.roitraining.demos.reactivefunk.controller;

import com.roitraining.demos.reactivefunk.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_getSampleBook() {
        webTestClient
                .get().uri("/book/sample").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(book -> {
                            assertThat(book.getTitle(), is(notNullValue()));
                        }
                );
    }

    @Test
    public void test_getBookById() {
        webTestClient
                .get().uri("/book/" + 2).accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(book -> {
                            assertThat(book.getTitle(), is("Idiots and Angels"));
                            assertThat(book.getISBN(), is(2));
                        }
                );
    }

    @Test
    public void test_addBook() {
        Book newBook = new Book("newbook", "author", 3, 3);
        webTestClient
                .put().uri("/book").accept(MediaType.APPLICATION_JSON).body(Mono.just(newBook), Book.class).exchange()
                .expectStatus().isOk()
                .returnResult(Book.class)
                .consumeWith(response -> {
                            response.getResponseBody().subscribe(book -> {
                                assertThat(book.getTitle(), is(newBook.getTitle()));
                                assertThat(book.getISBN(), is(1));
                                assertThat(book.getId(), is(6));
                                //this doesn't get thrown out -> false positive test
                            });
                        }
                );
    }


}