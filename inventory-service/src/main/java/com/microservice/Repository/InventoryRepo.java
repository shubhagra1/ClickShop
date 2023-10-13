package com.microservice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.Model.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory,Long> {

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
