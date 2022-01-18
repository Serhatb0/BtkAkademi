package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.CarSegmentService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarSegmentListDto;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.CreateCarSegmentRequest;
import com.btkAkademi.rentACar.business.requests.carSegmentReuqest.UpdateCarSegmentRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarSegmentDao;
import com.btkAkademi.rentACar.entities.concretes.CarSegment;

@Service
public class CarSegmentManager implements CarSegmentService {

	private CarSegmentDao carSegmentDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarSegmentManager(CarSegmentDao carSegmentDao, ModelMapperService modelMapperService) {
		super();
		this.carSegmentDao = carSegmentDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarSegmentListDto>> getAll() {
		List<CarSegment> carSegmentList = this.carSegmentDao.findAll();
		List<CarSegmentListDto> response = carSegmentList.stream()
				.map(carSegment -> modelMapperService.forDto().map(carSegment, CarSegmentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarSegmentListDto>>(response);
	}

	@Override
	public Result add(CreateCarSegmentRequest createCarSegmentRequest) {
		Result result = BusinessRules.run(CheckIfCarSegmentNameAlreadyExists(createCarSegmentRequest.getSegmentName()));

		if (result != null) {
			return result;
		}

		CarSegment segment = this.modelMapperService.forRequest().map(createCarSegmentRequest, CarSegment.class);
		this.carSegmentDao.save(segment);
		return new SuccessResult(Messages.carSegmentAdded);
	}

	@Override
	public Result update(UpdateCarSegmentRequest updateCarSegmentRequest) {
		if (this.carSegmentDao.existsById(updateCarSegmentRequest.getId())) {
			CarSegment segment = this.modelMapperService.forRequest().map(updateCarSegmentRequest, CarSegment.class);
			this.carSegmentDao.save(segment);
			return new SuccessResult(Messages.carSegmentUpdated);
		}
		return new ErrorResult(Messages.carSegmentIsNotFound);
	}

	private Result CheckIfCarSegmentNameAlreadyExists(String SegmentName) {
		if (carSegmentDao.findBySegmentName(SegmentName) == null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.carSegmentAlreadyExists);

	}

	@Override
	public Result delete(int id) {
		if (this.carSegmentDao.existsById(id)) {
			this.carSegmentDao.deleteById(id);
			return new SuccessResult(Messages.carSegmentDeleted);
		}
		return new ErrorResult(Messages.carSegmentIsNotFound);
	}

}
