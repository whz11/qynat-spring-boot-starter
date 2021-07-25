package com.qingyou.qynat.commom.util;


import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;

/**
 * @author ilak
 */
public class EncryptUtil {

    private static final byte[] key = "6f00cd9cade84e52".getBytes(StandardCharsets.UTF_8);
    private static final byte[] iv = "25d82196341548ef".getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String data) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
            instance.init(Cipher.ENCRYPT_MODE, sKeySpec, ivParameterSpec);
            byte[] crypt = instance.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.encode(Unpooled.wrappedBuffer(crypt)).toString(StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String data) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec sKeySpec = new SecretKeySpec(key, "AES");
            instance.init(Cipher.DECRYPT_MODE, sKeySpec, ivParameterSpec);
            byte[] plain = instance.doFinal(ByteBufUtil.getBytes(Base64.decode(Unpooled.wrappedBuffer(data.getBytes(StandardCharsets.UTF_8)))));
            if (plain.length == 0) {
                return null;
            }
            return new String(plain, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
            throws Exception {
        Key key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }

    /**
     * 生成iv
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {

        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
        params.init(new IvParameterSpec(iv));
        return params;
    }

    public static String toHex(byte[] arr) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte b : arr) {
            digital = b;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toLowerCase();
    }

    public static String calcMd5(String src) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = src.getBytes();
            byte[] buff = md.digest(input);
            md5str = toHex(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

}
