/*
 * This file is part of Nebula simple login system plugin.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.bus.MessageBus;
import tk.freaxsoftware.nebula.server.core.messages.MessagesClass;
import tk.freaxsoftware.nebula.server.lib.api.Features;
import tk.freaxsoftware.nebula.server.lib.api.NebulaFeature;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPlugin;
import tk.freaxsoftware.nebula.server.lib.api.Plugable;
import tk.freaxsoftware.nebula.server.lib.api.PluginTypes;

/**
 * Simple login plugin entry class.
 * @author Stanislav Nepochatov
 */
@NebulaPlugin(
        id = "Nebula.System.SimpleLogin",
        name = "SimpleLogin",
        type = PluginTypes.GENERIC,
        description = "Provides basic login support.",
        homepage = "http://freaxsoftware.tk/",
        icon = "web/SimpleLogin.ico",
        versionCode = 0,
        versionName = "0.1",
        initLocalization = false
)
@NebulaFeature(feature = Features.LOGIN_PROVIDER, concurent = true)
public class SimpleLoginPlugin implements Plugable {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleLoginPlugin.class);
    
    private SimpleLoginReceiver receiver;

    @Override
    public void install() throws Exception {
        
    }

    @Override
    public void start() throws Exception {
        LOGGER.debug("register simple login handler");
        receiver = new SimpleLoginReceiver();
        MessageBus.addSubscription(MessagesClass.NEBULA_INTERNAL_LOGIN_MESSAGE, receiver);
    }

    @Override
    public void stop() throws Exception {
        MessageBus.removeSubscription(MessagesClass.NEBULA_INTERNAL_LOGIN_MESSAGE, receiver);
        LOGGER.debug("removed simple login handler");
    }

    @Override
    public void uninstall() throws Exception {
        
    }

    @Override
    public void purge() throws Exception {
        
    }
    
}
