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

/**
 * A repository holding UI messages.
  */
public interface MessagesConstants {	
	public final static String NO_VALID_DEVELOPER_LIBRARY = "Developer library is invalid or not available";
	public static final String DEV_LIB_IS_NOT_JAR_URL = "Developer Library location is not a jar URL";
	public static final String RESOURCE_NOT_FOUND = "Resource Not Found !";
	public static final String NOT_AVAILABLE_HOVER_CSS = "Hover CSS file not found. Existing one will be used.";
	public static final String NOT_VALID_TIME_FORMAT = "Invalid date format found in the interchange file. Dates must be of the form YYYY MM DD:";
	public static final String AUTOMATICALLY_SELECT_LATEST_DEV_LIB_LABEL = "Automatically select latest Developer Library";
	public static final String UNDEFINED_DEV_LIB_LOC_TYPE = "Undefined Developer Library Source";
	public static final String HOVER_HELP_PROGRESS_MESSAGE = ": Interchange file has been indexed";
	public static final String NO_FREE_PORT = "Cannot find any free port on which to start the embedded web server";
	public static final String PREVIOUS_DEVLIB_NOTFOUND = "Unable to find previously selected developer library:";
	public static final String NEW_DEVLIB_USED = "The following developer library will be used instead:";
	public static final String NOT_AVAILABLE_ANY_DEVELOPER_LIBRARY = "No Developer Library is available to activate the hover help plug-in";
	public static final String ERROR_VIEW = "Please see Error View for further information.";
	public static final String DEACTIVATED = "Hover help plug-in is deactivated. Use Hover Help (Carbide.c++) panel in Preferences to activate it again. The cause is: ";
	public static final String HOVER_INDEXING_CANCELLED = "Hover help indexing is cancelled";
	public static final String HINT_PREFERENCE_AUTO_SELECTION = "Based on version, licence and publication date, a developer library will be automatically chosen.";
	public static final String HINT_PREFERENCE_AVAILABLE_DEVLIBS = "Available Developer Libraries";	
	public static final String NOT_AVAILABLE_SDK = "Failed to find any  installed SDK in system.";
	public static final String CANNOT_INIT_POP_UPBROWSER = "Can not create a pop-up browser for hovering";
}
