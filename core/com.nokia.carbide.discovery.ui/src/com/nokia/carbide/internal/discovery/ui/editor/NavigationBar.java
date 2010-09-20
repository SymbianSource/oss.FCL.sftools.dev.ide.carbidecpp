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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;

import com.nokia.cpp.internal.api.utils.core.ListenerList;

class NavigationBar extends RoundedCornerComposite {
	private class Button extends CLabel {
		private ListenerList<SelectionListener> listeners;
		private boolean selection;
		private MouseTrackListener mouseTrackListener;
		private MouseListener mouseListener;
		
		public Button(Composite parent) {
			super(parent, SWT.CENTER);
			listeners = new ListenerList<SelectionListener>();
			setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
			addMouseTrackListener(mouseTrackListener = new MouseTrackAdapter() {
				@Override
				public void mouseExit(MouseEvent e) {
					if (!selection)
						setBackgroundColor();
					
				}
				
				@Override
				public void mouseEnter(MouseEvent e) {
					if (!selection)
						setBackground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
			});
			
			addMouseListener(mouseListener = new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					Point size = Button.this.getSize();
					if (new Rectangle(0, 0, size.x, size.y).contains(e.x, e.y))
						setSelection(true);
				}
			});
		}
		
		@Override
		protected void checkSubclass() {
		}

		public void addSelectionListener(SelectionListener listener) {
			listeners.add(listener);
		}

		public void setSelection(boolean selection) {
			if (this.selection != selection) {
				this.selection = selection;
				setBackgroundColor();
				if (selection) {
					for (Control control : getParent().getChildren()) {
						if (control instanceof Button) {
							if (!control.equals(this)) {
								((Button) control).setSelection(false);
							}
						}
					}
					fireSelection();
				}
			}
		}

		private void fireSelection() {
			Event e = new Event();
			e.widget = this;
			e.display = getDisplay();
			for (SelectionListener listener : listeners) {
				listener.widgetSelected(new SelectionEvent(e));
			}
			
		}

		private void setBackgroundColor() {
			setBackground(getDisplay().getSystemColor(selection ? SWT.COLOR_WIDGET_NORMAL_SHADOW : SWT.COLOR_WHITE));
		}
		
		@Override
		public void dispose() {
			removeMouseTrackListener(mouseTrackListener);
			removeMouseListener(mouseListener);
			super.dispose();
		}
	}

	private final class ButtonListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Button button = (Button) e.getSource();
			selectNavButton(button);
		}
	}

	private static final int BUTTON_SPACING = 0;
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
		buttonToPageMap.keySet().iterator().next().setSelection(true);
	}

	public void addNavButton(NavigationBar bar, PortalPage page) {
		Button b = new Button(bar);
		b.setFont(buttonFont);
		b.setText(page.getTitle());
		b.setToolTipText(page.getTooltip());
		b.setImage(portalEditor.createImage(page.getImageDescriptor(), 32, 32));
		b.addSelectionListener(listener);
		buttonToPageMap.put(b, page);
	}


	public void selectNavButton(Button button) {
		portalEditor.showPage(buttonToPageMap.get(button));
		button.setFont(selectedButtonFont);
		layout();
	}
}