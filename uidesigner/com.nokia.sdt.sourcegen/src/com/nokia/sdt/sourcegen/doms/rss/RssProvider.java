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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.sourcegen.doms.rss.impl.*;
import com.nokia.sdt.symbian.ISymbianSourceGenSession;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

/**
 * This class provides a means for creating RSS-related data
 * structures.
 * 
 *
 */
public class RssProvider {
	private ISourceGenProvider provider;

	public RssProvider(ISourceGenProvider provider) {
		Check.checkArg(provider);
		this.provider = provider;
	}
	
	/**
	 * Create and establish a proxy for the given root model
	 * in a project.
	 * @param project
	 * @param dataModel
	 * @return
	 */
	public IRssRootModelProxy createRootModelProxy(IRssProjectInfo info, 
			IDesignerDataModelSpecifier dmSpec, String rssBaseName, String rssName) {
		Check.checkState(info.getRootModelProxy() == null);
		info.setRootModelProxy(new RssRootModelProxy(info, dmSpec, rssBaseName, rssName));
		return info.getRootModelProxy();
	}

	/**
	 * Get a model generator for the given proxy.
	 * @param proxy
	 * @param nameGenerator
	 * @param includeHandler
	 * @param sourceFormatter
	 * @return
	 */
	public IRssModelGenerator createModelGenerator(ISourceGenSession session, IRssModelProxy proxy) {
		return new RssModelGenerator(provider, session, proxy); 
	}

	/**
	 * Create a file manager
	 * @return
	 */
	public IRssProjectFileManager createFileManager() {
		return new RssProjectFileManager(provider);
	}
	
	/**
	 * Create a localized string handler
	 * @param format one of FORMAT_LOC, FORMAT_RLS
	 * @see ISymbianSourceGenSession#FORMAT_LOC
	 * @see ISymbianSourceGenSession#FORMAT_RLS
	 */
	public IRssModelStringHandler createStringHandler(int format) {
		if (format == ISymbianSourceGenSession.FORMAT_LOC)
			return new RssModelLocStringHandler();
		else if (format == ISymbianSourceGenSession.FORMAT_RLS)
			return new RssModelRlsStringHandler();
		else
			Check.checkArg(false);
		return null;
	}
}
