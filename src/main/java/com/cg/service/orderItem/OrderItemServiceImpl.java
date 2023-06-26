package com.cg.service.orderItem;

import com.cg.model.Order;
import com.cg.model.Orderitem;
import com.cg.repository.OrderItemRepository;
import com.cg.repository.OrderRespository;
import com.cg.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Iterable<Orderitem> findAll() {
        return null;
    }

    @Override
    public Optional<Orderitem> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Orderitem save(Orderitem orderitem) {
        return orderItemRepository.save(orderitem);
    }

    @Override
    public void remove(Long id) {

    }
}
