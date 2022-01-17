package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;

import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.UpdateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AdditionalServicesDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;

@Service
public class AdditionalServiceManager implements AdditionalServicesService {

	private AdditionalServicesDao additionalServicesDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServicesDao additionalServicesDao,
			ModelMapperService modelMapperService) {
		super();
		this.additionalServicesDao = additionalServicesDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServicesRequest) {

		AdditionalServices additionalService = this.modelMapperService.forRequest().map(createAdditionalServicesRequest,
				AdditionalServices.class);
		this.additionalServicesDao.save(additionalService);
		return new SuccessResult(Messages.additionalServiceAdded);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> findByRental_Id(int rentalId) {
		List<AdditionalServices> additionalServiceList = this.additionalServicesDao.findByRental_Id(rentalId);
		List<AdditionalServiceListDto> response = additionalServiceList.stream()
				.map(additionalService -> modelMapperService.forDto()
						.map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<AdditionalServiceListDto>>(response);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		AdditionalServices additionalService = this.additionalServicesDao
				.findById(updateAdditionalServiceRequest.getId());

		additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalServices.class);
		this.additionalServicesDao.save(additionalService);
		return new SuccessResult(Messages.additionalServiceUpdated);
	}

	@Override
	public Result deleteById(int id) {
		this.additionalServicesDao.deleteById(id);
		return new SuccessResult(Messages.additionalServiceDeleted);
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		List<AdditionalServices> additionalServicesList = this.additionalServicesDao.findAll();
		List<AdditionalServiceListDto> response = additionalServicesList.stream().map(
				additionalService -> modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalServiceListDto>>(response);
	}

}
