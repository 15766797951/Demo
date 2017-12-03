package cn.smbms.bill.service;

import java.util.List;

import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Bill;


public interface BillService {
	public void queryPaged(PageHelper<Bill> helper);
	public Bill getBillById(String id);
	public boolean Modify(Bill provider);
	public boolean add(Bill provider);
}
