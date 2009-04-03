/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.sdt.datamodel.images.IImagePropertyInfoBase;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * This is an image property pane which uses a selector to let the user
 * edit a property in different, usually independent, ways.  For instance,
 * either as an MBM or a URI.  The last pane selected determines which
 * one sets the property.
 * 
 *
 */
public class SelectableImagePropertyEditorPane extends Composite implements
		IImageEditorDialogMainPane {

	private Composite radioButtonGroup;
	private Composite editorPaneArea;
	private IImageEditorDialogMainPane currentPane;
	private StackLayout stackLayout;
	private List<IImageEditorDialogMainPane> paneList;
	private HashMap<IImageEditorDialogMainPane, Button> paneToButtonMap;
	private ImageEditorDialogBase dialog;



	/**
	 * Create the dialog with the panes that make it up
	 * @param singleMbmOrUriImageEditorDialog
	 * @param container
	 * @param strings
	 * @param imageEditorDialogMainPanes
	 */
	public SelectableImagePropertyEditorPane(
			ImageEditorDialogBase dialog,
			Composite container) {
		super(container, SWT.NONE);
		this.dialog = dialog;
		paneList = new ArrayList<IImageEditorDialogMainPane>();
		paneToButtonMap = new HashMap<IImageEditorDialogMainPane, Button>();
		createUI(this);
	}
	
	

	private void createUI(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Label description = new Label(parent, SWT.WRAP);
		description.setText(Messages.getString("SelectableImagePropertyEditorPane.SelectImageSourceLabel")); //$NON-NLS-1$
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		description.setLayoutData(gridData);
		
		radioButtonGroup = new Composite(parent, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.marginLeft = 6;
		radioButtonGroup.setLayout(rowLayout);
		
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		//gridData.verticalIndent = 3;
		radioButtonGroup.setLayoutData(gridData);
		
		editorPaneArea = new Group(parent, SWT.SHADOW_OUT);
		stackLayout = new StackLayout();
		editorPaneArea.setLayout(stackLayout);

		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		//gridData.verticalIndent = 3;
		editorPaneArea.setLayoutData(gridData);

	}



	/**
	 * @param label
	 * @param singleMbmImagePropertyEditorPane
	 */
	public void addPane(String labelText,
			String tooltipText,
			final IImageEditorDialogMainPane pane) {
		paneList.add(pane);
		if (currentPane == null)
			setCurrentPane(pane);
		
		Button button = new Button(radioButtonGroup, SWT.RADIO);
		button.setToolTipText(tooltipText);
		button.setText(labelText);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setCurrentPane(pane);
			}
		});
		paneToButtonMap.put(pane, button);
	}


	/**
	 * Set the pane which provides the image property's content
	 * @param pane
	 */
	protected void setCurrentPane(IImageEditorDialogMainPane pane) {
		if (pane == currentPane)
			return;
		
		if (currentPane != null)
			currentPane.hide();
		
		for (Map.Entry<IImageEditorDialogMainPane,Button> entry : paneToButtonMap.entrySet()) {
			entry.getValue().setSelection(entry.getKey() == pane);
		}
		stackLayout.topControl = (Control) pane;
		currentPane = pane;
		
		currentPane.show();
		
		dialog.validate();
		
		// ensure we can see the content
		editorPaneArea.pack();
		getShell().layout(true, true);
	}



	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#getImagePropertyInfo()
	 */
	public IImagePropertyInfoBase getImagePropertyInfo() {
		Check.checkContract(currentPane != null);
		return currentPane.getImagePropertyInfo();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#setImagePropertyInfo(com.nokia.sdt.datamodel.images.IImagePropertyInfoBase)
	 */
	public void setImagePropertyInfo(IImagePropertyInfoBase dataBlock) {
		for (IImageEditorDialogMainPane pane : paneList) {
			pane.setImagePropertyInfo(dataBlock);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#validate()
	 */
	public IStatus validate() {
		Check.checkContract(currentPane != null);
		return currentPane.validate();
	}



	/**
	 * @return
	 */
	public Composite getImageDialogPane() {
		return editorPaneArea;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.IImageEditorDialogMainPane#addButtons()
	 */
	public void addButtons(Composite parent) {
		for (IImageEditorDialogMainPane pane : paneList) {
			pane.addButtons(parent);
		}
	}

	public void show() {
		if (currentPane != null)
			currentPane.show();
	}
	
	public void hide() {
		if (currentPane != null)
			currentPane.hide();
	}
}
