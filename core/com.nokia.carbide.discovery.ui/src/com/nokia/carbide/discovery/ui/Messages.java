package com.nokia.carbide.discovery.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.discovery.ui.messages"; //$NON-NLS-1$
	public static String DiscoveryView_AdvancedInstallLabel;
	public static String DiscoveryView_CheckAllLabel;
	public static String DiscoveryView_GatherExtensionsTitle;
	public static String DiscoveryView_GatheringInstallInfoTitle;
	public static String DiscoveryView_InstallLabel;
	public static String DiscoveryView_MissingDirectoryURLError;
	public static String DiscoveryView_RefreshLabel;
	public static String DiscoveryView_StatusLineFmt;
	public static String DiscoveryView_UncheckAllLabel;
	public static String HomePage_Title;
	public static String InstallExtensionsPage_ActionBarTitle;
	public static String InstallExtensionsPage_BuzillaActionName;
	public static String InstallExtensionsPage_LinkBarTitle;
	public static String InstallExtensionsPage_Title;
	public static String PortalEditor_Name;
	public static String PortalEditor_PageLoadError;
	public static String PortalEditor_PageOpenError;
	public static String SupportPage_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
