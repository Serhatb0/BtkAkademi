package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;


public interface AdditionalServicesDao extends JpaRepository<AdditionalServices, Integer>{

	
}
