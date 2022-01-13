package com.btkAkademi.rentACar.business.requests.rentalRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

	private int pickUpCityId;

	private int returnCityId;

	private int rentedKilometer;

	private LocalDate rentDate;

	private int customerId;

	private int carId;

}
