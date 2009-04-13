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

package com.nokia.carbide.cpp.debug.kernelaware.testapi;

import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.nokia.carbide.cpp.debug.kernelaware.*;
import com.nokia.carbide.cpp.debug.kernelaware.ui.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;

/**
 * An interface for testing the Symbian OS View
 */
public class SymbianOSViewTester {

	/**
	 * Select a tab by id
	 * @param viewPart IViewPart
	 * @param tabId int
	 */
	public static void selectTab(IViewPart viewPart, int tabId) {
		TabFolder tabFolder = (TabFolder) viewPart.getAdapter(TabFolder.class);
		tabFolder.setSelection(tabId);
		// can't force this to notify so explicitly call notification routine
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		symbianOSView.DoAction_TabSelection(tabFolder.getItem(tabId));
	}
	
	/**
	 * Do refresh
	 * @param viewPart IViewPart
	 */
	public static void refresh(IViewPart viewPart) {
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		symbianOSView.DoAction_Refresh();
	}

	/**
	 * Enable or disable auto refresh
	 * @param viewPart IViewPart
	 * @param enabled boolean
	 */
	public static void setAutoRefreshEnabled(IViewPart viewPart, boolean enabled) {
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		symbianOSView.setAutoRefresh(enabled);
	}
	
	/**
	 * Do collapse all
	 * @param viewPart IViewPart
	 */
	public static void collapseAll(IViewPart viewPart) {
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		symbianOSView.DoAction_CollapseAll();
	}
	
	/**
	 * Debug the process or thread
	 * @param viewPart IViewPart
	 * @param object OSObject
	 * @see OSObjectProcess
	 * @see OSObjectThread
	 */
	public static void debug(IViewPart viewPart, OSObject object) {
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		getCurrentViewer(viewPart).setSelection(new StructuredSelection(object));
		symbianOSView.DoAction_Debug();
	}
	
	/**
	 * Set the auto refresh interval
	 * @param secs int
	 */
	public static void setAutoRefreshInterval(int secs) {
		CWPlugin.getDefault().getPluginPreferences().setValue(
				cwdbg.PreferenceConstants.J_PN_OSViewAutoRefreshInterval, secs);
	}
	
	/**
	 * Get the current refresh count (increments on every refresh)
	 * @param viewPart IViewPart
	 * @return int
	 */
	public static int getRefreshCount(IViewPart viewPart) {
		SymbianOSView symbianOSView = (SymbianOSView) viewPart.getAdapter(SymbianOSView.class);
		return symbianOSView.getRefreshCount();
	}
	
	/**
	 * Get the current viewer (TableViewer or TreeViewer depending on the selected tab)
	 * @param viewPart IViewPart
	 * @return Viewer
	 */
	public static Viewer getCurrentViewer(IViewPart viewPart) {
		return (Viewer) viewPart.getAdapter(Viewer.class);
	}
	
	/**
	 * Get the Text for the filter of the current tab
	 * This can be used with EclipseUtils.enterText(Text, String) to set the filter
	 * @param viewPart IViewPart
	 * @return Text
	 * @see EclipseUtils#enterText(Text, String)
	 */
	public static Text getFilterText(IViewPart viewPart) {
		Viewer viewer = getCurrentViewer(viewPart);
		Object data = viewer.getData("controller");
		if (data instanceof GenericTableTab) {
			return ((GenericTableTab) data).getFilterText();
		}
		else if (data instanceof OverviewTab) {
			return ((OverviewTab) data).getFilterText();
		}
		return null;
	}
}
