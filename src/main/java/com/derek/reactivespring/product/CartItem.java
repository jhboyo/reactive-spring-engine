package com.derek.reactivespring.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*
* 아이템과 수량을 포함하는 구매 상품 데이터
* */
@Getter
@Setter
@EqualsAndHashCode
public class CartItem {

    private Item item;
    private int quantity;

    private CartItem() {}

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
