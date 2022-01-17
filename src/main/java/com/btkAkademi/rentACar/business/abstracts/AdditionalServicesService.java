package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;

public interface AdditionalServicesService {

	DataResult<List<AdditionalServiceListDto>> getAll();

	Result add(CreateAdditionalServiceRequest createAdditionalServicesRequest);

	Result update(UpdateAdditionalServiceRequest updateCreateAdditionalServiceRequest);

	Result deleteById(int id);

	DataResult<List<AdditionalServiceListDto>> findByRental_Id(int id);

}
