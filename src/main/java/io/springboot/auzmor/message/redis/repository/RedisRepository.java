package io.springboot.auzmor.message.redis.repository;

import java.util.Map;

import io.springboot.auzmor.message.redis.model.PhoneNumberData;

public interface RedisRepository {
	
    Map<Integer, PhoneNumberData> findAllPhoneNumber();

    /**
     * Add key-value pair to Redis.
     */
    void add(PhoneNumberData number);

    /**
     * Delete a key-value pair in Redis.
     */
    void delete(Integer id);
    
    
    void addRequestHit(String PhoneNumber);

	Integer getNumberOfHits(String phonenumber);
    
}
