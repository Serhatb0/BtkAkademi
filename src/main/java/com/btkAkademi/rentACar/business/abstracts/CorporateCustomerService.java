package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.customerRequest.CreateCorporateCustomer;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerService {
	
	Result add(CreateCorporateCustomer createCorporateCustomer);
	
	DataResult<CorporateCustomer> findById(int id);
}
