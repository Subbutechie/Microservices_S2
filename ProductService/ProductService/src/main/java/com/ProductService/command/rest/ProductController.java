package com.ProductService.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProductService.commands.CreateProductCommand;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	
	@Autowired
	private CommandGateway commandGateway;	
	
	
	@PostMapping
	public String createProduct(@RequestBody CreateProductRestModel createProductModel) {
		
		CreateProductCommand createProductCommand = new CreateProductCommand();
		createProductCommand.setProductId(UUID.randomUUID().toString());
		createProductCommand.setTitle(createProductModel.getTitle());
		createProductCommand.setPrice(createProductModel.getPrice());
		createProductCommand.setQuantity(createProductModel.getQuantity());
		String returnValue;
		try {
			returnValue = commandGateway.sendAndWait(createProductCommand);
			
		} catch (Exception e) {
			returnValue = e.getLocalizedMessage();
		}
		
		return returnValue;
	}

}
