/*
 * This file is part of Nebula plugin loader library.
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
package tk.freaxsoftware.nebula.server.lib.loader;

/**
 * Plugin status in the system.
 * @author Stanislav Nepochatov
 */
public enum PluginStatus {
    
    //Normal lifecycle states.
    
    /**
     * Status of plugin unknown to the system.
     */
    UNKNOWN,
    
    /**
     * Plugin jar file is present and corresponds to records.
     */
    PRESENT,
    
    /**
     * Plugin installed into the system but not yet started.
     */
    INSTALLED,
    
    /**
     * Plugin started and currently active.
     */
    STARTED,
    
    /**
     * Plugin stopped and inactive.
     */
    STOPED,
    
    /**
     * Plugin was uninstalled by user.
     */
    UNINSTALLED,
    
    /**
     * All plugin's data was purged.
     */
    PURGED,
    
    /**
     * Plugin present in records, but it's jar file is missing.
     */
    NOT_PRESENT,
    
    //Error states
    
    /**
     * System receives error during plugin instancing.
     */
    INIT_ERROR,
    
    /**
     * System receives error during plugin installation.
     */
    INSTALL_ERROR,
    
    /**
     * System receivs error during plugin start.
     */
    START_ERROR,
}
