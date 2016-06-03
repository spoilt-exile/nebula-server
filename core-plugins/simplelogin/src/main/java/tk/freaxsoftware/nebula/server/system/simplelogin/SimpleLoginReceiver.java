/*
 * This file is part of Nebula core sync plugin.
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

package tk.freaxsoftware.nebula.server.system.simplelogin;

import java.util.Map;
import tk.freaxsoftware.extras.bus.GlobalIds;
import tk.freaxsoftware.extras.bus.Receiver;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.nebula.server.core.entities.User;
import tk.freaxsoftware.nebula.server.core.entities.handlers.UserHandler;
import tk.freaxsoftware.nebula.server.core.messages.MessagesClass;
import tk.freaxsoftware.nebula.server.core.utils.SHAHash;

/**
 * Simple login messaging handler.
 * @author Stanislav Nepochatov
 */
public class SimpleLoginReceiver implements Receiver {

    @Override
    public void receive(String messageId, Map<String, Object> arguments, Map<String, Object> result) throws Exception {
        String userLogin = (String) arguments.get(MessagesClass.NEBULA_INTERNAL_LOGIN_ARG_LOGIN);
        String userPassword = (String) arguments.get(MessagesClass.NEBULA_INTERNAL_LOGIN_ARG_PASSWORD);
        UserHandler userHandler = (UserHandler) Handlers.getHandlerByClass(User.class);
        User findedUser = userHandler.getUserByLogin(userLogin);
        if (findedUser != null && findedUser.getPassword().equals(SHAHash.hashPassword(userPassword))) {
            result.put(MessagesClass.NEBULA_INTERNAL_LOGIN_RES_USER, findedUser);
        } else {
            result.put(GlobalIds.GLOBAL_ERROR_MESSAGE, "User does'nt exsists or password incorrect!");
        }
    }
    
}
