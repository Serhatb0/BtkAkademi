package com.btkAkademi.rentACar.business.requests.ınvoiceRequest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	private LocalDate createDate;
	private int rentalId;
	
}
