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
 * Nebula system features.
 * @author Stanislav Nepochatov
 */
public enum Features {
    
    /**
     * Login feature: plugin may be used for login operation.
     */
    LOGIN_PROVIDER,
    
    /**
     * Sync feature: plugin may sync data in it's own way.
     */
    SYNC_PROVIDER,
    
    /**
     * Connection feature: plugin may connect to third-party system via network or else.
     */
    CONNECTION_PROVIDER;
}
