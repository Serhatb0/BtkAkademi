package com.btkAkademi.rentACar.business.abstracts;

import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.btkAkademi.rentACar.core.utilities.results.Result;



public interface IPosService {
	Result checkIfCreditCardIsValid(CreateCreditCardRequest createAccountRequest);
}
