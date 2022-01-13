package com.btkAkademi.rentACar.business.requests.carMaintenanceRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	private LocalDate dateOfCare;

	private LocalDate dateOfArrival;

	private int carId;
}
