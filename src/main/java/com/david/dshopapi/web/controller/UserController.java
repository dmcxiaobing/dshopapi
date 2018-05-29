package com.david.dshopapi.web.controller;


import com.david.dshopapi.constants.MessageConstant;
import com.david.dshopapi.constants.ProjectConstant;
import com.david.dshopapi.core.Result;
import com.david.dshopapi.core.ResultGenerator;
import com.david.dshopapi.model.User;
import com.david.dshopapi.service.UserService;
import com.david.dshopapi.utils.CodeUtil;
import com.david.dshopapi.utils.LogUtil;
import com.david.dshopapi.utils.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 *SELECT * from `user` WHERE username = 'mazi' and password = 'mazi'
 * 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    /**
     * 登录。。
     */
    @PostMapping("/login/{username}/{password}")
    public Result login(@PathVariable("username") String username, @PathVariable("password") String password) {

        User user = userService.findUserByUsernamePassword(username, password);
        if (user == null) {
            // 如果user为空，则说明用户名或者密码不正确
            return ResultGenerator.genFailResult(MessageConstant.LOGIN_MESSAGE);
        } else {
            // 登录成功。
            return ResultGenerator.genSuccessResult(user);
        }

    }

    /**
     * 注册，发送验证码，防止一直调用注册接口。。
     */
    @PostMapping("/regist/sendCode")
    public Result sendCode(HttpSession session) {

        String code = CodeUtil.getFourCode();
        // 然后将code存入到session中，并且发送到前段。
        if (StringUtils.isNotBlank(code)) {
            session.setAttribute(ProjectConstant.USER_SESSION_CHECK_CODE,code);
            return ResultGenerator.genSuccessResult(code);
        }
        return  ResultGenerator.genFailResult(MessageConstant.CHECK_CODE_MESSAGE);

    }


    /**
     * 注册,将用户名和密码保存到数据库。
     */
    @PostMapping("/regist/registSave")
    public Result registSave(HttpSession session,@RequestParam(value = "code",required = false) String code,@RequestParam(value = "username",required = false) String username,@RequestParam(value = "password",required = false) String password) {

        if (StringUtils.isEmpty(code)){
            // 则验证码填入为空
            return ResultGenerator.genFailResult(MessageConstant.CHECK_CODE_ERROR_MESSAGE);
        }

        // 首先查看 session的验证码和传来的是否一致。
        String sessionCode = (String) session.getAttribute(ProjectConstant.USER_SESSION_CHECK_CODE);
        LogUtil.E(sessionCode);
        if (!code.equalsIgnoreCase(sessionCode)){
            // 则验证码填入的不正确
            return ResultGenerator.genFailResult(MessageConstant.CHECK_CODE_ERROR_MESSAGE);
        }
        // 如果正确，查看用户名和密码不能为空，然后查看数据库中是否存在一样的用户名，
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return ResultGenerator.genFailResult(MessageConstant.NO_USERNAME_PASSWORD);
        }
        // 根据用户名查找用户。
        User dbUser = userService.findUserByUsername(username);
        if (dbUser!=null){
            // 如果查询到了，则说明用户已经存在了。
            return ResultGenerator.genFailResult(MessageConstant.USERNAME_IS_USED);
        }

        // 将用户名和密码保存到数据库
        User user = new User();
        user.setUsername(username);
        // 密码进行加密
        user.setPassword(MD5Util.encryptPassword(username,password,ProjectConstant.MD5_SALT));
        userService.save(user);
        // 这里为了防止重复调用，也可以注册之后，将session中的code清楚，这样必须要先调用验证码。
        return ResultGenerator.genSuccessResult();

    }

    /**
     * {
     * "code": 500,
     * "message": "接口 [/user/add] 内部错误，请联系管理员"
     * }
     * <p>
     * {
     * "code": 200,
     * "data": null,
     * "message": "SUCCESS"
     * }
     */
    @PostMapping("/add")
    public Result add(User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * {
     * "code": 200,
     * "data": {
     * "age": 30,
     * "uid": 1,
     * "username": "david"
     * },
     * "message": "SUCCESS"
     * }
     * <p>
     * {
     * "code": 500,
     * "message": "接口 [/user/detail] 内部错误，请联系管理员"
     * }
     */
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        // 故意搞一个异常
        //   int num = 100/0;
        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * http://localhost:8080/user/list?page=1&size=1
     *
     * @param page 当前页 从第1页开始
     * @param size 每页显示数量 默认 查所有
     *             {
     *             "code": 200,
     *             "data": {
     *             "endRow": 12,
     *             "firstPage": 0,
     *             "hasNextPage": false,
     *             "hasPreviousPage": false,
     *             "isFirstPage": false,
     *             "isLastPage": true,
     *             "lastPage": 0,
     *             "list": [
     *             {
     *             "age": 30,
     *             "uid": 1,
     *             "username": "david"
     *             },
     *             {
     *             "age": 22,
     *             "uid": 67,
     *             "username": "xiaobing"
     *             }
     *             ],
     *             "navigateFirstPage": 0,
     *             "navigateLastPage": 0,
     *             "navigatePages": 8,
     *             "navigatepageNums": [],
     *             "nextPage": 0,
     *             "orderBy": "",
     *             "pageNum": 0,
     *             "pageSize": 0,
     *             "pages": 0,
     *             "prePage": 0,
     *             "size": 12,
     *             "startRow": 1,
     *             "total": 12
     *             },
     *             "message": "SUCCESS"
     *             }
     */
    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        /**
         *     //当前页
         private int pageNum;
         //每页的数量
         private int pageSize;
         //当前页的数量
         private int size;
         //排序
         private String orderBy;

         //由于startRow和endRow不常用，这里说个具体的用法
         //可以在页面中"显示startRow到endRow 共size条数据"

         //当前页面第一个元素在数据库中的行号
         private int startRow;
         //当前页面最后一个元素在数据库中的行号
         private int endRow;
         //总记录数
         private long total;
         //总页数
         private int pages;
         //结果集
         private List<T> list;

         //前一页
         private int prePage;
         //下一页
         private int nextPage;

         //是否为第一页
         private boolean isFirstPage = false;
         //是否为最后一页
         private boolean isLastPage = false;
         //是否有前一页
         private boolean hasPreviousPage = false;
         //是否有下一页
         private boolean hasNextPage = false;
         //导航页码数
         private int navigatePages;
         //所有导航页号
         private int[] navigatepageNums;
         //导航条上的第一页
         private int navigateFirstPage;
         //导航条上的最后一页
         private int navigateLastPage;
         */
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
