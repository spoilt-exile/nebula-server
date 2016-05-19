/*
 * This file is part of Nebula Core library.
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
package tk.freaxsoftware.nebula.server.core.messages;

/**
 * System wide messages id class.
 * @author Stanislav Nepochatov
 */
public class MessagesClass {
    
    /**
     * Login message to the auth handling plugin. Arguments contains {@code login} and {@code pass}. 
     * Auth handler must return saved user instance as {@code user} and able to override token expire time 
     * with {@expireTime} as long.
     */
    public static final String NEBULA_INTERNAL_LOGIN_MESSAGE = "Nebula.Internal.Login";
    
}
