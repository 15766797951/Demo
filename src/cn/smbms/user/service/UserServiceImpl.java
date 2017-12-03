package cn.smbms.user.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.User;
import cn.smbms.service.user.UserServiceTest;
import cn.smbms.user.dao.UserMapper;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Service("UserService")
public class UserServiceImpl implements UserService{
	private Logger logger = Logger.getLogger(UserServiceTest.class);
	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public boolean add(User user) {
		// TODO Auto-generated method stub
		if(userMapper.insert(user)>0)
			return true;
		return false;
	}

	@Override
	public User login(String userCode, String userPassword) {
		// TODO Auto-generated method stub
		return userMapper.login(userCode, userPassword);
	}

	@Override
	public List<User> getUserList(String queryUserName, Integer queryUserRole,
			int currentPageNo, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getUserList(queryUserName, queryUserRole);
	}

	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User selectUserCodeExist(String userCode) {
		// TODO Auto-generated method stub
		return userMapper.selectUserCodeExist(userCode);
	}

	@Override
	public boolean deleteUserById(Integer delId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(Long.valueOf(id));
	}

	@Override
	public boolean modify(User user) {
		// TODO Auto-generated method stub
		int count = userMapper.updateByPrimaryKeySelective(user);
		if(count>0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePwd(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(user)>0;
	}

	@Override
	public void queryPaged(PageHelper<User> helper) {
		// TODO Auto-generated method stub
		//总行数
		helper.setTotalSize(userMapper.queryPagedCount(helper.getParams()).intValue());
		//当前页的记录
		helper.setResults(userMapper.queryPaged(helper.getParams()));
	}
}
