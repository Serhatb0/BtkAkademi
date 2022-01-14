package com.btkAkademi.rentACar.business.abstracts;



import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalService {


	Result add(CreateRentalRequest createRentalRequest);
	
	boolean findByCarIdAndReturnDateIsNotNull(int id);
	
	
	DataResult<Rental> findById(int id);
	

}
