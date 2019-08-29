package com.mgs.dlt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *   2018/4/11.
 */
public class SignatureUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureUtil.class);

    /**
     * 生成代理通接口秘钥
     * @param supplierId
     * @param key
     * @return
     */
    public static String buildSignature(Integer supplierId, String key, String timestamp) {
        String signature;
        try {
            LOG.debug("timestamp = " + timestamp + ", supplierId = " + supplierId + ", key = " + key);
            signature = encoderByMd5(supplierId + timestamp + key).toUpperCase();
            LOG.debug("signature = " + signature);
        } catch (Exception e) {
            LOG.error("生成接口秘钥失败", e);
            return null;
        }
        return signature;
    }


    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOG.error("MD5加密失败", e);
            throw e;
        }
    }
}
