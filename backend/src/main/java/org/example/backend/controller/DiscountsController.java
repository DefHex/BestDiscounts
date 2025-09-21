package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Discount;
import org.example.backend.security.CartItemTransferDto;
import org.example.backend.service.DiscountsService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/changeCart")
    public List<String> changeCart(@RequestBody CartItemTransferDto cartItem){
        System.out.println("Received item from frontend:" + cartItem);
        return service.changeCart(cartItem);
    }
}
