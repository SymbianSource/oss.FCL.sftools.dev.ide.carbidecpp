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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideViewOperation;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class ChangeDefFileOperation extends CarbideViewOperation {

	private Control defFileControl, noStrictDefControl;
	private String oldValue;
	private IPath newValue;
	private boolean isFixedDirectory;
	private boolean oldNoStrictDefSetting;
	private boolean newStrictDefSetting;

	public ChangeDefFileOperation(IMMPView view, 
			IEditingContext editingContext,
			Control defFileControl,
			Control noStrictDefControl,
			IPath newValue,
			boolean isFixedDirectory) {
		super(view, "", editingContext, ControlHandler.getHandlerForControl(defFileControl)); //$NON-NLS-1$
		this.defFileControl = defFileControl;
		this.noStrictDefControl = noStrictDefControl;
		this.newValue = newValue;
		this.isFixedDirectory = isFixedDirectory;
		oldValue = view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE);
		
		// force the "no strict def" setting if the file would otherwise be munged
		oldNoStrictDefSetting = view.getFlags().contains(EMMPStatement.NOSTRICTDEF);
		newStrictDefSetting = oldNoStrictDefSetting;
		if (!oldNoStrictDefSetting 
				&& newValue != null
				&& !newValue.removeFileExtension().toOSString().toUpperCase().endsWith("U")) { //$NON-NLS-1$
			newStrictDefSetting = true;
		}
	}

	@Override
	public String getLabel() {
		String label = Messages.getString("ChangeDefFileOperation.undoText"); //$NON-NLS-1$
		return label;
	}

	@Override
	public IStatus doOperation(boolean updateControl) {
		if (newStrictDefSetting)
			((IMMPView)getView()).getFlags().add(EMMPStatement.NOSTRICTDEF);
		else
			((IMMPView)getView()).getFlags().remove(EMMPStatement.NOSTRICTDEF);
		((IMMPView)getView()).setDefFile(newValue, isFixedDirectory);
		if (updateControl) {
			setControls(
					((IMMPView)getView()).getSingleArgumentSettings().get(EMMPStatement.DEFFILE), 
					newStrictDefSetting);
		}
		return Status.OK_STATUS; 
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (oldNoStrictDefSetting)
			((IMMPView)getView()).getFlags().add(EMMPStatement.NOSTRICTDEF);
		else
			((IMMPView)getView()).getFlags().remove(EMMPStatement.NOSTRICTDEF);
		((IMMPView)getView()).getSingleArgumentSettings().put(EMMPStatement.DEFFILE, oldValue);
		setControls(oldValue, oldNoStrictDefSetting);
		return Status.OK_STATUS;
	}
	
	private void setControls(String value, boolean noStrictDef) {
		ControlHandler handler = ControlHandler.getHandlerForControl(defFileControl);
		Check.checkState(handler != null);
		handler.setValue(value);

		handler = ControlHandler.getHandlerForControl(noStrictDefControl);
		Check.checkState(handler != null);
		handler.setValue(noStrictDef);
	}
}
