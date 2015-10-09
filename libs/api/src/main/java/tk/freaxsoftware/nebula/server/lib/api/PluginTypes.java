/*
 * This file is part of Nebula API library.
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
package tk.freaxsoftware.nebula.server.lib.api;

/**
 * Plugin types definition.
 * @author Stanislav Nepochatov
 */
public enum PluginTypes {
    
    /**
     * Generic plugin which performs some operations on system
     * events and has no UI.
     */
    GENERIC,
    
    /**
     * Plugin with UI resources.
     */
    UI,
    
    /**
     * Plugin which performs connection to other network services.
     */
    CONNECTOR;
}
