package com.david.dshopapi.service;


import com.david.dshopapi.core.Service;
import com.david.dshopapi.model.User;

/**
 * Created by David on 2017/12/20.
 */
public interface UserService extends Service<User> {
    /**
     * 根据用户名和密码查找用户
     */
    User findUserByUsernamePassword(String username,String password);

    User findUserByUsername(String username);
}
