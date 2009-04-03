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
package com.nokia.sdt.utils.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ThumbnailWithDescriptionComposite extends Composite {

	private Label descriptionTitleLabel;
	private Text descriptionText;
	private Label gridTitleLabel;
	private ThumbnailGridViewer gridViewer;
	public final static String NAME_KEY = ".uid"; //$NON-NLS-1$
	private boolean autoSelect;


	/**
	 * Create the composite.  The SWT.MULTI bit controls the
	 * thumbnail viewer and other style bits go to the composite.
	 * @param parent
	 * @param style
	 */
	public ThumbnailWithDescriptionComposite(Composite parent, int style) {
		super(parent, style & ~SWT.MULTI);
		
		autoSelect = true;

		setLayout(new FormLayout());
		//
		descriptionText = new Text(this, SWT.BORDER | SWT.WRAP);
		descriptionText.setEditable(false);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(100, -20);
		formData_1.top = new FormAttachment(0, 31);
		formData_1.right = new FormAttachment(100, -9);
		formData_1.left = new FormAttachment(100, -150);
		descriptionText.setLayoutData(formData_1);
		descriptionText.setData(NAME_KEY, "descriptionText"); //$NON-NLS-1$
		
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -20);
		formData.top = new FormAttachment(0, 32);
		formData.right = new FormAttachment(descriptionText, -10, SWT.LEFT);
		formData.left = new FormAttachment(0, 5);
		gridViewer = new ThumbnailGridViewer(this, SWT.BORDER | (style & SWT.MULTI));
		gridViewer.getControl().setLayoutData(formData);
		gridViewer.getControl().setData(NAME_KEY, "gridViewer"); //$NON-NLS-1$
		
		
		gridTitleLabel = new Label(this, SWT.NONE);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(gridViewer.getControl(), -5, SWT.TOP);
		formData_2.right = new FormAttachment(descriptionText, -5, SWT.LEFT);
		formData_2.top = new FormAttachment(0, 10);
		formData_2.left = new FormAttachment(0, 5);
		gridTitleLabel.setLayoutData(formData_2);

		descriptionTitleLabel = new Label(this, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(descriptionText, -5, SWT.TOP);
		formData_3.top = new FormAttachment(gridTitleLabel, 0, SWT.TOP);
		formData_3.left = new FormAttachment(descriptionText, 0, SWT.LEFT);
		descriptionTitleLabel.setLayoutData(formData_3);
	}
	
	public void setViewerTitle(String title) {
		gridTitleLabel.setText(title);
	}
	
	public ThumbnailGridViewer getThumbnailViewer() {
		return gridViewer;
	}
	
	public void setDescriptionTitle(String title) {
		descriptionTitleLabel.setText(title);
	}
	
	public Text getDescriptionText() {
		return descriptionText;
	}
	
	public Point computeSize(int wHint, int hHint, boolean changed) {
		if (isDisposed())
			return new Point(0, 0);
		return super.computeSize(wHint, hHint, changed);
	}
	
	/** Set the behavior of the viewer when it is first made visible. 
	 * 
	 * @param autoSelect if true, select the first item, if one exists,
	 * else, do nothing. (default is true)
	 */
	public void setAutoSelectFirst(boolean autoSelect) {
		this.autoSelect = autoSelect;
	}
	
	public void setVisible(boolean visible) {
		if (isDisposed())
			return;
		super.setVisible(visible);
		if (visible && autoSelect) {
			ISelection selection = getThumbnailViewer().getSelection();
			if (selection.isEmpty()) {
				if (getThumbnailViewer().getInput() != null)
				getThumbnailViewer().selectFirst();
			}
			else {
				getThumbnailViewer().setSelection(selection);
			}
				
		}
	}
}
