package com.microservice.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.Model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

}
