package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceIndividualCustomerListDto {
	
	private String ıdentityNumber;
	private String firstName;
	private String lastName;
	private String email;
	private double dailyPrice;
	private LocalDate rentDate;
	private LocalDate returnedDate;
	private double totalPrice;
	private double additionalTotalPrice;
	LocalDate creationDate;
	private List<AdditionalServiceListDto> additonalServices;

}
