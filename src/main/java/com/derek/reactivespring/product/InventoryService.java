package com.derek.reactivespring.product;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InventoryService {

    private ItemRepository itemRepository;

    private CartRepository cartRepository;

    InventoryService(ItemRepository repository,
                     CartRepository cartRepository) {
        this.itemRepository = repository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Flux<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    Mono<Item> saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    Mono<Void> deleteItem(String id) {
        return this.itemRepository.deleteById(id);
    }

    Flux<Item> searchByExample(String name, String description, boolean useAnd) {
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd
            ? ExampleMatcher.matchingAll()
            : ExampleMatcher.matchingAny())
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);

        return itemRepository.findAll(probe);
    }


    Mono<Cart> addItemToCart(String cartId, String itemId) {
       return this.cartRepository.findById(cartId)
               .log("foundCart")
               .defaultIfEmpty(new Cart(cartId))
               .log("emptyCart")
               .flatMap(cart -> cart.getCartItems().stream()
                       .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                       .findAny()
                       .map(cartItem -> {
                           cartItem.increment();
                           return Mono.just(cart).log("newCartItem");
                       })
                       .orElseGet(() -> {
                           return this.itemRepository.findById(itemId)
                                   .log("fetchedItem")
                                   .map(item -> new CartItem(item))
                                   .log("cartItem")
                                   .map(cartItem -> {
                                       cart.getCartItems().add(cartItem);
                                       return cart;
                                   }).log("addedCartItem");
                       }))
               .log("cartWithAnotherItem")
               .flatMap(cart -> this.cartRepository.save(cart))
               .log("savedCart");
    }


}
