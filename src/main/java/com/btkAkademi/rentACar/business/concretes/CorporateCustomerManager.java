package com.btkAkademi.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.customerRequest.CreateCorporateCustomer;
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
public class CorporateCustomerManager implements CorporateCustomerService{
	
	private CorporateCustomerDao corporateCustomerDao;
	
	private ModelMapperService modelMapperService;
	
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}



	@Override
	public Result add(CreateCorporateCustomer createCorporateCustomer) {
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
		if(corporateCustomer != null) {
			return new ErrorResult(Messages.customerIsExists);
		}
		return new SuccessResult();
	}



	@Override
	public DataResult<CorporateCustomer> findById(int id) {
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.findById(id));
	}

}
