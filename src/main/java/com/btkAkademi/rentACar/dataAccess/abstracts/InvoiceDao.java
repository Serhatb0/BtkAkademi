package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
	
	Invoice findById(int id);
	
	Invoice findByRentalId(int id);

}
