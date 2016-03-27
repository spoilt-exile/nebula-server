/*
 * This file is part of Nebula Server application.
 * 
 * Copyright (C) 2015 Freax Software
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package tk.freaxsoftware.nebula.server.standard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SHA password hashing utility class.
 *
 * @author Stanislav Nepochatov
 */
public class SHAHash {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SHAHash.class);

    /**
     * Hash given string with SHA-256 algorithm.
     *
     * @param rawPassword plain password;
     * @return hashed password string;
     */
    public static String hashPassword(String rawPassword) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(rawPassword.getBytes());
            byte[] digest = md.digest();
            StringBuffer hexString = new StringBuffer();
            hashedPassword = String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (NoSuchAlgorithmException nsaex) {
            LOGGER.error("There is no SHA-256 implementation!", nsaex);
        }
        return hashedPassword;
    }
}
