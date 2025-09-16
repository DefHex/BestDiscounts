package org.example.backend.controller;

import org.example.backend.model.Discount;
import org.example.backend.service.DiscountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DiscountsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DiscountsService discountsService;

    @InjectMocks
    private DiscountsController discountsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(discountsController).build();
    }

    @Test
    void getAllDiscounts_shouldReturnListOfDiscounts() throws Exception {
        // Given
        Discount discount1 = new Discount("1", "Sale 1", "image1.jpg", "10.00", "ProviderA");
        Discount discount2 = new Discount("2", "Sale 2", "image2.jpg", "20.00", "ProviderB");
        List<Discount> discounts = Arrays.asList(discount1, discount2);

        when(discountsService.getAllDiscounts()).thenReturn(discounts);

        // When & Then
        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Sale 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Sale 2"));
    }
}
