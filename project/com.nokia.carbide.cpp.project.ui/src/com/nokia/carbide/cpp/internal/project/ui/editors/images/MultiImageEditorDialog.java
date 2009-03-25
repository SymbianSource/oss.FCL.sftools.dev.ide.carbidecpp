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

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * Main entry for the image editor dialog.
 *
 */
public class MultiImageEditorDialog extends TitleAreaDialog {

	private MultiImageEditorContext context;
	private MultiImageListPage page;

	public MultiImageEditorDialog(Shell parentShell, MultiImageEditorContext context) {
		super(parentShell);
		Check.checkArg(context);
		this.context = context;
		this.context.addListener(new MultiImageEditorContext.Listener() {

			public void changed(MultiImageEditorContextBase context) {
				updateValidationMessage();
			}
			
		});
	}
	
	/**
	 * 
	 */
	protected void updateValidationMessage() {
		IStatus status = context.getValidationStatus();
		getButton(IDialogConstants.OK_ID).setEnabled(status.getSeverity() != IStatus.ERROR);
		if (status.isOK()) {
			setErrorMessage(null);
			setMessage(null, IMessageProvider.WARNING);
		}
		else if (status.getSeverity() == IStatus.ERROR)
			setErrorMessage(status.getMessage());
		else {
			if (status.getSeverity() == IStatus.WARNING)
				setMessage(status.getMessage(), IMessageProvider.WARNING);
			else
				setMessage(status.getMessage(), IMessageProvider.INFORMATION);
		}
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		this.page = new MultiImageListPage(composite, SWT.NONE, context);
		this.page.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		if (context.getMultiImageSource().isSVGSourceAllowed())
			setTitle(Messages.getString("MultiImageEditorDialog.EditMifTitle")); //$NON-NLS-1$
		else
			setTitle(Messages.getString("MultiImageEditorDialog.EditMbmTitle")); //$NON-NLS-1$
		setMessage(Messages.getString("MultiImageEditorDialog.EditMbmMifDescription")); //$NON-NLS-1$

		return composite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getShellStyle()
	 */
	@Override
	protected int getShellStyle() {
		return super.getShellStyle() | SWT.SHELL_TRIM;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (Platform.isRunning()) {
			newShell.setImage(CarbideUIPlugin.getSharedImages().
					getImage(ICarbideSharedImages.IMG_MBM_MIF_AIF_EDITOR_16_16));
			this.context.setHelpContext(newShell, ImageEditorIds.MULTI_IMAGE_EDITOR_DIALOG);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(900, 600);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	@Override
	public void create() {
		super.create();
		updateValidationMessage();
	}
}
