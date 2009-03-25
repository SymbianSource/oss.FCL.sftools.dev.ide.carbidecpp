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
package com.nokia.cdt.debug.cw.symbian.symbolreader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.freescale.cdt.debug.cw.CWCorbaMgr;
import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.nokia.cdt.debug.cw.symbian.internal.symbolreader.SymbolFile;

import cwdbg.DebuggerEngine;
import cwdbg.PreferenceConstants;
import cwdbg.SymWorldHolder;

public class SymbolReaderManager implements ISymbolReaderManager {

	static private ISymbolReaderManager symbolReaderManager;
	
	static public ISymbolReaderManager getSymbolReaderManager()
	{
		if (symbolReaderManager == null)
		{
			symbolReaderManager = new SymbolReaderManager();
		}
		return symbolReaderManager;
	}

	private void initDEConnection()
	{
		// get the global prefs which control whether or not we automatically
		// launch the DE, and if so, how long to wait for it to initialize and
		// handshake with us
		boolean bLaunchDE = CWPlugin.getDefault().getPluginPreferences()
				.getBoolean(PreferenceConstants.J_PN_AutoLaunchDE);

		// if we don't automatically launch the DE, the timeout value is
		// meaningless
		int timeout = bLaunchDE ? CWPlugin.getDefault().getPluginPreferences()
				.getInt(PreferenceConstants.J_PN_DELaunchTimeout) : 0;
		try {
			CWCorbaMgr.init(null, bLaunchDE, timeout, new NullProgressMonitor());
		} catch (CoreException e) { e.printStackTrace(); }
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolReaderManager#openSymbolFile(java.lang.String)
	 */
	public ISymbolFile openSymbolFile(String symbolFile) {
	
		initDEConnection();
		DebuggerEngine de = CWCorbaMgr.getDebuggerEngineInterface();
		if (de == null)
			return null;
		SymWorldHolder holder = new SymWorldHolder();
		
		de.GetSymWorldForFile(symbolFile, holder);
		
		ISymbolFile result = new SymbolFile(holder.value);
		holder.value.Release();
		
		return result;
	}
}
