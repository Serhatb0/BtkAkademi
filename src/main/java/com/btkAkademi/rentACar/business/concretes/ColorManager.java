package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.btkAkademi.rentACar.business.abstracts.ColorService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.ColorListDto;
import com.btkAkademi.rentACar.business.requests.colorRequest.CreateColorRequest;
import com.btkAkademi.rentACar.business.requests.colorRequest.UpdateColorRequest;
import com.btkAkademi.rentACar.core.utilities.business.BusinessRules;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorDataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.ColorDao;
import com.btkAkademi.rentACar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		super();
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		List<Color> colorList = this.colorDao.findAll();
		List<ColorListDto> response = colorList.stream()
				.map(color -> modelMapperService.forDto().map(color, ColorListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ColorListDto>>(response);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		Result result = BusinessRules.run(checkIfColorNameExists(createColorRequest.getName()));

		if (result != null) {
			return result;
		}
		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);
		return new SuccessResult(Messages.colorAdded);
	}

	private Result checkIfColorNameExists(String colorName) {
		Color color = this.colorDao.findByName(colorName);
		if (color != null) {
			return new ErrorResult(Messages.colorNameExists);
		}
		return new SuccessResult();
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		
		Color color = this.colorDao.findById(updateColorRequest.getId());
		if(color == null) {
			return new ErrorResult(Messages.colorIsNotFound);
		}
		color = modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
		return new SuccessResult(Messages.colorUpdated);
	}

	@Override
	public DataResult<Color> findById(int id) {
		if(this.colorDao.existsById(id)) {
			return new SuccessDataResult<Color>(this.colorDao.findById(id));
		}
		return new ErrorDataResult<>(Messages.colorIsNotFound);
	}
}
