package com.springboot.order.repository;

import com.springboot.order.entity.Order;
import com.springboot.order.entity.ReadableOrderCoffee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("SELECT O.*, OC.QUANTITY, C.* FROM ORDERS O " +
            "INNER JOIN ORDER_COFFEE OC " +
            "ON O.ORDER_ID = OC.ORDER_ID " +
            "INNER JOIN COFFEE C " +
            "ON OC.COFFEE_ID = C.COFFEE_ID " +
            "ORDER BY O.ORDER_ID DESC"
    )
    List<ReadableOrderCoffee> findAllOrderCoffee();
}
