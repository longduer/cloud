package yves.leung.com.auth.manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yves.leung.com.auth.mapper.MenuMapper;
import yves.leung.com.auth.mapper.UserMapper;
import yves.leung.com.common.entity.system.Menu;
import yves.leung.com.common.entity.system.SystemUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    public SystemUser findByName(String username) {
        return userMapper.findByName(username);
    }

     public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));  // 该方法返回的是用户权限集合以逗号拼接后的值，如user:add,user:update,user:delete
    }
}