package com.btkAkademi.rentACar.business.requests.rentalRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

	private String cityTaken;

	private String givenCity;

	private int rentedKilometer;

	private int returnedKilometer;

	private LocalDate rentDate;

	private int customerId;

	private int carId;

}
