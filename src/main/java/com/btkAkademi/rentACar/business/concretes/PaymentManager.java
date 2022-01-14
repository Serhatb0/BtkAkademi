package com.btkAkademi.rentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.PaymentService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.requests.paymentRequest.CreatePaymentRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.PaymentDao;
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

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService mapperService,
			AdditionalServicesService additionalServicesService, RentalService rentalService, CarService carService) {
		super();
		this.paymentDao = paymentDao;
		this.mapperService = mapperService;
		this.additionalServicesService = additionalServicesService;
		this.rentalService = rentalService;
		this.carService = carService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		Payment payment = mapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setTotalAmount(priceCalculation(createPaymentRequest.getRentalId()));
		this.paymentDao.save(payment);
		return new SuccessResult(Messages.moneyPaid);

	}

	private int priceCalculation(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		DataResult<AdditionalServices> additionalService = this.additionalServicesService.findByRental_Id(rentalId);
		int priceAdditional = additionalService.getData().getPrice();
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		return (int) (priceAdditional + rentDay * car.getData().getDailyPrice());

	}

}
