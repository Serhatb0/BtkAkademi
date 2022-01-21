package com.btkAkademi.rentACar.dataAccess.abstracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer> {
	
	List<Rental> findByAndReturnDateIsNull();
	 
	Rental findByCarIdAndReturnDateIsNotNull(int carId);

	Rental findById(int id);

	
	Rental findByCarId(int carId);

}
