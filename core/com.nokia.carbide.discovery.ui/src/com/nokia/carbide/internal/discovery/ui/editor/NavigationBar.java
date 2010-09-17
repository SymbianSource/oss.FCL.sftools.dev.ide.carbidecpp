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

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

class NavigationBar extends RoundedCornerComposite {

	private final class ButtonListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.getSource();
			selectNavButton(button);
		}
	}

	private static final int BUTTON_SPACING = -2;
	private PortalEditor portalEditor;
	private Map<Button, PortalPage> buttonToPageMap;
	private SelectionListener listener;
	private Font buttonFont;
	private Font selectedButtonFont;
	
	NavigationBar(PortalEditor portalEditor, Composite parent) {
		super(parent, portalEditor.getBackgroundParent(), null, null);
		this.portalEditor = portalEditor;
		buttonToPageMap = new LinkedHashMap<Button, PortalPage>();
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		layout.spacing = BUTTON_SPACING;
		layout.marginHeight = BUTTON_SPACING;
		layout.marginWidth = BUTTON_SPACING;
		setLayout(layout);
		listener = new ButtonListener();
		selectedButtonFont = JFaceResources.getHeaderFont();
		FontData[] fontData = selectedButtonFont.getFontData();
		for (FontData fd : fontData) {
			fd.setStyle(SWT.NORMAL);
		}
		FontDescriptor desc = FontDescriptor.createFrom(fontData);
		buttonFont = portalEditor.createFont(desc);
	}

	public void initUI() {
		if (buttonToPageMap.isEmpty())
			return;
		selectNavButton(buttonToPageMap.keySet().iterator().next());
	}

	public void addNavButton(NavigationBar bar, PortalPage page) {
		Button b = new Button(bar, SWT.LEFT | SWT.TOGGLE);
		b.setFont(buttonFont);
		b.setText(page.getTitle());
		b.setImage(portalEditor.createImage(page.getImageDescriptor(), 32, 32));
		b.addSelectionListener(listener);
		buttonToPageMap.put(b, page);
	}


	public void selectNavButton(Button button) {
		for (Button other : buttonToPageMap.keySet()) {
			other.setSelection(false);
			other.setFont(buttonFont);
		}
		button.setSelection(true);
		portalEditor.showPage(buttonToPageMap.get(button));
		button.setFont(selectedButtonFont);
		layout();
	}
}