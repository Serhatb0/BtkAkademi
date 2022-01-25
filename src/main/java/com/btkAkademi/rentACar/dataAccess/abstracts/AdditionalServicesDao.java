package com.btkAkademi.rentACar.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.AdditionalService;



public interface AdditionalServicesDao extends JpaRepository<AdditionalService, Integer>{

	
	AdditionalService findById(int id);
	
	AdditionalService findByName(String name);

	
}
