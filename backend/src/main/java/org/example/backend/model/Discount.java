package org.example.backend.model;

//import org.bson.Document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Discounters")
public record Discount(String id, String name, String image, String price,String provider) {
//    public static Discount fromDocument(Document doc) {
//        // map fields from doc to Discount
//        if (doc == null) {
//            return null;
//        }
//        return new Discount(
//                doc.getObjectId("_id").toString(),
//                doc.getString("Image"),
//                doc.getString("Name"),
//                doc.getString("Price"),
//                doc.getString("Provider")
//        );
//    }
}
