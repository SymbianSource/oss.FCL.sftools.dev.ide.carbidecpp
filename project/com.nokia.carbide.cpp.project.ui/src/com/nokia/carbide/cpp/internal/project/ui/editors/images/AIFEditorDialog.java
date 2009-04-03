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
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

/**
 * Main entry for the AIF image editor dialog.
 *
 */
public class AIFEditorDialog extends TitleAreaDialog {

	private AIFEditorContext context;
	private AIFEditorListPage page;

	public AIFEditorDialog(Shell parentShell, AIFEditorContext context) {
		super(parentShell);
		Check.checkArg(context);
		this.context = context;
		this.context.addListener(new AIFEditorContext.Listener() {

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
		this.page = new AIFEditorListPage(composite, SWT.NONE, context);
		this.page.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		setTitle(Messages.getString("AIFEditorDialog.EditAIFTitle")); //$NON-NLS-1$
		setMessage(Messages.getString("AIFEditorDialog.EditAIFDescription")); //$NON-NLS-1$
		
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
			WorkbenchUtils.setHelpContextId(newShell, ImageEditorIds.AIF_EDITOR_DIALOG);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(700, 575);
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
