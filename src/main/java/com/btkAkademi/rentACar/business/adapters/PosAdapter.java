package com.btkAkademi.rentACar.business.adapters;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.IPosService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.btkAkademi.rentACar.core.adapter.fakeService.FakePosManager;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;

@Service
public class PosAdapter implements IPosService {
	FakePosManager fakePosManager = new FakePosManager();

	@Override
	public Result checkIfCreditCardIsValid(CreateCreditCardRequest createAccountRequest) {
		if (fakePosManager.checkCreditCardInformation(createAccountRequest.getCardName(), createAccountRequest.getCardNumber(),
				createAccountRequest.getExpirationDate(), createAccountRequest.getCvv())) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.cardInformationError);
	}
}
