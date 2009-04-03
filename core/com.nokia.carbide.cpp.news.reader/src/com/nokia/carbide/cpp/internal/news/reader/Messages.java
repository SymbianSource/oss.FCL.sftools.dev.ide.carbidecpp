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
*
* Description: 
*
*/

package com.nokia.carbide.cpp.internal.news.reader;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.news.reader.messages"; //$NON-NLS-1$

	public static String NewsPage_GettingStartedSectionTitle;

	public static String NewsPage_NewsContentsSectionTitle;

	public static String NewsPage_NewsFeedsSectionTitle;

	public static String NewsPage_LaunchCtrlLabel;

	public static String NewsPage_LaunchCtrlMessage;

	public static String NewsPage_Title;

	public static String Plugin_LoadFeeds_JobMessage;

	public static String Plugin_LoadFeeds_JobFinishedMessage;

	public static String Plugin_UpdateFeeds_JobMessage;

	public static String Plugin_UpdateFeeds_JobFinishedMessage;

	public static String Preferences_ClearAllLabel;

	public static String Preferences_ClearAllMessage;

	public static String Preferences_LaunchCtrlLabel;

	public static String Preferences_LaunchCtrlMessage;

	public static String Preferences_NewsFeedsGroupLabel;

	public static String Preferences_NewsFeedsTableMessage;

	public static String Preferences_SelectAllLabel;

	public static String Preferences_SelectAllMessage;

	public static String Preferences_Update06HourLabel;

	public static String Preferences_Update12HourLabel;

	public static String Preferences_Update24HourLabel;

	public static String Preferences_UpdateIntervalLabel;

	public static String Preferences_UpdateIntervalMessage;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}	
}
