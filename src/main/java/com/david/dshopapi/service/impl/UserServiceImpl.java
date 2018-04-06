package com.david.dshopapi.service.impl;

import com.david.dshopapi.constants.ProjectConstant;
import com.david.dshopapi.dao.UserMapper;
import com.david.dshopapi.model.User;
import com.david.dshopapi.service.UserService;
import com.david.dshopapi.core.AbstractService;
import com.david.dshopapi.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by David on 2017/12/20.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名和密码查找用户
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Override
    public User findUserByUsernamePassword(String username, String password) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            User user = new User();
            user.setUsername(username);
            // 密码进行加密转换
            password = MD5Util.encryptPassword(username,password, ProjectConstant.MD5_SALT);
            user.setPassword(password);

            User dbuser = userMapper.findUserByUsernamePassword(user);
            if (dbuser != null) {
                // 查询结果得到之后，将密码去掉，
                dbuser.setPassword(null);
                return dbuser;

            }
        }

        return null;
    }

    /**
     * 根据用户名查找用户
     * @param username
     */
    @Override
    public User findUserByUsername(String username) {

        return userMapper.findUserByUsername(username);
    }
}
