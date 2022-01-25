package com.btkAkademi.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btkAkademi.rentACar.business.abstracts.AdditionalRentalItemService;
import com.btkAkademi.rentACar.business.constants.Messages;
import com.btkAkademi.rentACar.business.dtos.AdditionalRentalItemListDto;
import com.btkAkademi.rentACar.business.dtos.AdditionalServiceListDto;
import com.btkAkademi.rentACar.business.dtos.RentalListDto;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.CreateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.business.requests.AdditionalItemRequest.UpdateAdditionalRentalItemRequest;
import com.btkAkademi.rentACar.core.utilities.mapping.ModelMapperService;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.ErrorResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.core.utilities.results.SuccessDataResult;
import com.btkAkademi.rentACar.core.utilities.results.SuccessResult;
import com.btkAkademi.rentACar.dataAccess.abstracts.AdditionalRentalItemDao;
import com.btkAkademi.rentACar.entities.concretes.AdditionalRentalItem;
import com.btkAkademi.rentACar.entities.concretes.AdditionalService;
import com.btkAkademi.rentACar.entities.concretes.Brand;

@Service
public class AdditionalRentalItemManager implements AdditionalRentalItemService {

	private AdditionalRentalItemDao additionalRentalItemDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalRentalItemManager(AdditionalRentalItemDao additionalRentalItemDao,
			ModelMapperService modelMapperService) {
		super();
		this.additionalRentalItemDao = additionalRentalItemDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalRentalItemListDto>> getAll() {
		List<AdditionalRentalItem> additionalRentalItemList = this.additionalRentalItemDao.findAll();
		List<AdditionalRentalItemListDto> additionalRentalItemSearchListDtos = additionalRentalItemList.stream()
				.map(additional -> modelMapperService.forDto().map(additional, AdditionalRentalItemListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<>(additionalRentalItemSearchListDtos);
	}

	@Override
	public Result add(CreateAdditionalRentalItemRequest createAdditionaRentalItemRequest) {

		for (AdditionalServiceListDto additionalService : createAdditionaRentalItemRequest
				.getAdditionalServiceListDto()) {
			AdditionalService response = modelMapperService.forRequest().map(additionalService,
					AdditionalService.class);

			AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
					.map(createAdditionaRentalItemRequest, AdditionalRentalItem.class);
			additionalRentalItem.setAdditionalService(response);
			this.additionalRentalItemDao.save(additionalRentalItem);

		}



		return new SuccessResult(Messages.additionalRentalItemAdded);
	}

	@Override
	public Result delete(int id) {
		if (this.additionalRentalItemDao.existsById(id)) {
			this.additionalRentalItemDao.deleteById(id);
			return new SuccessResult(Messages.additionalRentalItemDeleted);
		}
		return new ErrorResult(Messages.additionalRentalItemIsNotFound);
	}

	@Override
	public Result update(UpdateAdditionalRentalItemRequest updateAdditionaItemRequest) {
		if (this.additionalRentalItemDao.existsById(updateAdditionaItemRequest.getId())) {
			AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest().map(updateAdditionaItemRequest,
					AdditionalRentalItem.class);
			this.additionalRentalItemDao.save(additionalRentalItem);
			return new SuccessResult(Messages.additionalRentalItemUpdated);
		}
		return new ErrorResult(Messages.additionalRentalItemIsNotFound);
	}

	@Override
	public DataResult<List<AdditionalRentalItemListDto>> findByRentalId(int rentalId) {
		List<AdditionalRentalItem> request = this.additionalRentalItemDao.findByRentalId(rentalId);
		List<AdditionalRentalItemListDto> response = request.stream().map(additionalRentalItem -> modelMapperService
				.forDto().map(additionalRentalItem, AdditionalRentalItemListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalRentalItemListDto>>(response);
	}

}
