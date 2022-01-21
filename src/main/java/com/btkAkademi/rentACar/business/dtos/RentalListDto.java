package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {
	private int id;
	private int rentedKilometer;
	private String pickUpCityName;
	private String returnCityName;
	private LocalDate rentDate;
	private int returnedKilometer;
	private LocalDate returnDate;
	private String email;
	private int carDailyPrice;

}
