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
package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo;
import com.nokia.sdt.sourcegen.doms.rss.IRssRootModelProxy;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

/**
 * 
 *
 */
public class RssRootModelProxy extends RssModelProxy implements
		IRssRootModelProxy {
	
	private String rssName;

	public RssRootModelProxy(IRssProjectInfo info, IDesignerDataModelSpecifier dmSpec, String rssBaseName, String rssName) {
		super(info, dmSpec, rssBaseName);
		setNameValue(rssName);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.impl.RssModelProxy#isRootModel()
	 */
	@Override
	public boolean isRootModel() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssRootModelProxy#getNameValue()
	 */
	public String getNameValue() {
		return rssName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssRootModelProxy#setNameValue(java.lang.String)
	 */
	public void setNameValue(String rssName) {
		Check.checkArg(rssName);
		this.rssName = rssName;
	}
	
}
