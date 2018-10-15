package io.springboot.auzmor.message.phonenumber.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.springboot.auzmor.message.account.entity.AccountEntity;
import io.springboot.auzmor.message.phonenumber.entity.PhoneNumberEntity;


@Repository
interface PhoneNumberRepository extends CrudRepository<PhoneNumberEntity,Integer>{
	
	List<PhoneNumberEntity> findByAccountId(Integer accountId);
	
	PhoneNumberEntity findByPhoneNumberAndAccountId(String phoneNumber,Integer accountId);

}
