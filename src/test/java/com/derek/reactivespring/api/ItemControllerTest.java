package com.derek.reactivespring.api;

import com.derek.reactivespring.product.InventoryService;
import com.derek.reactivespring.product.Item;
import com.derek.reactivespring.product.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.*;

@WebFluxTest(controllers = ItemController.class)
@AutoConfigureRestDocs
class ItemControllerTest {

    @Autowired private WebTestClient webTestClient;

    @MockBean
    InventoryService inventoryService;

    @MockBean
    ItemRepository itemRepository;


    @Test
    void findingAllItems() {
        when(itemRepository.findAll()).thenReturn(
                Flux.just(new Item("item-1", "Megastduy", "valuable stock", 332.22)));

        this.webTestClient.get().uri("/api/items")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("findAll", preprocessResponse(prettyPrint()))); // <2>

    }

    @Test
    void postNewItem() {
        when(itemRepository.save(any())).thenReturn(
                Mono.just(new Item("1", "Megastduy", "valuable stock", 332.22)));

        this.webTestClient.post().uri("/api/items")
                .bodyValue(new Item("Megastduy", "valuable stock", 332.22))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("post-new-item", preprocessResponse(prettyPrint()))); // <2>

    }

}