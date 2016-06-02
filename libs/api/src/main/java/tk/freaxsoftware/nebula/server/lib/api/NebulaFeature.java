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
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation used along wing {@code Plugable} to inform system this plugin provide some system feature.
 * @author Stanislav Nepochatov
 */
@Target(value=ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
@Repeatable(NebulaFeatures.class)
public @interface NebulaFeature {
    
    /**
     * Feature represented by plugin.
     */
    Features feature();
    
    /**
     * Flag display whether plugin could work with other plugins with same feature or not.
     */
    boolean concurent();
}
