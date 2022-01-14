package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;


public interface AdditionalServicesService  {
	
	Result add(CreateAdditionalServiceRequest createAdditionalServicesRequest);
	
	List<AdditionalServices> findByRental_Id(int id);



}
