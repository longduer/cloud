package yves.leung.com.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yves.leung.com.common.entity.system.UserRole;

public interface IUserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String[] roleIds);

	void deleteUserRolesByUserId(String[] userIds);
}