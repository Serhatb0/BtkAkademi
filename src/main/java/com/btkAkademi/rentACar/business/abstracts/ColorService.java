package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.ColorListDto;

import com.btkAkademi.rentACar.business.requests.colorRequest.CreateColorRequest;
import com.btkAkademi.rentACar.business.requests.colorRequest.UpdateColorRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Color;

public interface ColorService {
	DataResult<List<ColorListDto>> getAll();

	Result add(CreateColorRequest createColorRequest);

	Result update(UpdateColorRequest updateColorRequest);
	
	DataResult<Color> findById(int id);
	
}
