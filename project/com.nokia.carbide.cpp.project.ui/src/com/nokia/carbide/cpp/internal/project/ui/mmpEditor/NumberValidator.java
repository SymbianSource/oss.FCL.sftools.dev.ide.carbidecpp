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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler.IValidator;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * Simple number validator
 */
public class NumberValidator implements IValidator {
	
	private final long min;
	private final long max;
	private boolean emptyAllowed;
	private final String message;

	public NumberValidator(long min, long max, boolean emptyAllowed, String failureMessage) {
		this.min = min;
		this.max = max;
		this.emptyAllowed = emptyAllowed;
		this.message = failureMessage;
	}
	
	public void setEmptyAllowed(boolean emptyAllowed) {
		this.emptyAllowed = emptyAllowed;
	}
	
	boolean isEmptyAllowed() {
		return emptyAllowed;
	}

	public IStatus validate(Control control) {
		boolean valid = false;
		String text = ControlHandler.getControlText(control);
		if (TextUtils.strlen(text) == 0) {
			valid = isEmptyAllowed();
		} 
		else {
			try {
				Long value = Long.decode(text);
				valid = value >= min && value <= max;
			} catch (NumberFormatException x) {
			}
		}
		IStatus result = null;
		if (!valid) {
			result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0, message, null);
		}
		return result;
	}

}
