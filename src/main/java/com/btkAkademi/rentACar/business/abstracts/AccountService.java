package com.btkAkademi.rentACar.business.abstracts;

import java.util.List;
import com.btkAkademi.rentACar.business.dtos.AccountListDto;
import com.btkAkademi.rentACar.business.requests.accountRequest.CreateAccountRequest;
import com.btkAkademi.rentACar.business.requests.accountRequest.UpdateAccountRequest;
import com.btkAkademi.rentACar.core.utilities.results.DataResult;
import com.btkAkademi.rentACar.core.utilities.results.Result;
import com.btkAkademi.rentACar.entities.concretes.Account;

public interface AccountService {

	DataResult<List<AccountListDto>> getAll();

	Result update(UpdateAccountRequest updateAccountRequest);

	Result deleteById(int id);

	Result add(CreateAccountRequest createAccountRequest);

	DataResult<Account> findById(int id);

}
