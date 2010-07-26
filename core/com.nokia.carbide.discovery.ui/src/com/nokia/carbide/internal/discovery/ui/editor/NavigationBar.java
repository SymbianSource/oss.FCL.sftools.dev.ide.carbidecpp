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

import org.eclipse.jface.layout.RowDataFactory;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

class NavigationBar extends RoundedCornerComposite {

	private PortalEditor portalEditor;


	private final class ButtonListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.getSource();
			selectNavButton(button);
		}
	}

	private Map<Button, PortalPage> buttonToPageMap;
	private SelectionListener listener;
	private Font buttonFont;
	private Font selectedButtonFont;
	
	NavigationBar(PortalEditor portalEditor, Composite parent) {
		super(parent, portalEditor.getBackgroundParent(), 
				parent.getDisplay().getSystemColor(SWT.COLOR_BLACK),
				parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		this.portalEditor = portalEditor;
		buttonToPageMap = new LinkedHashMap<Button, PortalPage>();
		RowLayoutFactory.swtDefaults().margins(3, 3).wrap(false).applyTo(this);
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
		Button b = new Button(bar, SWT.TOGGLE | SWT.FLAT);
		b.setFont(buttonFont);
		b.setText(page.getPortalExtension().getText());
		b.setImage(portalEditor.createImage(page.getPortalExtension().getImageDescriptor(), 16, 16));
		b.addSelectionListener(listener);
		RowDataFactory.swtDefaults().applyTo(b);
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
	
	@Override
	public void layout() {
		super.layout();
		pack();
	}
}