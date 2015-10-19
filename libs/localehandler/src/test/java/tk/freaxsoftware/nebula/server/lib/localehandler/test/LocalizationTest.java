/*
 * This file is part of LocaleHandler library.
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

package tk.freaxsoftware.nebula.server.lib.localehandler.test;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tk.freaxsoftware.nebula.server.lib.localehandler.LocaleHandler;

/**
 * Localization unit test.
 * @author Stanislav Nepochatov
 */
public class LocalizationTest {
    
    public LocalizationTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        LocaleHandler.loadLocale("en_US", getClass().getClassLoader().getResourceAsStream("test_en_US.properties"));
        LocaleHandler.loadLocale("ru_RU", getClass().getClassLoader().getResourceAsStream("test_ru_RU.properties"));
    }
    
    @Test
    public void simpleTest() {
        assertEquals(LocaleHandler.getAccessFor("ru_RU").get("test.ui.sample.text"), "Простой текст");
    }
    
    public void fallbackTest() {
        assertEquals(LocaleHandler.getAccessFor("ru_RU").get("test.ui.sample.fallback"), "This is fallback");
    }
    
    @After
    public void tearDown() {
    }
    
}
