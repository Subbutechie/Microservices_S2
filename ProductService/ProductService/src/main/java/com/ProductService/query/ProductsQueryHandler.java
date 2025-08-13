package com.ProductService.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.ProductService.core.data.ProductEntity;
import com.ProductService.core.data.ProductRepository;
import com.ProductService.query.rest.ProductsRestModel;

@Component
public class ProductsQueryHandler {
	
	private ProductRepository productRepository;
	
	public ProductsQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@QueryHandler
	public List<ProductsRestModel> findProducts(GetProductsQuery getProductsQuery){
		
		List<ProductEntity> prEntities = productRepository.findAll();
		
		List<ProductsRestModel> storedProducts = new ArrayList<ProductsRestModel>();
		
		for(ProductEntity productEntity: prEntities) {
			ProductsRestModel prm = new ProductsRestModel();
			BeanUtils.copyProperties(productEntity, prm);
			storedProducts.add(prm);
		}
		return storedProducts;
		
	}
}
