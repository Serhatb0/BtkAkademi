package com.btkAkademi.rentACar.business.dtos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {
	
	Customer findById(int id);

}
