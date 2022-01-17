package com.btkAkademi.rentACar.business.adapters;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.IFindexService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.core.adapter.fakeService.FakeFindexScore;
import com.btkAkademi.rentACar.core.adapter.fakeService.FakePosManager;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;

@Service
public class FindexAdapter implements IFindexService {
	FakeFindexScore findexScore = new FakeFindexScore();


	@Override
	public int CorporateFindexScroe(String taxNumber) {
		return findexScore.checkCorporateFindexScore(taxNumber);
	}

	@Override
	public int IndividalFindexScore(String tc) {
		return findexScore.checkIndividualFindexScore(tc);
	}

	
}
