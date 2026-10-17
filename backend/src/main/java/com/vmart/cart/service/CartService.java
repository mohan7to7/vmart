package com.vmart.cart.service;

import com.vmart.cart.entity.Cart;
import com.vmart.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmart.cart.repository.CartItemRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
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
    @Transactional(readOnly = true)
    public java.util.List<com.vmart.cart.entity.CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }
}