package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;
import com.btkAkademi.rentACar.entities.concretes.Customer;


public interface AdditionalServicesDao extends JpaRepository<AdditionalServices, Integer>{
	AdditionalServices findByRental_Id(int id);

	
}
