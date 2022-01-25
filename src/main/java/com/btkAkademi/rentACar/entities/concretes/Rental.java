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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "return_date")
	private LocalDate returnDate;
	@Column(name = "rented_kilometer")
	private int rentedKilometer;
	@Column(name = "returned_Kilometer")
	private int returnedKilometer;

	@Column(name = "rent_date")
	private LocalDate rentDate;

	@ManyToOne
	@JoinColumn(name = "pick_up_city_id")
	private City pickUpCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;

	@OneToMany(mappedBy = "rental")
	private List<Invoice> invoice;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	@ManyToOne
	@JoinColumn(name = "promosyon_code_id")
	private PromosyonCode promosyonCode;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	
	@OneToOne(mappedBy = "rental")
	private Payment payment;
	
	@OneToMany(mappedBy = "rental")
	private List<AdditionalRentalItem>additionalRentalItems;

}


