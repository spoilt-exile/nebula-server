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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import tk.freaxsoftware.nebula.server.standard.SystemMain;
import tk.freaxsoftware.nebula.server.core.entities.User;

/**
 * JWT tocken service for encryption/decryption of tokens. Singletone.
 * @author Stanislav Nepochatov
 */
public class JWTTokenService {
    
    private static JWTTokenService instance;
    
    /**
     * Security key for JWT signing.
     */
    private final Key jwtKey;
    
    /**
     * Valid hours of JWT token.
     */
    private final Integer validHours;
    
    /**
     * Private controller.
     * @param secret JWT secret;
     * @param validHours valid hours value;
     */
    private JWTTokenService(String secret, Integer validHours) {
        byte[] jwtSecret = DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(secret.getBytes()));
        jwtKey = new SecretKeySpec(jwtSecret, SignatureAlgorithm.HS256.getJcaName());
        this.validHours = validHours;
    }
    
    /**
     * Encrypt token for certain user.
     * @param user given user;
     * @return encrypted token;
     */
    public String encryptToken(User user) {
        return Jwts.builder().setId(user.getLogin()).setExpiration(Date.from(ZonedDateTime.now().plusHours(validHours).toInstant())).signWith(SignatureAlgorithm.HS256, jwtKey).compact();
    }
    
    /**
     * Decrypt token from string to claims instance. May throws unchecked exceptions.
     * @param tokenValue raw token value;
     * @return claims instance;
     */
    public Claims decryptToken(String tokenValue) {
        return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(tokenValue).getBody();
    }
    
    /**
     * Get instance of JWT token service.
     * @return initiated service instance;
     */
    public static JWTTokenService getInstance() {
        if (instance == null) {
            instance = new JWTTokenService(SystemMain.config.getTokenSecret(), SystemMain.config.getTokenValidHours());
        }
        return instance;
    }
}
