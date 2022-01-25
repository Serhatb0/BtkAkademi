package com.btkAkademi.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.btkAkademi.rentACar.business.dtos.PromosyonCodeListDto;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonCodeRequest;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.UpdatePromosyonCodeRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.PromosyonCode;

public interface PromosyonCodeService {

	DataResult<PromosyonCode> findById(int id);

	DataResult<List<PromosyonCodeListDto>> getAll();

	Result add(CreatePromosyonCodeRequest createPromosyonRequest);
	
	boolean promosyonCodeTime(LocalDate promosyonStart,LocalDate promosyonEnd);

	boolean exsistById(int id);
	
	Result update(UpdatePromosyonCodeRequest updatePromosyonRequest);

	Result deleteById(int id);
	
	
	PromosyonCode findByPromosyonCode(String promosyonCode);
	
	boolean  checkPromoCode(String promoCode);

}
