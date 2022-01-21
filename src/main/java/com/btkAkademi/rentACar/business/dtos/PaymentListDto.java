package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentListDto {
	private LocalDate paymentDate;
	private boolean saveCreditStatus;
	private int rentalId;
	private CreateCreditCardRequest account;
}
