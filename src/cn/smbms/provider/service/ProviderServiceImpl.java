package cn.smbms.provider.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.provider.dao.ProviderMapper;
@Service
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	private ProviderMapper mapper;
	@Override
	public void queryPaged(PageHelper<Provider> helper) {
		//总行数
		helper.setTotalSize(mapper.queryPagedCount(helper.getParams()).intValue());
		//当前页的记录
		helper.setResults(mapper.queryPaged(helper.getParams()));
	}
	@Override
	public Provider getProById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(Long.valueOf(id));
	}
	@Override
	public boolean Modify(Provider provider) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(provider)>0;
	}
	@Override
	public boolean add(Provider provider) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(provider)>0;
	}
	@Override
	public List<Provider> getAllPro() {
		// TODO Auto-generated method stub
		return mapper.getAllPro();
	}

}
