package com.btkAkademi.rentACar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_maintenances")
public class CarMaintenance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "given_institution")
	private String givenInstitution;
	@Column(name = "date_of_care")
	private LocalDate dateOfCare;
	@Column(name = "date_of_arrival")
	private LocalDate dateOfArrival;


	@Column(name = "status")
	private Boolean status;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

}
