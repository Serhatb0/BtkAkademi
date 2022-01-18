package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;

import com.btkAkademi.rentACar.business.dtos.CarSegmentListDto;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.CreateCarSegmentRequest;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.UpdateCarSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;

public interface CarSegmentService {
	DataResult<List<CarSegmentListDto>> getAll();
	Result add(CreateCarSegmentRequest createCarSegmentRequest);
	Result update(UpdateCarSegmentRequest updateCarSegmentRequest);
	Result delete(int id);
}
