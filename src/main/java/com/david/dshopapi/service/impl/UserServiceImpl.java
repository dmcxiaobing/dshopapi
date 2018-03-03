package com.david.dshopapi.service.impl;

import com.david.dshopapi.dao.UserMapper;
import com.david.dshopapi.model.User;
import com.david.dshopapi.service.UserService;
import com.david.dshopapi.core.AbstractService;
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

}
