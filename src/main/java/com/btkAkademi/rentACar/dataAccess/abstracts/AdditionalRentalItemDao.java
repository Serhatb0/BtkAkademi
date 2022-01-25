package com.btkAkademi.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.AdditionalRentalItem;

public interface AdditionalRentalItemDao extends JpaRepository<AdditionalRentalItem, Integer> {

    List<AdditionalRentalItem> findByRentalId(int rentalId);
}
