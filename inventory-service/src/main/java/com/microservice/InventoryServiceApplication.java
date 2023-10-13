package com.microservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.microservice.Model.Inventory;
import com.microservice.Repository.InventoryRepo;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(InventoryRepo repo) {
		return args->{
			
		
		Inventory inventory = new Inventory();
		inventory.setQuantity(100);
		inventory.setSkuCode("iphone_13");
		
		Inventory inventory1 = new Inventory();
		inventory1.setQuantity(0);
		inventory1.setSkuCode("iphone_13_red");
		
		
		repo.save(inventory);
		repo.save(inventory1);
		
		};
		
	}

}
