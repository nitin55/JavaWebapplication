/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import com.cruddemo.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author nitin
 */
public class GenerateLinkUtils {

    private static final String CHECK_CODE = "checkCode";

//    public static String generateResetPwdLink(User user) {
//       String link = "http://localhost:8080/crud/resetpassword?username="
//                + user.getUsername() + "&" + CHECK_CODE + "=" + generateCheckcode(user);
//       
//       String link2 = "<a href='"+link+"'>"+link+"</a>";
//       return link2;
//    }
    
    public static String generateResetPwdLink(String token) {
       String link = "http://localhost:8080/crud/resetpassword?token="
                + token;
       
       String link2 = "<a href='"+link+"'>"+link+"</a>";
       return link2;
    }

    public static String generateCheckcode(User user) {
        String userName = user.getUsername();
        String randomCode = getSaltString();
        return md5(userName + ":" + randomCode);
    }

    private static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private static String md5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
            md.update(string.getBytes());
            byte[] md5Bytes = md.digest();
            return bytes2Hex(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String bytes2Hex(byte[] byteArray) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] >= 0 && byteArray[i] < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }
}
