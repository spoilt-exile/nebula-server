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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Plugin annotation type. System loads those classes which implements 
 * {@code Plugable} interface and marked with this annotation.
 * @author Stanislav Nepochatov
 * @see Plugable
 */
@Target(value=ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface NebulaPlugin {
    
    /**
     * Internal id of plugin in the system.
     * @return internal id;
     */
    String id();
    
    /**
     * Name of plugin which user can see.
     * @return plugin name;
     */
    String name();
    
    /**
     * Type of plugin.
     * @return type;
     * @see PluginTypes
     */
    PluginTypes type();
    
    /**
     * Short description of plugin.
     * @return plugin description;
     */
    String description();
    
    /**
     * Url to plugin homepage.
     * @return plugin home page.
     */
    String homepage();
    
    /**
     * Path to plugin icon in resource folder.
     * @return plugin icon URI;
     */
    String icon();
    
    /**
     * Version iteration counter for correct upgrade.
     * @return version counter;
     */
    int versionCode();
    
    /**
     * Version string name for display.
     * @return version name;
     */
    String versionName();
    
    /**
     * Flag which launces tranlation loading from plugin.
     * @return boolean flag;
     */
    boolean initLocalization() default false;
}
