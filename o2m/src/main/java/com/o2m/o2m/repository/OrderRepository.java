package com.o2m.o2m.repository;

import com.o2m.o2m.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
