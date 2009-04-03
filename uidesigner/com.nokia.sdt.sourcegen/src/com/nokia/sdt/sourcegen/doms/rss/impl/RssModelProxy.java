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
import com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IPath;

/**
 * 
 *
 */
public class RssModelProxy implements IRssModelProxy {

	private IRssProjectInfo projectInfo;
	private IDesignerDataModelSpecifier specifier;
	private IDesignerDataModel loadedModel;
	protected String rssBaseName;
	private boolean isCached;
	private String rssFileName;
	
	public RssModelProxy(IRssProjectInfo info, IDesignerDataModelSpecifier dmSpec, String rssBaseName) {
		Check.checkArg(info);
		Check.checkArg(dmSpec);
		
		this.projectInfo = info;
		this.specifier = dmSpec;
		this.loadedModel = null;
		this.isCached = false;
		setRssBaseName(rssBaseName);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RssModelProxy for " + specifier;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public synchronized void dispose() {
		if (loadedModel != null) {
			if (isCached)
				loadedModel.dispose();
			isCached = false;
			loadedModel = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getProjectInfo()
	 */
	public IRssProjectInfo getProjectInfo() {
		return projectInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getModelSpecifier()
	 */
	public IDesignerDataModelSpecifier getModelSpecifier() {
		return specifier;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setModelSpecifier(com.nokia.sdt.workspace.IDesignerDataModelSpecifier)
	 */
	public synchronized void setModelSpecifier(IDesignerDataModelSpecifier dmSpec) {
		if (this.specifier != null && this.specifier.equals(dmSpec))
			return;
		this.specifier = dmSpec;
		if (loadedModel != null) {
			if (isCached)
				loadedModel.dispose();
			isCached = false;
			loadedModel = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#requestDataModel()
	 */
	public synchronized IDesignerDataModel requestDataModel() {
		if (loadedModel == null) {
			loadedModel = specifier.loadNoSourceGen().getModel();
			isCached = true;
		}
		return loadedModel;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setDataModel(com.nokia.sdt.datamodel.IDesignerDataModel)
	 */
	public synchronized void setDataModel(IDesignerDataModel dataModel) {
		if (loadedModel == dataModel)
			return;
		if (loadedModel != null && isCached)
			loadedModel.dispose();
		loadedModel = dataModel;
		isCached = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#isRootModel()
	 */
	public boolean isRootModel() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getDesignLocation()
	 */
	public synchronized IPath getDesignPath() {
		return specifier.getPrimaryResource().getFullPath();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getRssBaseName()
	 */
	public String getRssBaseName() {
		return rssBaseName;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setRssBaseName(java.lang.String)
	 */
	public void setRssBaseName(String baseName) {
		Check.checkArg(baseName);
		this.rssBaseName = baseName;
		
		rssFileName = isRootModel() ?
				rssBaseName + ".rss" : //$NON-NLS-1$
					rssBaseName + ".rssi"; //$NON-NLS-1$

	}
	
	public String getRssFileName() {
		return rssFileName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setRssFileName(java.lang.String)
	 */
	public void setRssFileName(String fileName) {
		Check.checkArg(fileName);
		this.rssFileName = fileName;
	}
	
}
