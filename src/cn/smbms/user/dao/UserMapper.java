package cn.smbms.user.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User login(@Param("userCode")String userCode, @Param("userPassword")String userPassword);
    
    List<User> getUserList(@Param("userName")String queryUserName,@Param("userRole")Integer queryUserRole);
    //分页--获取总行数
    Long queryPagedCount(HashMap params);
    //分页--获取数据
    List<User> queryPaged(HashMap params);
    
	public User selectUserCodeExist(String userCode);
}