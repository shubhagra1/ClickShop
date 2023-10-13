package com.microservice.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.DTO.InventoryResponse;
import com.microservice.DTO.OrderLineItemsDto;
import com.microservice.DTO.OrderRequest;
import com.microservice.Model.Order;
import com.microservice.Model.OrderLineItems;
import com.microservice.Repo.OrderRepo;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional 
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new  Order();
		order.setOrderNumber(UUID.randomUUID()+"");
		
		List<OrderLineItems> orderLineItemList= orderRequest.getOrderLineItemsDtoList()
		.stream()
		.map(orderLineItem->mapToOrder(orderLineItem))
		.toList();
		
		order.setOrderLineItemsList(orderLineItemList);
		
		//order service sends list of skucode request to inventoryService which will check whether that product is 
		// available or not
		
		List<String> skuCodes = order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();
		
		InventoryResponse result[] = webClientBuilder.build().get()
									.uri("http://inventory-service/api/inventory", 
											uriBuilder-> uriBuilder.queryParam("skuCode", skuCodes).build())
									.retrieve()
									.bodyToMono(InventoryResponse[].class)
									.block();//("http://localhost:8082/api/inventory", null).
						
		boolean inStock=Arrays.stream(result).allMatch(inventoryResponse->inventoryResponse.isInStock());
		log.info(""+result);
		if(inStock) {
//			System.out.println(orderLineItemList);
			orderRepo.save(order);
		}else
			throw new IllegalArgumentException("Product is out of stock");
	}

	private OrderLineItems mapToOrder(OrderLineItemsDto orderLineItemList) {
		// TODO Auto-generated method stub
		OrderLineItems item = new OrderLineItems();
		item.setPrice(orderLineItemList.getPrice());
		item.setQuantity(orderLineItemList.getQuantity());
		item.setSkuCode(item.getSkuCode());
		return item;
	}
}
