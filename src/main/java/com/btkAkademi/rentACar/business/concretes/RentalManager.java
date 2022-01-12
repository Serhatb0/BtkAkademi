package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
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
import com.btkAkademi.rentACar.entities.concretes.Brand;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			CorporateCustomerService corporateCustomerService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService.findById(createRentalRequest.getCustomerId());
		if(corporateCustomer.getData() == null) {
			return new ErrorResult("Boyle Kullanıcı Bulunamadı");
		}
		Result result = BusinessRules.run(
				checkIfKilometer(createRentalRequest.getRentedKilometer(), createRentalRequest.getReturnedKilometer())
				,checkIfDate(createRentalRequest.getRentDate(),createRentalRequest.getReturnDate()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	private Result checkIfKilometer(int rentedKilometer, int returnedKilometer) {

		if (rentedKilometer >= returnedKilometer) {
			return new ErrorResult("Kilometre Daha Fazla Olması Lazım");
		}
		return new SuccessResult();
	}
	
	private Result checkIfDate(LocalDate rentDate , LocalDate returnDate) {

		if (rentDate.getDayOfMonth() >= returnDate.getDayOfMonth()) {
			return new ErrorResult("Tarih Hatalı");
		}
		return new SuccessResult();
	}

}
