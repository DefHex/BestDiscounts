package org.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Discounters")
public record Discount(String id, String name, String image, String price,String provider) {}
