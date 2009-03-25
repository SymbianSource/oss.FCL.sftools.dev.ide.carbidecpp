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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.carbide.cpp.ui.images.PreviewComposite;

import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class AIFImagePropertiesComposite extends Composite {

	private AIFImageFormatControls formatControls;
	private AIFEditorListPage page;
	private PreviewComposite previewComposite;
	private AIFEditorContext editorContext;
	
	/**
	 * @param parent
	 * @param style
	 */
	public AIFImagePropertiesComposite(AIFEditorListPage page, 
			Composite parent, int style) {
		super(parent, style);
		
		this.editorContext = page.getEditorContext();
		this.page = page;

		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 1;
		setLayout(gridLayout_1);

		Group group = new Group(this, SWT.SHADOW_NONE);
		group.setText(Messages.getString("AIFImagePropertiesComposite.SharedImagePropertiesLabel")); //$NON-NLS-1$
		group.setToolTipText(Messages.getString("AIFImagePropertiesComposite.AIFImagePropertiesGroupTooltip")); //$NON-NLS-1$
		GridLayout groupLayout = new GridLayout(2, false);
		groupLayout.horizontalSpacing = 15;

		group.setLayout(groupLayout);
		group.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 1, 1));

		formatControls = new AIFImageFormatControls(page, group, SWT.NONE);
		formatControls.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false, 1, 1));

		previewComposite = new PreviewComposite(this, SWT.NONE);
		previewComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));
		
	}

	/**
	 * Create a path from the given filename
	 * @param filename
	 * @return
	 */
	public IPath createPath(String filename) {
		return page.createPath(filename);
	}

	/**
	 * 
	 */
	public void refresh() {
		if (isDisposed())
			return;
		formatControls.refresh();
		
		IImageSourceReference[] imageSourceReferences = page.getImageSourceReferences();
		if (imageSourceReferences.length == 1) {
			previewComposite.refresh(editorContext.wrapImageSourceReference(
					imageSourceReferences[0], editorContext.getMMPAIFInfo().getImageFormat()));
		} else {
			previewComposite.refresh(null);
		}
	}
}
