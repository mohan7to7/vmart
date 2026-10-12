package com.vmart.cart.service;

import com.vmart.cart.entity.Cart;
import com.vmart.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Cart getOrCreateCustomerCart(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        return cartRepository.findByUserIdAndStatus(userId, "ACTIVE")
                .orElseGet(() -> {
                    Cart cart = Cart.createCustomerCart(userId);
                    return cartRepository.save(cart);
                });
    }
}