package com.o2m.o2m.controller;

import com.o2m.o2m.model.Order;
import com.o2m.o2m.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> list = orderRepository.findAll();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }
}
