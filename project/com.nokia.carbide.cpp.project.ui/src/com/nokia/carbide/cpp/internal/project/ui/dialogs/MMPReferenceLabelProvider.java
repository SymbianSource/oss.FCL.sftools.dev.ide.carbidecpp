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
package com.nokia.carbide.cpp.internal.project.ui.dialogs;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;

import org.eclipse.jface.viewers.LabelProvider;

public class MMPReferenceLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof IMMPReference) {
			IMMPReference mmp = (IMMPReference)element;
			return mmp.getPath().toOSString();
		}
		return super.getText(element);
	}
	
}
