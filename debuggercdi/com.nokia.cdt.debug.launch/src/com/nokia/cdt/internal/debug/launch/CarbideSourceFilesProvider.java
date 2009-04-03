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
package com.nokia.cdt.internal.debug.launch;

import java.util.ArrayList;

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.ISourceFilesProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.PlatformObject;

import com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile;
import com.nokia.cdt.debug.cw.symbian.symbolreader.SymbolReaderManager;

public class CarbideSourceFilesProvider extends PlatformObject implements
		ISourceFilesProvider {

	public String[] getSourceFiles(Executable executable,
			IProgressMonitor monitor) {	
		ArrayList<String> sourceFiles = new ArrayList<String>();
		ISymbolFile symFile = SymbolReaderManager.getSymbolReaderManager().openSymbolFile(executable.getPath().toOSString());
		String[] sourceArray = symFile.getSourceFiles();
		symFile.close();
		return sourceArray;
	}

	public int getPriority(Executable executable) {
		return ISourceFilesProvider.HIGH_PRIORITY;
	}

}
