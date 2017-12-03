package cn.smbms.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smbms.pojo.Role;
import cn.smbms.role.dao.RoleMapper;
@Service("RoleService")
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleMapper roleMapper;
	public RoleMapper getRoleMapper() {
		return roleMapper;
	}
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}
	@Override
	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		System.out.println(roleMapper);
		return roleMapper.getAllRole();
	}

}
