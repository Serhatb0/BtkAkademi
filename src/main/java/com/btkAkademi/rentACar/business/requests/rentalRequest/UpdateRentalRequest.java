package com.btkAkademi.rentACar.business.requests.rentalRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	private int id;
	private int returnedKilometer;
	private LocalDate returnDate;
	private int pickUpCityId;
	private int returnCityId;
	private int rentedKilometer;
	private LocalDate rentDate;
	private int customerId;
	private int carId;
	private String promosyonCode;
}
