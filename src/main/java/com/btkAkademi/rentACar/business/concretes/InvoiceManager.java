package com.btkAkademi.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.abstracts.AdditionalServicesService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.CorporateCustomerService;
import com.btkAkademi.rentACar.business.abstracts.IndividualCustomerService;
import com.btkAkademi.rentACar.business.abstracts.InvoiceService;
import com.btkAkademi.rentACar.business.abstracts.PromosyonCodeService;
import com.btkAkademi.rentACar.business.abstracts.RentalService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceCorporateCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceIndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.ınvoiceRequest.CreateInvoiceRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.InvoiceDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalRentalItem;
import com.btkAkademi.rentACar.entities.concretes.Car;
import com.btkAkademi.rentACar.entities.concretes.CorporateCustomer;
import com.btkAkademi.rentACar.entities.concretes.IndividualCustomer;
import com.btkAkademi.rentACar.entities.concretes.Invoice;
import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;
import com.btkAkademi.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {

	private ModelMapperService modelMapperService;
	private InvoiceDao invoiceDao;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private CarService carService;
	private RentalService rentalService;
	private AdditionalServicesService additionalServiceService;
	private PromosyonCodeService promosyonCodeService;
	private AdditionalRentalItemService additionalRentalItemService;

	@Autowired
	@Lazy
	public InvoiceManager(ModelMapperService modelMapperService, InvoiceDao invoiceDao,
			IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService,
			CarService carService, RentalService rentalService, AdditionalServicesService additionalServiceService,
			PromosyonCodeService promosyonCodeService, AdditionalRentalItemService additionalRentalItemService) {
		super();
		this.modelMapperService = modelMapperService;
		this.invoiceDao = invoiceDao;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.carService = carService;
		this.rentalService = rentalService;
		this.additionalServiceService = additionalServiceService;
		this.promosyonCodeService = promosyonCodeService;
		this.additionalRentalItemService = additionalRentalItemService;
	}

	@Override
	public DataResult<InvoiceCorporateCustomerListDto> createInvoiceForCorporateCustomer(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		DataResult<CorporateCustomer> corporateCustomer = this.corporateCustomerService
				.findById(rental.getData().getCustomer().getId());

		Invoice invoice = this.invoiceDao.findByRentalId(rentalId);
		InvoiceCorporateCustomerListDto invoiceCorporateCustomerListDto = InvoiceCorporateCustomerListDto.builder()
				.companyName(corporateCustomer.getData().getCompanyName()).email(corporateCustomer.getData().getEmail())
				.additionalTotalPrice(additionalServiceCalculation(rentalId))
				.totalPrice(totalCalculation(rentalId, rental.getData().getPromosyonCode().getId()))
				.creationDate(invoice.getCreateDate()).dailyPrice(car.getData().getDailyPrice())
				.taxNumber(corporateCustomer.getData().getTaxNumber()).rentDate(rental.getData().getRentDate())
				.returnedDate(rental.getData().getReturnDate()).build();

		return new SuccessDataResult<InvoiceCorporateCustomerListDto>(invoiceCorporateCustomerListDto);
	}

	@Override
	public DataResult<InvoiceIndividualCustomerListDto> createInvoiceForIndividualCustomer(int rentalId) {
		int promosyonCodeId;

		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		if (rental.getData().getPromosyonCode() == null) {
			promosyonCodeId = 0;
		} else {
			promosyonCodeId = rental.getData().getPromosyonCode().getId();
		}

		DataResult<IndividualCustomer> individualCustomer = this.individualCustomerService
				.findById(rental.getData().getCustomer().getId());

		Invoice invoice = this.invoiceDao.findByRentalId(rentalId);
		InvoiceIndividualCustomerListDto invoiceIndividualCustomerListDto = InvoiceIndividualCustomerListDto.builder()
				.ıdentityNumber(individualCustomer.getData().getIdentityNumber())
				.firstName(individualCustomer.getData().getFirstName())
				.lastName(individualCustomer.getData().getLastName()).email(individualCustomer.getData().getEmail())
				.rentDate(rental.getData().getRentDate()).returnedDate(rental.getData().getReturnDate())
				.rentTotalPrice(priceDailyPriceCalculation(rentalId))
				.discountAmount(discountAmountCalculation(rentalId, promosyonCodeId))
				.totalPrice(totalCalculation(rentalId, promosyonCodeId)).creationDate(LocalDate.now())
				.additionalTotalPrice(additionalServiceCalculation(rentalId)).dailyPrice(car.getData().getDailyPrice())
				.build();

		return new SuccessDataResult<InvoiceIndividualCustomerListDto>(invoiceIndividualCustomerListDto);
	}

	private int additionalServiceCalculation(int rentalId) {
		List<AdditionalRentalItemListDto> additionalRentalItem = this.additionalRentalItemService
				.findByRentalId(rentalId).getData();

		int total = 0;
		for (AdditionalRentalItemListDto additional : additionalRentalItem) {
			total += additional.getPrice();

		}
		return total;
	}

	private int discountAmountCalculation(int rentalId, int promosyonId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		int additionalTotal = additionalServiceCalculation(rentalId);
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}
		int total = (int) (additionalTotal + rentDay * car.getData().getDailyPrice());
		if (this.promosyonCodeService.exsistById(promosyonId)) {
			DataResult<PromosyonCode> promosyon = this.promosyonCodeService.findById(promosyonId);
			int discountAmount = (int) (total * (promosyon.getData().getDiscountRate()) / 100);
			return discountAmount;
		}
		return 0;
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
		if (this.promosyonCodeService.exsistById(promosyonId)) {
			DataResult<PromosyonCode> promosyon = this.promosyonCodeService.findById(promosyonId);
			int discountAmount = (int) (total * (promosyon.getData().getDiscountRate()) / 100);
			return total - discountAmount;
		}
		return total;

	}

	private int priceDailyPriceCalculation(int rentalId) {
		DataResult<Car> car = this.carService.findByRentals_Id(rentalId);
		DataResult<Rental> rental = this.rentalService.findById(rentalId);
		int rentDay = rental.getData().getReturnDate().getDayOfMonth() - rental.getData().getRentDate().getDayOfMonth();
		if (rentDay == 0) {
			rentDay = 1;
		}

		return (int) (rentDay * car.getData().getDailyPrice());

	}

	@Override
	public DataResult<List<Invoice>> getAll() {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll());
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		this.invoiceDao.save(invoice);
		return new SuccessResult(Messages.invoiceAdded);
	}

}
