package com.mgs.util;

import java.util.Random;
import java.util.UUID;

/**
 * @description 随机数工具类
 * @author ws
 */
public class NumberUtil {


    public static final String DEFAULT_PWD = "666666";


    private static final char[] letters
            = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
                        'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i',
                        'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','r',
                        '0','1','2','3','4','5','6','7','8','9'};

    /**
     * 生成随机数字
     * @param digits 位数
     */
    public static String generateNumber(int digits){
        String code = "";
        Random rand = new Random();//生成随机数
        for(int a = 0; a < digits; a++){
            code += rand.nextInt(10);//0-10
        }
        return code;
    }


    /**
     * 生成随机数字和字母的组合
     * @param digits 位数
     */
    public static String generateNumberAndLetters(int digits){
        String code = "";
        Random rand = new Random();//生成随机数
        int index;
        for(int a = 0; a < digits; a++){
            index = rand.nextInt(letters.length);
            code += letters[index];
        }
        return code;
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


}
