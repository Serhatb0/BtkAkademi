package com.btkAkademi.rentACar.business.abstracts;



import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;

import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface RentalService {


	Result add(CreateRentalRequest createRentalRequest);
	
	boolean findByCarIdAndReturnDateIsNotNull(int id);
	

}
