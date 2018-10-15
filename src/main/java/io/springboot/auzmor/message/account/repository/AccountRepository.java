package io.springboot.auzmor.message.account.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.springboot.auzmor.message.account.entity.AccountEntity;


@Repository
interface AccountRepository extends CrudRepository<AccountEntity,Integer>{

	AccountEntity findByUsernameAndPassword(String username,String password);

}
