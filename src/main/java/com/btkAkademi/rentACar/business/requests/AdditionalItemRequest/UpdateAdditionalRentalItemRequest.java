package com.btkAkademi.rentACar.business.requests.AdditionalItemRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalRentalItemRequest {
	private int id;
	private int additionalServiceId;
	private int rentalId;
}
