package com.btkAkademi.rentACar.business.adapters;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.IFindexService;
import com.btkAkademi.rentACar.core.adapter.fakeService.FakeFindexScore;


@Service
public class FindexAdapter implements IFindexService {
	FakeFindexScore findexScore = new FakeFindexScore();


	@Override
	public int CorporateFindexScroe(String taxNumber) {
		return findexScore.checkCorporateFindexScore(taxNumber);
	}

	@Override
	public int IndividalFindexScore(String ıdentityNumber) {
		return findexScore.checkIndividualFindexScore(ıdentityNumber);
	}

	
}
