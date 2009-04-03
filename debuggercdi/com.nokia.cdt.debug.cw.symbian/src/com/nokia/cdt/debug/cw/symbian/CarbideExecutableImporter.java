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

package com.nokia.cdt.debug.cw.symbian;

import org.eclipse.cdt.debug.core.executables.StandardExecutableImporter;
import org.eclipse.core.runtime.IPath;

public class CarbideExecutableImporter extends StandardExecutableImporter {

	private CarbideExecutablesProvider exeProvider;

	public CarbideExecutableImporter(CarbideExecutablesProvider provider ) {
		exeProvider = provider;
	}

	@Override
	public boolean AllowImport(IPath path) {
		if ( super.AllowImport(path) )
		{
			 return (!exeProvider.executableExists(path));			
		}
		return false;
	}

	public int getPriority(String[] fileNames) {
		return HIGH_PRIORITY;
	}

}
