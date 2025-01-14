package com.springboot.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Table("ORDERS")
public class Order {
    @Id
    private long orderId;

    // 테이블 외래키처럼 memberId를 추가해서 참조하도록 한다..
    private long memberId;
//    private AggregateReference<Member, Long> memberId;

    @MappedCollection(idColumn = "ORDER_ID", keyColumn = "ORDER_COFFEE_ID")
    private Set<OrderCoffee> orderCoffees = new LinkedHashSet<>();

    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;

        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
