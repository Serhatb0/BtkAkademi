package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CarMaintenanceService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.abstracts.CustomerService;
import com.btkAkademi.rentACar.business.abstracts.IFindexService;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.abstracts.PromosyonCodeService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarListDto;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequest.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.RentalDao;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.Customer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;
import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class RentalManager implements RentalService {
	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private CustomerService customerService;
	private IFindexService iFindexService;
	private IndividualCustomerService individualCustomerService;
	private CarService carService;
	private CorporateCustomerService corporateCustomerService;
	private PromosyonCodeService promosyonCodeService;


	@Autowired
	public RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService, CustomerService customerService, IFindexService iFindexService,
			IndividualCustomerService individualCustomerService, CarService carService,
			CorporateCustomerService corporateCustomerService, PromosyonCodeService promosyonCodeService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.customerService = customerService;
		this.iFindexService = iFindexService;
		this.individualCustomerService = individualCustomerService;
		this.carService = carService;
		this.corporateCustomerService = corporateCustomerService;
		this.promosyonCodeService = promosyonCodeService;
	}

	@Override
	public DataResult<List<RentalListDto>> getAll() {
		List<Rental> rentalList = this.rentalDao.findByAndReturnDateIsNull();
		List<RentalListDto> response = rentalList.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalListDto>>(response);
	}

	@Override
	public Result addCorporateCustomer(CreateRentalRequest createRentalRequest) {
		if (!checkIfCarMaintenance(createRentalRequest.getCarId()).isSuccess()
				|| !checkIfIsCarAlreadyRented(createRentalRequest.getCarId()).isSuccess()) {
			CarListDto car = findAvailableCarSegment(
					carService.findById(createRentalRequest.getCarId()).getData().getCarSegment().getId()).getData();
			if (car != null) {
				createRentalRequest.setCarId(car.getId());

			} else
				return new ErrorResult(Messages.noVehiclesAvailableInThisSegment);
		}

		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService
				.findById(createRentalRequest.getCustomerId());
		DataResult<Car> car = this.carService.findById(createRentalRequest.getCarId());

		Result result = BusinessRules.run(checkIfCustomerExist(createRentalRequest.getCustomerId()),
				checkIfCarMaintenance(createRentalRequest.getCarId()), checkIfCorporateCustomerFindexScoreIsEnough(
						corporateCustomer.getData().getTaxNumber(), car.getData().getFindexScore()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalAdded);
	}

	@Override
	public Result addIndividualCustomer(CreateRentalRequest createRentalRequest) {

		if (!checkIfCarMaintenance(createRentalRequest.getCarId()).isSuccess()
				|| !checkIfIsCarAlreadyRented(createRentalRequest.getCarId()).isSuccess()) {
			CarListDto car = findAvailableCarSegment(
					carService.findById(createRentalRequest.getCarId()).getData().getCarSegment().getId()).getData();
			if (car != null) {
				createRentalRequest.setCarId(car.getId());

			} else
				return new ErrorResult(Messages.noVehiclesAvailableInThisSegment);
		}

		DataResult<IndividualCustomer> individualCustomer = this.individualCustomerService
				.findById(createRentalRequest.getCustomerId());
		DataResult<Car> car = this.carService.findById(createRentalRequest.getCarId());

		Result result = BusinessRules.run(checkIfCustomerExist(createRentalRequest.getCustomerId()),
				checkIfCarMaintenance(createRentalRequest.getCarId()),
				checkIfIndividualCustomerFindexScoreIsEnough(individualCustomer.getData().getIdentityNumber(),
						car.getData().getFindexScore()),
				checkIfIndividualCustomerMinimumAgeIsEnough(individualCustomer.getData().getBirthDate(),
						car.getData().getMinimumAge()));

		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		this.rentalDao.save(rental);
		
		Rental rental1 = this.rentalDao.findById(rental.getId());
		RentalListDto response = modelMapperService.forDto().map(rental1, RentalListDto.class);
		return new SuccessDataResult(response,Messages.rentalAdded);
	}

	private DataResult<CarListDto> findAvailableCarSegment(int SegmentId) {
		if (carService.findAvailableCarBySegment(SegmentId).isSuccess()) {
			CarListDto car = carService
					.findByCarListDtoId(carService.findAvailableCarBySegment(SegmentId).getData().get(0)).getData();
			return new SuccessDataResult<CarListDto>(car);
		} else
			return new ErrorDataResult<CarListDto>();
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.rentalDao.findById(updateRentalRequest.getId());
		
		Result result = BusinessRules.run(
				checkIfKilometer(rental.getRentedKilometer(), updateRentalRequest.getReturnedKilometer()),
				
				checkIfPromosyonCodeExists(updateRentalRequest.getPromosyonCode()));

		if (result != null) {
			return result;
		}
		PromosyonCode promosyonCode = this.promosyonCodeService.findByPromosyonCode(updateRentalRequest.getPromosyonCode());
		rental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setPromosyonCode(promosyonCode);
		this.rentalDao.save(rental);
		return new SuccessResult(Messages.rentalUpdated);
	}

	private Result checkIfIndividualCustomerMinimumAgeIsEnough(LocalDate birthDate, int minimumAge) {
		int IndividualCustomerAge = Period.between(birthDate, LocalDate.now()).getYears();
		if (IndividualCustomerAge >= minimumAge) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.ageNotEnough);

	}
	private Result checkIfPromosyonCodeExists(String promosyonCode) {
		
		if(promosyonCode.equals("")) {
			return new SuccessResult();
		}
		if (this.promosyonCodeService.checkPromoCode(promosyonCode)) {
			return new SuccessResult();
			
		}
	
		return new ErrorResult(Messages.promosyonCodeIsNotFound);
	}

	private Result checkIfIndividualCustomerFindexScoreIsEnough(String tc, int findexScore) {
		if (this.iFindexService.IndividalFindexScore(tc) > findexScore) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.findexScoreNotEnough);

	}

	private Result checkIfCorporateCustomerFindexScoreIsEnough(String taxNumber, int findexScore) {
		if (this.iFindexService.CorporateFindexScroe(taxNumber) > findexScore) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.findexScoreNotEnough);

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

	private Result checkIfIsCarAlreadyRented(int carId) {
		if (this.rentalDao.findByCarIdAndReturnDateIsNotNull(carId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.carInRental);

	}

	@Override
	public DataResult<RentalListDto> findByListDtoId(int id) {
		if (this.rentalDao.existsById(id)) {

			RentalListDto response = modelMapperService.forDto().map(rentalDao.findById(id), RentalListDto.class);

			return new SuccessDataResult<RentalListDto>(response);
		} else
			return new ErrorDataResult<>(Messages.rentalNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if (this.rentalDao.existsById(id)) {
			this.rentalDao.deleteById(id);
			return new SuccessResult(Messages.rentalDeleted);
		}
		return new ErrorResult(Messages.rentalNotFound);
	}

	@Override
	public boolean findByIdAndPromosyonCodeIsNull(int id) {
		return this.rentalDao.findByIdAndPromosyonCodeIsNull(id);
	}

	@Override
	public DataResult<Rental> findById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.findById(id));
	}

	@Override
	public boolean exsistById(int id) {
		return this.exsistById(id);
	}

}
