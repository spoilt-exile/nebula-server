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

import tk.freaxsoftware.nebula.server.standard.entities.User;

/**
 * User security role holder.
 * @author Stanislav Nepochatov
 */
public final class UserHolder {
    
    private static ThreadLocal<User> user = new ThreadLocal<User>();

    public static User getUser() {
        return user.get();
    }

    public static void setUser(User user) {
        UserHolder.user.set(user);
    }
    
}
