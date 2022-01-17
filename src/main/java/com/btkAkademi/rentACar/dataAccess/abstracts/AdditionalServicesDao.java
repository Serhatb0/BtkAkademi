package com.btkAkademi.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;
import com.btkAkademi.rentACar.entities.concretes.Customer;


public interface AdditionalServicesDao extends JpaRepository<AdditionalServices, Integer>{
	List<AdditionalServices> findByRental_Id(int id);
	
	AdditionalServices findById(int id);

	
}
