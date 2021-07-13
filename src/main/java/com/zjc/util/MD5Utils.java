package com.zjc.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : zoujianchao
 * @version : 1.0
 * @date : 2021/7/13
 * @description : md5加密
 */
public class MD5Utils {
    public static String md5(String str) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static void main(String[] args) {
        System.out.println(md5("zoujianchao"));
        System.out.println(md5("zoujianchao").length());
    }
}
