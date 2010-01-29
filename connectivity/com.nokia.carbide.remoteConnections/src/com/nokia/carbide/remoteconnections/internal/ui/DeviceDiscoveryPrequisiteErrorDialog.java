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
package com.nokia.carbide.remoteconnections.internal.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;

public class DeviceDiscoveryPrequisiteErrorDialog extends Dialog {

	private class AgentItem {
		public String agentName;
		public String agentErrorText;
		public URL agentLocation;
		
		AgentItem(String name, String text, URL location) {
			agentName = name;
			agentErrorText = text;
			agentLocation = location;
			// if location is not null and error text doesn't contain href
			//  then do it here
			if (agentLocation != null && !agentErrorText.contains("href=")) {
				String msg = String.format("%s - For more information go to: <a href=\"%s\">%s</a>",
						agentErrorText, location.toString(), location.toString());
				agentErrorText = msg;
			}
		}
	}
	
	private Collection<AgentItem> agentList = new ArrayList<AgentItem>();
	private boolean isDontBotherMeOn = false;
	private ListViewer agentListViewer;
	private Link errorText;
	private Button dontBotherMeCheckBox;

	/**
	 * @param parentShell
	 */
	public DeviceDiscoveryPrequisiteErrorDialog(Shell parentShell) {
		super(parentShell);
		agentList.clear();
	}

	/**
	 * @param parentShell
	 */
	public DeviceDiscoveryPrequisiteErrorDialog(IShellProvider parentShell) {
		super(parentShell);
		agentList.clear();
	}

	public void addAgentData(String name, String errorText, URL location) {
		agentList.add(new AgentItem(name, errorText, location));
	}

	public boolean isDontBotherMeOn() {
		return isDontBotherMeOn;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// OK button == "Close"
		// no Cancel button
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		container.setLayout(layout);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	
		// Message at top
		Text topMessage = new Text(container, SWT.MULTI | SWT.WRAP);
		topMessage.setText("At least one device discovery agent had load errors that prevent it from discovering connections to devices. Select one to get more information about its error.");
		topMessage.setEditable(false);
		topMessage.setDoubleClickEnabled(false);
		GridData topMsgData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		topMsgData.heightHint = 48;
		topMessage.setLayoutData(topMsgData);
		topMessage.setToolTipText("Select an agent for more information about load errors.");

		// next two panes can be resized with a sash form
		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sashForm.setLayoutData(gridData);
		sashForm.setToolTipText("Slide to adjust pane size above and below to see more text.");
		

		// this pane lists all the agent display names
		agentListViewer = new ListViewer(sashForm, SWT.V_SCROLL | SWT.BORDER);
		agentListViewer.setContentProvider(new ArrayContentProvider());
		agentListViewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				return ((AgentItem)element).agentName;
			}
			
		});
		agentListViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				AgentItem item = (AgentItem) selection.getFirstElement();
				errorText.setText(item.agentErrorText);				
			}
			
		});
		agentListViewer.setInput(agentList);

		// pane to view the information about the selected agent
		errorText = new Link(sashForm, SWT.V_SCROLL | SWT.BORDER | SWT.WRAP);
		errorText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		errorText.setToolTipText("Error message for the selected agent above");
		errorText.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				// Launch an external browser
				String siteText = event.text;
				IWorkbench workbench = PlatformUI.getWorkbench();
				try {
					IWebBrowser browser = workbench.getBrowserSupport().getExternalBrowser();
					browser.openURL(new URL(siteText));
				} catch (Exception e) {
					RemoteConnectionsActivator.logError(e);
				}
			}
			
		});
		
		// add initial weights to the above two panes
		sashForm.setWeights(new int[] {150,200});

		// now the don't bother me check box
		dontBotherMeCheckBox = new Button(container, SWT.CHECK);
		dontBotherMeCheckBox.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		dontBotherMeCheckBox.setText("Don't bother me again.");
		dontBotherMeCheckBox.setToolTipText("Check this to ignore further discovery agent load errors");
		dontBotherMeCheckBox.setSelection(isDontBotherMeOn);
		dontBotherMeCheckBox.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				isDontBotherMeOn = dontBotherMeCheckBox.getSelection();
			}

		});
		
		// now finish by selecting the top most agent in the list
		// and bringing it into view
		Object o = agentListViewer.getElementAt(0);
		if (o != null)
			agentListViewer.setSelection(new StructuredSelection(o));
		
		ISelection selection = agentListViewer.getSelection();
		if (selection != null && !selection.isEmpty()) {
			agentListViewer.reveal(selection);
		}
		
		return container;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(400,400);
	}

	@Override
	protected void okPressed() {
		// TODO Auto-generated method stub
		super.okPressed();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		// set our title to the dialog
		newShell.setText("Device Discovery Load Errors");
	}
}
