package com.microservice.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.Service.InventoryService;
import com.microservice.dto.InventoryResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {
	
	@Autowired
	private InventoryService service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode ) {
		log.info("Recieved the skuCode"+skuCode);
		return service.isInStock(skuCode);
	}
	
	@GetMapping("/resp")
	public String reponse() {
		return "respponse from Inventory";
	}
}
