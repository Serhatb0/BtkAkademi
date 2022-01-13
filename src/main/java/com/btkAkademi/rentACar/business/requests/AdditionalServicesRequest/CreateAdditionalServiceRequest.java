package com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	private String name;
	private int rentalId;
}	
