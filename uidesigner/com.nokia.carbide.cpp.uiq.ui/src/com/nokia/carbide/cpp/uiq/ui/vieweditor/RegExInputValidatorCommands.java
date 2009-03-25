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
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

public class RegExInputValidatorCommands implements ControlHandlerCommands.IValidator {
	
	public static final String ANYTHING = ".+"; //$NON-NLS-1$
	public static final String WORD = "^[\\w]+$"; //$NON-NLS-1$
	public static final String HEX = "^0[xX][0-9a-fA-F]+$"; //$NON-NLS-1$

	private final String regex;
	private final String message;
	private final boolean emptyAllowed;

	public RegExInputValidatorCommands(String regex, boolean emptyAllowed, String failureMessage) {
		this.regex = regex;
		this.emptyAllowed = emptyAllowed;
		this.message = failureMessage;
	}

	public IStatus validate(Control control) {
		IStatus result = null;
		String text = ControlHandlerCommands.getControlText(control);
		if (TextUtils.strlen(text) == 0) {
			if (!emptyAllowed) {
				result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0, message, null);
			}
		} else {
			if (!text.matches(regex)) {
				result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0, message, null);
			}
		}
		return result;
	}
}
