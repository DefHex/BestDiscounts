package org.example.backend.service;

import org.example.backend.model.Discount;
import org.example.backend.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountsService {
    private final DiscountRepository repo;

    public DiscountsService(DiscountRepository repo) {
        this.repo = repo;
    }

    public List<Discount> getAllDiscounts() {
        return repo.findAll();
    }
    public List<Discount> findByStore(String Provider) {
        return repo.findDiscountByProvider(Provider);
    }
}
