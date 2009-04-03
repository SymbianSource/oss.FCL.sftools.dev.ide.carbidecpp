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

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Specialization of ControlHandler that corresponds to a value
 * in IMMPView's single argument settings map.
 * Populates the control from the setting and creates operations
 * to modify the mmp view when the control values is changed.
 */
public class SingleSettingTextHandlerCommands extends ControlHandlerCommands {
	
	public SingleSettingTextHandlerCommands(Control control,
			IEditingContext editingContext,
			IValidator validator
			) {
		super(control, validator);
	}
	
	public SingleSettingTextHandlerCommands(StructuredViewer viewer,
			IEditingContext editingContext,
			EMMPStatement statement,			
			boolean caseSensitive) {
		super(viewer, caseSensitive);
	
	}
	
}
