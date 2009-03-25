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


package com.nokia.sdt.component.symbian.childListener;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;

public interface IScriptChildListener {
	
   public void childAdded(WrappedInstance wrappedParentInstance, 
		   						WrappedInstance wrappedChildInstance,
		   	    				ILookAndFeel laf);
	
   public void childRemoved(WrappedInstance wrappedParentInstance, 
								WrappedInstance wrappedChildInstance,
				   				ILookAndFeel laf);
	
   public void childrenReordered(WrappedInstance wrappedParentInstance, 
		   						ILookAndFeel laf);
}