package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.BrandService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.requests.brandRequests.CreateBrandRequest;
import com.btkAkademi.rentACar.business.requests.brandRequests.UpdateBrandRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.BrandDao;
import com.btkAkademi.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> brandList = this.brandDao.findAll();
		List<BrandListDto> response = brandList.stream()
				.map(brand -> modelMapperService.forDto().map(brand, BrandListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<BrandListDto>>(response);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		Result result = BusinessRules.run(checkIfBrandNameExists(createBrandRequest.getName()), checkIfBrandLimitExceeded(10));
		
		if(result != null) {
			return result;
		}
		

		Brand brand = modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);
		return new SuccessResult(Messages.brandAdded);

	}

	private Result checkIfBrandNameExists(String brandName) {
		Brand brand = this.brandDao.findByName(brandName);
		if (brand != null) {
			return new ErrorResult(Messages.brandNameExists);
		}
		return new SuccessResult();
	}

	private Result checkIfBrandLimitExceeded(int limit) {
		if(this.brandDao.count()>= limit) {
			return new ErrorResult(Messages.brandLimitExceeded);
		}
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
	

		Brand brand = this.brandDao.findById(updateBrandRequest.getId());
		
		if(brand == null) {
			return new ErrorResult(Messages.brandIsNotFound);
		}
		brand = modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		return new SuccessResult(Messages.brandUpdated);
	}

	@Override
	public Result deleteById(int id) {
		if(this.brandDao.existsById(id)) {
			this.brandDao.deleteById(id);
			return new SuccessResult(Messages.brandDeleted);
		}
		return new ErrorResult(Messages.brandIsNotFound);
	}

	@Override
	public DataResult<Brand> findById(int id) {
		if(this.brandDao.existsById(id)) {
			return new SuccessDataResult<Brand>(this.brandDao.findById(id));
		}
		return new ErrorDataResult<>(Messages.brandIsNotFound);
	}
	

	

}
