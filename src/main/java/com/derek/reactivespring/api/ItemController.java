package com.derek.reactivespring.api;

import com.derek.reactivespring.product.Item;
import com.derek.reactivespring.product.ItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/items")
    Flux<Item> findAll() {
        return this.itemRepository.findAll();
    }

    @GetMapping("/api/items/{id}")
    Mono<Item> findOne(@PathVariable String id) {
       return this.itemRepository.findById(id);
    }

    @PostMapping("/api/items")
    Mono<ResponseEntity<?>> addNewItem(@RequestBody Mono<Item> item) {
        return item.flatMap(this.itemRepository::save)
                    .map(savedItem -> ResponseEntity.created(URI.create("/api/items/" + savedItem.getId())).body(savedItem));
    }


    @PutMapping("/api/items/{id}")
    public Mono<ResponseEntity<?>> updateItem(@RequestBody Mono<Item> item,
                                              @PathVariable String id) {
        return item.map(content -> new Item(id, content.getName(), content.getDescription(), content.getPrice()))
                    .flatMap(this.itemRepository::save)
                    .map(ResponseEntity::ok);
    }

}
