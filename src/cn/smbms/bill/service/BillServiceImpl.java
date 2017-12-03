package cn.smbms.bill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smbms.bill.dao.BillMapper;
import cn.smbms.helper.PageHelper;
import cn.smbms.pojo.Bill;
@Service
public class BillServiceImpl implements BillService {
	@Autowired
	private BillMapper mapper;
	@Override
	public void queryPaged(PageHelper<Bill> helper) {
		//总行数
		helper.setTotalSize(mapper.queryPagedCount(helper.getParams()).intValue());
		//当前页的记录
		helper.setResults(mapper.queryPaged(helper.getParams()));
	}
	@Override
	public Bill getBillById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(Long.valueOf(id));
	}
	@Override
	public boolean Modify(Bill bill) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(bill)>0;
	}
	@Override
	public boolean add(Bill bill) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(bill)>0;
	}

}
