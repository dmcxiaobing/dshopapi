package com.david.dshopapi.service.impl;

import com.david.dshopapi.dao.UserMapper;
import com.david.dshopapi.model.User;
import com.david.dshopapi.service.UserService;
import com.david.dshopapi.core.AbstractService;
import com.david.dshopapi.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User findUserByUsernamePassword(String username, String password) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            User dbuser = userMapper.findUserByUsernamePassword(user);
            LogUtils.e(dbuser+"");
            if (dbuser != null) {
                return dbuser;

            }
        }

        return null;
    }
}
