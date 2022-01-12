package com.btkAkademi.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;

import com.btkAkademi.rentACar.dataAccess.abstracts.IndividualCustomerDao;

import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomer createIndividualCustomer) {

		Result result = BusinessRules.run(checkIfEmailExists(createIndividualCustomer.getEmail()),
				checkIfAgeExists(createIndividualCustomer.getBirthDate().getYear()));

		if (result != null) {
			return result;
		}
		IndividualCustomer individualCustomer = modelMapperService.forRequest().map(createIndividualCustomer,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.individualAdded);
	}

	private Result checkIfEmailExists(String email) {
		IndividualCustomer individualCustomer = this.individualCustomerDao.findByEmail(email);
		if (individualCustomer != null) {
			return new ErrorResult("Email Zaten Mevcut");
		}
		return new SuccessResult();
	}

	private Result checkIfAgeExists(int age) {

		if (age > 2003) {
			return new ErrorResult("Yaşı 18 den Küçük");
		}
		return new SuccessResult();
	}

}
