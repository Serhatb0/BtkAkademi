package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.PromosyonListDto;
import com.btkAkademi.rentACar.business.requests.promosyonRequest.CreatePromosyonRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Promosyon;

public interface PromosyonService {

	DataResult<Promosyon> findById(int id);

	DataResult<List<PromosyonListDto>> getAll();

	Result add(CreatePromosyonRequest createPromosyonRequest);

	boolean exsistById(int id);

}
