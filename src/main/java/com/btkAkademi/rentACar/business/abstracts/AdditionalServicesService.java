package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;
import com.btkAkademi.rentACar.entities.concretes.Customer;

public interface AdditionalServicesService  {
	
	Result add(CreateAdditionalServiceRequest createAdditionalServicesRequest);
	
	DataResult<AdditionalServices> findByRental_Id(int id);



}
