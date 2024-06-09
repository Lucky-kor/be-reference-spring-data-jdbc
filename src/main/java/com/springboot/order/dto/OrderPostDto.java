package com.springboot.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderPostDto {
    @Positive
    private long memberId;

    @Valid
    @NotNull(message = "주문할 커피 정보는 필수입니다.")
    private List<OrderCoffeeDto> orderCoffees;
}
