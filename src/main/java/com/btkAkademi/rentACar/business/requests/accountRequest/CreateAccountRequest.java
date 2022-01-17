package com.btkAkademi.rentACar.business.requests.accountRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {
	String creditCard;

	String validDate;

	String cVC;

	String smsPassword;

	private int userId;

}
