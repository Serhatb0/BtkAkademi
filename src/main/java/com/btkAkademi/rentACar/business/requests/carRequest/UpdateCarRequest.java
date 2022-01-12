package com.btkAkademi.rentACar.business.requests.carRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	private int id;
	
	private double dailyPrice;

	private int modelYear;

	private String description;

	private int findexScore;

	private int kilometer;

}
