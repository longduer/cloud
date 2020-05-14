package yves.leung.com.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import yves.leung.com.common.entity.system.SystemUser;

public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}