package com.lljz.yrzx.test.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lljz.yrzx.common.front.entity.User;
import com.lljz.yrzx.common.front.service.UserService;
import com.lljz.yrzx.test.base.BaseTest;

public class UserServiceTest extends BaseTest{
	
	@Autowired
	private UserService userService;
	
	@Rollback(value=false)
	@Test
	public void testAdd(){
		User u = new User();
		u.setName("test");
		Date now = new Date();
		u.setCreateTime(now);
		u.setUpdateTime(now);
		userService.add(u);
	}

}
