package cn.smbms.provider.service;

import java.util.List;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;

public interface ProviderService {
	public void queryPaged(PageHelper<Provider> helper);
	public Provider getProById(String id);
	public boolean Modify(Provider provider);
	public boolean add(Provider provider);
	List<Provider> getAllPro();
}
