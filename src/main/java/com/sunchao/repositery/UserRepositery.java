package com.sunchao.repositery;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sunchao.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface UserRepositery<T> extends JpaRepository<User, Integer> {
	

	@Cacheable(value = "users", key="#p0")
	public User findByloginidAndPassword(@Param("loginid")String loginid,String password);
	
	@Cacheable(value ="users", key="#p0")
	public User findByloginid(@Param("loginid") String loginid);
	
	public User saveAndFlush(User user);
	
	public User save(User user);
}

