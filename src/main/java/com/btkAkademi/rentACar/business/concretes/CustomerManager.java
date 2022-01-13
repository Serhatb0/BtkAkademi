package com.btkAkademi.rentACar.business.concretes;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CustomerService;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CustomerDao;
import com.btkAkademi.rentACar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private CustomerDao customerDao;
	

	public CustomerManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}


	@Override
	public SuccessDataResult<Customer> findById(int id) {
		return new SuccessDataResult<Customer>(this.customerDao.findById(id));
	}

}
