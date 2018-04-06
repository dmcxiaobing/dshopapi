package com.david.dshopapi.constants;

/**
 * 一些常量的使用.按需求修改此配置即可
 * 
 * @Author ：David
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 */
public class ProjectConstant {
	public static final String BASE_PACKAGE = "com.david.dshopapi";// 项目基础包名称，根据自己公司的项目修改

	public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";// Model所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";// Mapper所在包
	public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";// Service所在包
	public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";// ServiceImpl所在包
	public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web.controller";// Controller所在包

	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";// Mapper插件基础接口的完全限定名

	// JDBC配置，请修改为你项目的实际配置
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/edspringboot";
	public static final String JDBC_USERNAME = "root";
	public static final String JDBC_PASSWORD = "1234";
	public static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	/*密码加密的盐*/
	public static final String MD5_SALT = "DAVID";
	//以这个字符串，随机取出四个做验证码，相似的去掉了
	public static final  String RANDOM_CODE = "3456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	// 注册时候的验证码 code。session的key
	public static final  String USER_SESSION_CHECK_CODE = "USER_SESSION_CHECK_CODE";

}
