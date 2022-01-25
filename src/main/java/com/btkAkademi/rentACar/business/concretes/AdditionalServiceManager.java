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
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AdditionalServicesDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;

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
		
		Result result = BusinessRules.run(checkIfAdditionalServiceNameExists(createAdditionalServicesRequest.getName()));
		
		if(result != null) {
			return result;
		}
	
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServicesRequest,
				AdditionalService.class);
		this.additionalServicesDao.save(additionalService);
		return new SuccessResult(Messages.additionalServiceAdded);
	}

	

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		if (this.additionalServicesDao.existsById(updateAdditionalServiceRequest.getId())) {
			AdditionalService additionalService = this.additionalServicesDao
					.findById(updateAdditionalServiceRequest.getId());

			additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
					AdditionalService.class);
			this.additionalServicesDao.save(additionalService);
			return new SuccessResult(Messages.additionalServiceUpdated);
		}
		return new ErrorResult(Messages.additionalServiceIsNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if (this.additionalServicesDao.existsById(id)) {
			this.additionalServicesDao.deleteById(id);
			return new SuccessResult(Messages.additionalServiceDeleted);
		}
		return new ErrorResult(Messages.additionalServiceIsNotFound);

	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		List<AdditionalService> additionalServicesList = this.additionalServicesDao.findAll();
		List<AdditionalServiceListDto> response = additionalServicesList.stream().map(
				additionalService -> modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalServiceListDto>>(response);
	}
	
	private Result checkIfAdditionalServiceNameExists(String name) {
		AdditionalService additionalServices = this.additionalServicesDao.findByName(name);
		if (additionalServices != null) {
			return new ErrorResult(Messages.additionalServiceIsNotFound);
		}
		return new SuccessResult();
	}

}
