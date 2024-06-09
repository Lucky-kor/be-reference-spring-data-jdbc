package com.springboot.order.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@Table("ORDER_COFFEE")
public class OrderCoffee {
    @Id
    private long orderCoffeeId;
    private long coffeeId;
    private int quantity;

    // 왜 private long orderId는 없는가?
    // --> Order가 private Set<CoffeeRef> orderCoffees로 이미 참조 가능하므로..
    // --> 즉, OrderCoffee가 이미 Order와 외래키의 관계를 가진다는 의미임.
}
