package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer> {


	Rental findByCarIdAndReturnDateIsNotNull(int carId);

	Rental findById(int id);

	Page<Rental> findAll(Pageable pageable);
	
	Rental findByCarId(int carId);

}
