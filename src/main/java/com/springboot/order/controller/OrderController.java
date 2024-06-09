package com.springboot.order.controller;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.order.dto.OrderCoffeeResponseDto;
import com.springboot.order.dto.OrderPostDto;
import com.springboot.order.dto.OrderResponseDto;
import com.springboot.order.dto.ReadableOrderGroupDto;
import com.springboot.order.entity.Order;
import com.springboot.order.entity.ReadableOrderCoffee;
import com.springboot.order.mapper.OrderMapper;
import com.springboot.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Comparator.comparing;

@RestController
@RequestMapping("/v10/orders")
@Validated
public class OrderController {
    private final static String ORDER_DEFAULT_URL = "/v10/orders";
    private final OrderService orderService;
    private final OrderMapper mapper;
    private final CoffeeService coffeeService;

    public OrderController(OrderService orderService,
                           OrderMapper mapper,
                           CoffeeService coffeeService) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.coffeeService = coffeeService;
    }

    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {
        Order order = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));

        URI location =
                UriComponentsBuilder
                        .newInstance()
                        .path(ORDER_DEFAULT_URL + "/{order-id}")
                        .buildAndExpand(order.getOrderId())
                        .toUri();               // "/v10/orders/{order-id}"

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);
        return new ResponseEntity<>(mapper.orderToOrderResponseDto(coffeeService, order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders() {
        List<Order> orders = orderService.findOrders();

        List<OrderResponseDto> response =
                orders.stream()
                        .sorted(comparing(Order::getOrderId).reversed())
                        .map(order -> mapper.orderToOrderResponseDto(coffeeService, order))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * N + 1 이슈가 없는 개선된 버전의 주문 목록 조회
     *
     */
    // Step 1: 네이티브 쿼리로 Join된 주문한 커피 정보
    @GetMapping("/no-nplus1/v1")
    public ResponseEntity getOrdersNoNPlus1V1() {
        List<ReadableOrderCoffee> orders = orderService.findOrders2();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Step 2: 주문한 커피별로 그룹핑하기
    @GetMapping("/no-nplus1/v2")
    public ResponseEntity getOrdersNoNPlus1V2() {
        List<ReadableOrderCoffee> orders = orderService.findOrders2();


        return new ResponseEntity<>(orders.stream()
                .collect(Collectors.groupingBy(ReadableOrderCoffee::getOrderId,
                        Collectors.groupingBy(ReadableOrderCoffee::getMemberId))), HttpStatus.OK);
    }

    // Step 3: 그룹핑된 주문한 커피 정보를 우리가 원하는 데이터 형식으로 변환하기
    @GetMapping("/no-nplus1/v3")
    public ResponseEntity getOrdersNoNPlus1V3() {
        List<ReadableOrderCoffee> orders = orderService.findOrders2();

        Map<ReadableOrderGroupDto, List<ReadableOrderCoffee>> grouped =
                orders.stream().collect(
                        Collectors.groupingBy(readableOrderCoffee -> new ReadableOrderGroupDto(readableOrderCoffee)));

        List<OrderResponseDto> response = grouped.entrySet().stream()
                .map(e -> {
                    ReadableOrderGroupDto groupDto = e.getKey();
                    List<ReadableOrderCoffee> readableOrderCoffees = e.getValue();
                    OrderResponseDto orderResponseDto = new OrderResponseDto();
                    orderResponseDto.setOrderId(groupDto.getOrderId());
                    orderResponseDto.setMemberId(groupDto.getMemberId());
                    orderResponseDto.setOrderStatus(groupDto.getOrderStatus());
                    orderResponseDto.setCreatedAt(groupDto.getCreatedAt());

                    List<OrderCoffeeResponseDto> orderCoffeeResponseDtos =
                            readableOrderCoffees.stream()
                                                .map(readableOrderCoffee -> {
                                                    OrderCoffeeResponseDto orderCoffeeResponseDto =
                                                            new OrderCoffeeResponseDto(readableOrderCoffee.getCoffeeId(),
                                                                    readableOrderCoffee.getKorName(),
                                                                    readableOrderCoffee.getEngName(),
                                                                    readableOrderCoffee.getPrice(),
                                                                    readableOrderCoffee.getQuantity());
                                                    return orderCoffeeResponseDto;
                            }).collect(Collectors.toList());
                    orderResponseDto.setOrderCoffees(orderCoffeeResponseDtos);

                    return orderResponseDto;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Step 4: 최근 주문 순으로 정렬하기
    @GetMapping("/no-nplus1/v4")
    public ResponseEntity getOrdersNoNPlus1V4() {
        List<ReadableOrderCoffee> orders = orderService.findOrders2();

        Map<ReadableOrderGroupDto, List<ReadableOrderCoffee>> grouped =
                orders.stream().collect(
                        Collectors.groupingBy(readableOrderCoffee -> new ReadableOrderGroupDto(readableOrderCoffee)));

        List<OrderResponseDto> response = grouped.entrySet().stream()
                .map(e -> {
                    ReadableOrderGroupDto groupDto = e.getKey();
                    List<ReadableOrderCoffee> readableOrderCoffees = e.getValue();
                    OrderResponseDto orderResponseDto = new OrderResponseDto();
                    orderResponseDto.setOrderId(groupDto.getOrderId());
                    orderResponseDto.setMemberId(groupDto.getMemberId());
                    orderResponseDto.setOrderStatus(groupDto.getOrderStatus());
                    orderResponseDto.setCreatedAt(groupDto.getCreatedAt());

                    List<OrderCoffeeResponseDto> orderCoffeeResponseDtos =
                            readableOrderCoffees.stream()
                                    .map(readableOrderCoffee -> {
                                        OrderCoffeeResponseDto orderCoffeeResponseDto =
                                                new OrderCoffeeResponseDto(readableOrderCoffee.getCoffeeId(),
                                                        readableOrderCoffee.getKorName(),
                                                        readableOrderCoffee.getEngName(),
                                                        readableOrderCoffee.getPrice(),
                                                        readableOrderCoffee.getQuantity());
                                        return orderCoffeeResponseDto;
                                    }).collect(Collectors.toList());
                    orderResponseDto.setOrderCoffees(orderCoffeeResponseDtos);

                    return orderResponseDto;
                }).collect(Collectors.toList());

        // 최근 주문 순으로 정렬
        response.sort(Comparator.comparing(OrderResponseDto::getOrderId).reversed());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Step 5: OrderMapper를 이용해 코드 리팩토링
    @GetMapping("/no-nplus1/v5")
    public ResponseEntity getOrdersNoNPlus1V5() {
        List<ReadableOrderCoffee> orders = orderService.findOrders2();
        return new ResponseEntity<>(mapper.readableOrderCoffeeToOrderResponseDto(orders), HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
