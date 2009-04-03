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
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectInfo;
import com.nokia.sdt.sourcegen.doms.rss.IRssRootModelProxy;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IPath;

/**
 * RSS model proxy for standalone model, as used in tests.
 * 
 *
 */
public class RssStandaloneModelProxy implements IRssRootModelProxy {

	
	private IRssProjectInfo info;
	private IDesignerDataModel dataModel;
	private String rssBaseName;
	private String rssName;
	private IPath designPath;
	private boolean isRoot;
	private String rssFileName;

	/**
	 * @param info
	 * @param dataModel
	 * @param rssBaseName
	 * @param rssName
	 */
	public RssStandaloneModelProxy(IRssProjectInfo info, 
			IDesignerDataModel dataModel, IPath designPath, 
			String rssBaseName, String rssName) {
		Check.checkArg(info);
		Check.checkArg(dataModel);
		Check.checkArg(designPath);
		Check.checkArg(rssBaseName);
		Check.checkArg(rssName);
		this.info = info;
		this.dataModel = dataModel;
		this.designPath = designPath;
		this.rssName = rssName;
		setRssBaseName(rssBaseName);
		isRoot = true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		setDataModel(null);
	}

	public RssStandaloneModelProxy(IRssProjectInfo info, 
			IDesignerDataModel dataModel, IPath designPath, 
			String rssBaseName) {
		Check.checkArg(info);
		Check.checkArg(dataModel);
		Check.checkArg(designPath);
		Check.checkArg(rssBaseName);
		this.info = info;
		this.dataModel = dataModel;
		this.designPath = designPath;
		this.rssBaseName = rssBaseName;
		this.rssName = null;
		isRoot = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getModelSpecifier()
	 */
	public IDesignerDataModelSpecifier getModelSpecifier() {
		return dataModel.getModelSpecifier();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setModelSpecifier(com.nokia.sdt.workspace.IDesignerDataModelSpecifier)
	 */
	public void setModelSpecifier(IDesignerDataModelSpecifier dmSpec) {
		Check.checkState(false);
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
		Check.checkState(isRoot);
		Check.checkArg(rssName);
		this.rssName = rssName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getProjectInfo()
	 */
	public IRssProjectInfo getProjectInfo() {
		return info;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#requestDataModel()
	 */
	public IDesignerDataModel requestDataModel() {
		return dataModel;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setDataModel(com.nokia.sdt.datamodel.IDesignerDataModel)
	 */
	public void setDataModel(IDesignerDataModel dataModel) {
		Check.checkArg(dataModel);
		if (this.dataModel == dataModel)
			return;
		this.dataModel.dispose();
		this.dataModel = dataModel;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#isRootModel()
	 */
	public boolean isRootModel() {
		return isRoot;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getDesignPath()
	 */
	public IPath getDesignPath() {
		return designPath; 
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
		this.rssBaseName = baseName;
		if (isRoot)
			rssFileName = rssBaseName + ".rss"; //$NON-NLS-1$
		else
			rssFileName =rssBaseName + ".rssi"; //$NON-NLS-1$

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#getRssFileName()
	 */
	public String getRssFileName() {
		return rssFileName;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssModelProxy#setRssFileName(java.lang.String)
	 */
	public void setRssFileName(String fileName) {
		this.rssFileName = fileName;
	}
}
