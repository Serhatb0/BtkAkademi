package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface AdditionalServicesService  {
	
	Result add(CreateAdditionalServiceRequest createAdditionalServicesRequest);

}
