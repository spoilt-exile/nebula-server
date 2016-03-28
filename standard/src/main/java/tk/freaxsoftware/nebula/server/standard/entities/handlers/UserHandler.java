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
package tk.freaxsoftware.nebula.server.standard.entities.handlers;

import java.util.List;
import tk.freaxsoftware.extras.faststorage.storage.AbstractEntityHandler;
import tk.freaxsoftware.nebula.server.standard.entities.User;
import tk.freaxsoftware.nebula.server.standard.utils.SHAHash;

/**
 * User entity handler.
 * @author Stanislav Nepochatov
 */
public class UserHandler extends AbstractEntityHandler<User, String> {

    public UserHandler(String filePath) {
        super(User.class, User.DEFINITION, filePath);
    }

    @Override
    public String getNewKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> find(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getNewEntity() {
        return new User();
    }

    @Override
    public String getType() {
        return User.TYPE;
    }

    @Override
    public void onStorageCreation() {
        appendEntityToStore(new User("root", "Root", "Superadmin account", null, "root@localhost", SHAHash.hashPassword("root"), true, null, new String[] {"Admin"}, null));
        appendEntityToStore(new User("user", "User", "User test account", null, "user@localhost", SHAHash.hashPassword("user"), true, null, new String[] {"Users"}, null));
    }
    
    /**
     * Gets user instance by login.
     * @param login
     * @return 
     */
    public User getUserByLogin(String login) {
        synchronized(entitiesLock) {
            for (User entity: entitiesStore) {
                if (entity.getLogin().equals(login)) {
                    return entity;
                }
            }
        }
        return null;
    }
    
}
