package com.david.dshopapi.dao;

import com.david.dshopapi.core.Mapper;
import com.david.dshopapi.model.User;
import org.apache.ibatis.annotations.Select;

/**
 * mapper查询接口
 */
public interface UserMapper extends Mapper<User> {
    //@Select(" SELECT * FROM tb_user WHERE username = #{username} and password = #{password}")
    User findUserByUsernamePassword(User user);

    User findUserByUsername(String username);
}