package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;

public interface PromosyonCodeDao extends JpaRepository<PromosyonCode, Integer>{
	
	PromosyonCode findById(int id);

}
