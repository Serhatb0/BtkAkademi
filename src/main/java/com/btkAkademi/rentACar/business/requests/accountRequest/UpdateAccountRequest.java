package com.btkAkademi.rentACar.business.requests.accountRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequest {

	private int id;
	
	String creditCard;

	String validDate;

	String cVC;

	private int userId;

}
