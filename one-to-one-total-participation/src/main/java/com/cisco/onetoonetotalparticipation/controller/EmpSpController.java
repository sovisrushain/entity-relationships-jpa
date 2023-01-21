package com.cisco.onetoonetotalparticipation.controller;

import com.cisco.onetoonetotalparticipation.dto.RequestDTO;
import com.cisco.onetoonetotalparticipation.service.EmpSpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmpSpController {

    private final EmpSpService empSpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveEmployeeData(@RequestBody RequestDTO requestDTO) {
        empSpService.saveEmployeeData(requestDTO);
        return "SUCCESS!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getEmployeeData() {
        return "emp data";
    }
}
