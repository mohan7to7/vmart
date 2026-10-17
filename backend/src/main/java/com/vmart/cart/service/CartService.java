
package com.vmart.cart.service;

import com.vmart.cart.entity.Cart;
import com.vmart.cart.entity.CartItem;
import com.vmart.cart.repository.CartItemRepository;
import com.vmart.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<CartItem> getCartItems(Long cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID is required");
        }

        return cartItemRepository.findAllByCartId(cartId);
    }

    @Transactional(readOnly = true)
    public java.util.Optional<CartItem> getCartItem(
            Long cartId, Long variantId) {

        if (cartId == null || variantId == null) {
            throw new IllegalArgumentException(
                    "Cart ID and variant ID are required");
        }

        return cartItemRepository.findByCartIdAndVariantId(
                cartId, variantId);
    }
}