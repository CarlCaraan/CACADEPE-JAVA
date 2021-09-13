package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Pricelist;
import com.oopclass.breadapp.repository.PricelistRepository;
import com.oopclass.breadapp.services.IPricelistService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class PricelistService implements IPricelistService {
	
	@Autowired
	private PricelistRepository pricelistRepository;
	
	@Override
	public Pricelist save(Pricelist entity) {
		return pricelistRepository.save(entity);
	}

	@Override
	public Pricelist update(Pricelist entity) {
		return pricelistRepository.save(entity);
	}

	@Override
	public void delete(Pricelist entity) {
		pricelistRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		pricelistRepository.deleteById(id);
	}

	@Override
	public Pricelist find(Long id) {
		return pricelistRepository.findById(id).orElse(null);
	}

	@Override
	public List<Pricelist> findAll() {
		return pricelistRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Pricelist> pricelists) {
		pricelistRepository.deleteInBatch(pricelists);
	}
	
}
