package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Promosyon;

public interface PromosyonDao extends JpaRepository<Promosyon, Integer>{
	
	Promosyon findById(int id);

}
