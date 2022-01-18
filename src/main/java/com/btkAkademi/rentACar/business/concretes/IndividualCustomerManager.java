package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.IndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateIndividualCustomer;
import com.btkAkademi.rentACar.business.requests.customerRequest.UpdateIndividualCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
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
				checkIfAgeControl(createIndividualCustomer.getBirthDate()));

		if (result != null) {
			return result;
		}
		IndividualCustomer individualCustomer = modelMapperService.forRequest().map(createIndividualCustomer,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.individualAdded);
	}
	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		
		Result result = BusinessRules.run(checkIfEmailExists(updateIndividualCustomerRequest.getEmail())
				,checkIfAgeControl(updateIndividualCustomerRequest.getBirthDate()));
		
		if(result != null) {
			return result;
		}
		
		
		if(this.individualCustomerDao.existsById(updateIndividualCustomerRequest.getId())) {
			IndividualCustomer individualCustomer = this.findById(updateIndividualCustomerRequest.getId()).getData();
			individualCustomer = modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
			this.individualCustomerDao.save(individualCustomer);
			return new SuccessResult(Messages.individualCustomerUpdated);
		}
		return new ErrorResult(Messages.individualCustomerIsNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if(this.individualCustomerDao.existsById(id)) {
			this.individualCustomerDao.deleteById(id);
			return new ErrorResult(Messages.individualCustomerDeleted);
		}
		return new ErrorResult(Messages.individualCustomerIsNotFound);
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		List<IndividualCustomer> individualCustomerList = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = individualCustomerList.stream()
				.map(individualCustomer -> modelMapperService.forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<IndividualCustomerListDto>>(response);
	}

	private Result checkIfEmailExists(String email) {
		IndividualCustomer individualCustomer = this.individualCustomerDao.findByEmail(email);
		if (individualCustomer != null) {
			return new ErrorResult(Messages.individualCustomerEmailExists);
		}
		return new SuccessResult();
	}

	private Result checkIfAgeControl(LocalDate birthDate) {
		int age = Period.between(birthDate, LocalDate.now()).getYears();
		if (age<18) {
			return new ErrorResult(Messages.ageError);
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<IndividualCustomer> findById(int id) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.findById(id));
	}

	

	

}
