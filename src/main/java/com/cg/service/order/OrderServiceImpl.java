package com.cg.service.order;

import com.cg.model.Order;
import com.cg.repository.OrderRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRespository orderRespository;
    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return orderRespository.save(order);
    }

    @Override
    public void remove(Long id) {

    }
}
