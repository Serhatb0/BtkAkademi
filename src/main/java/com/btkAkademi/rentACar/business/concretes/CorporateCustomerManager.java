package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CorporateCustomerListDto;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateCorporateCustomerRequest;
import com.btkAkademi.rentACar.business.requests.customerRequest.UpdateCorporateCustomerRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;

	private ModelMapperService modelMapperService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomer) {
		Result result = BusinessRules.run(checkIfCompanyNameExists(createCorporateCustomer.getCompanyName()));

		if (result != null) {
			return result;
		}
		CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(createCorporateCustomer,
				CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.corporateCustomerAdded);
	}

	private Result checkIfCompanyNameExists(String companyName) {
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.findByCompanyName(companyName);
		if (corporateCustomer != null) {
			return new ErrorResult(Messages.customerIsExists);
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<CorporateCustomer> findById(int id) {
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.findById(id));
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		List<CorporateCustomer> corporateCustomerList = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = corporateCustomerList.stream().map(
				corporateCustomer -> modelMapperService.forDto().map(corporateCustomer, CorporateCustomerListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CorporateCustomerListDto>>(response);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		if (this.corporateCustomerDao.existsById(updateCorporateCustomerRequest.getId())) {
			CorporateCustomer corporateCustomer = this.corporateCustomerDao
					.findById(updateCorporateCustomerRequest.getId());
			corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
					CorporateCustomer.class);
			this.corporateCustomerDao.save(corporateCustomer);
			return new SuccessResult(Messages.corporateCustomerUpdated);
		}
		return new ErrorResult(Messages.corporateCustomerIsNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if (this.corporateCustomerDao.existsById(id)) {
			this.corporateCustomerDao.deleteById(id);
			return new SuccessResult(Messages.corporateCustomerDeleted);
		}
		return new ErrorResult(Messages.corporateCustomerIsNotFound);
	}

}
