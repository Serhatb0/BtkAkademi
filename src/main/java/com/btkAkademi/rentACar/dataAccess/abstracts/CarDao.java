package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface CarDao extends JpaRepository<Car, Integer> {
	
	Car  findById(int id);
	
	
	Page<Car> findByRentals_IdIsNullOrRentals_returnDateIsNotNull(Pageable pageable);

}
