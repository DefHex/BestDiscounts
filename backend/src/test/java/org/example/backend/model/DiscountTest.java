package org.example.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void testDiscountRecord_ConstructorAndAccessors() {
        // Given
        String id = "1";
        String name = "Super Sale";
        String image = "image.jpg";
        String price = "19.99";
        String provider = "TestProvider";

        // When
        Discount discount = new Discount(id, name, image, price, provider);

        // Then
        assertEquals(id, discount.id());
        assertEquals(name, discount.name());
        assertEquals(image, discount.image());
        assertEquals(price, discount.price());
        assertEquals(provider, discount.provider());
    }

    @Test
    void testDiscountRecord_EqualsAndHashCode() {
        // Given
        Discount discount1 = new Discount("1", "Super Sale", "image.jpg", "19.99", "TestProvider");
        Discount discount2 = new Discount("1", "Super Sale", "image.jpg", "19.99", "TestProvider");
        Discount discount3 = new Discount("2", "Different Sale", "image2.jpg", "29.99", "AnotherProvider");

        // Then
        assertEquals(discount1, discount2);
        assertEquals(discount1.hashCode(), discount2.hashCode());

        assertNotEquals(discount1, discount3);
        assertNotEquals(discount1.hashCode(), discount3.hashCode());
        assertNotEquals(null, discount1);
        assertNotEquals(new Object(),discount1);
    }

    @Test
    void testDiscountRecord_ToString() {
        // Given
        Discount discount = new Discount("1", "Super Sale", "image.jpg", "19.99", "TestProvider");
        String expectedString = "Discount[id=1, name=Super Sale, image=image.jpg, price=19.99, provider=TestProvider]";

        // When
        String actualString = discount.toString();

        // Then
        assertEquals(expectedString, actualString);
    }
}
