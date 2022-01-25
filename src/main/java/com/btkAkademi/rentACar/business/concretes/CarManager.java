package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.BrandService;
import com.btkAkademi.rentACar.business.abstracts.CarService;
import com.btkAkademi.rentACar.business.abstracts.ColorService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.CarListDto;

import com.btkAkademi.rentACar.business.requests.carRequest.CreateCarRequest;
import com.btkAkademi.rentACar.business.requests.carRequest.UpdateCarRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CarDao;

import com.btkAkademi.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {
	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private ColorService colorService;
	private BrandService brandService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, ColorService colorService,
			BrandService brandService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.colorService = colorService;
		this.brandService = brandService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		List<Car> carList = this.carDao.findAll();
		List<CarListDto> response = carList.stream().map(car -> modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		Result result = BusinessRules.run(checkIfBrandExists(createCarRequest.getBrandId()),
				checkIfColorExist(createCarRequest.getColorId()));

		if (result != null) {
			return result;
		}

		Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.carAdded);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Result result = BusinessRules.run(checkIfBrandExists(updateCarRequest.getBrandId()),
				checkIfColorExist(updateCarRequest.getColorId()));

		if (result != null) {
			return result;
		}

		Car car = this.carDao.findById(updateCarRequest.getId());
		if (car == null) {
			return new ErrorResult(Messages.carIsNotFound);
		}

		car = modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult(Messages.carUpdated);

	}

	@Override
	public DataResult<List<CarListDto>> findSuitableCar(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		Page<Car> rentalList = this.carDao.findByRentals_IdIsNullOrRentals_returnDateIsNotNull(pageable);
		List<CarListDto> response = rentalList.stream()
				.map(car -> modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<Car> findByRentals_Id(int id) {
		return new SuccessDataResult<Car>(this.carDao.findByRentals_Id(id));
	}

	@Override
	public DataResult<Car> findById(int id) {
		return new SuccessDataResult<Car>(this.carDao.findById(id));
	}

	@Override
	public DataResult<List<Integer>> findAvailableCarBySegment(int segmentId) {
		if(this.carDao.findByCarSegmentId(segmentId) != null) {
			if (carDao.findAvailableCarBySegment(segmentId).size() < 1) {
				return new ErrorDataResult<List<Integer>>();
			} else
				return new SuccessDataResult<List<Integer>>(this.carDao.findAvailableCarBySegment(segmentId));

		}
		return new ErrorDataResult<>(Messages.carIsNotSegmentExist);


	}

	private Result checkIfColorExist(int colorId) {
		if (colorService.findById(colorId).isSuccess()) {
			return new SuccessResult();
		} else
			return new ErrorResult(Messages.colorIsNotFound);

	}

	private Result checkIfBrandExists(int brandId) {
		if (brandService.findById(brandId).isSuccess()) {
			return new SuccessResult();
		} else
			return new ErrorResult(Messages.brandIsNotFound);

	}

	@Override
	public DataResult<CarListDto> findByCarListDtoId(int id) {
		if (carDao.existsById(id)) {

			CarListDto response = modelMapperService.forDto().map(carDao.findById(id), CarListDto.class);

			return new SuccessDataResult<CarListDto>(response);
		} else
			return new ErrorDataResult<>(Messages.carIsNotFound);
	}

	@Override
	public DataResult<List<CarListDto>> findByCarSegmentName(String segmentName) {
		List<Car> carList = this.carDao.findByCarSegment_SegmentName(segmentName);
		List<CarListDto> response = carList.stream().map(car -> modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}


}
