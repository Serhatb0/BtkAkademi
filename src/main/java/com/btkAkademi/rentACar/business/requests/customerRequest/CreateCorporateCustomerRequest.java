package com.btkAkademi.rentACar.business.requests.customerRequest;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {
	private String email;

	private String password;
	
	private String companyName;

	private String taxNumber;
}
