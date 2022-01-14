package com.btkAkademi.rentACar.business.requests.paymentRequest;

import java.time.LocalDate;

import com.btkAkademi.rentACar.entities.concretes.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {


	private LocalDate paymentDate;
	
	private int rentalId;
}
