package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Payment;

public interface PaymentDao extends JpaRepository<Payment, Integer>{
	
	Payment findByRental_Id(int rentalId);
	
	Payment findById(int id);

}
