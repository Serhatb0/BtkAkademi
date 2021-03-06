package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CreditCardService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.IPosService;

import com.btkAkademi.rentACar.business.abstracts.PaymentService;
import com.btkAkademi.rentACar.business.abstracts.PromosyonCodeService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.dtos.PaymentListDto;
import com.btkAkademi.rentACar.business.requests.creditCardRequest.CreateCreditCardRequest;
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
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.Payment;
import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService mapperService;
	private AdditionalRentalItemService additionalRentalItemService;
	private RentalService rentalService;
	private CarService carService;
	private CreditCardService creditCardService;
	private IPosService posService;
	private PromosyonCodeService promosyonService;

	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService mapperService,
			AdditionalRentalItemService additionalRentalItemService, RentalService rentalService, CarService carService,
			CreditCardService creditCardService, IPosService posService, PromosyonCodeService promosyonService) {
		this.paymentDao = paymentDao;
		this.mapperService = mapperService;
		this.additionalRentalItemService = additionalRentalItemService;
		this.rentalService = rentalService;
		this.carService = carService;
		this.creditCardService = creditCardService;
		this.posService = posService;
		this.promosyonService = promosyonService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {
		Result result = BusinessRules.run(checkIfPayment(createPaymentRequest.getRentalId()),
				checkIfPomosyonExsist(createPaymentRequest.getPromosyonId()),
				checkIfPromosyonCodeExpire(createPaymentRequest.getPromosyonId()));

		if (result != null) {
			return result;
		}

		Payment payment = mapperService.forRequest().map(createPaymentRequest, Payment.class);

		payment.setTotalAmount(
				totalCalculation(createPaymentRequest.getRentalId(), createPaymentRequest.getPromosyonId()));

		this.paymentDao.save(payment);
		return new SuccessResult(Messages.paymentCompleted);

	}

	private int additionalServiceCalculation(int rentalId) {
		List<AdditionalRentalItemListDto> additionalServices = this.additionalRentalItemService.findByRentalId(rentalId)
				.getData();
		int total = 0;
		for (AdditionalRentalItemListDto additional : additionalServices) {
			total += additional.getPrice();

		}
		return total;

	}

	private int totalCalculation(int rentalId, int promosyonId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		int additionalTotal = additionalServiceCalculation(rentalId);
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}
		int total = (int) (additionalTotal + rentDay * car.getData().getDailyPrice());
		if (this.promosyonService.exsistById(promosyonId)) {
			DataResult<PromosyonCode> promosyon = this.promosyonService.findById(promosyonId);
			int discountAmount = (int) (total * (promosyon.getData().getDiscountRate()) / 100);
			return total - discountAmount;
		}
		return total;

	}




	private Result checkIfPomosyonExsist(int promosyonId) {
		if (promosyonId != 0) {
			if (!this.promosyonService.exsistById(promosyonId)) {
				return new ErrorResult(Messages.promosyonCodeIsNotFound);
			}
		}
		return new SuccessResult();

	}

	private Result checkIfPromosyonCodeExpire(int promosyonId) {
		if (!checkIfPomosyonExsist(promosyonId).isSuccess() || promosyonId == 0) {
			return new SuccessResult();
		} else {
			PromosyonCode promosyon = this.promosyonService.findById(promosyonId).getData();
			if (this.promosyonService.promosyonCodeTime(promosyon.getPromosyonStart(), promosyon.getPromosyonEnd())) {
				return new SuccessResult();
			}
			return new ErrorResult(Messages.expiredTime);
		}

	}

	private Result checkIfPayment(int rentalId) {
		if (this.paymentDao.findByRental_Id(rentalId) != null) {
			return new ErrorResult(Messages.paymentAvailable);
		}
		return new SuccessResult();
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
		if (this.paymentDao.existsById(id)) {
			this.paymentDao.deleteById(id);
			return new SuccessResult(Messages.paymentDeleted);
		}
		return new ErrorResult(Messages.paymentIsNotFound);

	}

}
