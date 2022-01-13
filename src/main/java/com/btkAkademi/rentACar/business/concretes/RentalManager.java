package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintenanceService;

import com.btkAkademi.rentACar.business.abstracts.CustomerService;

import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.RentalDao;
import com.btkAkademi.rentACar.entities.concretes.CarMaintenance;

import com.btkAkademi.rentACar.entities.concretes.Customer;

import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CustomerService customerService;

	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CustomerService customerService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.customerService = customerService;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

		Result result = BusinessRules.run(
				checkIfKilometer(createRentalRequest.getRentedKilometer(), createRentalRequest.getReturnedKilometer()),
				checkIfDate(createRentalRequest.getRentDate(), createRentalRequest.getReturnDate()),
				checkIfCarMaintenance(createRentalRequest.getCarId()),
				checkIfCustomerExist(createRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	private Result checkIfCustomerExist(int customerId) {
		DataResult<Customer> customer = this.customerService.findById(customerId);
		if (customer.getData() == null) {
			return new ErrorResult(Messages.customerIsNotFound);
		}

		return new SuccessResult();
	}

	private Result checkIfKilometer(int rentedKilometer, int returnedKilometer) {

		if (rentedKilometer >= returnedKilometer) {
			return new ErrorResult(Messages.kilometerError);
		}
		return new SuccessResult();
	}

	private Result checkIfDate(LocalDate rentDate, LocalDate returnDate) {

		if (!returnDate.isAfter(rentDate)) {
			return new ErrorResult(Messages.rentalDateError);
		}
		return new SuccessResult();
	}

	private Result checkIfCarMaintenance(int id) {
		DataResult<CarMaintenance> carMaintenance = this.carMaintenanceService.findByIdAndDateOfArrivalIsNotNull(id);
		if (carMaintenance.getData() == null) {
			return new ErrorResult(Messages.carInMaintenance);
		}
		return new SuccessResult();
	}

}
