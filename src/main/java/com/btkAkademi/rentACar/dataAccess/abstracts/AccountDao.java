package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
	
	Account findById(int id);

}
