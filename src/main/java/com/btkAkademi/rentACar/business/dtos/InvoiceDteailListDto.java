package com.btkAkademi.rentACar.business.dtos;

import java.util.List;

import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDteailListDto {
	
	private int carId;
	private int rentalTotal;
	private int additionalServicesTotal;
	private List<AdditionalServices> additionalServices;

   

}
