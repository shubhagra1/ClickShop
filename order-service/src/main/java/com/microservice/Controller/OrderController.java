package com.microservice.Controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.DTO.OrderRequest;
import com.microservice.Service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name = "inventory" ,fallbackMethod = "fallbackMethod")
	@TimeLimiter(name="inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		return CompletableFuture.supplyAsync(()-> orderService.placeOrder(orderRequest));
	//	return "order placed Successfully";
	}
	
	public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest , RuntimeException exception) {
		return CompletableFuture.supplyAsync(()->"Site is not working properly, retry after sometime");
	}
}
