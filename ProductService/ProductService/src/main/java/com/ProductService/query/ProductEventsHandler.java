package com.ProductService.query;

import java.util.UUID;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ProductService.core.data.ProductEntity;
import com.ProductService.core.data.ProductRepository;
import com.ProductService.core.events.ProductCreatedEvent;

@Component
public class ProductEventsHandler {
	
	private final ProductRepository productRepository;
	
	public ProductEventsHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@EventHandler
	public void on(ProductCreatedEvent event) {
	    try {
	    	
	    	String title = event.getTitle();
	    	if(productRepository.existsByTitle(title)) {
	    		title += "_" + UUID.randomUUID().toString().substring(0, 8);
	    	}
	        ProductEntity entity = new ProductEntity();
	        event.setTitle(title);
	        BeanUtils.copyProperties(event, entity);

	        System.out.println("Attempting to save: " + entity.getProductId());
	        productRepository.save(entity);
	    } catch (Exception e) {
	        System.err.println("Failed to save product: " + e.getMessage());
	    }
	}

}
