package cn.smbms.provider.dao;

import java.util.HashMap;
import java.util.List;

import cn.smbms.pojo.Provider;

public interface ProviderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Provider record);

    int insertSelective(Provider record);

    Provider selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Provider record);

    int updateByPrimaryKey(Provider record);
    //分页--获取总行数
    Long queryPagedCount(HashMap params);
    //分页--获取数据
    List<Provider> queryPaged(HashMap params);
    List<Provider> getAllPro();
    

}