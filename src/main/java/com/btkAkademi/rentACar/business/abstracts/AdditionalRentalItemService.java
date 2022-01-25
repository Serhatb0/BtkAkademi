package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.CreateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.UpdateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface AdditionalRentalItemService {
	DataResult<List<AdditionalRentalItemListDto>> getAll();

	Result add(CreateAdditionalRentalItemRequest CreateAdditionaItemRequest);

	Result delete(int id);

	Result update(UpdateAdditionalRentalItemRequest updateAdditionaItemRequest);

	DataResult<List<AdditionalRentalItemListDto>> findByRentalId(int rentalId);
}
