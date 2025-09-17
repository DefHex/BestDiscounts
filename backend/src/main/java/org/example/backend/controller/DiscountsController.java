package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Discount;
import org.example.backend.service.DiscountsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscountsController {

    private final DiscountsService service;

    @GetMapping("/data" )
    public List<Discount> getAllDiscounts(){
        return service.getAllDiscounts();
    }
}
