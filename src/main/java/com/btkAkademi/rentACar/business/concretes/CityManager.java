package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.CityService;
import com.btkAkademi.rentACar.business.dtos.BrandListDto;
import com.btkAkademi.rentACar.business.dtos.CityListDto;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.CityDao;
import com.btkAkademi.rentACar.entities.concretes.Brand;
import com.btkAkademi.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService{
	
	private CityDao cityDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		super();
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public DataResult<List<CityListDto>> getAll() {
		List<City> cityList = this.cityDao.findAll();
		List<CityListDto> response = cityList.stream()
				.map(city -> modelMapperService.forDto().map(city, CityListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CityListDto>>(response);
	}

}
