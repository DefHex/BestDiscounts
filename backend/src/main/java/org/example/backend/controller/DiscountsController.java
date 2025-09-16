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
//    private final MongoRepository repository;
    private final DiscountsService service;
//    @GetMapping("/data")
//    public List<Discount> DbData(){
//        return repository.allDocuments();
//    }
    @GetMapping("/data" )
    public List<Discount> getAllDiscounts(){
        return service.getAllDiscounts();
    }

    @GetMapping("/edeka" )
    public List<Discount> getEdeka(){
        return service.findByStore("Edeka");
    }
    @GetMapping("/lidl" )
    public List<Discount> getLidl(){
        return service.findByStore("Lidl");
    }
    @GetMapping("/aldinord" )
    public List<Discount> getAldiNord(){
        return service.findByStore("AldiNord");
    }
}
