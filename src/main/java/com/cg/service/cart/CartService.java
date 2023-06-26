package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface CartService extends IGeneralService<Cart> {
    Boolean existsByCreateBy(String createBy);
    Optional<Cart> findByCreatedBy(String createdBy);
    void delete(Cart cart);
}
