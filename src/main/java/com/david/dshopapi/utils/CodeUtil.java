package com.david.dshopapi.utils;

import com.david.dshopapi.constants.ProjectConstant;

import java.util.Random;

/**
 * @author ：David
 * @weibo ：http://weibo.com/mcxiaobing
 * @github: https://github.com/QQ986945193
 * 生成一个随机字符串。
 */
public class CodeUtil {

    /**
     * 生成一个随机字符串。四位。
     */
    public static String getFourCode() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = ProjectConstant.RANDOM_CODE.charAt(new Random().nextInt(ProjectConstant.RANDOM_CODE.length()));
            sb.append(ch);
        }
        System.out.println(sb.toString());
        return  sb.toString();
    }

    public static void main(String args[]){
        getFourCode();
    }
}
