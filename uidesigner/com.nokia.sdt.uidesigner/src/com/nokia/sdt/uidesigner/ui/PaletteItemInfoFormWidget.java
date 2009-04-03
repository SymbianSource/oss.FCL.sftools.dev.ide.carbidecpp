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
package com.nokia.sdt.uidesigner.ui;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteListener;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.nokia.sdt.component.adapter.IInfoItems;
import com.nokia.sdt.editor.ICreationTool;
import com.nokia.sdt.uidesigner.derived.IExtendedFlyoutPreferences;
import com.nokia.sdt.uidesigner.derived.IPaletteItemInfoFormWidget;
import com.nokia.cpp.internal.api.utils.core.Check;

public class PaletteItemInfoFormWidget implements IPaletteItemInfoFormWidget {

	private Composite borderComposite;
	private PaletteViewer viewer;
	private PaletteListener paletteListener;
	private FormToolkit toolkit;
	private Form form;
	private Section section;
	private Composite sectionClient;
	private final static int MAX_FIELDS = 2;
	private ToolEntry lastEntry;
	private IExtendedFlyoutPreferences prefs;
	private Composite parent;
	
	class HelpTopicHyperLinkHandler extends HyperlinkAdapter {
		
		private String helpTopic;
		
		public HelpTopicHyperLinkHandler(String helpTopic) {
			this.helpTopic = helpTopic;
		}
		
		public void linkActivated(HyperlinkEvent e) {
			PlatformUI.getWorkbench().getHelpSystem().displayHelp(helpTopic);
		}
	}

	public PaletteItemInfoFormWidget(Composite parent, IExtendedFlyoutPreferences prefs) {
		this.parent = parent;
		this.prefs = prefs;
		borderComposite = new Composite(parent, SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		borderComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		toolkit = new FormToolkit(borderComposite.getDisplay());
		form = toolkit.createForm(borderComposite);
		borderComposite.setBackground(form.getBody().getBackground());
		form.getBody().setLayout(new FillLayout(SWT.VERTICAL));
		boolean expanded = prefs.isInfoWidgetExpanded();
		section = toolkit.createSection(form.getBody(), Section.TWISTIE | (expanded ? Section.EXPANDED : 0));
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				updateUI();
				PaletteItemInfoFormWidget.this.prefs.setInfoWidgetExpanded(e.getState());
			}
		});
		toolkit.createCompositeSeparator(section);
		sectionClient = toolkit.createComposite(section);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		section.setClient(sectionClient);
		
		borderComposite.getParent().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});

	}
	
	private void createSectionClientControls(ToolEntry entry) {
		if ((entry == null) || entry.equals(lastEntry))
			return;

		lastEntry = entry;
		section.setText(getSectionLabel(entry));

		Control[] controls = sectionClient.getChildren();
		for (int i = 0; i < controls.length; i++) {
			controls[i].dispose();
		}
		
		IInfoItems items = getInfoItems(entry);
		int numItems = 0;
		if (items != null)
			numItems = items.getNumItems(MAX_FIELDS);
			
		for (int i = 0; i < MAX_FIELDS; i++) {
			if (i < numItems) {
				String typeLabelStr = items.getItemTypeLabel(i);
				Label typeLabel = toolkit.createLabel(sectionClient, typeLabelStr, SWT.WRAP);
				String helpTopic = items.getItemHelpTopic(i);
				String itemLabelStr = items.getItemLabel(i);
				String tooltipText = typeLabelStr + " " + itemLabelStr; //$NON-NLS-1$
				typeLabel.setToolTipText(tooltipText);
				if ((helpTopic != null) && (helpTopic.length() > 0)) {
					Hyperlink link = toolkit.createHyperlink(sectionClient, itemLabelStr, SWT.WRAP);					
					link.addHyperlinkListener(new HelpTopicHyperLinkHandler(helpTopic));
				}
				else {
					Label itemLabel = toolkit.createLabel(sectionClient, itemLabelStr, SWT.WRAP);
					itemLabel.setToolTipText(tooltipText);
				}
			}
			else {
				toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
				toolkit.createLabel(sectionClient, ""); //$NON-NLS-1$
			}
		}
		
		section.layout(true);
		updateUI();
	}
	
	
	
	private String getSectionLabel(ToolEntry toolEntry) {
		String name = null;
		if (toolEntry instanceof CombinedTemplateCreationEntry) {
			CombinedTemplateCreationEntry entry = (CombinedTemplateCreationEntry) toolEntry;
			name = ((ICreationTool) entry.getTemplate()).getLabel();
		}
		else
			name = toolEntry.getLabel();
		
		return name;
	}
	
	private IInfoItems getInfoItems(ToolEntry toolEntry) {
		IInfoItems items = null;
		if (toolEntry instanceof CombinedTemplateCreationEntry) {
			CombinedTemplateCreationEntry entry = (CombinedTemplateCreationEntry) toolEntry;
			ICreationTool tool = (ICreationTool) entry.getTemplate();
			Object adapter = tool.getAdapter(IInfoItems.class);
			if (adapter instanceof IInfoItems)
				items = (IInfoItems) adapter;
		}
		
		return items;
	}
	
	public void setViewer(PaletteViewer viewer) {
		Check.checkArg(viewer);
		this.viewer = viewer;
		createSectionClientControls(viewer.getActiveTool());
		viewer.addPaletteListener(paletteListener = new PaletteListener() {
			public void activeToolChanged(PaletteViewer palette, ToolEntry entry) {
				createSectionClientControls(entry);
			}
		});
	}
	
	private void dispose() {
		if (toolkit != null)
			toolkit.dispose();
		if ((viewer != null) && (paletteListener != null))
			viewer.removePaletteListener(paletteListener);
	}
	
	public Control getControl() {
		return borderComposite;
	}
	
	public Form getForm() {
		return form;
	}
	
	void updateUI() {
		parent.getParent().layout(true);
	}
	
	public int getHeight() {
		int width = parent.getParent().getSize().x;
		return form.getBody().computeSize(width, SWT.DEFAULT).y + 10;
	}
}
