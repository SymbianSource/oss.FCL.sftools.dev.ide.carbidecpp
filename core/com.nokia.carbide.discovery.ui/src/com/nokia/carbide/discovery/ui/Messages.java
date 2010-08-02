/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.discovery.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.discovery.ui.messages"; //$NON-NLS-1$
	public static String AbstractBrowserPortalPage_BackLabel;
	public static String AbstractBrowserPortalPage_NavigationTitle;
	public static String AbstractBrowserPortalPage_RefreshLabel;
	public static String AbstractBrowserPortalPage_StopLabel;
	public static String AbstractBrowserPortalPageLayer_BadURLError;
	public static String AbstractBrowserPortalPageLayer_ForwardLabel;
	public static String AbstractBrowserPortalPageLayer_InvalidURLError;
	public static String AbstractBrowserPortalPageLayer_OpenLocationLabel;
	public static String AbstractBrowserPortalPageLayer_OpenLocationTitle;
	public static String AbstractBrowserPortalPageLayer_ShowInBrowserLabel;
	public static String AbstractBrowserPortalPageLayer_URLLabel;
	public static String AbstractDiscoveryPortalPageLayer_AdvancedInstallActionLabel;
	public static String AbstractDiscoveryPortalPageLayer_CheckAllActionLabel;
	public static String AbstractDiscoveryPortalPageLayer_CheckedItemsStatusMessage;
	public static String AbstractDiscoveryPortalPageLayer_GatheringExtensionsDesc;
	public static String AbstractDiscoveryPortalPageLayer_GatheringInstallInfoDesc;
	public static String AbstractDiscoveryPortalPageLayer_InstallActionLabel;
	public static String AbstractDiscoveryPortalPageLayer_InstallActionTooltip;
	public static String AbstractDiscoveryPortalPageLayer_RefreshActionLabel;
	public static String AbstractDiscoveryPortalPageLayer_Title;
	public static String AbstractDiscoveryPortalPageLayer_UncheckAllActionLabel;
	public static String AbstractRSSPortalPageLayer_ReturnToFeedLabel;
	public static String AbstractRSSPortalPageLayer_RSSReadError;
	public static String Activator_MissingConfigURLError;
	public static String PortalEditor_BadCommandBarFactoryError;
	public static String PortalEditor_Name;
	public static String PortalEditor_NoLayersError;
	public static String PortalEditor_PageLoadError;
	public static String PortalEditor_PageOpenError;
	public static String PortalEditor_PageRankError;
	public static String SettingsBarCreator_CapabilitiesActionLabel;
	public static String SettingsBarCreator_CodeStyleActionLabel;
	public static String SettingsBarCreator_KeyBindingsActionLabel;
	public static String SettingsBarCreator_ProxiesActionLabel;
	public static String SettingsBarCreator_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
