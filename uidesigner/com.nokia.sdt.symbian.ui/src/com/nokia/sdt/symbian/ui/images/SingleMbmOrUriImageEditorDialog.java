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
import com.nokia.sdt.symbian.images.ImagePropertyInfo;
import com.nokia.sdt.symbian.images.ProjectImageInfo;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * This dialog allows editing a single image property, which may be an
 * MBM-style file/image/mask property or a URI.
 * 
 *
 */
public class SingleMbmOrUriImageEditorDialog extends ImageEditorDialogBase {

	private EObject object;
	private String propertyPath;

	/**
	 * @param parentShell
	 * @param imageData
	 * @param object
	 * @param propertyPath
	 * @param cellEditorValidator
	 */
	public SingleMbmOrUriImageEditorDialog(Shell parentShell,
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
		SelectableImagePropertyEditorPane pane = new SelectableImagePropertyEditorPane(this, container);
		ProjectImageInfo projectImageInfo = (ProjectImageInfo) ModelUtils.getProjectImageInfo(object);
		SingleMbmImagePropertyEditorPane mbmPane = new SingleMbmImagePropertyEditorPane(this, 
				pane.getImageDialogPane(),
				projectImageInfo, 
				object, propertyPath);
		SingleURIImagePropertyEditorPane uriPane = new SingleURIImagePropertyEditorPane(this, 
				pane.getImageDialogPane(),
				projectImageInfo, 
				object, propertyPath);
		pane.addPane(
				Messages.getString("SingleMbmOrUriImageEditorDialog.MBM_Entry_Label"),  //$NON-NLS-1$
				Messages.getString("SingleMbmOrUriImageEditorDialog.MbmPaneTooltipText"), //$NON-NLS-1$
				mbmPane); //$NON-NLS-1$
		pane.addPane(Messages.getString("SingleMbmOrUriImageEditorDialog.ExportedImageLabel"), //$NON-NLS-1$
				Messages.getString("SingleMbmOrUriImageEditorDialog.URIPaneTooltipText"), //$NON-NLS-1$
				uriPane); //$NON-NLS-1$
		
		// select the pane based on the content
		IPropertySource ps = (IPropertySource) ModelUtils.readProperty(object, propertyPath, false).result;
		ImagePropertyInfo info = (ImagePropertyInfo) ModelUtils.getImagePropertyInfo(object, ps);
		if (info.uriInfo != null)
			pane.setCurrentPane(uriPane);
		else
			pane.setCurrentPane(mbmPane);
		
		return pane;
	}

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.getString("ImageEditorDialog.SelectImage")); //$NON-NLS-1$
    }

}
