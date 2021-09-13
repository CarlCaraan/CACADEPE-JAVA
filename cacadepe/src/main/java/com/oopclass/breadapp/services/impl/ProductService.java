package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Product;
import com.oopclass.breadapp.repository.ProductRepository;
import com.oopclass.breadapp.services.IProductService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class ProductService implements IProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product save(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public Product update(Product entity) {
		return productRepository.save(entity);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product find(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Product> products) {
		productRepository.deleteInBatch(products);
	}
	
}
