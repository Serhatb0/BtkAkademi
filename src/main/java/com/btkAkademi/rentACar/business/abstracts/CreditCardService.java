package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;
import com.btkAkademi.rentACar.business.dtos.CreditCardListDto;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.CreditCard;

public interface CreditCardService {

	DataResult<List<CreditCardListDto>> getAll();

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result deleteById(int id);

	Result add(CreateCreditCardRequest createCreditCardRequest);

	DataResult<CreditCard> findById(int id);

}
