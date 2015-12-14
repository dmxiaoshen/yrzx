package com.lljz.yrzx.common.front.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lljz.yrzx.base.web.BaseController;
import com.lljz.yrzx.common.front.entity.User;
import com.lljz.yrzx.common.front.service.UserService;

@Controller
@RequestMapping(value="/")
public class HomeController extends BaseController{
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/home")
	public String index(ModelMap model){
//		logger.debug("debug");
//		logger.info("info");
//		logger.warn("warn");
//		logger.error("error");
//		User user = new User();
//		user.setName("kq");
//		Date now = new Date();
//		user.setCreateTime(now);
//		user.setUpdateTime(now);
//		userService.add(user);
		return "/index";
	}
}
