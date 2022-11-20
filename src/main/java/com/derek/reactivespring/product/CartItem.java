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

    public void increment() {
        this.quantity++;
    }
    /*
    * 새로운 상품을 카트에 담을 때 수량을 1로 지정 한다.
    * */
    CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }
}
