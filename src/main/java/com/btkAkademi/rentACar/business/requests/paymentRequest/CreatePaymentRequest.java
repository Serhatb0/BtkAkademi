package com.btkAkademi.rentACar.business.requests.paymentRequest;

import java.time.LocalDate;

import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

	private LocalDate paymentDate;
	private boolean saveCreditStatus;
	private int rentalId;
	private CreateAccountRequest account;
	private int promosyonId;

}
