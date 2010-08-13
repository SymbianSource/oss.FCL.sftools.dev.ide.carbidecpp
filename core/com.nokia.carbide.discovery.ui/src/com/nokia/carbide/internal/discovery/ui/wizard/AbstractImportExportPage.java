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
package com.nokia.carbide.internal.discovery.ui.wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;

/**
 * Abstract superclass of import and export page
 */
abstract class AbstractImportExportPage extends WizardPage {

	protected Text pathText;
	protected Button browseButton;
	protected CheckboxTableViewer viewer;
	protected Button checkAllButton;
	protected Button checkNoneButton;
	protected static final ImageDescriptor FEATURE_IMGDESC = Activator
			.getImageDescriptor("icons/iu_obj.gif"); //$NON-NLS-1$
	protected Image featureImg;

	public AbstractImportExportPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		featureImg = FEATURE_IMGDESC.createImage();

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(composite);
		GridDataFactory.fillDefaults().applyTo(composite);
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Dialog.applyDialogFont(composite);
		setControl(composite);
	}

	protected void createBrowseGroup(Composite parent, String labelText) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
				.grab(true, false).applyTo(composite);

		Label label = new Label(composite, SWT.NONE);
		label.setText(labelText);

		pathText = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).hint(250, SWT.DEFAULT).applyTo(pathText);
		pathText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		});
		browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(Messages.AbstractImportExportPage_BrowseButtonLabel);
		browseButton
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		setButtonLayoutData(browseButton);
		
		new Label(parent, SWT.NONE); // spacer
	}

	protected void createViewerGroup(Composite parent, String labelText) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		GridDataFactory.defaultsFor(label).span(2, 1).applyTo(label);

		viewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		TableViewerColumn featureColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		featureColumn.getColumn().setText(Messages.AbstractImportExportPage_FeatureColTitle);
		featureColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				return featureImg;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof FeatureInfo)
					return ((FeatureInfo) element).getId();
				return null;
			}
		});
		TableViewerColumn versionColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		versionColumn.getColumn().setText(Messages.AbstractImportExportPage_VersionColTitle);
		versionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof FeatureInfo) {
					return ((FeatureInfo) element).getVersion().toString();
				}
				return null;
			}
		});

		viewer.getControl().setLayoutData(
				new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL
						| GridData.GRAB_VERTICAL));
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setHeaderVisible(true);

		Composite buttonComposite = new Composite(parent, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.BEGINNING)
				.applyTo(buttonComposite);
		buttonComposite.setLayout(new GridLayout());
		checkAllButton = new Button(buttonComposite, SWT.PUSH);
		checkAllButton.setText(Messages.AbstractImportExportPage_CheckAllLabel);
		setButtonLayoutData(checkAllButton);
		checkAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setAllChecked(true);
				enableButtons();
				setPageComplete(validatePage());
			}
		});
		checkNoneButton = new Button(buttonComposite, SWT.PUSH);
		checkNoneButton.setText(Messages.AbstractImportExportPage_CheckNoneLabel);
		setButtonLayoutData(checkNoneButton);
		checkNoneButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setAllChecked(false);
				enableButtons();
				setPageComplete(validatePage());
			}
		});
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				enableButtons();
				setPageComplete(validatePage());
			}
		});
	}

	protected abstract boolean validatePage();

	public Collection<FeatureInfo> getSelectedFeatures() {
		Object[] checkedElements = viewer.getCheckedElements();
		List<FeatureInfo> infos = new ArrayList<FeatureInfo>();
		for (Object o : checkedElements) {
			infos.add((FeatureInfo) o);
		}
		return infos;
	}

	protected void packColumns() {
		TableColumn[] columns = viewer.getTable().getColumns();
		for (TableColumn column : columns) {
			column.pack();
		}
	}

	protected void updateViewer() {
		packColumns();
		viewer.setAllChecked(true);
		enableButtons();
		setPageComplete(validatePage());
	}

	private void enableButtons() {
		checkAllButton.setEnabled(viewer.getTable().getItemCount() > viewer.getCheckedElements().length);
		checkNoneButton.setEnabled(viewer.getCheckedElements().length > 0);
	}

	@Override
	public void dispose() {
		super.dispose();
		featureImg.dispose();
	}
}