/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IActionFilter;

import com.nokia.sdt.uidesigner.ui.utils.Adapters;

public class FilterHelper {
	private IActionFilter filterAdapter;
	
	public void setModel(EObject model) {
		filterAdapter = Adapters.getActionFilter(model);
	}
	
	public IActionFilter getFilter() {
		return filterAdapter;
	}

}
