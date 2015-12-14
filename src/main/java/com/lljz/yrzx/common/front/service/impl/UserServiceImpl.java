package com.lljz.yrzx.common.front.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lljz.yrzx.base.service.AbstractService;
import com.lljz.yrzx.common.front.entity.User;
import com.lljz.yrzx.common.front.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends AbstractService implements UserService {

	@Override
	@Transactional(readOnly = false)
	public void add(User user) {
		baseHibernateDao.add(user);
	}

}
