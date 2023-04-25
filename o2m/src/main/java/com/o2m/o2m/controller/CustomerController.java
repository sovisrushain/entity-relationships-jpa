package com.o2m.o2m.controller;

import com.o2m.o2m.model.Customer;
import com.o2m.o2m.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<String> saveCustomers(@RequestBody List<Customer> list) {
        customerRepository.saveAll(list);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> list = customerRepository.findAll();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }
}
