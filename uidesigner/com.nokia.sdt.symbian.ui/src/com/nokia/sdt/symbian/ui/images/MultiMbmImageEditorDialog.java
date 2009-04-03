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
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.ProjectImageInfo;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog allows editing a multi-image property, where
 * the file is shared but the image/mask pairs can change.
 * 
 *
 */
public class MultiMbmImageEditorDialog extends ImageEditorDialogBase {

	private EObject object;
	private String propertyPath;

	/**
	 * @param parentShell
	 * @param imageData
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public MultiMbmImageEditorDialog(Shell parentShell,
			IImagePropertyInfoBase imagePropertyInfo, EObject object,
			String propertyPath, ICellEditorValidator cellEditorValidator) {
		super(parentShell, imagePropertyInfo, cellEditorValidator);
		this.object = object;
		this.propertyPath = propertyPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialogBase#createEditorPane(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected IImageEditorDialogMainPane createEditorPane(Composite container) {
		return new MultiMbmImagePropertyEditorPane(this, container,
				(ProjectImageInfo) ModelUtils.getProjectImageInfo(object), 
				object, propertyPath,
				cellEditorValidator);
				
				
				
	}

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.getString("MultiImageEditorDialog.EditMultiImagePropertyTitle")); //$NON-NLS-1$
    }


	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.ui.images.ImageEditorDialogBase#initImage()
	 */
	/*
	@Override
	protected void initImage() {
		((MultiMbmImagePropertyEditorPane) editorPane).initImage();
	}*/
}
