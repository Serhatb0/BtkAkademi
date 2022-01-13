package com.btkAkademi.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.AdditionalServicesRequest.CreateAdditionalServiceRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.Result;
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

		AdditionalServices additionalService = this.modelMapperService.forRequest().map(createAdditionalServicesRequest, AdditionalServices.class);
		this.additionalServicesDao.save(additionalService);
		return new SuccessResult(Messages.additionalAdded);
	}
	
		
	

}
