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
package tk.freaxsoftware.nebula.server.core.entities.handlers;

import java.util.List;
import tk.freaxsoftware.extras.faststorage.storage.AbstractEntityHandler;
import tk.freaxsoftware.nebula.server.core.entities.Group;

/**
 * Group entity handler.
 * @author Stanislav Nepochatov
 */
public class GroupHandler extends AbstractEntityHandler<Group, String> {

    public GroupHandler(String filePath) {
        super(Group.class, Group.DEFINITION, filePath);
    }

    @Override
    public String getNewKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Group> find(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group getNewEntity() {
        return new Group();
    }

    @Override
    public String getType() {
        return Group.TYPE;
    }

    @Override
    public void onStorageCreation() {
        create(new Group("Admin", "Admin default group. Please don't remove it!", null, new String[] {"root"}, null));
        create(new Group("Users", "Test user group", null, new String[] {"user"}, null));
    }
    
}
