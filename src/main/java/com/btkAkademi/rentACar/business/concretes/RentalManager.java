package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintenanceService;

import com.btkAkademi.rentACar.business.abstracts.CustomerService;

import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequest.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.RentalDao;
import com.btkAkademi.rentACar.entities.concretes.Brand;
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
	public DataResult<List<RentalListDto>> getAll() {
		List<Rental> rentalList = this.rentalDao.findAll();
		List<RentalListDto> response = rentalList.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response);
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

		Result result = BusinessRules.run(checkIfCustomerExist(createRentalRequest.getCustomerId()),
				checkIfCarMaintenance(createRentalRequest.getCarId()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.rentalDao.findById(updateRentalRequest.getId());

		Result result = BusinessRules.run(
				checkIfKilometer(rental.getRentedKilometer(), updateRentalRequest.getReturnedKilometer()),
				checkIfDate(rental.getRentDate(), updateRentalRequest.getReturnDate()));

		if (result != null) {
			return result;
		}

		rental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalUpdated);
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

	private Result checkIfCarMaintenance(int carId) {
		if (carMaintenanceService.findByCarIdAndDateOfArrivalIsNull(carId)) {
			return new ErrorResult(Messages.carInMaintenance);
		}
		return new SuccessResult();
	}

	@Override
	public boolean findByCarIdAndReturnDateIsNotNull(int carId) {
		if (this.rentalDao.findByCarIdAndReturnDateIsNotNull(carId) != null) {
			return false;
		} else
			return true;
	}

	@Override
	public DataResult<Rental> findById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.findById(id));
	}

	@Override
	public Result deleteById(int id) {
		if(this.rentalDao.existsById(id)) {
			this.rentalDao.deleteById(id);
			return new SuccessResult(Messages.rentalDeleted);
		}
		return new  ErrorResult(Messages.rentalNotFound);
	}

	

}
