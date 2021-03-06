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
package com.nokia.carbide.internal.discovery.ui.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.RowDataFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

import com.nokia.carbide.internal.discovery.ui.extension.IActionBar;

class TaskBar extends RoundedCornerComposite {

	private final class ActionListener implements IHyperlinkListener {
		@Override
		public void linkActivated(HyperlinkEvent e) {
			Hyperlink link = (Hyperlink) e.getSource();
			runAction(link);
		}
		
		@Override
		public void linkEntered(HyperlinkEvent e) {
			Hyperlink link = (Hyperlink) e.getSource();
			link.setUnderlined(true);
		}
		
		@Override
		public void linkExited(HyperlinkEvent e) {
			Hyperlink link = (Hyperlink) e.getSource();
			link.setUnderlined(false);
		}
	}

	private Map<Hyperlink, IAction> linkToActionMap;
	private ActionListener listener;

	public TaskBar(Composite parent, Composite backgroundParent, IActionBar actionBar) {
		super(parent, backgroundParent, 
				parent.getDisplay().getSystemColor(SWT.COLOR_BLACK),
				parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		createTitle(actionBar);
		createActions(actionBar);
		RowLayoutFactory.swtDefaults().type(SWT.VERTICAL).margins(5, 5).extendedMargins(7, 7, 7, 7).fill(true).wrap(false).applyTo(this);
	}

	private void createTitle(IActionBar actionBar) {
		Label l = new Label(this, SWT.LEFT);
		l.setFont(JFaceResources.getBannerFont());
		l.setBackground(l.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		l.setText(actionBar.getTitle());
		RowDataFactory.swtDefaults().applyTo(l);
		l = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		RowDataFactory.swtDefaults().applyTo(l);
	}

	private void createActions(IActionBar actionBar) {
		listener = new ActionListener();
		linkToActionMap = new HashMap<Hyperlink, IAction>();
		for (IAction action : actionBar.getActions()) {
			Hyperlink link = new Hyperlink(this, SWT.NONE);
			link.setText(action.getText());
			String toolTipText = action.getToolTipText();
			if (toolTipText != null)
				link.setToolTipText(toolTipText);
			link.setForeground(link.getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));
			link.setBackground(link.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			String actionId = action.getId();
			String[] highlightedActionIds = actionBar.getHighlightedActionIds();
			if (actionId != null && highlightedActionIds != null) {
				for (String highlightedId : highlightedActionIds) {
					if (highlightedId.equals(actionId)) {
						link.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
						break;
					}
				}
			}
			linkToActionMap.put(link, action);
			link.addHyperlinkListener(listener);
		}
	}
	
	public void runAction(Hyperlink link) {
		IAction action = linkToActionMap.get(link);
		action.run();
	}

	public void updateActionUI(String actionId) {
		for (Entry<Hyperlink, IAction> entry : linkToActionMap.entrySet()) {
			IAction action = entry.getValue();
			if (actionId.equals(action.getId())) {
				Hyperlink link = entry.getKey();
				if (link != null && !link.isDisposed())
					link.setEnabled(entry.getValue().isEnabled());
			}
		}
	}

	public void updateAllActionsUI() {
		for (Entry<Hyperlink, IAction> entry : linkToActionMap.entrySet()) {
			Hyperlink link = entry.getKey();
			if (link != null && !link.isDisposed())
				link.setEnabled(entry.getValue().isEnabled());
		}
	}

}