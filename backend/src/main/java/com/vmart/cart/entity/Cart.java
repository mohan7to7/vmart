
package com.vmart.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.Instant;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "guest_token_hash", length = 255)
    private String guestTokenHash;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 0L;

    @Column(name = "last_activity_at", nullable = false)
    private Instant lastActivityAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Cart() {
    }

    public static Cart createCustomerCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus("ACTIVE");
        return cart;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();

        if (status == null) {
            status = "ACTIVE";
        }
        if (version == null) {
            version = 0L;
        }
        if (lastActivityAt == null) {
            lastActivityAt = now;
        }
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
        lastActivityAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGuestTokenHash() {
        return guestTokenHash;
    }

    public void setGuestTokenHash(String guestTokenHash) {
        this.guestTokenHash = guestTokenHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getLastActivityAt() {
        return lastActivityAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}