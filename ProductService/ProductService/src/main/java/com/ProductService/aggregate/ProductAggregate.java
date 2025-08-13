package com.ProductService.aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.ProductService.commands.CreateProductCommand;
import com.ProductService.core.events.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	
	//need a no-arg constructor to source the events 
	public ProductAggregate() {
		
	}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		
		//validating the command received
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0) {
			throw new IllegalArgumentException("price cannot be less than or equal to zero");
		}
		
		if(createProductCommand.getTitle()==null || createProductCommand.getTitle().isEmpty()) {
			throw new IllegalArgumentException("Title cannot be null or empty");
		}
		
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		
		//Apply a org.axonframework.eventhandling.DomainEventMessage with given payload without metadata (though interceptors can also be used to provide metadata).
		//Applying events means they are immediately applied (published) to the aggregate and scheduled for publication to other event handlers.
		AggregateLifecycle.apply(productCreatedEvent);
	}
	
	//sources or stores the current state of the event into the variables
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.productId = productCreatedEvent.getProductId();
		this.title = productCreatedEvent.getTitle();
		this.price= productCreatedEvent.getPrice();
		this.quantity = productCreatedEvent.getQuantity();
	}
	

}
