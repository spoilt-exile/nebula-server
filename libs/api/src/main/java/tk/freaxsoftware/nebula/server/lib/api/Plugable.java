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
 * Plugin main interface. Defines all lifecycle methods.
 * @author Stanislav Nepochatov
 */
public interface Plugable {
    
    /**
     * Installs internal resources to plugin related directory, 
     * performs data initialization on this stage. 
     * This method called once when user press <b>Activate</b> button in 
     * the list. Also will be triggered during upgrade.
     */
    void install();
    
    /**
     * Starts plugin internal background tasks if they were. 
     * Route registration and message receivers init should be done here.
     * This methods called every time on system launch, upgrade finish or 
     * on wake up from maintaince state.
     */
    void start();
    
    /**
     * Stops plugin internal tasks, remove listeners and reset routes.
     * This methods called on system shoutdown, upgrade init or on going to 
     * maintaince mode.
     */
    void stop();
    
    /**
     * Uninstalls all internal resources in plugin related directory. 
     * This methods shouldn't affect storage data of plugin. Called when user 
     * press <b>Deactivate</b> button in the list.
     */
    void uninstall();
    
    /**
     * Purges all stored data of plugin. Called during plugin removal from
     * the system.
     */
    void purge();
    
}
