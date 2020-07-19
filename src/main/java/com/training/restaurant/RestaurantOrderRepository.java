package com.training.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder,Long> {
    List<RestaurantOrder> findByCustomerId(String CustomerId);
}
