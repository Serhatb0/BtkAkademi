package com.btkAkademi.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btkAkademi.rentACar.entities.concretes.CarSegment;

public interface CarSegmentDao extends JpaRepository<CarSegment, Integer> {

	CarSegment findBySegmentName(String name);
}
