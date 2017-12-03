package cn.smbms.service.user;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.smbms.pojo.User;
import cn.smbms.user.service.UserService;
import cn.smbms.user.service.UserServiceImpl;

public class UserServiceTest {
	private UserService userService;
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService)context.getBean("UserService");
	}

	@Test
	public void testAdd() {
		User user = new User();
		user.setUserCode("111");
		user.setUserName("222");
		boolean result = userService.add(user);
		//result = false;
		//断言
		Assert.assertTrue("增加失败", result);
	}
	
	@Test 
	public void testGetUserList(){
		
		User user = userService.selectUserCodeExist("admin");
		System.out.println("user:"+user);
	}

}
