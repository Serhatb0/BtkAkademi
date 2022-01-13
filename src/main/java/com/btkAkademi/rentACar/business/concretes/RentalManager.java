package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintenanceService;
import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
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
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;
	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			CorporateCustomerService corporateCustomerService, IndividualCustomerService individualCustomerService,
			CarMaintenanceService carMaintenanceService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		this.carMaintenanceService = carMaintenanceService;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {

//		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService
//				.findById(createRentalRequest.getCustomerId());
//
//		DataResult<IndividualCustomer> individualCustomerd = this.individualCustomerService
//				.findById(createRentalRequest.getCustomerId());
//		if (corporateCustomer.getData() == null) {
//			return new ErrorResult(Messages.customerIsNotFound);
//		}

	
		Result result = BusinessRules.run(
				checkIfKilometer(createRentalRequest.getRentedKilometer(), createRentalRequest.getReturnedKilometer()),
				checkIfDate(createRentalRequest.getRentDate(), createRentalRequest.getReturnDate()),
				checkCarMaintenance(createRentalRequest.getCarId()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	private Result checkIfKilometer(int rentedKilometer, int returnedKilometer) {

		if (rentedKilometer >= returnedKilometer) {
			return new ErrorResult(Messages.kilometerError);
		}
		return new SuccessResult();
	}

	private Result checkIfDate(LocalDate rentDate, LocalDate returnDate) {

		if (rentDate.getDayOfMonth() >= returnDate.getDayOfMonth()) {
			return new ErrorResult(Messages.rentalDateError);
		}
		return new SuccessResult();
	}

	private Result checkCarMaintenance(int id) {
		DataResult<CarMaintenance> carMaintenance = this.carMaintenanceService.findById(id);
		if (carMaintenance.getData() == null) {
			return new ErrorResult("Araba Bakımda");
		}
		return new SuccessResult();
	}

}
