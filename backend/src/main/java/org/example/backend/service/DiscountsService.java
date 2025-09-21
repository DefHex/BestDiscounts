package org.example.backend.service;

import org.example.backend.model.Discount;
import org.example.backend.repository.DiscountRepository;
import org.example.backend.security.AppUser;
import org.example.backend.security.AppUserRepository;
import org.example.backend.security.CartItemTransferDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DiscountsService {
    private final DiscountRepository repo;
    private final AppUserRepository userRepo;
    public DiscountsService(DiscountRepository repo, AppUserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public List<Discount> getAllDiscounts() {
        return repo.findAll();
    }

    public List<String> changeCart(CartItemTransferDto cartItem) {
        AppUser user = userRepo.findById(cartItem.userId())
                .orElseThrow(() ->
                        new NoSuchElementException("User with id " + cartItem.userId() + " not found"));
        List<String> currentCart = user.shoppingCart();
        System.out.println("Current cart: " + currentCart);
        if (currentCart == null) {
            currentCart = new ArrayList<>();
        }
        List<String> updatedCart = new ArrayList<>(currentCart);

        if(cartItem.addTrueFalseDelete()){
            updatedCart.add(cartItem.productId());
        }else{
            updatedCart.remove(cartItem.productId());
        }
        System.out.println("Updated cart: " + updatedCart);

        AppUser updatedUser = new AppUser(
                user.id(),
                user.providerId(),
                user.provider(),
                user.userName(),
                user.avatarUrl(),
                updatedCart
        );
        userRepo.save(updatedUser);
        System.out.println("Updated user: " + updatedUser);
        return updatedUser.shoppingCart();
    }
}
