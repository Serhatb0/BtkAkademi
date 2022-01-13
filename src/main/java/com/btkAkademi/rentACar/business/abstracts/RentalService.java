package com.btkAkademi.rentACar.business.abstracts;


import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.CarMaintenance;
import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalService {


	Result add(CreateRentalRequest createRentalRequest);
	
	DataResult<Rental> findByIdAndReturnDateIsNotNull(int id);
}
