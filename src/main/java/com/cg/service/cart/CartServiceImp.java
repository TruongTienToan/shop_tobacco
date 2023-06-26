package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class CartServiceImp implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Iterable<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Boolean existsByCreateBy(String createBy) {
        return cartRepository.existsByCreatedBy(createBy);
    }

    @Override
    public Optional<Cart> findByCreatedBy(String createdBy) {
        return cartRepository.findByCreatedBy(createdBy);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
