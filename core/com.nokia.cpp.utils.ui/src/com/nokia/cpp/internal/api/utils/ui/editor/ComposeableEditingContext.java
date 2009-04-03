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
package com.nokia.cpp.internal.api.utils.ui.editor;

import org.eclipse.core.runtime.IStatus;

public abstract class ComposeableEditingContext implements IEditingContext {
	
	private IEditingContext outerContext;

	protected ComposeableEditingContext(IEditingContext outerContext) {
		this.outerContext = outerContext;
	}
	
	protected abstract IStatus showSelf();

	public IStatus show() {
		IStatus result = null;
		if (outerContext != null) {
			result = outerContext.show();
		}
		if (result == null) {
			result = showSelf();
		}
		return result;
	}
}
