package com.btkAkademi.rentACar.business.requests.paymentRequest;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	private LocalDate paymentDate;
	private int rentalId;
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
	private int promosyonId;

}
