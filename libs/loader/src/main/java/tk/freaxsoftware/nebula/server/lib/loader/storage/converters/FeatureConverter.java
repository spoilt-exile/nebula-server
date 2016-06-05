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

package tk.freaxsoftware.nebula.server.lib.loader.storage.converters;

import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.nebula.server.lib.api.Features;

/**
 * Feature converter for Fast Storage.
 * @author Stanislav Nepochatov
 */
public class FeatureConverter implements ECSVDefinition.FieldConverter<Features> {

    @Override
    public Features convertFrom(String rawValue) {
        return Features.valueOf(rawValue);
    }

    @Override
    public String convertTo(Features fieldValue) {
        return fieldValue.name();
    }
    
}
