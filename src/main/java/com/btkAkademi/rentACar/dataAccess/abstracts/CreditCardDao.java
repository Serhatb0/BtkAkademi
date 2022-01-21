package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.CreditCard;

public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {
    Boolean existsByCardNumber(String cardNumber);
	CreditCard findById(int id);

}
