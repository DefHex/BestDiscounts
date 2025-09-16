package org.example.backend.repository;

import org.example.backend.model.Discount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends MongoRepository<Discount,String> {
    List<Discount> findDiscountByProvider(String provider);
}
