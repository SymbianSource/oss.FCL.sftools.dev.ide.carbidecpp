/*
 * Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
 * All rights reserved.
 * This component and the accompanying materials are made available
 * under the terms of the License "Eclipse Public License v1.0"
 * which accompanies this distribution, and is available
 * at the URL "http://www.eclipse.org/legal/epl-v10.html".
 *
 * Initial Contributors:
 * Nokia Corporation - initial contribution.
 *
 * Contributors:
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;

/**
 * Constants which are used through the hover pluging.
 */
public interface HoverConstants {
	public static final String DEFAULT_SDK_DOC_HOVER_DIR = "developerlibrary/standard/";
	public static final String DEFAULT_INTERCHANGE_FILE_NAME = "hover_help.xml";
	public static final String[] JAR_HOVER_HELP_ROOT_DIR = { "", "standard/","sdk/" };
	public static final int BROWSER_WIDTH = 400;
	public static final int BROWSER_HEIGHT = 300;
	public static final String SDL_WEB_ADDRESS = "http://developer.symbian.com/main/documentation/sdl/";
	public static final String DeveloperLibraryPluginPrefix = "com.symbian.help.developer_library";
	public static final String HELP_CONTEXTID = Activator.PLUGIN_ID + ".hoverHelpContext";
	public static final String SERVLET_HOVER_CONTEXT = "/hover/";
	public static final String[] DEVLIB_CSS_FILE_NAMES = { "sysdoc.css","symbian.css" };
	public static final String HOVER_CSS_PATH = "_stock/hover/";
	public static final String TEST_CONTENT = "test is OK";
	public static final String PREFERENCE_DEACTIVATE_HOVERING = "DeactivateHovering";
	public static final String PREFERENCE_DEV_LIB_LOC = "developer_library";
	public static final String PREFERENCE_AUTO_DEVLIB_SELECTION = "autoDevLibSelection";

	public enum LICENCE {
		NONE, PUBLIC, PLATFORM;

		public static LICENCE getLicenceType(String type) {

			if (type.toLowerCase().contains("platform")
					|| type.toLowerCase().contains("partner")) {
				return PLATFORM;
			}
			if (type.toLowerCase().contains("public")
					|| type.toLowerCase().contains("publishedAll")) {
				return PUBLIC;
			}
			return NONE;
		}
	};

}
