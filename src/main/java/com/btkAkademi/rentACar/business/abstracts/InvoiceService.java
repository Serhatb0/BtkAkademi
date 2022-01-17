package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.InvoiceCorporateCustomerListDto;
import com.btkAkademi.rentACar.business.dtos.InvoiceIndividualCustomerListDto;
import com.btkAkademi.rentACar.business.requests.ınvoiceRequest.CreateInvoiceRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Invoice;

public interface InvoiceService {
	DataResult<List<Invoice>> getAll();
	DataResult<InvoiceIndividualCustomerListDto> createInvoiceForIndividualCustomer(int rentalId);
	DataResult<InvoiceCorporateCustomerListDto> createInvoiceForCorporateCustomer(int rentalId);
	
	Result add(CreateInvoiceRequest createInvoiceRequest);
}
