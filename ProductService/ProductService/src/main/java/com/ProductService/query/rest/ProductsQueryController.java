package com.ProductService.query.rest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProductService.query.GetProductsQuery;

@RestController
@RequestMapping("/products")
public class ProductsQueryController {

	@Autowired
	private QueryGateway queryGateway;

	@GetMapping
	public List<ProductsRestModel> getProducts() throws InterruptedException, ExecutionException {
		GetProductsQuery getProductsQuery = new GetProductsQuery();
		// List<ProductsRestModel> products ;

		List<ProductsRestModel> products = queryGateway
				.query(getProductsQuery, ResponseTypes.multipleInstancesOf(ProductsRestModel.class)).get();

		return products;
	}
}
