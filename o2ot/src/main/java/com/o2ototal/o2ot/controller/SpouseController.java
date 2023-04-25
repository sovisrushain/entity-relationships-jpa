package com.o2ototal.o2ot.controller;

import com.o2ototal.o2ot.model.Spouse;
import com.o2ototal.o2ot.repository.SpouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("spouses")
@RequiredArgsConstructor
public class SpouseController {

    private final SpouseRepository spouseRepository;

    @GetMapping
    public ResponseEntity<List<Spouse>> getAllSpouses() {
        List<Spouse> list = spouseRepository.findAll();
        return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
    }
}
