package com.derek.reactivespring.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class Item {

    // mongo db의 ObjectId 필드로 지정
    private @Id String id;
    private String name;
    private String description;
    private double price;
    private String distributorRegion;
    private Date releaseDate;
    private int availableUnits;
    private Point location;
    private boolean active;

    private Item() {}

    Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
