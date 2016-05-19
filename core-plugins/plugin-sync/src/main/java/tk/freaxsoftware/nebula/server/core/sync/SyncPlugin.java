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

package tk.freaxsoftware.nebula.server.core.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.nebula.server.lib.api.NebulaPlugin;
import tk.freaxsoftware.nebula.server.lib.api.Plugable;
import tk.freaxsoftware.nebula.server.lib.api.PluginTypes;

/**
 * Sync plugin entry class.
 * @author Stanislav Nepochatov
 */
@NebulaPlugin(
        id = "Nebula.Plugin.Sync",
        name = "Sync",
        type = PluginTypes.UI,
        description = "Allows to sync data through Nebula Sync Protocol.",
        homepage = "http://freaxsoftware.tk/",
        icon = "web/sync.ico",
        versionCode = 0,
        versionName = "0.1",
        initLocalization = true
)
public class SyncPlugin implements Plugable {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SyncPlugin.class);

    @Override
    public void install() {

    }

    @Override
    public void start() {
        LOGGER.info("Starting Sync plugin");
    }

    @Override
    public void stop() {

    }

    @Override
    public void uninstall() {

    }

    @Override
    public void purge() {

    }
    
}
