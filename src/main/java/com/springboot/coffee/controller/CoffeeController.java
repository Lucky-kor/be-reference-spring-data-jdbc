package com.springboot.coffee.controller;

import com.springboot.coffee.dto.CoffeePatchDto;
import com.springboot.coffee.dto.CoffeePostDto;
import com.springboot.coffee.dto.CoffeeResponseDto;
import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.mapper.CoffeeMapper;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v10/coffees")
@Validated
public class CoffeeController {
    private final static String COFFEE_DEFAULT_URL = "/v10/coffees";
    private CoffeeService coffeeService;
    private CoffeeMapper mapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper coffeeMapper) {
        this.coffeeService = coffeeService;
        this.mapper = coffeeMapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeePostDto coffeePostDto) {
        Coffee coffee = coffeeService.createCoffee(mapper.coffeePostDtoToCoffee(coffeePostDto));
        // TODO README에 포함시키기
        URI location = UriCreator.createUri(COFFEE_DEFAULT_URL, coffee.getCoffeeId()); // "/v10/coffees/{coffee-id}"
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id") @Positive long coffeeId,
                                      @Valid @RequestBody CoffeePatchDto coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);
        Coffee coffee = coffeeService.updateCoffee(mapper.coffeePatchDtoToCoffee(coffeePatchDto));

        return new ResponseEntity<>(mapper.coffeeToCoffeeResponseDto(coffee), HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@PathVariable("coffee-id") long coffeeId) {
        Coffee coffee = coffeeService.findCoffee(coffeeId);

        return new ResponseEntity<>(mapper.coffeeToCoffeeResponseDto(coffee), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees() {
        List<Coffee> coffees = coffeeService.findCoffees();
        List<CoffeeResponseDto> response = mapper.coffeesToCoffeeResponseDtos(coffees);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@PathVariable("coffee-id") long coffeeId) {
        coffeeService.deleteCoffee(coffeeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
