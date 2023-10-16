package com.microservice.Service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.Model.Inventory;
import com.microservice.Repository.InventoryRepo;
import com.microservice.dto.InventoryResponse;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class InventoryService {
	
	@Autowired
	InventoryRepo repo;


	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		// TODO Auto-generated method stub
		log.info("Wait start");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("wait end");
		System.out.print("Inventory database called....");
		List<Inventory> response = repo.findBySkuCodeIn(skuCode);
		List<InventoryResponse> inventoryResponseList = response.stream()
															.map(inventory -> InventoryResponse.builder()
																		.skuCode(inventory.getSkuCode())
																		.isInStock(inventory.getQuantity()>-1)
																		.build()
													).toList();
		log.info("RESPONSE from INVENTORY DATABASE"+response.size());
		return inventoryResponseList;
	}

}
