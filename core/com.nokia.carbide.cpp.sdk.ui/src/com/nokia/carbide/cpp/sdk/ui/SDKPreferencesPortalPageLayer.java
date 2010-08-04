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
package com.nokia.carbide.cpp.sdk.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKPreferencePage;
import com.nokia.carbide.internal.discovery.ui.extension.IActionBar;
import com.nokia.carbide.internal.discovery.ui.extension.IActionUIUpdater;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;

@SuppressWarnings("restriction")
public class SDKPreferencesPortalPageLayer implements IPortalPageLayer {

	protected class SDKActionBar implements IActionBar {
		private IAction[] actions;

		public SDKActionBar(IEditorPart part) {
			actions = makeActions(part);
		}

		public String getTitle() {
			return Messages.getString("SDKPreferencePage.Manage_SDKs_Label");
		}

		public IAction[] getActions() {
			return actions;
		}

		public String[] getHighlightedActionIds() {
			return null;
		}
	}

	private SDKPreferencePage preferencePage;

	public Control createControl(Composite parent, IEditorPart part) {
		preferencePage = new SDKPreferencePage();
		preferencePage.createControl(parent);
		preferencePage.updateForPortalLayer();
		return preferencePage.getControl();
	}

	public void init() {
		preferencePage.init(PlatformUI.getWorkbench());
	}

	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		return new IActionBar[] { new SDKActionBar(part) };
	}

	public void dispose() {
		preferencePage.dispose();
	}

	protected IAction[] makeActions(final IEditorPart part) {
		List<IAction> actions = new ArrayList<IAction>();
		IAction action;

		// Add SDK
		action = new Action(Messages.getString("SDKPreferencePage.Add_Button_Label")) {
			public void run() {
				preferencePage.handleAddButton();
			}
		};
		action.setToolTipText(Messages.getString("SDKPreferencePage.Add_Button_ToolTip"));
		actions.add(action);

		// Delete SDK
		action = new Action(Messages.getString("SDKPreferencePage.Delete_Button_Label")) {
			public void run() {
				preferencePage.handleDeleteButton();
			}
		};
		action.setToolTipText(Messages.getString("SDKPreferencePage.Delete_Button_ToolTip"));
		actions.add(action);

		// SDK Properties
		action = new Action(Messages.getString("SDKPreferencePage.Properties_Button_Label")) {
			public void run() {
				preferencePage.handlePropertiesButton();
			}
		};
		action.setToolTipText(Messages.getString("SDKPreferencePage.Properties_Button_ToolTip"));
		actions.add(action);

		// Rescan SDKs
		action = new Action(Messages.getString("SDKPreferencePage.Rescan_Button_Label")) {
			public void run() {
				preferencePage.handleRescanButton(true);
			}
		};
		action.setToolTipText(Messages.getString("SDKPreferencePage.Rescan_Button_ToolTip"));
		actions.add(action);

		return (IAction[]) actions.toArray(new IAction[actions.size()]);
	}
}
