/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.wizard;

import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract superclass of import and export page
 */
public abstract class AbstractImportExportPage extends WizardPage {

	protected List<IConnection> connections;
	protected Text pathText;
	protected Button browseButton;
	protected CheckboxTableViewer viewer;
	protected static final ImageDescriptor CONNECTION_IMGDESC = 
		RemoteConnectionsActivator.getImageDescriptor("icons/connection.png"); //$NON-NLS-1$
	protected static final Image CONNECTION_IMG = CONNECTION_IMGDESC.createImage();
	private static final String UID = ".uid"; //$NON-NLS-1$

	public AbstractImportExportPage(String pageName) {
		super(pageName);
	}
	
	public void createControl(Composite parent) {
        initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        composite.setFont(parent.getFont());
        composite.setData(UID, "ImportExportPage"); //$NON-NLS-1$

        setControl(composite);
	}

	protected void createBrowseGroup(Composite parent, String labelText) {
	    Composite composite = new Composite(parent, SWT.NONE);
	    GridLayout layout = new GridLayout();
	    layout.numColumns = 3;
	    composite.setLayout(layout);
	    composite.setFont(parent.getFont());
	    composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
	
	    Label label = new Label(composite, SWT.NONE);
	    label.setText(labelText);
	    label.setFont(parent.getFont());
	
	    pathText = new Text(composite, SWT.BORDER);
	    GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
	            | GridData.GRAB_HORIZONTAL);
	    data.widthHint = 250;
	    pathText.setLayoutData(data);
	    pathText.setFont(parent.getFont());
	    pathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage(true));
			}
	    });
	    pathText.setData(UID, "pathText"); //$NON-NLS-1$
	    browseButton = new Button(composite, SWT.PUSH);
	    browseButton.setText(Messages.getString("AbstractImportExportPage.BrowseButtonLabel")); //$NON-NLS-1$
	    browseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
	    browseButton.setFont(parent.getFont());
	    browseButton.setData(UID, "browseButton"); //$NON-NLS-1$
	    setButtonLayoutData(browseButton);
	}

	protected void createViewerGroup(Composite parent, String labelText) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
	
		viewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		TableViewerColumn connectionColumn = new TableViewerColumn(viewer, SWT.CENTER);
		connectionColumn.getColumn().setText(Messages.getString("AbstractImportExportPage.ConnectionColumnLabel")); //$NON-NLS-1$
		connectionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				return CONNECTION_IMG;
			}
	
			@Override
			public String getText(Object element) {
				return ((IConnection) element).getDisplayName();
			}
		});
		TableViewerColumn typeColumn = new TableViewerColumn(viewer, SWT.CENTER);
		typeColumn.getColumn().setText(Messages.getString("AbstractImportExportPage.TypeColumnLabel")); //$NON-NLS-1$
		typeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IConnection) element).getConnectionType().getDisplayName();
			}
		});
		
		viewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setHeaderVisible(true);
		viewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		
//		Composite buttonComposite = new Composite(parent, SWT.NONE);
//		buttonComposite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
//		buttonComposite.setLayout(new GridLayout());
//		Button checkAllButton = new Button(buttonComposite, SWT.PUSH);
//		checkAllButton.setText("Check All");
//		setButtonLayoutData(checkAllButton);
//		Button checkNoneButton = new Button(buttonComposite, SWT.PUSH);
//		checkNoneButton.setText("Uncheck All");
//		setButtonLayoutData(checkNoneButton);
	}
	
	protected abstract boolean validatePage(boolean validateFile);

	public List<IConnection> getSelectedConnections() {
		List<IConnection> selectedConnections = new ArrayList<IConnection>();
		for (IConnection connection : connections) {
			if (viewer.getChecked(connection))
				selectedConnections.add(connection);
		}
		return selectedConnections;
	}

}