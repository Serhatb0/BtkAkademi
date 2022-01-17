package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AccountService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.IPosService;
import com.btkAkademi.rentACar.business.abstracts.PaymentService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.business.requests.paymentRequest.CreatePaymentRequest;
import com.btkAkademi.rentACar.business.requests.paymentRequest.UpdatePaymentRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PaymentDao;
import com.btkAkademi.rentACar.entities.concretes.Account;
import com.btkAkademi.rentACar.entities.concretes.AdditionalServices;
import com.btkAkademi.rentACar.entities.concretes.Brand;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.Payment;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService mapperService;
	private AdditionalServicesService additionalServicesService;
	private RentalService rentalService;
	private CarService carService;
	private AccountService accountService;
	private IPosService posService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService mapperService,
			AdditionalServicesService additionalServicesService, RentalService rentalService, CarService carService,
			AccountService accountService, IPosService posService) {
		super();
		this.paymentDao = paymentDao;
		this.mapperService = mapperService;
		this.additionalServicesService = additionalServicesService;
		this.rentalService = rentalService;
		this.carService = carService;
		this.accountService = accountService;
		this.posService = posService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {
		Result result = BusinessRules.run(checkIfPayment(createPaymentRequest.getRentalId()),
				checkIfCreditCardValid(createPaymentRequest.getAccount()));

		if (result != null) {
			return result;
		}

		if (checkIfSaveCredit(createPaymentRequest.isSaveCreditStatus())) {
			this.accountService.add(createPaymentRequest.getAccount());
		}

		Payment payment = mapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setTotalAmount(priceCalculation(createPaymentRequest.getRentalId()));
		this.paymentDao.save(payment);
		return new SuccessResult(Messages.paymentCompleted);

	}

	private int priceCalculation(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		List<AdditionalServices> additionalServices = this.additionalServicesService.findByRental_Id(rentalId);
		int total = 0;
		for (AdditionalServices additional : additionalServices) {
			total += additional.getPrice();

		}
//		Period rentDay = Period.between(rental.getData().getRentDate(),rental.getData().getReturnDate());
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}
		return (int) (total + rentDay * car.getData().getDailyPrice());

	}

	private Result checkIfPayment(int rentalId) {
		if (this.paymentDao.findByRental_Id(rentalId) != null) {
			return new ErrorResult(Messages.paymentAvailable);
		}
		return new SuccessResult();
	}

	private Result checkIfCreditCardValid(CreateAccountRequest account) {
		return this.posService.checkIfCreditCardIsValid(account);
	}

	private boolean checkIfSaveCredit(boolean status) {
		return status;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		List<Payment> paymnetList = this.paymentDao.findAll();
		List<PaymentListDto> response = paymnetList.stream()
				.map(payment -> mapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<PaymentListDto>>(response);
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {
		if (this.paymentDao.existsById(updatePaymentRequest.getId())) {
			Payment payment = this.paymentDao.findById(updatePaymentRequest.getId());

			payment = this.mapperService.forRequest().map(updatePaymentRequest, Payment.class);
			this.paymentDao.save(payment);
			return new SuccessResult(Messages.paymentUpdated);
		}
		return new ErrorResult(Messages.paymentIsNotFound);
	}

	@Override
	public Result deleteById(int id) {
		if(this.paymentDao.existsById(id)) {
			this.paymentDao.deleteById(id);
			return new SuccessResult(Messages.paymentDeleted);
		}
		return new ErrorResult(Messages.paymentIsNotFound);
		
	}

}
