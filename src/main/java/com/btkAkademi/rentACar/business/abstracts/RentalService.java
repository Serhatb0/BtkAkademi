package com.btkAkademi.rentACar.business.abstracts;



import java.util.List;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.rentalRequest.CreateRentalRequest;
import com.btkAkademi.rentACar.business.requests.rentalRequest.UpdateRentalRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Rental;

public interface RentalService {
	
	DataResult<List<RentalListDto>> getAll();

	Result addIndividualCustomer(CreateRentalRequest createRentalRequest);
	
	Result addCorporateCustomer(CreateRentalRequest createRentalRequest);

	
	Result update(UpdateRentalRequest updateRentalRequest);
	
	boolean findByCarIdAndReturnDateIsNotNull(int id);
	
	DataResult<Rental> findById(int id);
	
	DataResult<RentalListDto> findByListDtoId(int id);
	
	Result deleteById(int id);
	
	boolean exsistById(int id);
	
	boolean findByIdAndPromosyonCodeIsNull(int id);

}
