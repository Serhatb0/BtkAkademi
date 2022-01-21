package com.btkAkademi.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {
	
	private String creditCard;

	private String validDate;

	private String cVC;

	private String smsPassword;

	private int userId;

}
