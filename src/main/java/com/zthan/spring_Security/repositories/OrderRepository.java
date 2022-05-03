package com.zthan.spring_Security.repositories;

import com.zthan.spring_Security.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findAllByCustomerId(long id);
}
