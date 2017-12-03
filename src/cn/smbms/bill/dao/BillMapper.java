package cn.smbms.bill.dao;

import java.util.HashMap;
import java.util.List;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;

public interface BillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    int insertSelective(Bill record);

    Bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);
    
    //分页--获取总行数
    Long queryPagedCount(HashMap params);
    //分页--获取数据
    List<Bill> queryPaged(HashMap params);
}