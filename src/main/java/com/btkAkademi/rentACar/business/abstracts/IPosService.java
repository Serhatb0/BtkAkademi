package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.core.utilities.results.Result;



public interface IPosService {
	Result checkIfCreditCardIsValid(CreateAccountRequest createAccountRequest);
}
