package org.example.backend.service;

import org.example.backend.model.Discount;
import org.example.backend.repository.DiscountRepository;
import org.example.backend.security.AppUser;
import org.example.backend.security.AppUserRepository;
import org.example.backend.security.CartItemTransferDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountsServiceTest {

    private final DiscountRepository repo = mock(DiscountRepository.class);
    private final AppUserRepository userRepo = mock(AppUserRepository.class);
    private final DiscountsService service = new DiscountsService(repo, userRepo);

    @Test
    void getAllDiscounts_whenDiscountsExist_thenReturnListOfDiscounts() {
        // GIVEN
        Discount discount1 = new Discount("1", "Discount 1", "Description 1", "10","aldi");
        Discount discount2 = new Discount("2", "Discount 2", "Description 2", "20","lidl");
        List<Discount> expected = List.of(discount1, discount2);
        when(repo.findAll()).thenReturn(expected);

        // WHEN
        List<Discount> actual = service.getAllDiscounts();

        // THEN
        assertEquals(expected, actual);
        verify(repo).findAll();
    }

    @Test
    void changeCart_whenAddTrue_shouldAddToCart() {
        // GIVEN
        CartItemTransferDto cartItem = new CartItemTransferDto("user1", "product1", true);
        AppUser user = new AppUser("user1", "providerId", "provider", "username", "avatarUrl", List.of());
        when(userRepo.findById("user1")).thenReturn(Optional.of(user));

        // WHEN
        List<String> actual = service.changeCart(cartItem);

        // THEN
        List<String> expected = List.of("product1");
        assertEquals(expected, actual);
        verify(userRepo).findById("user1");
        verify(userRepo).save(any(AppUser.class));
    }

    @Test
    void changeCart_whenAddFalse_shouldRemoveFromCart() {
        // GIVEN
        CartItemTransferDto cartItem = new CartItemTransferDto("user1", "product1", false);
        AppUser user = new AppUser("user1", "providerId", "provider", "username", "avatarUrl", List.of("product1", "product2"));
        when(userRepo.findById("user1")).thenReturn(Optional.of(user));

        // WHEN
        List<String> actual = service.changeCart(cartItem);

        // THEN
        List<String> expected = List.of("product2");
        assertEquals(expected, actual);
        verify(userRepo).findById("user1");
        verify(userRepo).save(any(AppUser.class));
    }

    @Test
    void changeCart_whenUserNotFound_shouldThrowNoSuchElementException() {
        // GIVEN
        CartItemTransferDto cartItem = new CartItemTransferDto("user1", "product1", true);
        when(userRepo.findById("user1")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(NoSuchElementException.class, () -> service.changeCart(cartItem));
        verify(userRepo).findById("user1");
        verify(userRepo, never()).save(any(AppUser.class));
    }
}