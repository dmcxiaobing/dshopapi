package com.david.dshopapi.model;

import java.io.Serializable;

/**
 * 统一返回的json数据封装
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 */
public class AppResult implements Serializable{


    /**
     * code : 200
     * data : {"uid":1,"password":"","age":30,"username":"david"}
     * message : SUCCESS
     */
    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public class DataEntity {
        /**
         * uid : 1
         * password :
         * age : 30
         * username : david
         */
        private int uid;
        private String password;
        private int age;
        private String username;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUid() {
            return uid;
        }

        public String getPassword() {
            return password;
        }

        public int getAge() {
            return age;
        }

        public String getUsername() {
            return username;
        }
    }
}
