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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.ResourceTracker;
import com.nokia.sdt.sourcegen.doms.rss.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;

public class RssModelManipulator implements IRssModelManipulator, IDisposable {

	private IDesignerDataModel dataModel;
	private ResourceTracker tracker;
	private IRssProjectFileManager manager;

	private IRssModelTypeHandler typeHandler;

	private IRssModelResourceHandler resourceHandler;

	private INameGenerator nameGenerator;

	private IIncludeFileLocator includeHandler;

	private ISourceFormatter formatter;

	private IRssModelProxy proxy;
	private IVariableProvider varProvider;

	public RssModelManipulator(ISourceGenProvider provider, ISourceGenSession session, IRssModelProxy proxy) {
		
		Check.checkArg(proxy);
		
		this.manager = (IRssProjectFileManager) provider.getAdapter(IRssProjectFileManager.class);
		this.proxy = proxy;
		this.nameGenerator = provider.getNameGenerator();
		this.includeHandler = provider.getIncludeFileLocator();
		this.formatter = provider.getSourceFormatter();
		this.varProvider = session.getVariableProvider();
		
		this.dataModel = proxy.requestDataModel();
		Check.checkArg(dataModel);
		Check.checkArg(dataModel instanceof DesignerDataModel);
		
		this.tracker = new ResourceTracker();
		
		this.typeHandler = new RssModelTypeHandler(manager, this);
		this.resourceHandler = new RssModelResourceHandler(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getModelProxy()
	 */
	public IRssModelProxy getModelProxy() {
		return proxy;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getIncludeHandler()
	 */
	public IIncludeFileLocator getIncludeHandler() {
		return includeHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getNameGenerator()
	 */
	public INameGenerator getNameGenerator() {
		return nameGenerator;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getSourceFormatter()
	 */
	public ISourceFormatter getSourceFormatter() {
		return formatter;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getResourceTracker()
	 */
	public ResourceTracker getResourceTracker() {
		return tracker;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getFileManager()
	 */
	public IRssProjectFileManager getFileManager() {
		return manager;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getTypeHandler()
	 */
	public IRssModelTypeHandler getTypeHandler() {
		return typeHandler;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getResourceHandler()
	 */
	public IRssModelResourceHandler getResourceHandler() {
		return resourceHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelManipulator#getVariableProvider()
	 */
	public IVariableProvider getVariableProvider() {
		return varProvider;
	}
}
