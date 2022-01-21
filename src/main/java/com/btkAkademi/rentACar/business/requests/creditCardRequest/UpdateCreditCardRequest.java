package com.btkAkademi.rentACar.business.requests.creditCardRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {

	@NotNull
	private int id;

	@NotNull
	private String expirationDate;

	@NotNull
	@NotBlank
	private String cardNumber;
	@NotNull
	@NotBlank
	private String cardName;
	@NotNull
	@NotBlank
	@Size(min = 3,max=3)
	private String cvv;
	
	@NotNull
	private int userId;

}
