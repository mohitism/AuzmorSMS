package io.springboot.auzmor.message.redis.repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import io.springboot.auzmor.message.redis.model.PhoneNumberData;

@Repository
public class RedisRepositoryImpl implements RedisRepository{
	
	private static final String KEY = "PhoneNumber";
	
	private static final String KEY1 = "NumberOfHits";
    
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;
    
    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

	@Override
	public Map<Integer, PhoneNumberData> findAllPhoneNumber() {
		// TODO Auto-generated method stub
		return hashOperations.entries(KEY);
	}

	@Override
	public void add(PhoneNumberData phoneNumber) {
		// TODO Auto-generated method stub
		hashOperations.put(KEY, phoneNumber.getId(), phoneNumber);
		redisTemplate.expire(KEY, 4, TimeUnit.MINUTES);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		hashOperations.delete(KEY, id);
	}
	
	@Override
	public void addRequestHit(String phonenumber) {
		// TODO Auto-generated method stub
		if(hashOperations.get(KEY1, phonenumber) == null) {
			hashOperations.put(KEY1, phonenumber, 1);
		}else {
			hashOperations.put(KEY1, phonenumber, (Integer)hashOperations.get(KEY1, phonenumber)+1);
		}
		redisTemplate.expire(KEY1, 24, TimeUnit.HOURS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getNumberOfHits(String phonenumber) {
		// TODO Auto-generated method stub
		return (Integer) hashOperations.get(KEY1, phonenumber);
	}
	

}
