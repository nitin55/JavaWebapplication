/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import java.security.SecureRandom;

/**
 *
 * @author nitin
 */
public class PasswordHasherUtil {
    private static final int LOG_ROUNDS = 5;


    public String hashPassword(String password) {
        verifyPasswordInput(password);

        String salt = BCrypt.gensalt(LOG_ROUNDS, new SecureRandom());
        return BCrypt.hashpw(password, salt);
    }

    
    public boolean comparePassword(String password, String hash) {
        verifyPasswordInput(password);
        if (hash == null) throw new IllegalArgumentException("Hash cannot be null");
        return BCrypt.checkpw(password, hash);
    }

    private void verifyPasswordInput(String password) {
        if (password == null)
            throw new IllegalArgumentException("Password cannot be null");
        if (password.length() < 1)
            throw new IllegalArgumentException("Password cannot be less than one character");
}
}
