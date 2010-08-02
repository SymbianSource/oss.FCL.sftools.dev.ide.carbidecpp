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
package com.nokia.carbide.internal.discovery.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;

import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.IActionBar;
import com.nokia.carbide.internal.discovery.ui.extension.IActionUIUpdater;
import com.nokia.carbide.internal.discovery.ui.extension.ICommandBarFactory;
import com.nokia.carbide.internal.discovery.ui.extension.OpenPreferencePageAction;

public class SettingsBarCreator implements ICommandBarFactory {

	private class SettingsBar implements IActionBar {
		@Override
		public String getTitle() {
			return Messages.SettingsBarCreator_Title;
		}

		@Override
		public IAction[] getActions() {
			List<IAction> actions = new ArrayList<IAction>();
			actions.add(new OpenPreferencePageAction(
					Messages.SettingsBarCreator_CapabilitiesActionLabel, 
					"org.eclipse.sdk.capabilities")); //$NON-NLS-1$
			actions.add(new OpenPreferencePageAction(
					Messages.SettingsBarCreator_CodeStyleActionLabel, 
					"org.eclipse.cdt.ui.preferences.CodeFormatterPreferencePage")); //$NON-NLS-1$
			actions.add(new OpenPreferencePageAction(
					Messages.SettingsBarCreator_KeyBindingsActionLabel, 
					"org.eclipse.ui.preferencePages.Keys")); //$NON-NLS-1$
			actions.add(new OpenPreferencePageAction(
					Messages.SettingsBarCreator_ProxiesActionLabel, 
					"org.eclipse.ui.net.NetPreferences")); //$NON-NLS-1$
			return (IAction[]) actions.toArray(new IAction[actions.size()]);
		}

		@Override
		public String[] getHighlightedActionIds() {
			return null;
		} 
	}
	
	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		return new IActionBar[] { new SettingsBar() };
	}
	
}